package controlador;

import modelo.EstadoJuego;

import java.io.*;

public class PartidaManager {

    public static void guardarPartida(EstadoJuego estado, String rutaArchivo) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            out.writeObject(estado);
            System.out.println("Estado del juego guardado en: " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar el estado del juego.");
            e.printStackTrace();
        }
    }

    public static EstadoJuego cargarPartida(String rutaArchivo) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            EstadoJuego estado = (EstadoJuego) in.readObject();
            System.out.println("Estado del juego cargado desde: " + rutaArchivo);
            return estado;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar el estado del juego.");
            e.printStackTrace();
            return null;
        }
    }
}
