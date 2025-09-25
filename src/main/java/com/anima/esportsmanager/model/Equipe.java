package com.anima.esportsmanager.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa a entidade Equipe (Time de eSports).
 * Contém informações sobre o time e uma lista de seus membros.
 */
public class Equipe {
    
    private int id;
    private String nomeEquipe;
    private String jogo; // Ex: "LoL", "CS2", "Valorant"
    private String descricao;
    private List<Usuario> membros;

    /**
     * Construtor padrão. Inicializa a lista de membros.
     */
    public Equipe() {
        this.membros = new ArrayList<>();
    }

    /**
     * Construtor parametrizado.
     *
     * @param nomeEquipe O nome da equipe.
     * @param jogo       O jogo em que a equipe compete.
     * @param descricao  Uma breve descrição da equipe.
     */
    public Equipe(String nomeEquipe, String jogo, String descricao) {
        this.nomeEquipe = nomeEquipe;
        this.jogo = jogo;
        this.descricao = descricao;
        this.membros = new ArrayList<>();
    }

    // --- Getters e Setters ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNomeEquipe() { return nomeEquipe; }
    public void setNomeEquipe(String nomeEquipe) { this.nomeEquipe = nomeEquipe; }
    public String getJogo() { return jogo; }
    public void setJogo(String jogo) { this.jogo = jogo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public List<Usuario> getMembros() { return membros; }
    public void setMembros(List<Usuario> membros) { this.membros = membros; }

    /**
     * Fornece uma representação textual do objeto Equipe.
     *
     * @return Uma string contendo o nome da equipe e seu jogo.
     */
    @Override
    public String toString() {
        return this.nomeEquipe + " (" + this.jogo + ")";
    }
}