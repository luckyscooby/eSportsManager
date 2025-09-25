package com.anima.esportsmanager;

/**
 * Classe de Lançamento (Launcher) utilizada para criar o JAR executável ("fat JAR").
 * <p>
 * Esta classe serve como um ponto de entrada alternativo que não herda de {@link javafx.application.Application}.
 * Isso resolve problemas de modularidade do JavaFX ao empacotar a aplicação com todas as suas
 * dependências usando o `maven-shade-plugin`, garantindo que o JavaFX runtime seja
 * corretamente inicializado.
 */
public class Launcher {

    /**
     * Ponto de entrada principal do JAR executável.
     * Simplesmente invoca o método `main` da classe principal da aplicação JavaFX.
     *
     * @param args Argumentos de linha de comando.
     */
    public static void main(String[] args) {
        ESportsManager.main(args);
    }
}