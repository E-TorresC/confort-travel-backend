package com.conforttravel.api.shared.util;

import org.springframework.stereotype.Component;

@Component
public class GeneradorCodigoUtil {

    public String generarSiguiente(String prefijo, String ultimoCodigo) {
        if (ultimoCodigo == null || ultimoCodigo.isBlank() || !ultimoCodigo.startsWith(prefijo)) {
            return prefijo + "001";
        }
        try {
            // Extrae la parte numérica después del prefijo (ej: de "CLI001" saca "001")
            String numeroStr = ultimoCodigo.substring(prefijo.length());
            int siguienteNumero = Integer.parseInt(numeroStr) + 1;

            // Retorna el prefijo + número con ceros a la izquierda (mínimo 3 dígitos)
            return prefijo + String.format("%03d", siguienteNumero);
        } catch (NumberFormatException e) {
            // Fallback de seguridad por si el formato antiguo estaba corrupto
            return prefijo + "001";
        }
    }
}