package com.example.ms_usuarios.Mapper;

import com.example.ms_usuarios.Dto.CuentaDTO;
import com.example.ms_usuarios.Model.Cuenta;
import org.springframework.stereotype.Component;

@Component
public class CuentaMapper {

    public Cuenta toEntity(CuentaDTO dto) {
        if (dto == null) {
            return null;
        }
        Cuenta cuenta = new Cuenta();
        cuenta.setClienteId(dto.getClienteId());
        cuenta.setUsername(dto.getUsername());
        cuenta.setPassword(dto.getPassword());
        cuenta.setRol(dto.getRol());
        return cuenta;
    }

    public CuentaDTO toDTO(Cuenta cuenta) {
        if (cuenta == null) {
            return null;
        }
        CuentaDTO dto = new CuentaDTO();
        dto.setClienteId(cuenta.getClienteId());
        dto.setUsername(cuenta.getUsername());
        dto.setPassword(cuenta.getPassword());
        dto.setRol(cuenta.getRol());
        return dto;
    }
}
