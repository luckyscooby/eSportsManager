package com.anima.esportsmanager.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilitária para gerenciar a conexão com o banco de dados MariaDB.
 * <p>
 * Utiliza o padrão Singleton para garantir que exista apenas uma instância
 * da conexão em toda a aplicação, otimizando o uso de recursos.
 */
public class ConexaoDB {

    /** URL de conexão JDBC para o MariaDB. */
    private static final String URL = "jdbc:mariadb://localhost:3306/esports_manager";

    /** Usuário do banco de dados. */
    private static final String USER = "root";

    /** Senha do banco de dados. ALTERE ESTE VALOR para a sua senha local. */
    private static final String PASSWORD = "sua_senha_aqui";

    /** Instância única da conexão (Padrão Singleton). */
    private static Connection conexao = null;

    /**
     * Obtém a conexão com o banco de dados.
     * Se a conexão ainda não existir ou estiver fechada, uma nova será criada.
     *
     * @return Uma instância de {@link Connection} com o banco de dados.
     * @throws SQLException se ocorrer um erro ao tentar se conectar.
     */
    public static Connection conectar() throws SQLException {
        if (conexao == null || conexao.isClosed()) {
            try {
                conexao = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                System.err.println("Falha ao conectar com o banco de dados: " + e.getMessage());
                throw e;
            }
        }
        return conexao;
    }

    /**
     * Fecha a conexão ativa com o banco de dados.
     * Deve ser chamado ao final da execução da aplicação para liberar recursos.
     */
    public static void desconectar() {
        if (conexao != null) {
            try {
                conexao.close();
                conexao = null;
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}