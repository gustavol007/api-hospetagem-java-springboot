package com.hospetagem.hotel.model.enums;

import java.text.Normalizer;

public enum SexoPet {
    MASCULINO,
    FEMININO,
    INDEFINIDO;

    public static SexoPet fromString(String value) {
        if (value == null || value.isEmpty()) {
            return INDEFINIDO;
        }
        try {
            // Remove acentos e converte para maiúsculas
            String normalizedValue = Normalizer.normalize(value, Normalizer.Form.NFD)
                                               .replaceAll("\\p{M}", "")
                                               .toUpperCase();
            return SexoPet.valueOf(normalizedValue);
        } catch (IllegalArgumentException e) {
            return INDEFINIDO;
        }
    }
}