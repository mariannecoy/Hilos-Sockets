/* =============================================================================
 *  Universidad: Pontificia Universidad Javeriana
 *  Facultad:    Ingeniería - Departamento de Ingeniería de Sistemas
 *  Materia:     Sistemas Distribuidos
 *  Taller:      Sockets y Threads en Java
 *  Archivo:     serUDPsocket.java
 *  Autores:     Daniel Diaz y Marianne Coy
 *
 *  Descripción:
 *  ------------
 *  Servidor UDP que escucha en el puerto 6000 y recibe datagramas de uno o
 *  varios clientes. Por la naturaleza no orientada a conexión de UDP, el
 *  servidor NO mantiene una sesión con el cliente: cada datagrama llega de
 *  forma independiente y puede provenir de cualquier emisor.
 *
 *  El servidor termina cuando recibe un mensaje que comienza con "fin".
 *
 *  Uso:
 *  ----
 *      java serUDPsocket
 * ============================================================================= */

import java.net.*;
import java.io.*;

public class serUDPsocket {

    public static void main(String argv[]) {

        System.out.println("Prueba de sockets UDP (servidor)");

        DatagramSocket socket;        // Socket UDP del servidor
        boolean fin = false;          // Bandera de terminación

        try {
            // 1) Creación del socket UDP enlazado al puerto 6000.
            System.out.print("Creando socket... ");
            socket = new DatagramSocket(6000);
            System.out.println("ok");

            System.out.println("Recibiendo mensajes... ");

            // 2) Bucle de recepción: bloquea hasta recibir un datagrama.
            do {
                byte[] mensaje_bytes = new byte[256];

                DatagramPacket paquete = new DatagramPacket(mensaje_bytes, 256);

                // receive() es bloqueante: espera la llegada de un datagrama.
                socket.receive(paquete);

                // Conversión de bytes a String (usando codificación por defecto).
                String mensaje = new String(mensaje_bytes);

                System.out.println(mensaje);

                if (mensaje.startsWith("fin")) fin = true;
            } while (!fin);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
