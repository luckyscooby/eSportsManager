package com.anima.esportsmanager.controller;

import com.anima.esportsmanager.dao.UsuarioDAO;
import com.anima.esportsmanager.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller para a tela de Edição de Usuário.
 * Responsável por preencher o formulário com dados existentes,
 * coletar as alterações e enviá-las para o DAO para atualização.
 */
public class EdicaoUsuarioController {

    @FXML private TextField nomeCompletoField;
    @FXML private TextField cpfField;
    @FXML private TextField emailField;
    @FXML private TextField loginField;
    @FXML private ComboBox<String> cargoComboBox;
    @FXML private Button salvarButton;

    private Usuario usuario;
    private final UsuarioDAO usuarioDAO;

    /**
     * Construtor. Inicializa o DAO.
     */
    public EdicaoUsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    /**
     * Método de inicialização. Popula o ComboBox de cargos.
     */
    @FXML
    public void initialize() {
        cargoComboBox.getItems().addAll("Administrador", "Técnico", "Jogador");
    }

    /**
     * Recebe o objeto {@link Usuario} da tela de listagem e preenche os campos do formulário.
     *
     * @param usuario O usuário a ser editado.
     */
    public void initData(Usuario usuario) {
        this.usuario = usuario;
        nomeCompletoField.setText(usuario.getNomeCompleto());
        cpfField.setText(usuario.getCpf());
        emailField.setText(usuario.getEmail());
        loginField.setText(usuario.getLogin());
        cargoComboBox.setValue(usuario.getCargo());
    }

    /**
     * Manipula o evento de clique no botão "Salvar Alterações".
     * Atualiza o objeto de usuário com os novos dados e o persiste no banco.
     */
    @FXML
    private void handleSalvarButtonAction() {
        usuario.setNomeCompleto(nomeCompletoField.getText());
        usuario.setCpf(cpfField.getText());
        usuario.setEmail(emailField.getText());
        usuario.setLogin(loginField.getText());
        usuario.setCargo(cargoComboBox.getValue());

        usuarioDAO.atualizar(usuario);
        fecharJanela();
    }
    
    /**
     * Fecha a janela de edição.
     */
    private void fecharJanela() {
        Stage stage = (Stage) salvarButton.getScene().getWindow();
        stage.close();
    }
}