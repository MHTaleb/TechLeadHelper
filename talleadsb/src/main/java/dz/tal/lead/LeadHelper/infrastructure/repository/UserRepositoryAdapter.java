package dz.tal.lead.LeadHelper.infrastructure.repository;

import dz.tal.lead.LeadHelper.domain.model.User;
import dz.tal.lead.LeadHelper.domain.port.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {
    private final UserRepository repository;

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }
}
