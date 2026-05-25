package com.example.ms_clientes.Dto;



import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DireccionDTO {
    @NotBlank(message = "La calle es obligatoria")
    private String calle;

    @NotBlank(message = "La ciudad es obligatoria")
    private String ciudad;
}