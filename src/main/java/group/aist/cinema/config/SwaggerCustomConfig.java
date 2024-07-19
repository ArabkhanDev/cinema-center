package group.aist.cinema.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerCustomConfig {

    private SecurityScheme createOpenIdConnectScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .flows(new OAuthFlows()
                        .authorizationCode(new OAuthFlow()
                        .authorizationUrl("https://auth.md7.info/auth/realms/team1/protocol/openid-connect/auth")
                        .tokenUrl("https://auth.md7.info/auth/realms/team1/protocol/openid-connect/token")
                        .scopes(new Scopes()
                                .addString("offline_access", "Offline access")
                                .addString("email", "Email address")))
                        .password(new OAuthFlow()
                                .tokenUrl("https://auth.md7.info/auth/realms/team1/protocol/openid-connect/token")
                                .scopes(new Scopes()
                                        .addString("offline_access", "Offline access")
                                        .addString("email", "Email address"))));
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("Keycloak"))
                .components(new Components()
                        .addSecuritySchemes("Keycloak", createOpenIdConnectScheme()))
                .info(new Info().title("Cinema API")
                        .description("Cinema Application API Documentation.")
                        .version("1.0")
                        .contact(new Contact().name("Aist")
                                .email("aist@gmail.com"))
                        .license(new License().name("License of API")
                                .url("API license URL")));
    }
}