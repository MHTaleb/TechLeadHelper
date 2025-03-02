package dz.tal.lead.LeadHelper.auth.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dz.tal.lead.LeadHelper.auth.application.port.in.RegisterUserUseCase;
import dz.tal.lead.LeadHelper.auth.application.port.out.RoleRepositoryPort;
import dz.tal.lead.LeadHelper.auth.application.port.out.UserRepositoryPort;
import dz.tal.lead.LeadHelper.auth.domain.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final RoleRepositoryPort roleRepositoryPort;
    private final PasswordEncoder passwordEncoder; // We will inject from infrastructure

    @Override
    public User registerUser(String username, String rawPassword, Long bpartnerId, List<String> roleNames) {
        // Check if username already exists
        userRepositoryPort.findByUsername(username).ifPresent(u -> {
            throw new RuntimeException("Username already exists: " + username);
        });

        // Encode the password
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Fetch the Role entities
        var roles = roleNames.stream()
                .map(roleName -> roleRepositoryPort.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName))
                )
                .toList();

        // Create and save the new user
        User user = User.builder()
                .username(username)
                .password(encodedPassword)
                .bpartnerId(bpartnerId)
                .build();

        roles.forEach(user.getRoles()::add);
        return userRepositoryPort.saveUser(user);
    }
}