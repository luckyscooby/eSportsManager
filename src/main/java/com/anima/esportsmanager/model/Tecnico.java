package com.anima.esportsmanager.model;

/**
 * Representa a entidade Técnico.
 * Esta classe herda de {@link Usuario} e adiciona informações específicas
 * de um técnico, como sua especialidade.
 */
public class Tecnico extends Usuario {

    private String especialidade; // Ex: "Estrategista", "Analista", "Coach de Performance"

    /**
     * Construtor padrão que define o cargo como "Técnico".
     */
    public Tecnico() {
        super();
        this.setCargo("Técnico");
    }

    // --- Getters e Setters ---

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
}