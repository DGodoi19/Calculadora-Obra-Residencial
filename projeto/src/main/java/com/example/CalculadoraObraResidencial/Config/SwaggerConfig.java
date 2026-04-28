package com.example.CalculadoraObraResidencial.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Calculadora de Materiais para Obra Residencial")
                        .description("API para cálculo de volume de concreto (vigas baldrames) e " +
                                "quantidade de tijolos em obras residenciais, modelando a planta " +
                                "como um grafo G=(V,A) onde arestas são paredes e vértices são pilares.")
                        .version("1.0.0"))
                .tags(List.of(
                        new Tag().name("Paredes").description("Gerenciamento de paredes (arestas do grafo)"),
                        new Tag().name("Cálculos").description("Cálculo de materiais por projeto")
                ));
    }
}
