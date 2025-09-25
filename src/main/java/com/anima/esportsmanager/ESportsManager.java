package com.anima.esportsmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Classe principal da aplicação, responsável por iniciar o ambiente JavaFX.
 * Esta classe herda de {@link Application} e é o ponto de entrada para a interface gráfica.
 */
public class ESportsManager extends Application {

    /**
     * Ponto de entrada principal para a aplicação JavaFX.
     * Este método é chamado após o método `launch()` ser invocado. Ele configura e exibe
     * a janela principal (Stage) com a cena inicial, que é o Dashboard.
     *
     * @param stage O contêiner principal da interface gráfica, fornecido pelo JavaFX.
     * @throws IOException Se o arquivo FXML do Dashboard não puder ser carregado.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ESportsManager.class.getResource("/view/DashboardView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("eSports Manager");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * O método main é tradicionalmente o ponto de entrada de uma aplicação Java.
     * Para aplicações JavaFX, seu principal papel é chamar o método {@link #launch(String...)},
     * que inicializa o toolkit do JavaFX e chama o método {@link #start(Stage)}.
     *
     * @param args Argumentos de linha de comando passados para a aplicação.
     */
    public static void main(String[] args) {
        launch(args);
    }
}