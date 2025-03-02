package dz.tal.lead.LeadHelper.auth.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dz.tal.lead.LeadHelper.auth.application.port.in.LoginUserUseCase;
import dz.tal.lead.LeadHelper.auth.application.port.out.JwtTokenRepositoryPort;
import dz.tal.lead.LeadHelper.auth.application.port.out.UserRepositoryPort;
import dz.tal.lead.LeadHelper.auth.domain.JwtToken;
import dz.tal.lead.LeadHelper.auth.domain.User;
import dz.tal.lead.LeadHelper.auth.infrastructure.utils.JwtUtil;

@Service
@RequiredArgsConstructor
public class LoginUserService implements LoginUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final JwtTokenRepositoryPort jwtTokenRepositoryPort;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager; 
    private final JwtUtil jwtUtil; // We'll define/implement this in the infrastructure or a shared util

    @Override
    public String login(String username, String rawPassword) {
        // Confirm user existence
        User user = userRepositoryPort.findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        // Optionally, do a manual password check if not using authenticationManager:
        /*
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }
        */

        // Or we can use the Spring Security AuthenticationManager to check credentials
        var authToken = new UsernamePasswordAuthenticationToken(username, rawPassword);
        var authentication = authenticationManager.authenticate(authToken);
        if (!authentication.isAuthenticated()) {
            throw new BadCredentialsException("Invalid credentials");
        }

        // Generate a JWT
        String token = jwtUtil.generateToken(username);

        // Save the token in the DB
        JwtToken jwtToken = JwtToken.builder()
                .user(user)
                .token(token)
                .build();
        jwtTokenRepositoryPort.saveToken(jwtToken);

        return token;
    }
}