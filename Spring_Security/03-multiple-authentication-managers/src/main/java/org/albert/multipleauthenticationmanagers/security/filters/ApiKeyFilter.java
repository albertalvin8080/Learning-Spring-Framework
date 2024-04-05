package org.albert.multipleauthenticationmanagers.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.albert.multipleauthenticationmanagers.security.authentication.ApiKeyAuthentication;
import org.albert.multipleauthenticationmanagers.security.managers.ApiKeyAuthenticationManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {

    @Value("${my.very.secret.key}")
    private String key;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final ApiKeyAuthenticationManager manager = new ApiKeyAuthenticationManager(this.key);
        final String requestKey = request.getHeader("x-api-key");

        if(requestKey == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final Authentication a = manager.authenticate(new ApiKeyAuthentication(false, requestKey));
            if (a.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        filterChain.doFilter(request, response);
    }
}
