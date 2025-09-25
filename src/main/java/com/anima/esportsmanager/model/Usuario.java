package com.anima.esportsmanager.model;

/**
 * Representa a entidade Usuário.
 * Esta é uma superclasse que contém os atributos e métodos comuns
 * a todos os tipos de usuários do sistema, como administradores, técnicos e jogadores.
 */
public class Usuario {

    private int id;
    private String nomeCompleto;
    private String cpf;
    private String email;
    private String cargo;
    private String login;
    private String senha;

    /**
     * Construtor padrão.
     */
    public Usuario() {
    }

    /**
     * Construtor parametrizado para criar um objeto Usuário com dados iniciais.
     *
     * @param nomeCompleto O nome completo do usuário.
     * @param cpf          O CPF do usuário.
     * @param email        O e-mail do usuário.
     * @param cargo        O cargo/perfil do usuário no sistema.
     * @param login        O login de acesso.
     * @param senha        A senha de acesso.
     */
    public Usuario(String nomeCompleto, String cpf, String email, String cargo, String login, String senha) {
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.cargo = cargo;
        this.login = login;
        this.senha = senha;
    }

    // --- Getters e Setters ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    /**
     * Fornece uma representação textual do objeto Usuário, útil para exibição
     * em logs, depuração ou componentes de UI que chamam este método implicitamente.
     *
     * @return Uma string contendo o nome completo do usuário, formatada para exibição.
     */
    @Override
    public String toString() {
        return this.nomeCompleto;
    }
}