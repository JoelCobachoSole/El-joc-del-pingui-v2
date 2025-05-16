package vista;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import dao.JugadorDAO;
import modelo.EstadoJuego;
import modelo.JugadorDB;
import modelo.Tablero;
import controlador.PartidaManager;

import java.io.File;
import java.util.Optional;

public class pantallaPrincipalController {

    private static final String SAVE_FOLDER = "partidas";
    private final JugadorDAO dao = new JugadorDAO();
    private JugadorDB jugadorActual;

    @FXML private MenuItem newGame;
    @FXML private MenuItem saveGame;
    @FXML private MenuItem loadGame;
    @FXML private MenuItem quitGame;

    @FXML private TextField userField;
    @FXML private PasswordField passField;

    @FXML private Button loginButton;
    @FXML private Button registerButton;

    @FXML
    private void initialize() {
        System.out.println("pantallaPrincipalController initialized");
        File carpeta = new File(SAVE_FOLDER);
        if (!carpeta.exists())
            carpeta.mkdir();
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = userField.getText();
        String password = passField.getText();

        JugadorDB jugador = dao.findByNombre(username);

        if (jugador != null && jugador.getPassword().equals(password)) {
            jugadorActual = jugador;
            System.out.println("Login correcto para: " + jugador.getNombre());

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/pantallaJuego.fxml"));
                Parent root = loader.load();

                pantallaJuegoController controller = loader.getController();
                controller.setJugador(jugadorActual);
                controller.setTurno(0); // Si aplica

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Pantalla de Juego");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Usuario o contraseña incorrectos.");
        }
    }

    @FXML
    private void handleRegister() {
        String username = userField.getText();
        String password = passField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Nombre o contraseña vacíos.");
            return;
        }

        if (dao.findByNombre(username) != null) {
            System.out.println("Usuario ya existe.");
            return;
        }

        JugadorDB nuevo = new JugadorDB(0, username, "azul", password, 1, null);
        if (dao.insertNewUser(nuevo)) {
            System.out.println("Usuario registrado con éxito.");
        } else {
            System.out.println("Error al registrar usuario.");
        }
    }

    @FXML
    private void handleNewGame(ActionEvent event) {
        if (jugadorActual == null) {
            System.out.println("Debes iniciar sesión primero.");
            return;
        }

        jugadorActual.setPosicion(0);
        jugadorActual.setNivel(1);
        jugadorActual.setPartida("Nueva partida iniciada");

        Tablero tableroNuevo = new Tablero();
        tableroNuevo.generarTablero();

        EstadoJuego estado = new EstadoJuego(jugadorActual, tableroNuevo, 0);
        String path = SAVE_FOLDER + "/" + jugadorActual.getNombre() + ".bin";
        PartidaManager.guardarPartida(estado, path);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pantallaJuego.fxml"));
            Parent root = loader.load();

            pantallaJuegoController controller = loader.getController();
            controller.setJugador(jugadorActual);
            controller.setTablero(tableroNuevo);
            controller.setTurno(0);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Nueva Partida");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar la pantalla del juego.");
        }
    }

    @FXML
    private void handleSaveGame() {
        if (jugadorActual != null) {
            jugadorActual.setPartida("Estado del juego actual");

            Tablero tablero = new Tablero(); // Usa el real si lo tienes
            tablero.generarTablero();

            EstadoJuego estado = new EstadoJuego(jugadorActual, tablero, 0);
            String path = SAVE_FOLDER + "/" + jugadorActual.getNombre() + ".bin";
            PartidaManager.guardarPartida(estado, path);
        } else {
            System.out.println("Debes iniciar sesión primero.");
        }
    }

    @FXML
    private void handleLoadGame() {
        if (jugadorActual == null) {
            System.out.println("Debes iniciar sesión primero.");
            return;
        }

        String path = SAVE_FOLDER + "/" + jugadorActual.getNombre() + ".bin";
        File archivo = new File(path);

        if (archivo.exists()) {
            EstadoJuego estado = PartidaManager.cargarPartida(path);
            if (estado != null) {
                jugadorActual = estado.getJugador();
                System.out.println("Partida de " + jugadorActual.getNombre() + " cargada.");
            }
        } else {
            System.out.println("No hay partida guardada para este usuario.");
        }
    }

    @FXML
    private void handleQuitGame() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Salir del juego");
        alert.setHeaderText("¿Estás seguro de que quieres salir?");
        alert.setContentText("Tu progreso se guardará automáticamente.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (jugadorActual != null) {
                jugadorActual.setPartida("Auto-guardado al salir");

                Tablero tablero = new Tablero(); // Usa el real si lo tienes
                tablero.generarTablero();

                EstadoJuego estado = new EstadoJuego(jugadorActual, tablero, 0);
                String path = SAVE_FOLDER + "/" + jugadorActual.getNombre() + ".bin";
                PartidaManager.guardarPartida(estado, path);

                System.out.println("Partida guardada automáticamente al salir.");
            } else {
                System.out.println("No hay jugador activo. No se puede guardar.");
            }

            System.exit(0);
        }
    }
}
