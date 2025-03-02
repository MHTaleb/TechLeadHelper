package dz.tal.lead.LeadHelper.auth.infrastructure.adapters.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import dz.tal.lead.LeadHelper.auth.application.port.out.UserRepositoryPort;
import dz.tal.lead.LeadHelper.auth.domain.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JpaUserRepositoryAdapter implements UserRepositoryPort {

    private final JpaUserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}