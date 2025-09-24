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

public class GerenciamentoUsuariosController {

    @FXML
    private TableView<Usuario> tabelaUsuarios;
    @FXML
    private TableColumn<Usuario, Integer> colunaId;
    @FXML
    private TableColumn<Usuario, String> colunaNome;
    @FXML
    private TableColumn<Usuario, String> colunaEmail;
    @FXML
    private TableColumn<Usuario, String> colunaCargo;

    private UsuarioDAO usuarioDAO;

    public GerenciamentoUsuariosController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    @FXML
    public void initialize() {
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nomeCompleto"));
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colunaCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        carregarDadosTabela();
    }

    private void carregarDadosTabela() {
        try {
            List<Usuario> usuarios = usuarioDAO.listarTodos();
            ObservableList<Usuario> usuariosObservaveis = FXCollections.observableArrayList(usuarios);
            tabelaUsuarios.setItems(usuariosObservaveis);
        } catch (Exception e) {
            mostrarAlerta("Erro", "Não foi possível carregar os usuários.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdicionarButtonAction() {
        try {
            // CAMINHO ATUALIZADO
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

    @FXML
    private void handleEditarButtonAction() {
        Usuario selecionado = tabelaUsuarios.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            try {
                // CAMINHO ATUALIZADO
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
            mostrarAlerta("Nenhum usuário selecionado", "Por favor, selecione um usuário na tabela para editar.");
        }
    }

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
            mostrarAlerta("Nenhum usuário selecionado", "Por favor, selecione um usuário na tabela para excluir.");
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}