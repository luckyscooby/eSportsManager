package com.anima.esportsmanager.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa a entidade Equipe (Time).
 */
public class Equipe {
    
    private int id;
    private String nomeEquipe;
    private String jogo; // Ex: "LoL", "CS2", "Valorant"
    private String descricao;
    
    // Uma equipe pode ter vários membros (jogadores, técnicos)
    private List<Usuario> membros;

    public Equipe() {
        this.membros = new ArrayList<>();
    }

    public Equipe(String nomeEquipe, String jogo, String descricao) {
        this.nomeEquipe = nomeEquipe;
        this.jogo = jogo;
        this.descricao = descricao;
        this.membros = new ArrayList<>();
    }

    // --- Getters e Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeEquipe() {
        return nomeEquipe;
    }

    public void setNomeEquipe(String nomeEquipe) {
        this.nomeEquipe = nomeEquipe;
    }

    public String getJogo() {
        return jogo;
    }

    public void setJogo(String jogo) {
        this.jogo = jogo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Usuario> getMembros() {
        return membros;
    }

    public void setMembros(List<Usuario> membros) {
        this.membros = membros;
    }

    @Override
    public String toString() {
        return this.nomeEquipe + " (" + this.jogo + ")";
    }
}