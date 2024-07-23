package group.aist.cinema.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakSecurityUtil {

    @Value("${keycloak.clientId}")
    private String adminClientId;

    @Value("${keycloak.clientSecret}")
    private String adminClientSecret;

    @Value("${keycloak.host}")
    private String authServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Bean
    public Keycloak keycloakInstance(){

        return KeycloakBuilder.builder()
                .serverUrl(authServerUrl)
                .realm(realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(adminClientId)
                .clientSecret(adminClientSecret)
                .build();
    }

}