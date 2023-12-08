package ma.ensa.gateway.config;

import lombok.Data;
import ma.ensa.gateway.dto.Role;
import ma.ensa.gateway.exceptions.UnauthorizedAccess;
import org.springframework.boot.autoconfigure.security.servlet.RequestMatcherProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "authorized-paths")
@Data
public class AuthorizedPathConfig {

    private Map<String, Role> paths;



    public void validateByRole(String path, Role role) {

        boolean isAuthorized = true;

        if(!isAuthorized){
            throw new UnauthorizedAccess();
        }

    }

}
