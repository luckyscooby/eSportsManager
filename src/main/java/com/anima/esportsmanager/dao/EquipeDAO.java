package com.anima.esportsmanager.dao;

import com.anima.esportsmanager.model.Equipe;
import com.anima.esportsmanager.util.ConexaoDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipeDAO {

    public void inserir(Equipe equipe) {
        String sql = "INSERT INTO Equipes (nome_equipe, jogo, descricao) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, equipe.getNomeEquipe());
            pstmt.setString(2, equipe.getJogo());
            pstmt.setString(3, equipe.getDescricao());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao inserir equipe: " + e.getMessage());
        }
    }

    public List<Equipe> listarTodas() {
        List<Equipe> equipes = new ArrayList<>();
        String sql = "SELECT * FROM Equipes";

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Equipe equipe = new Equipe();
                equipe.setId(rs.getInt("id"));
                equipe.setNomeEquipe(rs.getString("nome_equipe"));
                equipe.setJogo(rs.getString("jogo"));
                equipe.setDescricao(rs.getString("descricao"));
                equipes.add(equipe);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar equipes: " + e.getMessage());
        }
        return equipes;
    }
    
    public void atualizar(Equipe equipe) {
        String sql = "UPDATE Equipes SET nome_equipe = ?, jogo = ?, descricao = ? WHERE id = ?";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, equipe.getNomeEquipe());
            pstmt.setString(2, equipe.getJogo());
            pstmt.setString(3, equipe.getDescricao());
            pstmt.setInt(4, equipe.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar equipe: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM Equipes WHERE id = ?";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao deletar equipe: " + e.getMessage());
        }
    }
    
    public int contarTotal() {
        String sql = "SELECT COUNT(*) FROM Equipes";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao contar equipes: " + e.getMessage());
        }
        return 0;
    }
}