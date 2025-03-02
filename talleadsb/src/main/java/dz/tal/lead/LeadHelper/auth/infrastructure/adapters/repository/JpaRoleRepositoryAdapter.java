package dz.tal.lead.LeadHelper.auth.infrastructure.adapters.repository;

import java.util.Optional;

import org.springframework.stereotype.Service;

import dz.tal.lead.LeadHelper.auth.application.port.out.RoleRepositoryPort;
import dz.tal.lead.LeadHelper.auth.domain.Role;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JpaRoleRepositoryAdapter implements RoleRepositoryPort {

    private JpaRoleRepository roleRepository;

    @Override
    public Optional<Role> findByName(String roleName) {
        return this.roleRepository.findByName(roleName);
    }
    
}
