package com.anima.esportsmanager.model;

/**
 * Representa a entidade Técnico.
 * Esta classe herda de Usuário e adiciona informações específicas
 * de um técnico, como sua especialidade.
 */
public class Tecnico extends Usuario {

    private String especialidade; // Ex: "Estrategista", "Analista", "Coach de Performance"

    /**
     * Construtor padrão.
     */
    public Tecnico() {
        // Chama o construtor da superclasse (Usuario)
        super();
        // Define o cargo padrão para esta classe
        this.setCargo("Técnico");
    }

    // --- Getters e Setters ---

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    /**
     * Polimorfismo (Sobrescrita de Método).
     * Representação textual específica para o objeto Técnico.
     */
    @Override
    public String toString() {
        return super.toString().replace("}", "") +
               ", especialidade='" + especialidade + '\'' +
               '}';
    }
}