package dz.tal.lead.LeadHelper.auth.application.port.in;

public interface LoginUserUseCase {
    String login(String username, String rawPassword);
}