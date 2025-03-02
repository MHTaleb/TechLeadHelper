package dz.tal.lead.LeadHelper.auth.infrastructure.adapters.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import dz.tal.lead.LeadHelper.auth.application.port.out.JwtTokenRepositoryPort;
import dz.tal.lead.LeadHelper.auth.domain.JwtToken;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JpaJwtTokenRepositoryAdapter implements JwtTokenRepositoryPort {

    private final JpaJwtTokenRepository jwtTokenRepository;

    @Override
    public JwtToken saveToken(JwtToken jwtToken) {
        return jwtTokenRepository.save(jwtToken);
    }

    @Override
    public Optional<JwtToken> findByToken(String token) {
        return jwtTokenRepository.findByToken(token);
    }
}