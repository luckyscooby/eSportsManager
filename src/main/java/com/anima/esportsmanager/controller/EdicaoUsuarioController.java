package com.anima.esportsmanager.controller;

import com.anima.esportsmanager.dao.UsuarioDAO;
import com.anima.esportsmanager.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EdicaoUsuarioController {

    @FXML private TextField nomeCompletoField;
    @FXML private TextField cpfField;
    @FXML private TextField emailField;
    @FXML private TextField loginField;
    @FXML private ComboBox<String> cargoComboBox;
    @FXML private Button salvarButton;

    private Usuario usuario;
    private UsuarioDAO usuarioDAO;

    public EdicaoUsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    @FXML
    public void initialize() {
        cargoComboBox.getItems().addAll("Administrador", "Técnico", "Jogador");
    }

    /**
     * Recebe o objeto Usuário da tela de listagem e preenche os campos.
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

    @FXML
    private void handleSalvarButtonAction() {
        // Atualiza o objeto usuario com os novos dados dos campos
        usuario.setNomeCompleto(nomeCompletoField.getText());
        usuario.setCpf(cpfField.getText());
        usuario.setEmail(emailField.getText());
        usuario.setLogin(loginField.getText());
        usuario.setCargo(cargoComboBox.getValue());

        // Chama o DAO para persistir as alterações
        usuarioDAO.atualizar(usuario);

        // Fecha a janela de edição
        Stage stage = (Stage) salvarButton.getScene().getWindow();
        stage.close();
    }
}