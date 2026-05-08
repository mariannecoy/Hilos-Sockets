/* =============================================================================
 *  Universidad: Pontificia Universidad Javeriana
 *  Facultad:    Ingeniería - Departamento de Ingeniería de Sistemas
 *  Materia:     Sistemas Distribuidos
 *  Taller:      Sockets y Threads en Java
 *  Archivo:     cliUDPsocket.java
 *  Autores:     Daniel Diaz y Marianne Coy
 *
 *  Descripción:
 *  ------------
 *  Cliente UDP que envía mensajes de texto a un servidor remoto a través de
 *  datagramas. UDP es un protocolo NO orientado a conexión: no existe un
 *  "handshake" previo, no garantiza entrega ni orden, pero es muy ligero y
 *  rápido. Cada mensaje se encapsula en un DatagramPacket y se envía al
 *  puerto 6000 del host indicado por argumento.
 *
 *  Uso:
 *  ----
 *      java cliUDPsocket <host_servidor>
 *
 *  El cliente termina cuando el usuario escribe una línea que comience con
 *  la palabra "fin".
 * ============================================================================= */

import java.net.*;
import java.io.*;

public class cliUDPsocket {

    public static void main(String argv[]) {

        // Validación de argumentos: se requiere la dirección del servidor.
        if (argv.length == 0) {
            System.err.println("Uso: java cliUDPsocket <servidor>");
            System.exit(1);
        }

        // Lector estándar para capturar mensajes desde el teclado.
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Prueba de sockets UDP (cliente)");

        DatagramSocket socket;            // Socket UDP del cliente
        InetAddress address;              // Dirección IP resuelta del servidor
        byte[] mensaje_bytes = new byte[256]; // Buffer de envío
        String mensaje = "";              // Mensaje en formato String
        DatagramPacket paquete;           // Datagrama a enviar

        mensaje_bytes = mensaje.getBytes();

        try {
            // 1) Creación del socket UDP en un puerto libre asignado por el SO.
            System.out.print("Creando socket... ");
            socket = new DatagramSocket();
            System.out.println("ok");

            // 2) Resolución DNS del nombre del host del servidor.
            System.out.print("Capturando direccion de host... ");
            address = InetAddress.getByName(argv[0]);
            System.out.println("ok");

            System.out.println("Introduce mensajes a enviar:");

            // 3) Bucle de envío: lee del teclado y envía hasta recibir "fin".
            do {
                mensaje = in.readLine();

                mensaje_bytes = mensaje.getBytes();

                // NOTA: se usa mensaje_bytes.length (longitud real en bytes)
                // y no mensaje.length() (caracteres), para evitar problemas
                // con caracteres multi-byte (acentos, ñ, emojis, etc.).
                paquete = new DatagramPacket(
                        mensaje_bytes,
                        mensaje_bytes.length,
                        address,
                        6000);

                socket.send(paquete);
            } while (!mensaje.startsWith("fin"));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
