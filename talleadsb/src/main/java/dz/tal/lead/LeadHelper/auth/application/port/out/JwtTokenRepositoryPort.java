package dz.tal.lead.LeadHelper.auth.application.port.out;


import java.util.Optional;

import dz.tal.lead.LeadHelper.auth.domain.JwtToken;

public interface JwtTokenRepositoryPort {
    JwtToken saveToken(JwtToken jwtToken);
    Optional<JwtToken> findByToken(String token);
}