package com.anima.esportsmanager.controller;

import com.anima.esportsmanager.dao.UsuarioDAO;
import com.anima.esportsmanager.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller para a tela de Cadastro de Usuário.
 * Responsável por coletar os dados do formulário, validar e
 * enviar para o DAO para inserção no banco de dados.
 */
public class CadastroUsuarioController {

    @FXML private TextField nomeCompletoField;
    @FXML private TextField cpfField;
    @FXML private TextField emailField;
    @FXML private TextField loginField;
    @FXML private PasswordField senhaField;
    @FXML private ComboBox<String> cargoComboBox;
    @FXML private Label statusLabel;
    @FXML private Button salvarButton;

    private final UsuarioDAO usuarioDAO;

    /**
     * Construtor. Inicializa o DAO.
     */
    public CadastroUsuarioController() {
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
     * Manipula o evento de clique no botão "Salvar".
     * Coleta, valida e persiste os dados do novo usuário.
     */
    @FXML
    private void handleSalvarButtonAction() {
        String nome = nomeCompletoField.getText();
        String cpf = cpfField.getText();
        String email = emailField.getText();
        String login = loginField.getText();
        String senha = senhaField.getText();
        String cargo = cargoComboBox.getValue();

        if (!validarCampos(nome, login, senha, cargo)) {
            return;
        }

        Usuario novoUsuario = new Usuario(nome, cpf, email, cargo, login, senha);

        try {
            usuarioDAO.inserir(novoUsuario);
            fecharJanela();
        } catch (Exception e) {
            setStatusLabel("Erro ao cadastrar: " + e.getMessage(), true);
        }
    }
    
    /**
     * Valida se os campos obrigatórios do formulário foram preenchidos.
     *
     * @param nome O nome do usuário.
     * @param login O login do usuário.
     * @param senha A senha do usuário.
     * @param cargo O cargo do usuário.
     * @return {@code true} se os campos forem válidos, {@code false} caso contrário.
     */
    private boolean validarCampos(String nome, String login, String senha, String cargo) {
        if (nome.isBlank() || login.isBlank() || senha.isBlank() || cargo == null) {
            setStatusLabel("Erro: Preencha todos os campos obrigatórios!", true);
            return false;
        }
        return true;
    }
    
    /**
     * Atualiza a label de status com uma mensagem para o usuário.
     *
     * @param mensagem A mensagem a ser exibida.
     * @param isError  Define se a mensagem é de erro (vermelha) ou sucesso (verde).
     */
    private void setStatusLabel(String mensagem, boolean isError) {
        statusLabel.setText(mensagem);
        statusLabel.setStyle(isError ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
    }

    /**
     * Fecha a janela de cadastro.
     */
    private void fecharJanela() {
        Stage stage = (Stage) salvarButton.getScene().getWindow();
        stage.close();
    }
}