package com.example.ms_clientes.Config;

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
    public OpenAPI clientesOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EcoMarket - MS Clientes API")
                        .description("Gestion de perfiles de clientes de EcoMarket.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo EcoMarket")
                                .email("equipo.ecomarket@duocuc.cl")));
    }
}
