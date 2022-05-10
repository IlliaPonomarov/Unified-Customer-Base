package com.example.demo.configuration;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class EmployeeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean hasStaffRole = false;
        boolean hasAdminRole = false;


        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_USER")){
                hasStaffRole = true;
                break;
            }
            else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")){
                hasAdminRole = true;
                break;
            }
        }

        if (hasStaffRole)
            redirectStrategy.sendRedirect(request, response, "/user");
        else if (hasAdminRole)
            redirectStrategy.sendRedirect(request, response, "/admin");
        else
            throw new IllegalStateException();


    }

}
