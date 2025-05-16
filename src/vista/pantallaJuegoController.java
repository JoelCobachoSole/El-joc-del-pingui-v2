package vista;

import java.util.Random;

import controlador.PartidaManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.util.Duration;
import javafx.scene.control.Alert.AlertType;
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
    @FXML private Button botonInventario;
    @FXML private Button bolaDeNieve;
    @FXML private Button subornarOso;

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
    private Pinguino[] jugadores = new Pinguino[4]; // 4 jugadores
    private int currentPlayer = 0; // Jugador actual

    private Tablero tableroLogico;
    private JugadorDB jugador;

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


    @FXML
    private void initialize() {
        eventos.setText("¡El juego ha comenzado!");

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

        Text finishText = new Text("FINISH");
        finishText.setFill(Color.RED);
        finishText.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");
        int row = 49 / COLUMNS;
        int col = 49 % COLUMNS;
        GridPane.setRowIndex(finishText, row);
        GridPane.setColumnIndex(finishText, col);
        tablero.getChildren().add(finishText);
    }

    // Button and menu actions

    @FXML
    private void handleNewGame() {
        System.out.println("Nueva partida iniciada");
        eventos.setText("Nueva partida iniciada");
        // Aquí va la lógica para reiniciar el juego si quieres
    }

    @FXML
    private void handleSaveGame() {
        System.out.println("Partida guardada");
        eventos.setText("Partida guardada");
        // Aquí puedes llamar a un método que guarde los datos en la base
    }

    @FXML
    private void handleLoadGame() {
        System.out.println("Partida cargada");
        eventos.setText("Partida cargada");
        // Aquí puedes implementar la lógica de recuperación de partida
    }

    @FXML
    private void handleQuitGame() {
        System.out.println("Saliendo del juego...");
        Platform.exit(); // Esto cierra la aplicación
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
        int totalCells = 50;
        if (playerPositions[playerIndex] >= totalCells) {
            playerPositions[playerIndex] = totalCells - 1;
        }

        // --- EFECTOS DE CASILLAS ESPECIALES ---
        Pinguino jugador = jugadores[playerIndex];
        int posicion = playerPositions[playerIndex];

        // Si el jugador ha llegado al final
        if (posicion == totalCells - 1) {
            eventos.setText("¡" + jugador.getNombre() + " ha ganado la partida!");

            Alert alertaVictoria = new Alert(Alert.AlertType.INFORMATION);
            alertaVictoria.setTitle("¡Victoria!");
            alertaVictoria.setHeaderText("Juego finalizado");
            alertaVictoria.setContentText("¡" + jugador.getNombre() + " ha llegado a la casilla 50 y ha ganado!");
            alertaVictoria.showAndWait();

            // Desactivamos botones del tablero
            dado.setDisable(true);
            rapido.setDisable(true);
            lento.setDisable(true);
            peces.setDisable(true);
            nieve.setDisable(true);
            botonInventario.setDisable(true);
            bolaDeNieve.setDisable(true);
            subornarOso.setDisable(true);

            return;
        }

        Casilla casilla = tableroLogico.getCasillas().get(posicion);

        if (casilla instanceof Oso) {
            eventos.setText("¡" + jugador.getNombre() + " ha sido atacado por el oso y vuelve al inicio!");
            playerPositions[playerIndex] = 0;
        } else if (casilla instanceof Agujero) {
            int posActual = posicion;
            int posForatAnterior = 0;
            for (int i = posActual - 1; i >= 0; i--) {
                if (tableroLogico.getCasillas().get(i) instanceof Agujero) {
                    posForatAnterior = i;
                    break;
                }
            }
            eventos.setText("¡" + jugador.getNombre() + " ha caído en un agujero y retrocede al anterior!");
            playerPositions[playerIndex] = posForatAnterior;
        } else if (casilla instanceof Trineo) {
            // Buscar el siguiente trineo
            int posActual = playerPositions[playerIndex];
            int posSeguentTrineo = -1;
            for (int i = posActual + 1; i < totalCells; i++) {
                if (tableroLogico.getCasillas().get(i) instanceof Trineo) {
                    posSeguentTrineo = i;
                    break;
                }
            }
            if (posSeguentTrineo == -1) {
                // Si no hay siguiente, no se mueve
                eventos.setText("¡" + jugador.getNombre() + " está en el último trineo!");
            } else {
                eventos.setText("¡" + jugador.getNombre() + " avanza al siguiente trineo!");
                playerPositions[playerIndex] = posSeguentTrineo;
            }
        } else if (casilla instanceof Evento) {
            // Evento aleatorio simple de ejemplo
            String[] eventosPosibles = {
                "¡Avanzas 2 casillas extra!",
                "¡Retrocedes 2 casillas!",
                "¡Pierdes un turno!",
                "¡Ganas un pez!"
            };
            int idx = new Random().nextInt(eventosPosibles.length);
            eventos.setText("Casilla de evento: " + eventosPosibles[idx]);
            // Ejemplo de efecto real:
            if (idx == 0) { // Avanza 2
                playerPositions[playerIndex] = Math.min(playerPositions[playerIndex] + 2, totalCells - 1);
            } else if (idx == 1) { // Retrocede 2
                playerPositions[playerIndex] = Math.max(playerPositions[playerIndex] - 2, 0);
            } else if (idx == 2) { // Pierde turno (puedes implementar una bandera para saltar turno)
                // Implementa lógica de perder turno si lo deseas
            } else if (idx == 3) { // Gana un pez
                jugador.getInv().añadirItem(new Item("Pez", 1));
            }
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
            playerCircle.setTranslateX(0);
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

            if (jugadorActual.getInv().getItems().isEmpty()) {
                inventarioTexto.append("El inventario está vacío.");
            } else {
                jugadorActual.getInv().getItems().forEach(item -> {
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
    }

    public void setTablero(Tablero tablero) {
        this.tableroLogico = tablero;
        mostrarCasillasEspeciales();
    }

}