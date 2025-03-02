package dz.tal.lead.LeadHelper.auth.application.port.in;
 

import java.util.List;

import dz.tal.lead.LeadHelper.auth.domain.User;

public interface RegisterUserUseCase {
    User registerUser(String username, 
                      String rawPassword, 
                      Long bpartnerId, 
                      List<String> roleNames);
}