package com.anima.esportsmanager;

/**
 * Classe de Lançamento (Launcher) para contornar problemas de módulo do JavaFX
 * ao criar um JAR executável.
 */
public class Launcher {
    public static void main(String[] args) {
        // Chama o main da classe principal da aplicação JavaFX
        ESportsManager.main(args);
    }
}