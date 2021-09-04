package ru.digitalsoft.document.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.digitalsoft.document.dto.ResultDto;
import ru.digitalsoft.document.dto.SimpleDto;
import ru.digitalsoft.document.dto.auth.FullUserDto;
import ru.digitalsoft.document.dto.common.FilterDto;
import ru.digitalsoft.document.dto.common.LoginDto;
import ru.digitalsoft.document.dto.common.TableResultDto;
import ru.digitalsoft.document.service.auth.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultDto login(@RequestBody LoginDto loginDto, HttpServletRequest request, HttpServletResponse resp) throws SQLException {
        return authService.login(loginDto, request, resp);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResultDto logout(HttpServletRequest request, HttpServletResponse resp) throws SQLException {
        return authService.logout(request, resp);
    }

    @RequestMapping(value = "/getRoleList", method = RequestMethod.POST)
    public TableResultDto getRoleList (@RequestBody FilterDto filterDto) {
        return authService.getRoleList(filterDto);
    }

    /*@RequestMapping(value = "/getUserList", method = RequestMethod.POST)
    public TableResultDto getUserList (@RequestBody FilterDto filterDto) {
        return authService.getUserList(filterDto);
    }*/

    @RequestMapping(value = "/getFullUserInfo", method = RequestMethod.POST)
    public FullUserDto getFullUserInfo (@RequestBody SimpleDto simpleDto) {
        return authService.getFullUserInfo(simpleDto);
    }

    @RequestMapping(value = "/getCurrentUserInfo", method = RequestMethod.GET)
    public FullUserDto getCurrentUserInfo () {
        return authService.getCurrentUserInfo();
    }


    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public FullUserDto updateUserInfo (@RequestBody FullUserDto fullUserDto) {
        return authService.updateUserInfo(fullUserDto);
    }

}
