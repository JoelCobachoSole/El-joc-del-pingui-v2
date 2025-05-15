package vista;

import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import modelo.Item;
import modelo.Pinguino;

public class pantallaJuegoController {

    // Menu items
    @FXML private MenuItem newGame;
    @FXML private MenuItem saveGame;
    @FXML private MenuItem loadGame;
    @FXML private MenuItem quitGame;

    // Buttons
    @FXML private Button dado;
    @FXML private Button rapido;
    @FXML private Button lento;
    @FXML private Button peces;
    @FXML private Button nieve;

    // Texts
    @FXML private Text dadoResultText;
    @FXML private Text rapido_t;
    @FXML private Text lento_t;
    @FXML private Text peces_t;
    @FXML private Text nieve_t;
    @FXML private Text eventos;

    // Game board and player pieces
    @FXML private GridPane tablero;
    @FXML private Circle P1;
    @FXML private Circle P2;
    @FXML private Circle P3;
    @FXML private Circle P4;

    // Player positions and turn management
    private int[] playerPositions = {0, 0, 0, 0}; // Positions for P1, P2, P3, P4
    private final int COLUMNS = 5;
    private Pinguino[] jugadores = new Pinguino[4]; // 4 jugadores
    private int currentPlayer = 0; // Jugador actual

    private Pinguino jugadorActual;

    @FXML
    private void initialize() {
        eventos.setText("¡El juego ha comenzado!");

        // Inicializar jugadores y sus inventarios
        for (int i = 0; i < jugadores.length; i++) {
            jugadores[i] = new Pinguino(0, "Jugador " + (i + 1), "Color" + i, new modelo.Inventario());
            jugadores[i].getInv().añadirItem(new Item("Dado Normal", 3));
            jugadores[i].getInv().añadirItem(new Item("Dado Especial", 2));
            jugadores[i].getInv().añadirItem(new Item("Pez", 2));
            jugadores[i].getInv().añadirItem(new Item("Bola de Nieve", 6));
        }

        // Colocar las fichas en la posición inicial
        GridPane.setRowIndex(P1, 0);
        GridPane.setColumnIndex(P1, 0);
        GridPane.setRowIndex(P2, 0);
        GridPane.setColumnIndex(P2, 0);
        GridPane.setRowIndex(P3, 0);
        GridPane.setColumnIndex(P3, 0);
        GridPane.setRowIndex(P4, 0);
        GridPane.setColumnIndex(P4, 0);
    }

    // Button and menu actions

    @FXML
    private void handleNewGame() {
        System.out.println("New game.");
        // TODO
    }

    @FXML
    private void handleSaveGame() {
        System.out.println("Saved game.");
        // TODO
    }

    @FXML
    private void handleLoadGame() {
        System.out.println("Loaded game.");
        // TODO
    }

    @FXML
    private void handleQuitGame() {
        System.out.println("Exit...");
        // TODO
    }

    @FXML
    private void handleDado(ActionEvent event) {
        tirarDado();
    }

    private void tirarDado() {
        // Mostrar un cuadro de diálogo para elegir el tipo de dado
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Tirar dado");
        alert.setHeaderText("Elige el tipo de dado que quieres tirar:");
        alert.setContentText("Selecciona una opción:");

        ButtonType buttonNormal = new ButtonType("Dado Normal");
        ButtonType buttonEspecial = new ButtonType("Dado Especial");
        ButtonType buttonCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonNormal, buttonEspecial, buttonCancel);

        // Obtener la elección del jugador
        ButtonType result = alert.showAndWait().orElse(buttonCancel);

        int resultado;
        if (result == buttonNormal) {
            // Tirar dado normal
            resultado = (int) (Math.random() * 6) + 1; // Dado normal: 1-6
        } else if (result == buttonEspecial) {
            // Verificar si el jugador tiene dados especiales
            if (jugadores[currentPlayer].getInv().contarItemsPorNombre("Dado Especial") > 0) {
                // Mostrar una lista de los dados especiales disponibles
                Alert elegirDado = new Alert(Alert.AlertType.CONFIRMATION);
                elegirDado.setTitle("Elegir Dado Especial");
                elegirDado.setHeaderText("Selecciona un dado especial para tirar:");
                elegirDado.setContentText("Selecciona una opción:");

                ButtonType dadoRapido = new ButtonType("Dado Rápido");
                ButtonType dadoLento = new ButtonType("Dado Lento");
                ButtonType buttonCancelEspecial = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

                elegirDado.getButtonTypes().setAll(dadoRapido, dadoLento, buttonCancelEspecial);

                ButtonType dadoElegido = elegirDado.showAndWait().orElse(buttonCancelEspecial);

                if (dadoElegido == dadoRapido) {
                    resultado = (int) (Math.random() * 6) + 5; // Dado rápido: 5-10
                    jugadores[currentPlayer].getInv().quitarItem(new Item("Dado Especial", 1)); // Consumir un dado especial
                } else if (dadoElegido == dadoLento) {
                    resultado = (int) (Math.random() * 5) + 1; // Dado lento: 1-5
                    jugadores[currentPlayer].getInv().quitarItem(new Item("Dado Especial", 1)); // Consumir un dado especial
                } else {
                    // Cancelar
                    return;
                }
            } else {
                mostrarAlerta("Inventario vacío", "No tienes dados especiales disponibles.");
                return;
            }
        } else {
            // Cancelar
            return;
        }

        dadoResultText.setText("Ha salido: " + resultado);

        // Mover al jugador actual
        movePlayer(currentPlayer, resultado);

        // Cambiar al siguiente jugador
        currentPlayer = (currentPlayer + 1) % jugadores.length;
    }

    private void movePlayer(int playerIndex, int steps) {
        playerPositions[playerIndex] += steps;

        // Limitar la posición del jugador al tamaño del tablero
        int totalCells = 50; // 5 columnas x 10 filas
        if (playerPositions[playerIndex] >= totalCells) {
            playerPositions[playerIndex] = totalCells - 1; // Última celda
        }

        // Calcular la fila y columna en el tablero
        int row = playerPositions[playerIndex] / COLUMNS;
        int col = playerPositions[playerIndex] % COLUMNS;

        // Obtener el círculo del jugador
        Circle playerCircle = getPlayerCircle(playerIndex);

        // Obtener la fila y columna actuales
        Integer currentRow = GridPane.getRowIndex(playerCircle);
        Integer currentCol = GridPane.getColumnIndex(playerCircle);
        if (currentRow == null) currentRow = 0;
        if (currentCol == null) currentCol = 0;

        // Calcular desplazamiento en píxeles
        double offsetX = (col - currentCol) * (tablero.getWidth() / COLUMNS);
        double offsetY = (row - currentRow) * (tablero.getHeight() / tablero.getRowConstraints().size());

        // Crear una transición para mover al jugador
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), playerCircle);
        transition.setByX(offsetX);
        transition.setByY(offsetY);

        // Actualizar la posición en el GridPane al finalizar la animación
        transition.setOnFinished(event -> {
            GridPane.setRowIndex(playerCircle, row);
            GridPane.setColumnIndex(playerCircle, col);
            playerCircle.setTranslateX(0); // Resetear la traslación
            playerCircle.setTranslateY(0);
        });

        transition.play();
    }

    private Circle getPlayerCircle(int playerIndex) {
        switch (playerIndex) {
            case 0: return P1;
            case 1: return P2;
            case 2: return P3;
            case 3: return P4;
            default: throw new IllegalArgumentException("Invalid player index: " + playerIndex);
        }
    }

    @FXML
    private void handleRapido() {
        System.out.println("Fast.");
        // TODO
    }

    @FXML
    private void handleLento() {
        System.out.println("Slow.");
        // TODO
    }

    @FXML
    private void handlePeces() {
        System.out.println("Fish.");
        // TODO
    }

    @FXML
    private void handleNieve() {
        System.out.println("Snow.");
        // TODO
    }

    @FXML
    private void handleInventario() {
        Pinguino jugadorActual = jugadores[currentPlayer];
        if (jugadorActual != null) {
            StringBuilder inventarioTexto = new StringBuilder();
            inventarioTexto.append("Inventario de ").append(jugadorActual.getNombre()).append(":\n\n");

            if (jugadorActual.getInv().getLista().isEmpty()) {
                inventarioTexto.append("El inventario está vacío.");
            } else {
                jugadorActual.getInv().getLista().forEach(item -> {
                    inventarioTexto.append("- ").append(item.getNombre())
                            .append(" (Cantidad: ").append(item.getCantidad()).append(")\n");
                });
            }

            // Mostrar el inventario en un cuadro de diálogo
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Inventario");
            alert.setHeaderText("Contenido del Inventario");
            alert.setContentText(inventarioTexto.toString());
            alert.showAndWait();
        } else {
            System.out.println("No se pudo obtener el jugador actual.");
        }
    }

    @FXML
    private void handleBolaDeNieve(ActionEvent event) {
        if (jugadorActual.getInv().contarItemsPorNombre("Bola de Nieve") > 0) {
            eventos.setText("¡Has usado una bola de nieve!");
            jugadorActual.getInv().quitarItem(new Item("Bola de Nieve", 1));
        } else {
            mostrarAlerta("Inventario vacío", "No tienes bolas de nieve disponibles.");
        }
    }

    @FXML
    private void handleSubornarOso(ActionEvent event) {
        if (jugadorActual.getInv().contarItemsPorNombre("Pez") > 0) {
            eventos.setText("¡Has subornado al oso!");
            jugadorActual.getInv().quitarItem(new Item("Pez", 1));
        } else {
            mostrarAlerta("Inventario vacío", "No tienes peces para subornar al oso.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
