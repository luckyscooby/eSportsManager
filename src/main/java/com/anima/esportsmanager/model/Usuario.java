package com.anima.esportsmanager.model;

/**
 * Classe que representa a entidade Usuário.
 * Esta é uma superclasse (classe pai) que contém os atributos e métodos
 * comuns a todos os tipos de usuários do sistema.
 * Os requisitos de encapsulamento (atributos privados e getters/setters) são aplicados aqui.
 */
public class Usuario {

    // Atributos privados para garantir o encapsulamento [cite: 56]
    private int id;
    private String nomeCompleto;
    private String cpf;
    private String email;
    private String cargo; // Ex: "Administrador", "Técnico", "Jogador" [cite: 80]
    private String login;
    private String senha;

    /**
     * Construtor padrão (vazio).
     * Necessário para criar objetos sem inicializar todos os atributos de uma vez.
     */
    public Usuario() {
    }

    /**
     * Construtor parametrizado para facilitar a criação de objetos Usuário com dados iniciais.
     * @param nomeCompleto Nome completo do usuário.
     * @param cpf CPF do usuário.
     * @param email E-mail do usuário.
     * @param cargo Cargo/Perfil do usuário no sistema.
     * @param login Login de acesso.
     * @param senha Senha de acesso.
     */
    public Usuario(String nomeCompleto, String cpf, String email, String cargo, String login, String senha) {
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.cargo = cargo;
        this.login = login;
        this.senha = senha;
    }

    // --- Métodos Getters e Setters ---
    // Fornecem acesso controlado aos atributos privados da classe[cite: 57].

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Sobrescrevendo o método toString() para fornecer uma representação textual
     * útil do objeto Usuário, o que é ótimo para logs e depuração.
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nomeCompleto='" + nomeCompleto + '\'' +
                ", cargo='" + cargo + '\'' +
                '}';
    }
}