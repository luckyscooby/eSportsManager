package com.anima.esportsmanager.controller;

import com.anima.esportsmanager.dao.EquipeDAO;
import com.anima.esportsmanager.model.Equipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Optional;

/**
 * Controller para a tela de Gerenciamento de Equipes.
 * Permite listar, criar, editar e excluir equipes em uma única interface.
 */
public class GerenciamentoEquipesController {

    @FXML private TableView<Equipe> tabelaEquipes;
    @FXML private TableColumn<Equipe, Integer> colunaId;
    @FXML private TableColumn<Equipe, String> colunaNome;
    @FXML private TableColumn<Equipe, String> colunaJogo;

    @FXML private TextField nomeField;
    @FXML private ComboBox<String> jogoComboBox;
    @FXML private TextArea descricaoArea;

    private final EquipeDAO equipeDAO;

    /**
     * Construtor. Inicializa o DAO.
     */
    public GerenciamentoEquipesController() {
        this.equipeDAO = new EquipeDAO();
    }

    /**
     * Método de inicialização. Configura a tabela, o ComboBox e o listener de seleção.
     */
    @FXML
    public void initialize() {
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nomeEquipe"));
        colunaJogo.setCellValueFactory(new PropertyValueFactory<>("jogo"));

        jogoComboBox.getItems().addAll("LoL", "CS2", "Valorant", "Outro");

        tabelaEquipes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> preencherFormulario(newValue)
        );

        carregarEquipes();
    }

    /**
     * Carrega as equipes do banco de dados e as exibe na tabela.
     */
    private void carregarEquipes() {
        List<Equipe> equipes = equipeDAO.listarTodas();
        ObservableList<Equipe> observableEquipes = FXCollections.observableArrayList(equipes);
        tabelaEquipes.setItems(observableEquipes);
    }

    /**
     * Preenche o formulário de detalhes com as informações da equipe selecionada.
     *
     * @param equipe A {@link Equipe} selecionada na tabela, ou null.
     */
    private void preencherFormulario(Equipe equipe) {
        if (equipe != null) {
            nomeField.setText(equipe.getNomeEquipe());
            jogoComboBox.setValue(equipe.getJogo());
            descricaoArea.setText(equipe.getDescricao());
        } else {
            limparFormulario();
        }
    }

    /**
     * Manipula o clique no botão "Salvar".
     * Cria uma nova equipe se nenhuma estiver selecionada, ou atualiza a existente.
     */
    @FXML
    private void handleSalvarButton() {
        String nome = nomeField.getText();
        String jogo = jogoComboBox.getValue();
        String descricao = descricaoArea.getText();

        Equipe selecionada = tabelaEquipes.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            Equipe novaEquipe = new Equipe(nome, jogo, descricao);
            equipeDAO.inserir(novaEquipe);
        } else {
            selecionada.setNomeEquipe(nome);
            selecionada.setJogo(jogo);
            selecionada.setDescricao(descricao);
            equipeDAO.atualizar(selecionada);
        }

        carregarEquipes();
        limparFormulario();
    }

    /**
     * Limpa a seleção da tabela e o formulário, preparando para uma nova inserção.
     */
    @FXML
    private void handleNovoButton() {
        limparFormulario();
    }
    
    /**
     * Exclui a equipe selecionada após pedir confirmação ao usuário.
     */
    @FXML
    private void handleExcluirButton() {
        Equipe selecionada = tabelaEquipes.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de Exclusão");
            alert.setHeaderText("Deseja excluir a equipe '" + selecionada.getNomeEquipe() + "'?");
            
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                equipeDAO.deletar(selecionada.getId());
                carregarEquipes();
                limparFormulario();
            }
        }
    }

    /**
     * Reseta o formulário para seu estado inicial.
     */
    private void limparFormulario() {
        tabelaEquipes.getSelectionModel().clearSelection();
        nomeField.clear();
        jogoComboBox.setValue(null);
        descricaoArea.clear();
    }
}