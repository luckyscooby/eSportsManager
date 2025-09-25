package com.anima.esportsmanager.model;

/**
 * Representa a entidade Jogador.
 * Esta classe herda todos os atributos e métodos de {@link Usuario} e adiciona
 * informações específicas de um jogador, como nickname e função no jogo.
 */
public class Jogador extends Usuario {

    private String nickname;
    private String funcao; // Ex: "Duelista", "AWPer", "Suporte", "Jungler"

    /**
     * Construtor padrão que define o cargo como "Jogador".
     */
    public Jogador() {
        super();
        this.setCargo("Jogador");
    }

    // --- Getters e Setters ---

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getFuncao() { return funcao; }
    public void setFuncao(String funcao) { this.funcao = funcao; }
}