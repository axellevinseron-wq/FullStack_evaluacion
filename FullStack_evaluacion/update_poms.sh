#!/bin/bash

# Script para actualizar todos los POMs a usar el parent POM

BASE_DIR="/c/Users/axell/OneDrive/Desktop/FullStack_evaluacion/PROYECTO/PROYECTO"

# Array con microservicios y rutas relativas desde la raíz
declare -A MODULES=(
    ["ms-pedidos"]="ms-pedidos"
    ["ms-envios"]="ms-envios"
    ["ms-usuarios"]="ms-usuarios"
    ["ms-pagos"]="ms-pagos"
    ["ms-catalogo"]="ms-catalogo"
    ["ms_carrito"]="ms_carrito"
    ["ms_notificaciones"]="ms_notificaciones"
)

# Archivos en subdirectorios
NESTED_MODULES=(
    "ms-clientes/ms-clientes"
    "ms-promociones/ms-promociones"
    "ms-inventario(3)/ms-inventario/ms-inventario"
)

echo "Script created for manual POM updates"
