package com.hospetagem.hotel.model.enums;

import java.text.Normalizer;

public enum PortePet {
    PEQUENO,
    MEDIO,
    GRANDE,
    INDEFINIDO;

    public static PortePet fromString(String value) {
        if (value == null || value.isEmpty()) {
            return INDEFINIDO;
        }
        try {
            // Remove acentos e converte para maiúsculas
            String normalizedValue = Normalizer.normalize(value, Normalizer.Form.NFD)
                                               .replaceAll("\\p{M}", "")
                                               .toUpperCase();
            return PortePet.valueOf(normalizedValue);
        } catch (IllegalArgumentException e) {
            return INDEFINIDO;
        }
    }
}