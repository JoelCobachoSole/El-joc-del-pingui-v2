package vista;

import java.util.Random;

import controlador.PartidaManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import javafx.scene.control.Alert.AlertType;
<<<<<<< Updated upstream
import modelo.Pinguino;
=======
import modelo.Item;
import modelo.JugadorDB;
import modelo.Pinguino;
import modelo.Tablero;
import modelo.Casilla;
import modelo.Agujero;
import modelo.Trineo;
import modelo.Oso;
import modelo.Evento;
import modelo.CasillaNormal;
import modelo.EstadoJuego;

>>>>>>> Stashed changes

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

    @FXML private Label nombreJugadorLabel;


    // Player positions and turn management
    private int[] playerPositions = {0, 0, 0, 0}; // Positions for P1, P2, P3, P4
    private final int COLUMNS = 5;
<<<<<<< Updated upstream
    private int currentPlayer = 0; // Tracks the current player's turn (0 to 3)
=======
    private Pinguino[] jugadores = new Pinguino[4]; // 4 jugadores
    private int currentPlayer = 0; // Jugador actual

    private Tablero tableroLogico;
    private JugadorDB jugador;
    private int turnoActual = 0;

    public void setJugador(JugadorDB jugador) {
        this.jugador = jugador;
        inicializarJugadorEnPantalla();
    }

    private void inicializarJugadorEnPantalla() {
        if (jugador != null) {
            System.out.println("Jugador recibido: " + jugador.getNombre());
            // Ejemplo: si tienes un label en pantalla
            nombreJugadorLabel.setText("Jugador: " + jugador.getNombre());
        }
    }

    private void guardarPartida() {
    
        EstadoJuego estado = new EstadoJuego(jugador, tableroLogico, turnoActual);

        String ruta = "partidas/" + jugador.getNombre() + ".bin";
        PartidaManager.guardarPartida(estado, ruta);
    }

    private void cargarPartida() {
        String ruta = "partidas/" + jugador.getNombre() + ".bin";
        EstadoJuego estado = PartidaManager.cargarPartida(ruta);

        if (estado != null) {

            this.jugador = estado.getJugador();
            this.tableroLogico = estado.getTablero();
            this.turnoActual = estado.getTurnoActual();

            System.out.println("Partida cargada para " + jugador.getNombre());

            // Puedes reconfigurar visualmente el tablero aquí si hace falta
            inicializarJugadorEnPantalla();
        } else {
            System.out.println("No se pudo cargar la partida.");
        }
    }

>>>>>>> Stashed changes

    @FXML
    private void initialize() {
        eventos.setText("¡El juego ha comenzado!");
<<<<<<< Updated upstream
=======

        if (tableroLogico == null) {
            tableroLogico = new Tablero(new java.util.ArrayList<>(), new java.util.ArrayList<>(), 0, null);
            tableroLogico.generarTablero();
        }

        mostrarCasillasEspeciales();

        // Si los jugadores no han sido inicializados aún
        if (tableroLogico.getJugadores().isEmpty()) {
            for (int i = 0; i < jugadores.length; i++) {
                jugadores[i] = new Pinguino(0, "Jugador " + (i + 1), "Color" + i, new modelo.Inventario());
                jugadores[i].getInv().añadirItem(new Item("Dado Normal", 3));
                jugadores[i].getInv().añadirItem(new Item("Dado Especial", 2));
                jugadores[i].getInv().añadirItem(new Item("Pez", 2));
                jugadores[i].getInv().añadirItem(new Item("Bola de Nieve", 6));
                jugadores[i].setTablero(tableroLogico);
                tableroLogico.getJugadores().add(jugadores[i]);
            }
        }

        // Posicionar fichas al inicio
        GridPane.setRowIndex(P1, 0);
        GridPane.setColumnIndex(P1, 0);
        GridPane.setRowIndex(P2, 0);
        GridPane.setColumnIndex(P2, 0);
        GridPane.setRowIndex(P3, 0);
        GridPane.setColumnIndex(P3, 0);
        GridPane.setRowIndex(P4, 0);
        GridPane.setColumnIndex(P4, 0);
    }

    private void mostrarCasillasEspeciales() {
        tablero.getChildren().removeIf(node -> node instanceof Text); // Limpia textos previos

        // Mostrar "START" en la primera casilla (posición 0)
        Text startText = new Text("START");
        startText.setFill(Color.GREEN);
        startText.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");
        GridPane.setRowIndex(startText, 0);
        GridPane.setColumnIndex(startText, 0);
        tablero.getChildren().add(startText);

        // Mostrar el resto de casillas especiales
        for (int i = 1; i < tableroLogico.getCasillas().size(); i++) {
            Casilla casilla = tableroLogico.getCasillas().get(i);
            Text t = null;
            if (casilla instanceof Oso) {
                t = new Text("O");
                t.setFill(Color.BROWN);
            } else if (casilla instanceof Agujero) {
                t = new Text("A");
                t.setFill(Color.DARKBLUE);
            } else if (casilla instanceof Trineo) {
                t = new Text("T");
                t.setFill(Color.DARKRED);
            } else if (casilla instanceof Evento) {
                t = new Text("?");
                t.setFill(Color.ORANGE);
            }
            if (t != null) {
                int row = i / COLUMNS;
                int col = i % COLUMNS;
                GridPane.setRowIndex(t, row);
                GridPane.setColumnIndex(t, col);
                tablero.getChildren().add(t);
            }
        }
>>>>>>> Stashed changes
    }

    // Button and menu actions

    @FXML
    private void handleNewGame() {
        
        System.out.println("New game.");
   
    }

    @FXML
    private void handleSaveGame() {
        guardarPartida();
        System.out.println("Saved game.");

    }

    @FXML
    private void handleLoadGame() {
        cargarPartida();
        System.out.println("Loaded game.");
    }

    @FXML
    private void handleQuitGame() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Salir del juego");
        alert.setHeaderText("¿Estás seguro de que quieres salir?");
        alert.setContentText("Se guardará automáticamente tu partida.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                guardarPartida();
                System.exit(0);
            }
        });
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
<<<<<<< Updated upstream
            if (jugadorActual.getInv().getLista().isEmpty()) {
                inventarioTexto.append("El inventario está vacío.");
            } else {
                inventarioTexto.append("Inventario:\n");
                jugadorActual.getInv().getLista().forEach(item -> {
=======
            inventarioTexto.append("Inventario de ").append(jugadorActual.getNombre()).append(":\n\n");

            if (jugadorActual.getInv().getItems().isEmpty()) {
                inventarioTexto.append("El inventario está vacío.");
            } else {
                jugadorActual.getInv().getItems().forEach(item -> {
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
    private Pinguino obtenerJugadorActual() {
        // Implementa la lógica para obtener el jugador actual
        // Por ejemplo, si tienes un objeto Tablero que gestiona los turnos:
        // return (Pinguino) tablero.getJugadorActual();
        return null; // Cambia esto por la lógica real
=======
    @FXML
    private void handleBolaDeNieve(ActionEvent event) {
        Pinguino jugador = jugadores[currentPlayer];
        if (jugador.getInv().contarItemsPorNombre("Bola de Nieve") > 0) {
            eventos.setText("¡Has usado una bola de nieve!");
            jugador.getInv().quitarItem(new Item("Bola de Nieve", 1));
        } else {
            mostrarAlerta("Inventario vacío", "No tienes bolas de nieve disponibles.");
        }
    }

    @FXML
    private void handleSubornarOso(ActionEvent event) {
        Pinguino jugador = jugadores[currentPlayer];

        if (jugador.getInv().contarItemsPorNombre("Pez") > 0) {
            eventos.setText("¡Has subornado al oso!");
            jugador.getInv().quitarItem(new Item("Pez", 1));
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
>>>>>>> Stashed changes
    }

    public void setTablero(Tablero tablero) {
        this.tableroLogico = tablero;
        mostrarCasillasEspeciales();
    }

    public void setTurno(int turno) {
        this.turnoActual = turno;
    }

}