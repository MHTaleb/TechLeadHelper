package dz.tal.lead.LeadHelper.auth.infrastructure.adapters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dz.tal.lead.LeadHelper.auth.domain.JwtToken;

import java.util.Optional;

@Repository
public interface JpaJwtTokenRepository extends JpaRepository<JwtToken, Long> {
    Optional<JwtToken> findByToken(String token);
}