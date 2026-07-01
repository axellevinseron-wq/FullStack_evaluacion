package com.example.ms_usuarios.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuracion de documentacion tecnica con Swagger / OpenAPI.
 * UI disponible en /swagger-ui.html - JSON en /v3/api-docs
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI usuariosOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EcoMarket - MS Usuarios API")
                        .description("Gestion de autenticacion y cuentas de usuario de EcoMarket.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo EcoMarket")
                                .email("equipo.ecomarket@duocuc.cl")));
    }
}
