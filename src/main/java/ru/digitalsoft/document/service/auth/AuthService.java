package ru.digitalsoft.document.service.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalsoft.document.dao.entity.abilities.PermissionEntity;
import ru.digitalsoft.document.dao.entity.abilities.RoleEntity;
import ru.digitalsoft.document.dao.entity.auth.SessionEntity;
import ru.digitalsoft.document.dao.entity.auth.UserEntity;
import ru.digitalsoft.document.dao.repositories.abilities.AbilitiesRepository;
import ru.digitalsoft.document.dao.repositories.abilities.PermissionRepository;
import ru.digitalsoft.document.dao.repositories.abilities.RoleRepository;
import ru.digitalsoft.document.dao.repositories.auth.SessionRepository;
import ru.digitalsoft.document.dao.repositories.auth.UserRepository;
import ru.digitalsoft.document.dto.ResultDto;
import ru.digitalsoft.document.dto.SimpleDto;
import ru.digitalsoft.document.dto.abilities.PermissionDto;
import ru.digitalsoft.document.dto.abilities.RoleDto;
import ru.digitalsoft.document.dto.auth.FullUserDto;
import ru.digitalsoft.document.dto.auth.UserDto;
import ru.digitalsoft.document.dto.ResultDto;
import ru.digitalsoft.document.dto.SimpleDto;
import ru.digitalsoft.document.dto.common.FilterDto;
import ru.digitalsoft.document.dto.common.LoginDto;
import ru.digitalsoft.document.dto.common.TableResultDto;
import ru.digitalsoft.document.dto.exceptions.UserException;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthService{

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    public final static String USER_SESSION_COOKIE = "session";

    public final static String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";

    public final static String IS_EXPIRED = "IS_EXPIRED";

    public final static String SALT = "$2a$10$G4Wejbvh0XoK6zmNvRsUR.";

    private static final int ONE_YEAR = 31536000;

    private UserRepository userRepository;

    private SessionRepository sessionRepository;

    private PermissionRepository permissionRepository;

    private RoleRepository roleRepository;

    private AbilitiesRepository abilitiesRepository;

    @Autowired
    public AuthService(UserRepository userRepository, SessionRepository sessionRepository, PermissionRepository permissionRepository,
                       RoleRepository roleRepository, AbilitiesRepository abilitiesRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.abilitiesRepository = abilitiesRepository;
    }


    public UserEntity loadBySessionId (String sessionId) {
        return userRepository.getBySessionKey(sessionId);
    }

    public List<PermissionDto> getUserPermissionList (long userId) {
        List<PermissionEntity> permissionEntityList = permissionRepository.getAllByUserId(userId);

        return permissionEntityList.stream()
                .map(PermissionDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ResultDto login (LoginDto loginDto, HttpServletRequest req, HttpServletResponse resp) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        UserEntity userEntity = userRepository.getByEmail(email);

        if (userEntity == null) {
            throw new RuntimeException("Не верный email или пароль");
        }

        String hashpw = BCrypt.hashpw(password, SALT);

        if (!hashpw.equals(userEntity.getPassword())) {
            throw new RuntimeException("Не верный email или пароль");
        }

        ResultDto result = new ResultDto(false);

        UUID sessionKey = UUID.randomUUID();

        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setSessionKey(sessionKey.toString());
        sessionEntity.setUserId(userEntity.getId());

        sessionEntity = sessionRepository.save(sessionEntity);

        List<PermissionDto> permissionDtoList = getUserPermissionList(userEntity.getId());

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userEntity, null, permissionDtoList);
//        usernamePasswordAuthenticationToken.setAuthenticated(true);

        req.getSession().setAttribute(SPRING_SECURITY_CONTEXT, new SecurityContextImpl(usernamePasswordAuthenticationToken));
        req.getSession().setAttribute(IS_EXPIRED, false);
        Cookie cookie = new Cookie(USER_SESSION_COOKIE, sessionKey.toString());
        cookie.setMaxAge(ONE_YEAR);
        cookie.setPath("/");
        resp.addCookie(cookie);

        result.setSuccess(true);
        return result;
    }

    public ResultDto logout (HttpServletRequest req, HttpServletResponse resp) throws SQLException {

        String sessionKey = (String)req.getSession().getAttribute(USER_SESSION_COOKIE);

//        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        req.getSession().setAttribute(SPRING_SECURITY_CONTEXT, null);

        Cookie cookie = new Cookie(USER_SESSION_COOKIE, sessionKey);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        resp.addCookie(cookie);

        return new ResultDto(true);
    }

    public TableResultDto getRoleList (FilterDto filterDto) {
        Pageable page = PageRequest.of(filterDto.getPage() - 1, 25, Sort.by("id").ascending());

        List<RoleEntity> roleEntityList = roleRepository.findAll(page);

        List<RoleDto> roleDtoList = roleEntityList.stream().map(RoleDto::new)
                .collect(Collectors.toList());

        return new TableResultDto(true, roleDtoList, roleDtoList.size(), 1);
    }

    public TableResultDto getUserList (FilterDto filterDto) {
        Pageable page = PageRequest.of(filterDto.getPage() - 1, 25, Sort.by("id").ascending());

        List<UserEntity> authorListEnties = userRepository.findAll(page);

        List<UserDto> specialtyDtoList = authorListEnties.stream().map(UserDto::new)
                .collect(Collectors.toList());

        return new TableResultDto(true, specialtyDtoList, specialtyDtoList.size(), 1);
    }

    public FullUserDto getFullUserInfo (SimpleDto dto) {

        UserEntity userEntity = userRepository.getById(dto.getId());

        List<RoleEntity> roleEntityList = roleRepository.getAllByUserId(userEntity.getId());

        List<RoleDto> roleDtoList = roleEntityList.stream().map(RoleDto::new).collect(Collectors.toList());

        FullUserDto fullUserDto = new FullUserDto(userEntity);
        fullUserDto.setRoleDtoList(roleDtoList);

        return fullUserDto;
    }

    private void mapDtoToEntity (FullUserDto fullUserDto, UserEntity entity) {
        entity.setEmail(fullUserDto.getEmail());
        entity.setName(fullUserDto.getName());
        entity.setSurname(fullUserDto.getSurname());
        entity.setMiddlename(fullUserDto.getMiddlename());
        entity.setRegisterDate(fullUserDto.getRegisterDate());
        entity.setSalt(SALT);

        if (fullUserDto.isChangePassword()) {
            entity.setPassword(BCrypt.hashpw(fullUserDto.getPassword(), SALT));
        }
    }

    private FullUserDto createUserInfo(FullUserDto fullUserDto) {
        UserEntity entity = new UserEntity();
        mapDtoToEntity(fullUserDto, entity);

        entity = userRepository.save(entity);
        fullUserDto.setId(entity.getId());

        return fullUserDto;
    }

    private void updateRolesAndPermissions(UserEntity entity, List<Long> roleDtoIdList) {

        abilitiesRepository.clearRolesAndPermissionFromUser(entity);

        /*List<Long> roleIdList =  roleDtoList.stream().map((roleDto -> {
            return roleDto.getId();
        })).collect(Collectors.toList());*/

        abilitiesRepository.createRolesAndPermissionsToUser(entity, roleDtoIdList);
    }

    @Transactional
    public FullUserDto updateUserInfo (FullUserDto fullUserDto) {

        if (fullUserDto.getId() <= 0) {
            return createUserInfo (fullUserDto);
        }

        UserEntity entity = userRepository.getById(fullUserDto.getId());

        if (entity == null) {
            LOGGER.error("Запись не найдена в БД.");
            throw new UserException("Запись не найдена в БД.");
        }

        mapDtoToEntity(fullUserDto, entity);

        entity = userRepository.save(entity);

        updateRolesAndPermissions(entity, fullUserDto.getRoleDtoIdList());

        return fullUserDto;
    }

    public FullUserDto getCurrentUserInfo () {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = (UserEntity)authentication.getPrincipal();

        List<PermissionDto> permissionDtoList = (List<PermissionDto>)authentication.getAuthorities();

        return new FullUserDto(userEntity, permissionDtoList);
    }
}
