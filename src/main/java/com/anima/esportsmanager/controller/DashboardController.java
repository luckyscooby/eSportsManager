package com.anima.esportsmanager.controller;

import com.anima.esportsmanager.ESportsManager;
import com.anima.esportsmanager.dao.EquipeDAO;
import com.anima.esportsmanager.dao.ProjetoDAO;
import com.anima.esportsmanager.dao.UsuarioDAO;
import com.anima.esportsmanager.model.Projeto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Controller para a tela principal (Dashboard).
 * Responsável por exibir estatísticas e navegar para outros módulos.
 */
public class DashboardController {

    @FXML private Label statsUsuariosLabel;
    @FXML private Label statsEquipesLabel;
    @FXML private Label statsProjetosLabel;
    @FXML private ListView<Projeto> projetosListView;
    @FXML private VBox projetoAtualBox;
    @FXML private Label projetoAtualNomeLabel;
    @FXML private Label projetoAtualInicioLabel;
    @FXML private Label projetoAtualDataLabel;

    private final UsuarioDAO usuarioDAO;
    private final EquipeDAO equipeDAO;
    private final ProjetoDAO projetoDAO;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Construtor. Inicializa as instâncias dos DAOs.
     */
    public DashboardController() {
        this.usuarioDAO = new UsuarioDAO();
        this.equipeDAO = new EquipeDAO();
        this.projetoDAO = new ProjetoDAO();
    }

    /**
     * Método de inicialização, chamado automaticamente após o FXML ser carregado.
     * Popula todos os componentes visuais do dashboard.
     */
    @FXML
    public void initialize() {
        carregarEstatisticas();
        carregarProjetoAtual();
        carregarProximosProjetos();
    }

    /**
     * Busca os dados agregados (contagens) nos DAOs e atualiza as labels de estatísticas.
     */
    private void carregarEstatisticas() {
        statsUsuariosLabel.setText(String.valueOf(usuarioDAO.contarTotal()));
        statsEquipesLabel.setText(String.valueOf(equipeDAO.contarTotal()));
        statsProjetosLabel.setText(String.valueOf(projetoDAO.contarAtivos()));
    }

    /**
     * Busca o projeto vigente no banco de dados e exibe suas informações.
     * Se nenhum projeto estiver vigente, exibe uma mensagem informativa.
     */
    private void carregarProjetoAtual() {
        Optional<Projeto> projetoAtualOpt = projetoDAO.buscarProjetoAtual();
        
        if (projetoAtualOpt.isPresent()) {
            Projeto projetoAtual = projetoAtualOpt.get();
            projetoAtualNomeLabel.setText(projetoAtual.getNomeProjeto());
            projetoAtualInicioLabel.setText("Início em: " + projetoAtual.getDataInicio().format(formatter));
            projetoAtualDataLabel.setText("Término previsto para: " + projetoAtual.getDataTerminoPrevista().format(formatter));
        } else {
            projetoAtualNomeLabel.setText("Nenhum projeto em vigência no momento.");
            projetoAtualInicioLabel.setText("");
            projetoAtualDataLabel.setText("");
        }
        projetoAtualBox.setVisible(true);
        projetoAtualBox.setManaged(true);
    }

    /**
     * Busca os próximos projetos futuros e os exibe na ListView.
     * Formata cada célula para mostrar o nome e a data de início do projeto.
     */
    private void carregarProximosProjetos() {
        projetosListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Projeto projeto, boolean empty) {
                super.updateItem(projeto, empty);
                if (empty || projeto == null) {
                    setText(null);
                } else {
                    setText(projeto.getNomeProjeto() + " (Início: " + projeto.getDataInicio().format(formatter) + ")");
                }
            }
        });
        projetosListView.setItems(FXCollections.observableArrayList(projetoDAO.listarProximos(5)));
    }

    /**
     * Abre a janela de gerenciamento de usuários.
     */
    @FXML
    private void abrirGerenciamentoUsuarios() {
        abrirJanela("/view/GerenciamentoUsuariosView.fxml", "Gerenciamento de Usuários", true);
    }

    /**
     * Abre a janela de gerenciamento de equipes.
     */
    @FXML
    private void abrirGerenciamentoEquipes() {
        abrirJanela("/view/GerenciamentoEquipesView.fxml", "Gerenciamento de Equipes", true);
    }

    /**
     * Abre a janela de gerenciamento de projetos.
     */
    @FXML
    private void abrirGerenciamentoProjetos() {
        abrirJanela("/view/GerenciamentoProjetosView.fxml", "Gerenciamento de Projetos", true);
    }

    /**
     * Método utilitário para abrir uma nova janela FXML de forma modal.
     *
     * @param caminhoFxml      O caminho para o arquivo FXML a ser carregado.
     * @param titulo           O título da nova janela.
     * @param redimensionavel  Define se a janela pode ser redimensionada.
     */
    private void abrirJanela(String caminhoFxml, String titulo, boolean redimensionavel) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ESportsManager.class.getResource(caminhoFxml));
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.setResizable(redimensionavel);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}