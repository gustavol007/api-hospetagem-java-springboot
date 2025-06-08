package com.hospetagem.hotel.model.enums;

public enum TipoServico {
    AREA_BRINCADEIRAS("Área de Brincadeiras"),
    PISCINA_CAES("Piscina para Cães"),
    TREINAMENTO("Treinamento"),
    CUIDADOS_PERSONALIZADOS("Cuidados Personalizados"),
    SERVICO_TRANSPORTE("Serviço de Transporte"),
    PASSEIO_AR_LIVRE("Passeio ao Ar Livre"),
    AREA_DESCANSO("Área de Descanso"),
    BANHO_TOSA("Banho e Tosa"),
    HOSPEDAGEM_NOTURNA("Hospedagem Noturna");

    private final String descricao;

    TipoServico(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}