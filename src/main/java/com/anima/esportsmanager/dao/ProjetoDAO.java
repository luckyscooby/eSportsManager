package com.anima.esportsmanager.dao;

import com.anima.esportsmanager.model.Projeto;
import com.anima.esportsmanager.model.Usuario;
import com.anima.esportsmanager.util.ConexaoDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object (DAO) para a entidade {@link Projeto}.
 * Gerencia todas as operações de banco de dados relacionadas a projetos.
 */
public class ProjetoDAO {

    /**
     * Insere um novo projeto no banco de dados.
     *
     * @param projeto O objeto {@link Projeto} a ser inserido.
     */
    public void inserir(Projeto projeto) {
        String sql = "INSERT INTO Projetos (nome_projeto, descricao, data_inicio, data_termino_prevista, status, id_gerente) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, projeto.getNomeProjeto());
            pstmt.setString(2, projeto.getDescricao());
            pstmt.setDate(3, Date.valueOf(projeto.getDataInicio()));
            pstmt.setDate(4, Date.valueOf(projeto.getDataTerminoPrevista()));
            pstmt.setString(5, projeto.getStatus());
            
            if (projeto.getGerente() != null) {
                pstmt.setInt(6, projeto.getGerente().getId());
            } else {
                pstmt.setNull(6, Types.INTEGER);
            }

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao inserir projeto: " + e.getMessage());
        }
    }

    /**
     * Busca e retorna uma lista de todos os projetos, incluindo os dados de seus gerentes.
     *
     * @return Uma {@link List} de objetos {@link Projeto}.
     */
    public List<Projeto> listarTodos() {
        List<Projeto> projetos = new ArrayList<>();
        String sql = "SELECT p.*, u.id as gerente_id, u.nome_completo as gerente_nome " +
                     "FROM Projetos p LEFT JOIN Usuarios u ON p.id_gerente = u.id";

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Projeto projeto = new Projeto();
                projeto.setId(rs.getInt("id"));
                projeto.setNomeProjeto(rs.getString("nome_projeto"));
                projeto.setDescricao(rs.getString("descricao"));
                projeto.setDataInicio(rs.getDate("data_inicio").toLocalDate());
                projeto.setDataTerminoPrevista(rs.getDate("data_termino_prevista").toLocalDate());
                projeto.setStatus(rs.getString("status"));
                
                int gerenteId = rs.getInt("gerente_id");
                if (!rs.wasNull()) {
                    Usuario gerente = new Usuario();
                    gerente.setId(gerenteId);
                    gerente.setNomeCompleto(rs.getString("gerente_nome"));
                    projeto.setGerente(gerente);
                }
                
                projetos.add(projeto);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar projetos: " + e.getMessage());
        }
        return projetos;
    }
    
    /**
     * Atualiza os dados de um projeto existente.
     *
     * @param projeto O objeto {@link Projeto} com os dados atualizados.
     */
    public void atualizar(Projeto projeto) {
        String sql = "UPDATE Projetos SET nome_projeto = ?, descricao = ?, data_inicio = ?, data_termino_prevista = ?, status = ?, id_gerente = ? WHERE id = ?";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, projeto.getNomeProjeto());
            pstmt.setString(2, projeto.getDescricao());
            pstmt.setDate(3, Date.valueOf(projeto.getDataInicio()));
            pstmt.setDate(4, Date.valueOf(projeto.getDataTerminoPrevista()));
            pstmt.setString(5, projeto.getStatus());
            pstmt.setInt(6, projeto.getGerente().getId());
            pstmt.setInt(7, projeto.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar projeto: " + e.getMessage());
        }
    }
    
    /**
     * Deleta um projeto do banco de dados com base no seu ID.
     *
     * @param id O ID do projeto a ser deletado.
     */
    public void deletar(int id) {
        String sql = "DELETE FROM Projetos WHERE id = ?";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar projeto: " + e.getMessage());
        }
    }
    
    /**
     * Busca o projeto mais recente que está vigente na data atual.
     * Um projeto é considerado vigente se a data atual está entre sua data de início e de término.
     *
     * @return Um {@link Optional} contendo o Projeto, se encontrado, ou um Optional vazio.
     */
    public Optional<Projeto> buscarProjetoAtual() {
        String sql = "SELECT p.*, u.id as gerente_id, u.nome_completo as gerente_nome " +
                     "FROM Projetos p LEFT JOIN Usuarios u ON p.id_gerente = u.id " +
                     "WHERE CURDATE() BETWEEN p.data_inicio AND p.data_termino_prevista " +
                     "ORDER BY p.data_inicio DESC LIMIT 1";

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                Projeto projeto = new Projeto();
                projeto.setId(rs.getInt("id"));
                projeto.setNomeProjeto(rs.getString("nome_projeto"));
                projeto.setDescricao(rs.getString("descricao"));
                projeto.setDataInicio(rs.getDate("data_inicio").toLocalDate());
                projeto.setDataTerminoPrevista(rs.getDate("data_termino_prevista").toLocalDate());
                projeto.setStatus(rs.getString("status"));

                int gerenteId = rs.getInt("gerente_id");
                if (!rs.wasNull()) {
                    Usuario gerente = new Usuario();
                    gerente.setId(gerenteId);
                    gerente.setNomeCompleto(rs.getString("gerente_nome"));
                    projeto.setGerente(gerente);
                }
                return Optional.of(projeto);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar projeto atual: " + e.getMessage());
        }
        return Optional.empty();
    }
    
    /**
     * Busca uma lista de projetos futuros, ordenados pela data de início mais próxima.
     *
     * @param limite O número máximo de projetos a serem retornados.
     * @return Uma {@link List} de objetos {@link Projeto}.
     */
    public List<Projeto> listarProximos(int limite) {
        List<Projeto> projetos = new ArrayList<>();
        String sql = "SELECT * FROM Projetos WHERE data_inicio > CURDATE() ORDER BY data_inicio ASC LIMIT ?";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, limite);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Projeto projeto = new Projeto();
                projeto.setId(rs.getInt("id"));
                projeto.setNomeProjeto(rs.getString("nome_projeto"));
                projeto.setDataInicio(rs.getDate("data_inicio").toLocalDate());
                projetos.add(projeto);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar próximos projetos: " + e.getMessage());
        }
        return projetos;
    }

    /**
     * Conta o número total de projetos com o status "Em Andamento".
     *
     * @return O número de projetos ativos.
     */
    public int contarAtivos() {
        String sql = "SELECT COUNT(*) FROM Projetos WHERE status = 'Em Andamento'";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao contar projetos ativos: " + e.getMessage());
        }
        return 0;
    }
}