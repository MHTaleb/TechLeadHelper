package dz.tal.lead.LeadHelper.auth.application.port.in;

public interface LogoutUserUseCase {
    void logout(String token);
}