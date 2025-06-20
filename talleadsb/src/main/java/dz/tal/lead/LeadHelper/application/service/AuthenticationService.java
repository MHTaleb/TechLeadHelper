package dz.tal.lead.LeadHelper.application.service;

import dz.tal.lead.LeadHelper.domain.model.User;
import dz.tal.lead.LeadHelper.domain.port.UserRepositoryPort;
import dz.tal.lead.LeadHelper.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepositoryPort userRepository;

    public String login(String username, String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        User user = (User) auth.getPrincipal();
        return jwtService.generateToken(user);
    }
}
