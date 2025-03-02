package dz.tal.lead.LeadHelper.auth.application.port.out;


import java.util.Optional;

import dz.tal.lead.LeadHelper.auth.domain.Role;

public interface RoleRepositoryPort {
    Optional<Role> findByName(String roleName);
}