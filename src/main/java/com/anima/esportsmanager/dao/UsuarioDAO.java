package com.anima.esportsmanager.dao;

import com.anima.esportsmanager.model.Usuario;
import com.anima.esportsmanager.util.ConexaoDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) para a entidade {@link Usuario}.
 * Esta classe encapsula toda a lógica de acesso ao banco de dados
 * para as operações de CRUD (Create, Read, Update, Delete) de usuários.
 */
public class UsuarioDAO {

    /**
     * Insere um novo usuário no banco de dados.
     *
     * @param usuario O objeto {@link Usuario} a ser persistido.
     */
    public void inserir(Usuario usuario) {
        String sql = "INSERT INTO Usuarios (nome_completo, cpf, email, cargo, login, senha) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNomeCompleto());
            pstmt.setString(2, usuario.getCpf());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, usuario.getCargo());
            pstmt.setString(5, usuario.getLogin());
            pstmt.setString(6, usuario.getSenha());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
        }
    }

    /**
     * Busca e retorna uma lista de todos os usuários cadastrados no banco de dados.
     *
     * @return Uma {@link List} de objetos {@link Usuario}.
     */
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios";

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNomeCompleto(rs.getString("nome_completo"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setEmail(rs.getString("email"));
                usuario.setCargo(rs.getString("cargo"));
                usuario.setLogin(rs.getString("login"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
        }
        return usuarios;
    }

    /**
     * Atualiza os dados de um usuário existente no banco de dados.
     * A senha não é atualizada neste método.
     *
     * @param usuario O objeto {@link Usuario} com os dados atualizados.
     */
    public void atualizar(Usuario usuario) {
        String sql = "UPDATE Usuarios SET nome_completo = ?, cpf = ?, email = ?, cargo = ?, login = ? WHERE id = ?";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNomeCompleto());
            pstmt.setString(2, usuario.getCpf());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, usuario.getCargo());
            pstmt.setString(5, usuario.getLogin());
            pstmt.setInt(6, usuario.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    /**
     * Deleta um usuário do banco de dados com base no seu ID.
     *
     * @param id O ID do usuário a ser deletado.
     */
    public void deletar(int id) {
        String sql = "DELETE FROM Usuarios WHERE id = ?";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar usuário: " + e.getMessage());
        }
    }

    /**
     * Conta o número total de usuários cadastrados no banco de dados.
     *
     * @return O número total de usuários.
     */
    public int contarTotal() {
        String sql = "SELECT COUNT(*) FROM Usuarios";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao contar usuários: " + e.getMessage());
        }
        return 0;
    }
}