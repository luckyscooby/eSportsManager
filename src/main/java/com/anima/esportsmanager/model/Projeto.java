package com.anima.esportsmanager.model;

import java.time.LocalDate;

/**
 * Representa a entidade Projeto, que pode ser um campeonato, evento ou treino.
 */
public class Projeto {

    private int id;
    private String nomeProjeto;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataTerminoPrevista;
    private String status;
    private Usuario gerente;

    /**
     * Construtor padrão.
     */
    public Projeto() {
    }

    /**
     * Construtor parametrizado.
     *
     * @param nomeProjeto         O nome do projeto.
     * @param descricao           Uma descrição detalhada do projeto.
     * @param dataInicio          A data de início do projeto.
     * @param dataTerminoPrevista A data de término prevista.
     * @param status              O status atual do projeto (ex: "Planejado").
     * @param gerente             O usuário responsável pelo projeto.
     */
    public Projeto(String nomeProjeto, String descricao, LocalDate dataInicio, LocalDate dataTerminoPrevista, String status, Usuario gerente) {
        this.nomeProjeto = nomeProjeto;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataTerminoPrevista = dataTerminoPrevista;
        this.status = status;
        this.gerente = gerente;
    }
    
    // --- Getters e Setters ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNomeProjeto() { return nomeProjeto; }
    public void setNomeProjeto(String nomeProjeto) { this.nomeProjeto = nomeProjeto; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    public LocalDate getDataTerminoPrevista() { return dataTerminoPrevista; }
    public void setDataTerminoPrevista(LocalDate dataTerminoPrevista) { this.dataTerminoPrevista = dataTerminoPrevista; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Usuario getGerente() { return gerente; }
    public void setGerente(Usuario gerente) { this.gerente = gerente; }
    
    /**
     * Fornece uma representação textual do objeto Projeto.
     *
     * @return Uma string contendo o nome do projeto.
     */
    @Override
    public String toString() {
        return this.nomeProjeto;
    }
}