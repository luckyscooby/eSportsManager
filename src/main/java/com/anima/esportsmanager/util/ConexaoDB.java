package com.anima.esportsmanager.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe de utilidade para gerenciar a conexão com o banco de dados MariaDB.
 * Utiliza o padrão Singleton para garantir que exista apenas uma instância
 * da conexão em toda a aplicação, otimizando o uso de recursos.
 */
public class ConexaoDB {

    // --- INÍCIO DA ÁREA DE CONFIGURAÇÃO ---

    // URL de conexão JDBC para o MariaDB.
    // Formato: jdbc:mariadb://<host>:<porta>/<nome_do_banco>
    private static final String URL = "jdbc:mariadb://localhost:3306/esports_manager";

    // Usuário do banco de dados.
    // É uma boa prática criar um usuário específico para a aplicação em vez de usar 'root'.
    private static final String USER = "root";

    // TODO: Insira aqui a senha que você definiu para o usuário 'root' do MariaDB.
    private static final String PASSWORD = "";

    // --- FIM DA ÁREA DE CONFIGURAÇÃO ---


    // Instância única da conexão (Padrão Singleton).
    private static Connection conexao = null;

    /**
     * Método estático público para obter a conexão com o banco de dados.
     * Se a conexão ainda não existir ou estiver fechada, uma nova será criada.
     *
     * @return Uma instância de Connection com o banco de dados.
     * @throws SQLException se ocorrer um erro ao tentar se conectar.
     */
    public static Connection conectar() throws SQLException {
        // Verifica se a conexão não foi criada ou se foi fechada
        if (conexao == null || conexao.isClosed()) {
            try {
                // O DriverManager tentará encontrar o driver do MariaDB que adicionamos via Maven.
                // A partir do JDBC 4.0, o carregamento manual da classe do driver não é mais necessário.
                conexao = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
            } catch (SQLException e) {
                // Lança uma exceção mais específica ou trata o erro aqui.
                System.err.println("Falha ao conectar com o banco de dados: " + e.getMessage());
                // Relança a exceção para que a camada que chamou o método possa tratá-la.
                throw e;
            }
        }
        // Retorna a conexão existente
        return conexao;
    }

    /**
     * Método opcional para fechar a conexão.
     * Pode ser chamado ao final da execução da aplicação.
     */
    public static void desconectar() {
        if (conexao != null) {
            try {
                conexao.close();
                conexao = null; // Garante que na próxima chamada de conectar(), uma nova conexão seja criada.
                System.out.println("Conexão com o banco de dados fechada.");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}