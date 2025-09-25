package com.anima.esportsmanager.controller;

import com.anima.esportsmanager.dao.ProjetoDAO;
import com.anima.esportsmanager.dao.UsuarioDAO;
import com.anima.esportsmanager.model.Projeto;
import com.anima.esportsmanager.model.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller para a tela de Gerenciamento de Projetos.
 * Permite listar, criar, editar e excluir projetos.
 */
public class GerenciamentoProjetosController {

    @FXML private TableView<Projeto> tabelaProjetos;
    @FXML private TableColumn<Projeto, Integer> colunaId;
    @FXML private TableColumn<Projeto, String> colunaNome;
    @FXML private TableColumn<Projeto, LocalDate> colunaDataInicio;
    @FXML private TableColumn<Projeto, LocalDate> colunaDataTermino;
    @FXML private TableColumn<Projeto, String> colunaStatus;
    @FXML private TableColumn<Projeto, String> colunaGerente;

    @FXML private TextField nomeField;
    @FXML private DatePicker dataInicioPicker;
    @FXML private DatePicker dataFimPicker;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private ComboBox<Usuario> gerenteComboBox;
    @FXML private TextArea descricaoArea;

    private final ProjetoDAO projetoDAO;
    private final UsuarioDAO usuarioDAO;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Construtor. Inicializa os DAOs.
     */
    public GerenciamentoProjetosController() {
        this.projetoDAO = new ProjetoDAO();
        this.usuarioDAO = new UsuarioDAO();
    }

    /**
     * Método de inicialização. Configura todos os componentes da tela.
     */
    @FXML
    public void initialize() {
        configurarTabela();
        configurarFormulario();
        carregarProjetos();
    }
    
    /**
     * Configura as colunas da tabela, incluindo formatação customizada para datas e objetos.
     */
    private void configurarTabela() {
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nomeProjeto"));
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        colunaDataInicio.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        colunaDataTermino.setCellValueFactory(new PropertyValueFactory<>("dataTerminoPrevista"));
        
        formatarColunaData(colunaDataInicio);
        formatarColunaData(colunaDataTermino);

        colunaGerente.setCellValueFactory(cellData -> {
            Usuario gerente = cellData.getValue().getGerente();
            return new SimpleStringProperty(gerente != null ? gerente.getNomeCompleto() : "N/D");
        });
    }

    /**
     * Configura os componentes do formulário, como ComboBoxes e o listener de seleção da tabela.
     */
    private void configurarFormulario() {
        statusComboBox.getItems().addAll("Planejado", "Em Andamento", "Concluído", "Cancelado");
        carregarGerentes();

        tabelaProjetos.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldV, newV) -> preencherFormulario(newV)
        );
    }
    
    /**
     * Carrega os projetos do banco e atualiza a tabela.
     */
    private void carregarProjetos() {
        tabelaProjetos.setItems(FXCollections.observableArrayList(projetoDAO.listarTodos()));
    }
    
    /**
     * Carrega os usuários que podem ser gerentes (Técnicos e Administradores) e os popula no ComboBox.
     */
    private void carregarGerentes() {
        List<Usuario> possiveisGerentes = usuarioDAO.listarTodos().stream()
                .filter(u -> u.getCargo().equals("Técnico") || u.getCargo().equals("Administrador"))
                .collect(Collectors.toList());
        gerenteComboBox.setItems(FXCollections.observableArrayList(possiveisGerentes));

        gerenteComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Usuario usuario) {
                return usuario == null ? "" : usuario.getNomeCompleto();
            }
            @Override
            public Usuario fromString(String string) {
                return null;
            }
        });
    }

    /**
     * Preenche o formulário com os dados do projeto selecionado.
     *
     * @param projeto O {@link Projeto} selecionado, ou null.
     */
    private void preencherFormulario(Projeto projeto) {
        if (projeto != null) {
            nomeField.setText(projeto.getNomeProjeto());
            dataInicioPicker.setValue(projeto.getDataInicio());
            dataFimPicker.setValue(projeto.getDataTerminoPrevista());
            statusComboBox.setValue(projeto.getStatus());
            gerenteComboBox.setValue(projeto.getGerente());
            descricaoArea.setText(projeto.getDescricao());
        } else {
            limparFormulario();
        }
    }

    /**
     * Manipula o clique no botão "Salvar", criando ou atualizando um projeto.
     */
    @FXML
    private void handleSalvarButton() {
        Projeto selecionado = tabelaProjetos.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            Projeto novo = new Projeto(
                    nomeField.getText(),
                    descricaoArea.getText(),
                    dataInicioPicker.getValue(),
                    dataFimPicker.getValue(),
                    statusComboBox.getValue(),
                    gerenteComboBox.getValue()
            );
            projetoDAO.inserir(novo);
        } else {
            selecionado.setNomeProjeto(nomeField.getText());
            selecionado.setDescricao(descricaoArea.getText());
            selecionado.setDataInicio(dataInicioPicker.getValue());
            selecionado.setDataTerminoPrevista(dataFimPicker.getValue());
            selecionado.setStatus(statusComboBox.getValue());
            selecionado.setGerente(gerenteComboBox.getValue());
            projetoDAO.atualizar(selecionado);
        }
        carregarProjetos();
        limparFormulario();
    }

    /**
     * Limpa a seleção e o formulário.
     */
    @FXML
    private void handleNovoButton() {
        limparFormulario();
    }

    /**
     * Exclui o projeto selecionado após confirmação.
     */
    @FXML
    private void handleExcluirButton() {
        Projeto selecionado = tabelaProjetos.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de Exclusão");
            alert.setHeaderText("Deseja excluir o projeto '" + selecionado.getNomeProjeto() + "'?");
            
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                projetoDAO.deletar(selecionado.getId());
                carregarProjetos();
                limparFormulario();
            }
        }
    }
    
    /**
     * Reseta o formulário para seu estado inicial.
     */
    private void limparFormulario() {
        tabelaProjetos.getSelectionModel().clearSelection();
        nomeField.clear();
        descricaoArea.clear();
        dataInicioPicker.setValue(null);
        dataFimPicker.setValue(null);
        statusComboBox.setValue(null);
        gerenteComboBox.setValue(null);
    }
    
    /**
     * Método utilitário para formatar a exibição de datas em uma coluna da tabela.
     *
     * @param coluna A {@link TableColumn} a ser formatada.
     */
    private void formatarColunaData(TableColumn<Projeto, LocalDate> coluna) {
        coluna.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(formatter.format(item));
                }
            }
        });
    }
}