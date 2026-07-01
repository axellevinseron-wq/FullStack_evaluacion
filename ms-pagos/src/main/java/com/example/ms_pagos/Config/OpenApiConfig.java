package com.example.ms_pagos.Config;

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
    public OpenAPI pagosOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EcoMarket - MS Pagos API")
                        .description("Procesamiento de transacciones y pagos de EcoMarket.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo EcoMarket")
                                .email("equipo.ecomarket@duocuc.cl")));
    }
}
