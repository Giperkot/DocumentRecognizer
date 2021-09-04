package ru.digitalsoft.document.config.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.filter.GenericFilterBean;
import ru.digitalsoft.document.dao.entity.auth.UserEntity;
import ru.digitalsoft.document.dto.abilities.PermissionDto;
import ru.digitalsoft.document.service.auth.AuthService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class AuthFilter extends GenericFilterBean {

    private AuthService authService;

    public AuthFilter(AuthService authService) {
        this.authService = authService;
    }

    private void getUserBySessionId (HttpSession session, HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        String sessionKey = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (AuthService.USER_SESSION_COOKIE.equals(cookie.getName())) {
                    sessionKey = cookie.getValue();
                    break;
                }
            }
        }

        if (sessionKey == null) {
            session.setAttribute(AuthService.IS_EXPIRED, true);
            return;
        }

        UserEntity userEntity = authService.loadBySessionId(sessionKey);

        if (userEntity == null) {
            session.setAttribute(AuthService.IS_EXPIRED, true);
            return;
        }

        List<PermissionDto> permissionList = authService.getUserPermissionList(userEntity.getId());

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userEntity, null, permissionList);
        session.setAttribute(AuthService.SPRING_SECURITY_CONTEXT, new SecurityContextImpl(usernamePasswordAuthenticationToken));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpSession session = ((HttpServletRequestWrapper) servletRequest).getSession();

        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute(AuthService.SPRING_SECURITY_CONTEXT);

        if (securityContext == null) {
            Boolean isExpired = (Boolean)session.getAttribute(AuthService.IS_EXPIRED);
            String sessionId = session.getId();
//            35D87B969A7F664146979B05AA408C9F
            if (isExpired == null || !isExpired) {
                getUserBySessionId(session, (HttpServletRequest)servletRequest);
            }
        }

//        String sessionId = ((HttpServletRequestWrapper) servletRequest).getSession().getId();
//        servletRequest.getSession();
        int i = 0;
        filterChain.doFilter(servletRequest, servletResponse);

    }
}
