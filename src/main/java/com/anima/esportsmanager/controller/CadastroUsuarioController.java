package com.anima.esportsmanager.controller;

import com.anima.esportsmanager.dao.UsuarioDAO;
import com.anima.esportsmanager.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CadastroUsuarioController {

    // Injeção dos componentes da interface gráfica (definidos no FXML com fx:id)
    @FXML private TextField nomeCompletoField;
    @FXML private TextField cpfField;
    @FXML private TextField emailField;
    @FXML private TextField loginField;
    @FXML private PasswordField senhaField;
    @FXML private ComboBox<String> cargoComboBox;
    @FXML private Label statusLabel;

    private UsuarioDAO usuarioDAO;

    public CadastroUsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }
    
    /**
     * Método executado automaticamente quando o FXML é carregado.
     * Usamos para inicializar componentes.
     */
    @FXML
    public void initialize() {
        // Popula o ComboBox com as opções de cargo
        cargoComboBox.getItems().addAll("Administrador", "Técnico", "Jogador");
    }

    /**
     * Método chamado quando o botão "Salvar" é clicado (definido no onAction do FXML).
     */
    @FXML
    private void handleSalvarButtonAction() {
        // 1. Coletar os dados da interface
        String nome = nomeCompletoField.getText();
        String cpf = cpfField.getText();
        String email = emailField.getText();
        String login = loginField.getText();
        String senha = senhaField.getText();
        String cargo = cargoComboBox.getValue();

        // 2. Validação simples (verificar se campos essenciais não estão vazios)
        if (nome.isEmpty() || login.isEmpty() || senha.isEmpty() || cargo == null) {
            statusLabel.setText("Erro: Preencha todos os campos obrigatórios!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // 3. Criar o objeto Model
        Usuario novoUsuario = new Usuario(nome, cpf, email, cargo, login, senha);

        // 4. Chamar o DAO para persistir o objeto
        try {
            usuarioDAO.inserir(novoUsuario);
            statusLabel.setText("Usuário cadastrado com sucesso!");
            statusLabel.setStyle("-fx-text-fill: green;");
            limparCampos();
        } catch (Exception e) {
            statusLabel.setText("Erro ao cadastrar usuário: " + e.getMessage());
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    /**
     * Limpa os campos do formulário após uma inserção bem-sucedida.
     */
    private void limparCampos() {
        nomeCompletoField.clear();
        cpfField.clear();
        emailField.clear();
        loginField.clear();
        senhaField.clear();
        cargoComboBox.getSelectionModel().clearSelection();
    }
}