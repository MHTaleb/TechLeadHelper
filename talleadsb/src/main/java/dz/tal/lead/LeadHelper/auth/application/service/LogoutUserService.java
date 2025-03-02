package dz.tal.lead.LeadHelper.auth.application.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import dz.tal.lead.LeadHelper.auth.application.port.in.LogoutUserUseCase;
import dz.tal.lead.LeadHelper.auth.application.port.out.JwtTokenRepositoryPort;

@Service
@RequiredArgsConstructor
public class LogoutUserService implements LogoutUserUseCase {

    private final JwtTokenRepositoryPort jwtTokenRepositoryPort;

    @Override
    public void logout(String token) {
        jwtTokenRepositoryPort.findByToken(token).ifPresent(jwtToken -> {
            jwtToken.setRevoked(true);
            jwtTokenRepositoryPort.saveToken(jwtToken);
        });
    }
}