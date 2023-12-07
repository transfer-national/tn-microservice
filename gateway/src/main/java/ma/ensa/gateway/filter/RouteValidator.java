package ma.ensa.gateway.filter;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RouteValidator {


    public final List<String> openEndpoints = List.of(
            "/eureka",
            "/auth"
    );

    public boolean isOpenEndpoint(String path){
        return openEndpoints.contains(path);
    }


    public void validateByRole(String path, char r) {



    }
}
