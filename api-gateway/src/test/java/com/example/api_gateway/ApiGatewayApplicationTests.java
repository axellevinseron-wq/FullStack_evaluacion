package com.example.api_gateway;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Given el contexto del Gateway levantado con el perfil de test,
 * When se consultan las rutas configuradas,
 * Then deben existir las 10 rutas hacia los microservicios de EcoMarket.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class ApiGatewayApplicationTests {

    @Autowired
    private RouteLocator routeLocator;

    @Test
    @DisplayName("El contexto de Spring carga correctamente")
    void contextLoads() {
        assertThat(routeLocator).isNotNull();
    }

    @Test
    @DisplayName("Given el Gateway configurado, When se listan las rutas, Then existen las 10 rutas de microservicios")
    void debeExistirUnaRutaPorCadaMicroservicio() {
        StepVerifier.create(routeLocator.getRoutes().collectList())
                .assertNext(routes -> {
                    assertThat(routes).hasSize(10);
                    assertThat(routes).extracting(route -> route.getId())
                            .containsExactlyInAnyOrder(
                                    "ms-catalogo", "ms-pedidos", "ms-carrito", "ms-usuarios",
                                    "ms-pagos", "ms-notificaciones", "ms-envios", "ms-clientes",
                                    "ms-promociones", "ms-inventario");
                })
                .verifyComplete();
    }
}
