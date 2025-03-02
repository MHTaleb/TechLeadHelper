package dz.tal.lead.LeadHelper.auth.infrastructure.adapters.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import dz.tal.lead.LeadHelper.auth.application.port.out.JwtTokenRepositoryPort;
import dz.tal.lead.LeadHelper.auth.domain.JwtToken;
import dz.tal.lead.LeadHelper.auth.infrastructure.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final JwtTokenRepositoryPort jwtTokenRepositoryPort;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = authHeader.substring(7); // Remove "Bearer "

            // 1) Validate the token's signature & expiration
            if (!jwtUtil.isTokenValid(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            // 2) Check if token is revoked in DB
            Optional<JwtToken> dbTokenOpt = jwtTokenRepositoryPort.findByToken(token);
            if (dbTokenOpt.isEmpty() || dbTokenOpt.get().getRevoked() || dbTokenOpt.get().getExpired()) {
                filterChain.doFilter(request, response);
                return;
            }

            // 3) Extract username, load user details, set authentication
            String username = jwtUtil.extractUsername(token);
            var userDetails = userDetailsService.loadUserByUsername(username);

            // Create an Authentication object
            var authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
            );

            // Set it in SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authToken);
        } catch (Exception ex) {
            // Optionally log or handle the exception
        }
        filterChain.doFilter(request, response);
    }
}