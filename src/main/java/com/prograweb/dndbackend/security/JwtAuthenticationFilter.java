package com.prograweb.dndbackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.prograweb.dndbackend.services.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = extractJwtFromRequest(request);
            
            if (jwt != null && !jwt.isEmpty()) {
                logger.info("Token JWT encontrado, validando con Keycloak...");
                String issuer = authService.getIssuerFromToken(jwt);
                logger.debug("Token issuer='" + issuer + "' for request " + request.getRequestURI());
                if (authService.validateTokenWithKeycloak(jwt)) {
                    logger.info("Token validado exitosamente");
                    String username = authService.getUsernameFromToken(jwt);
                    logger.info("Estableciendo autenticación para el usuario: " + username);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    String uri = request.getRequestURI();
                    if (uri != null && (uri.equals("/api/auth/refresh") || uri.startsWith("/api/auth/refresh"))) {
                        logger.warn("Validación de token fallida pero permitiendo continuar para endpoint de refresh: " + uri);
                    } else {
                        logger.warn("Validación de token fallida con Keycloak para la petición: " + request.getRequestURI());
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("application/json");
                        response.getWriter().write("{\"error\":\"Token inválido o expirado\"}");
                        return;
                    }
                }
            } else {
                logger.debug("No se encontró token JWT en la petición a: " + request.getRequestURI());
            }
        } catch (Exception ex) {
            logger.error("No se pudo establecer la autenticación del usuario en el contexto de seguridad", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
