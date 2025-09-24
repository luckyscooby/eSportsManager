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

public class GerenciamentoEquipesController {

    // Componentes da Tabela
    @FXML private TableView<Equipe> tabelaEquipes;
    @FXML private TableColumn<Equipe, Integer> colunaId;
    @FXML private TableColumn<Equipe, String> colunaNome;
    @FXML private TableColumn<Equipe, String> colunaJogo;

    // Componentes do Formulário
    @FXML private TextField nomeField;
    @FXML private ComboBox<String> jogoComboBox;
    @FXML private TextArea descricaoArea;

    private EquipeDAO equipeDAO;

    public GerenciamentoEquipesController() {
        this.equipeDAO = new EquipeDAO();
    }

    @FXML
    public void initialize() {
        // Configura as colunas da tabela
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nomeEquipe"));
        colunaJogo.setCellValueFactory(new PropertyValueFactory<>("jogo"));

        // Popula o ComboBox de jogos
        jogoComboBox.getItems().addAll("LoL", "CS2", "Valorant", "Outro");

        // Adiciona um listener para a seleção na tabela
        tabelaEquipes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> preencherFormulario(newValue)
        );

        // Carrega os dados iniciais
        carregarEquipes();
    }

    private void carregarEquipes() {
        List<Equipe> equipes = equipeDAO.listarTodas();
        ObservableList<Equipe> observableEquipes = FXCollections.observableArrayList(equipes);
        tabelaEquipes.setItems(observableEquipes);
    }

    private void preencherFormulario(Equipe equipe) {
        if (equipe != null) {
            nomeField.setText(equipe.getNomeEquipe());
            jogoComboBox.setValue(equipe.getJogo());
            descricaoArea.setText(equipe.getDescricao());
        } else {
            limparFormulario();
        }
    }

    @FXML
    private void handleSalvarButton() {
        String nome = nomeField.getText();
        String jogo = jogoComboBox.getValue();
        String descricao = descricaoArea.getText();

        Equipe selecionada = tabelaEquipes.getSelectionModel().getSelectedItem();

        if (selecionada == null) { // Criar nova equipe
            Equipe novaEquipe = new Equipe(nome, jogo, descricao);
            equipeDAO.inserir(novaEquipe);
        } else { // Atualizar equipe existente
            selecionada.setNomeEquipe(nome);
            selecionada.setJogo(jogo);
            selecionada.setDescricao(descricao);
            equipeDAO.atualizar(selecionada);
        }

        carregarEquipes();
        limparFormulario();
    }

    @FXML
    private void handleNovoButton() {
        limparFormulario();
    }
    
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

    private void limparFormulario() {
        tabelaEquipes.getSelectionModel().clearSelection();
        nomeField.clear();
        jogoComboBox.setValue(null);
        descricaoArea.clear();
    }
}