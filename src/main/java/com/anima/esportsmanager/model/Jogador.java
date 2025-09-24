package com.anima.esportsmanager.model;

/**
 * Representa a entidade Jogador.
 * Esta classe herda todos os atributos e métodos de Usuário e adiciona
 * informações específicas de um jogador, como nickname e função no jogo.
 */
public class Jogador extends Usuario {

    private String nickname;
    private String funcao; // Ex: "Duelista", "AWPer", "Suporte", "Jungler"

    /**
     * Construtor padrão.
     */
    public Jogador() {
        // Chama o construtor da superclasse (Usuario) implicitamente
        super();
        // Define o cargo padrão para esta classe
        this.setCargo("Jogador");
    }

    // --- Getters e Setters para os atributos específicos ---

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    /**
     * Demonstração de Polimorfismo (Sobrescrita de Método).
     * Fornece uma representação textual específica para o objeto Jogador.
     */
    @Override
    public String toString() {
        // Aproveita o toString() da classe pai e adiciona as novas informações
        return super.toString().replace("}", "") + // Remove o "}" do final do toString de Usuario
               ", nickname='" + nickname + '\'' +
               ", funcao='" + funcao + '\'' +
               '}';
    }
}