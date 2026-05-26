package com.example.ms_pedidos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"spring.datasource.url=jdbc:h2:mem:ms_pedidos_test;MODE=MySQL;DB_CLOSE_DELAY=-1;DATABASE_TO_LOWER=TRUE",
		"spring.datasource.driver-class-name=org.h2.Driver",
		"spring.datasource.username=sa",
		"spring.datasource.password=",
		"spring.flyway.enabled=false",
		"spring.jpa.hibernate.ddl-auto=create-drop"
})
class MsPedidosApplicationTests {

	@Test
	void contextLoads() {
	}

}
