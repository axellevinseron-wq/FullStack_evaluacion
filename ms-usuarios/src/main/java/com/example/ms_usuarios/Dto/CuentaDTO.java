package com.example.ms_usuarios.Dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CuentaDTO {
    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 4, message = "El username debe tener al menos 4 caracteres")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @NotBlank(message = "El rol es obligatorio")
    private String rol;

    public CuentaDTO() {
    }

    public CuentaDTO(Long clienteId, String username, String password, String rol) {
        this.clienteId = clienteId;
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
