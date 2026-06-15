package com.example.ms_usuarios.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cuenta")
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del cliente es obligatorio")
    @Column(nullable = false)
    private Long clienteId;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 4, max = 100, message = "El username debe tener entre 4 y 100 caracteres")
    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "El rol es obligatorio")
    @Pattern(regexp = "^(ADMIN|USER|GUEST)$", message = "El rol debe ser ADMIN, USER o GUEST")
    @Column(nullable = false, length = 50)
    private String rol;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Cuenta() {
    }

    public Cuenta(Long id, Long clienteId, String username, String password, String rol,
                  LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.clienteId = clienteId;
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
