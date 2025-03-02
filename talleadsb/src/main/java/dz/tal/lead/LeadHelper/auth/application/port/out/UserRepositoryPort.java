package dz.tal.lead.LeadHelper.auth.application.port.out;


import java.util.Optional;

import dz.tal.lead.LeadHelper.auth.domain.User;

public interface UserRepositoryPort {
    User saveUser(User user);
    Optional<User> findByUsername(String username);
    // You can add findById, delete, etc. as needed
}