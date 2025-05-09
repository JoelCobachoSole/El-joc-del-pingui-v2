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
    private int currentPlayer = 0; // Tracks the current player's turn (0 to 3)

    @FXML
    private void initialize() {
        eventos.setText("¡El juego ha comenzado!");
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
        Random rand = new Random();
        int diceResult = rand.nextInt(6) + 1;

        // Update the Text
        dadoResultText.setText("Ha salido: " + diceResult);

        // Move the current player
        movePlayer(currentPlayer, diceResult);

        // Switch to the next player's turn
        currentPlayer = (currentPlayer + 1) % 4; // Cycle through players 0 to 3
        eventos.setText("Turno del jugador " + (currentPlayer + 1));
    }

    private void movePlayer(int playerIndex, int steps) {
        playerPositions[playerIndex] += steps;

        // Bound player position
        if (playerPositions[playerIndex] >= 50) {
            playerPositions[playerIndex] = 49; // Limit to the last cell
        }

        // Check row and column
        int row = playerPositions[playerIndex] / COLUMNS;
        int col = playerPositions[playerIndex] % COLUMNS;

        // Get the player's Circle
        Circle playerCircle = getPlayerCircle(playerIndex);

        // Get current row and column
        int currentRow = GridPane.getRowIndex(playerCircle) != null ? GridPane.getRowIndex(playerCircle) : 0;
        int currentCol = GridPane.getColumnIndex(playerCircle) != null ? GridPane.getColumnIndex(playerCircle) : 0;

        // Calculate pixel offsets for animation
        double offsetX = (col - currentCol) * tablero.getWidth() / COLUMNS;
        double offsetY = (row - currentRow) * tablero.getHeight() / (50 / COLUMNS);

        // Create a TranslateTransition for smooth movement
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), playerCircle);
        transition.setByX(offsetX);
        transition.setByY(offsetY);

        // After the animation, update the GridPane position
        transition.setOnFinished(event -> {
            GridPane.setRowIndex(playerCircle, row);
            GridPane.setColumnIndex(playerCircle, col);
            playerCircle.setTranslateX(0); // Reset translation
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
        // Suponiendo que el jugador actual es un Pinguino
        Pinguino jugadorActual = obtenerJugadorActual(); // Implementa este método según tu lógica
        if (jugadorActual != null) {
            StringBuilder inventarioTexto = new StringBuilder();
            if (jugadorActual.getInv().getLista().isEmpty()) {
                inventarioTexto.append("El inventario está vacío.");
            } else {
                inventarioTexto.append("Inventario:\n");
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

    private Pinguino obtenerJugadorActual() {
        // Implementa la lógica para obtener el jugador actual
        // Por ejemplo, si tienes un objeto Tablero que gestiona los turnos:
        // return (Pinguino) tablero.getJugadorActual();
        return null; // Cambia esto por la lógica real
    }
}
