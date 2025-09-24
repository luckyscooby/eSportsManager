package com.anima.esportsmanager.model;

import java.time.LocalDate;

/**
 * Classe que representa a entidade Projeto (Campeonatos, Eventos, etc.).
 */
public class Projeto {

    private int id;
    private String nomeProjeto;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataTerminoPrevista;
    private String status; // "Planejado", "Em Andamento", "Concluído", "Cancelado"
    
    // Um projeto tem um gerente responsável (que é um Usuário)
    private Usuario gerente;

    public Projeto() {
    }

    public Projeto(String nomeProjeto, String descricao, LocalDate dataInicio, LocalDate dataTerminoPrevista, String status, Usuario gerente) {
        this.nomeProjeto = nomeProjeto;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataTerminoPrevista = dataTerminoPrevista;
        this.status = status;
        this.gerente = gerente;
    }
    
    // --- Getters e Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeProjeto() {
        return nomeProjeto;
    }

    public void setNomeProjeto(String nomeProjeto) {
        this.nomeProjeto = nomeProjeto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataTerminoPrevista() {
        return dataTerminoPrevista;
    }

    public void setDataTerminoPrevista(LocalDate dataTerminoPrevista) {
        this.dataTerminoPrevista = dataTerminoPrevista;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Usuario getGerente() {
        return gerente;
    }

    public void setGerente(Usuario gerente) {
        this.gerente = gerente;
    }
    
    @Override
    public String toString() {
        return "Projeto{" + "id=" + id + ", nomeProjeto='" + nomeProjeto + '\'' + ", status='" + status + '\'' + '}';
    }
}