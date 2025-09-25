package com.anima.esportsmanager.controller;

import com.anima.esportsmanager.ESportsManager;
import com.anima.esportsmanager.dao.UsuarioDAO;
import com.anima.esportsmanager.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Controller para a tela de Gerenciamento de Usuários.
 * Responsável por listar, e acionar as janelas de adicionar, editar e excluir usuários.
 */
public class GerenciamentoUsuariosController {

    @FXML private TableView<Usuario> tabelaUsuarios;
    @FXML private TableColumn<Usuario, Integer> colunaId;
    @FXML private TableColumn<Usuario, String> colunaNome;
    @FXML private TableColumn<Usuario, String> colunaEmail;
    @FXML private TableColumn<Usuario, String> colunaCargo;

    private final UsuarioDAO usuarioDAO;

    /**
     * Construtor. Inicializa o DAO.
     */
    public GerenciamentoUsuariosController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    /**
     * Método de inicialização. Configura as colunas da tabela e carrega os dados iniciais.
     */
    @FXML
    public void initialize() {
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nomeCompleto"));
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colunaCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        carregarDadosTabela();
    }

    /**
     * Busca os usuários no banco de dados e atualiza a TableView.
     */
    private void carregarDadosTabela() {
        try {
            List<Usuario> usuarios = usuarioDAO.listarTodos();
            ObservableList<Usuario> usuariosObservaveis = FXCollections.observableArrayList(usuarios);
            tabelaUsuarios.setItems(usuariosObservaveis);
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível carregar os usuários.");
            e.printStackTrace();
        }
    }

    /**
     * Manipula o evento de clique no botão "Adicionar Novo".
     * Abre a janela de cadastro de usuário e atualiza a tabela após o fechamento.
     */
    @FXML
    private void handleAdicionarButtonAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ESportsManager.class.getResource("/view/CadastroUsuarioView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Cadastrar Novo Usuário");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
            carregarDadosTabela();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Manipula o evento de clique no botão "Editar Selecionado".
     * Abre a janela de edição com os dados do usuário selecionado na tabela.
     */
    @FXML
    private void handleEditarButtonAction() {
        Usuario selecionado = tabelaUsuarios.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(ESportsManager.class.getResource("/view/EdicaoUsuarioView.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Editar Usuário");
                stage.setScene(new Scene(fxmlLoader.load()));
                stage.setResizable(false);
                
                EdicaoUsuarioController controller = fxmlLoader.getController();
                controller.initData(selecionado);

                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                carregarDadosTabela();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Nenhum usuário selecionado", "Por favor, selecione um usuário na tabela para editar.");
        }
    }

    /**
     * Manipula o evento de clique no botão "Excluir Selecionado".
     * Pede confirmação e remove o usuário selecionado do banco de dados.
     */
    @FXML
    private void handleExcluirButtonAction() {
        Usuario selecionado = tabelaUsuarios.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de Exclusão");
            alert.setHeaderText("Você tem certeza que deseja excluir o usuário?");
            alert.setContentText(selecionado.getNomeCompleto());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                usuarioDAO.deletar(selecionado.getId());
                carregarDadosTabela();
            }
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Nenhum usuário selecionado", "Por favor, selecione um usuário na tabela para excluir.");
        }
    }

    /**
     * Exibe um diálogo de alerta genérico para o usuário.
     *
     * @param tipoAlerta O tipo de alerta (ex: INFORMATION, WARNING, ERROR).
     * @param titulo O título da janela de alerta.
     * @param mensagem A mensagem a ser exibida.
     */
    private void mostrarAlerta(Alert.AlertType tipoAlerta, String titulo, String mensagem) {
        Alert alert = new Alert(tipoAlerta);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}