package com.example.ms_pagos.Mapper;

import com.example.ms_pagos.Dto.PagoDTO;
import com.example.ms_pagos.Model.Pago;
import org.springframework.stereotype.Component;

@Component
public class PagoMapper {

    public Pago toEntity(PagoDTO dto) {
        if (dto == null) {
            return null;
        }
        Pago pago = new Pago();
        pago.setPedidoId(dto.getPedidoId());
        pago.setMonto(dto.getMonto());
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setEstado(dto.getEstado());
        return pago;
    }

    public PagoDTO toDTO(Pago pago) {
        if (pago == null) {
            return null;
        }
        PagoDTO dto = new PagoDTO();
        dto.setPedidoId(pago.getPedidoId());
        dto.setMonto(pago.getMonto());
        dto.setMetodoPago(pago.getMetodoPago());
        dto.setEstado(pago.getEstado());
        return dto;
    }
}
