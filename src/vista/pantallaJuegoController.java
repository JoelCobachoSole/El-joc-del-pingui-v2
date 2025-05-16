package vista;

import java.util.Random;
import java.util.ArrayList;

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
import modelo.Tablero;
import modelo.Casilla;
import modelo.Oso;
import modelo.Agujero;
import modelo.Trineo;
import modelo.Evento;
import modelo.CasillaNormal;
import modelo.Inventario;

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

    private Tablero tableroLogico;

    @FXML
    private void initialize() {
        eventos.setText("¡El juego ha comenzado!");

        ArrayList<modelo.Jugador> jugadores = new ArrayList<>();
        jugadores.add(new Pinguino(0, "Jugador1", "Azul", new Inventario()));
        jugadores.add(new Pinguino(0, "Jugador2", "Rojo", new Inventario()));
        jugadores.add(new Pinguino(0, "Jugador3", "Verde", new Inventario()));
        jugadores.add(new Pinguino(0, "Jugador4", "Amarillo", new Inventario()));

        tableroLogico = crearTableroAleatorio(jugadores);

        mostrarTablero();
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

        // Cambia de turno DESPUÉS de mostrar el mensaje
        currentPlayer = (currentPlayer + 1) % 4; // Cycle through players 0 to 3
        // NO sobreescribas el mensaje aquí
    }

    private void movePlayer(int playerIndex, int steps) {
        playerPositions[playerIndex] += steps;
        if (playerPositions[playerIndex] >= 50) {
            playerPositions[playerIndex] = 49;
        }

        modelo.Jugador jugador = tableroLogico.getJugadores().get(playerIndex);
        jugador.setPosicion(playerPositions[playerIndex]);

        int nuevaPos = playerPositions[playerIndex];
        modelo.Casilla casilla = tableroLogico.getCasillas().get(nuevaPos);

        // Animación inicial: mueve al jugador a la casilla donde ha caído
        int row = nuevaPos / COLUMNS;
        int col = nuevaPos % COLUMNS;
        Circle playerCircle = getPlayerCircle(playerIndex);
        int currentRow = GridPane.getRowIndex(playerCircle) != null ? GridPane.getRowIndex(playerCircle) : 0;
        int currentCol = GridPane.getColumnIndex(playerCircle) != null ? GridPane.getColumnIndex(playerCircle) : 0;
        double offsetX = (col - currentCol) * tablero.getWidth() / COLUMNS;
        double offsetY = (row - currentRow) * tablero.getHeight() / (50 / COLUMNS);

        TranslateTransition transition = new TranslateTransition(Duration.millis(500), playerCircle);
        transition.setByX(offsetX);
        transition.setByY(offsetY);

        transition.setOnFinished(event -> {
            GridPane.setRowIndex(playerCircle, row);
            GridPane.setColumnIndex(playerCircle, col);
            playerCircle.setTranslateX(0);
            playerCircle.setTranslateY(0);

            // Espera 1 segundo y luego realiza la acción especial si corresponde
            javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> {
                String mensaje = "";
                String consola = "";
                int posAntes = jugador.getPosicion();

                if (casilla instanceof modelo.Oso) {
                    mensaje = ((modelo.Oso) casilla).realizarAccion(jugador);
                    consola = jugador.getNombre() + " ha caído en un oso y es devuelto al inicio.";
                } else if (casilla instanceof modelo.Agujero) {
                    int posAnterior = jugador.getPosicion();
                    mensaje = ((modelo.Agujero) casilla).realizarAccion(jugador);
                    if (jugador.getPosicion() != posAnterior) {
                        consola = jugador.getNombre() + " ha caído en un agujero y retrocede a la casilla " + jugador.getPosicion() + ".";
                    } else {
                        consola = jugador.getNombre() + " ha caído en un agujero pero no hay agujero anterior.";
                    }
                } else if (casilla instanceof modelo.Trineo) {
                    int posAnterior = jugador.getPosicion();
                    mensaje = ((modelo.Trineo) casilla).realizarAccion(jugador);
                    if (jugador.getPosicion() != posAnterior) {
                        consola = jugador.getNombre() + " ha caído en un trineo y avanza a la casilla " + jugador.getPosicion() + ".";
                    } else {
                        consola = jugador.getNombre() + " ha caído en un trineo pero no hay más trineos adelante.";
                    }
                } else if (casilla instanceof modelo.Evento) {
                    mensaje = ((modelo.Evento) casilla).realizarAccion(jugador);
                    consola = jugador.getNombre() + " ha caído en un evento: " + mensaje.replace("¡Evento activado: ", "").replace("!", "") + ".";
                }

                // Solo imprime si es casilla especial
                if (!consola.isEmpty()) {
                    System.out.println(consola);
                }
                // Solo muestra mensaje si es especial
                if (!mensaje.isEmpty()) {
                    eventos.setText(mensaje);
                }

                // Si la acción especial cambia la posición, anima de nuevo
                int finalPos = jugador.getPosicion();
                if (finalPos != nuevaPos) {
                    int finalRow = finalPos / COLUMNS;
                    int finalCol = finalPos % COLUMNS;
                    double finalOffsetX = (finalCol - col) * tablero.getWidth() / COLUMNS;
                    double finalOffsetY = (finalRow - row) * tablero.getHeight() / (50 / COLUMNS);

                    TranslateTransition specialTransition = new TranslateTransition(Duration.millis(500), playerCircle);
                    specialTransition.setByX(finalOffsetX);
                    specialTransition.setByY(finalOffsetY);
                    specialTransition.setOnFinished(ev -> {
                        GridPane.setRowIndex(playerCircle, finalRow);
                        GridPane.setColumnIndex(playerCircle, finalCol);
                        playerCircle.setTranslateX(0);
                        playerCircle.setTranslateY(0);
                    });
                    specialTransition.play();
                }
                // Actualiza la posición lógica del jugador
                playerPositions[playerIndex] = jugador.getPosicion();
            });
            pause.play();
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

    private void mostrarTablero() {
        tablero.getChildren().removeIf(node -> node instanceof Label); // Limpia solo los labels previos
        int columnas = 5;
        for (int i = 0; i < 50; i++) {
            Casilla casilla = tableroLogico.getCasillas().get(i);
            int row = i / columnas;
            int col = i % columnas;

            String texto = "";
            if (casilla instanceof Oso) {
                texto = "Oso";
            } else if (casilla instanceof Agujero) {
                texto = "Agujero";
            } else if (casilla instanceof Trineo) {
                texto = "Trineo";
            } else if (casilla instanceof Evento) {
                texto = "Evento";
            }

            if (!texto.isEmpty()) {
                Label label = new Label(texto);
                tablero.add(label, col, row);
            }
        }
    }

    private Tablero crearTableroAleatorio(ArrayList<modelo.Jugador> jugadores) {
        int totalCasillas = 50;
        ArrayList<modelo.Casilla> casillas = new ArrayList<>(totalCasillas);
        for (int i = 0; i < totalCasillas; i++) casillas.add(null);

        java.util.Random rand = new java.util.Random();
        java.util.HashSet<Integer> posicionesUsadas = new java.util.HashSet<>();

        // Helper para obtener posiciones únicas (evita la 0 y la 49)
        java.util.function.IntSupplier getLibre = () -> {
            int pos;
            do {
                pos = rand.nextInt(totalCasillas);
            } while (posicionesUsadas.contains(pos) || pos == 0 || pos == 49);
            posicionesUsadas.add(pos);
            return pos;
        };

        // 2 Osos
        for (int i = 0; i < 2; i++) {
            int pos = getLibre.getAsInt();
            casillas.set(pos, new Oso(pos, new ArrayList<>()));
        }
        // 7 Agujeros
        for (int i = 0; i < 7; i++) {
            int pos = getLibre.getAsInt();
            casillas.set(pos, new Agujero(pos, new ArrayList<>()));
        }
        // 4 Trineos
        for (int i = 0; i < 4; i++) {
            int pos = getLibre.getAsInt();
            casillas.set(pos, new Trineo(pos, new ArrayList<>()));
        }
        // 8 Eventos
        for (int i = 0; i < 8; i++) {
            int pos = getLibre.getAsInt();
            casillas.set(pos, new Evento(pos, new ArrayList<>(), "aleatorio"));
        }
        // El resto normales
        for (int i = 0; i < totalCasillas; i++) {
            if (casillas.get(i) == null) {
                casillas.set(i, new CasillaNormal(i, new ArrayList<>()));
            }
        }

        // Puedes poner aquí el jugador actual real si lo necesitas
        modelo.Jugador jugadorActual = jugadores.get(0);
        Tablero tablero = new Tablero(casillas, jugadores, 0, jugadores.get(0));
        // Assignar el taulell a cada casella
        for (modelo.Casilla c : casillas) {
            c.setTablero(tablero);
        }
        return tablero;
    }
}
