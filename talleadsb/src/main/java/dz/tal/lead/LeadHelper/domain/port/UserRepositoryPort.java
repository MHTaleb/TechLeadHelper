package dz.tal.lead.LeadHelper.domain.port;

import dz.tal.lead.LeadHelper.domain.model.User;
import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findByUsername(String username);
}
