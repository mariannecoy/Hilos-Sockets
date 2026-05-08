/* =============================================================================
 *  Universidad: Pontificia Universidad Javeriana
 *  Facultad:    Ingeniería - Departamento de Ingeniería de Sistemas
 *  Materia:     Sistemas Distribuidos
 *  Taller:      Sockets y Threads en Java
 *  Archivo:     cliTCPsocket.java
 *  Autores:     Daniel Diaz y Marianne Coy
 *
 *  Descripción:
 *  ------------
 *  Cliente TCP que establece una conexión orientada a sesión con un servidor
 *  remoto en el puerto 6001. A diferencia de UDP, TCP garantiza:
 *      - Entrega confiable (con retransmisión).
 *      - Orden de los mensajes.
 *      - Control de flujo y de congestión.
 *
 *  El cliente envía cadenas UTF-8 a través del flujo de salida del socket
 *  hasta que se introduce un mensaje que empiece con "fin".
 *
 *  Uso:
 *  ----
 *      java cliTCPsocket <host_servidor>
 * ============================================================================= */

import java.net.*;
import java.io.*;

public class cliTCPsocket {

    public static void main(String argv[]) {

        // Validación de argumentos: se requiere la dirección del servidor.
        if (argv.length == 0) {
            System.err.println("Uso: java cliTCPsocket <servidor>");
            System.exit(1);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Prueba de sockets TCP (CLIENTE)");

        Socket socket;                        // Socket TCP del cliente
        InetAddress address;                  // Dirección IP resuelta del servidor
        byte[] mensaje_bytes = new byte[256]; // (no se usa directamente)
        String mensaje = "";                  // Mensaje a enviar

        try {
            // 1) Resolución DNS del nombre del host del servidor.
            System.out.print("Capturando direccion de host... ");
            address = InetAddress.getByName(argv[0]);
            System.out.println("ok");

            // 2) Establecimiento de la conexión TCP (3-way handshake).
            System.out.print("Creando socket... ");
            socket = new Socket(address, 6001);
            System.out.println("ok");

            // 3) Flujo de salida orientado a datos primitivos (incluye writeUTF).
            DataOutputStream out =
                    new DataOutputStream(socket.getOutputStream());

            System.out.println("Introduce mensajes a enviar:");

            // 4) Bucle de envío: writeUTF antepone la longitud y usa Modified UTF-8.
            do {
                mensaje = in.readLine();
                out.writeUTF(mensaje);
            } while (!mensaje.startsWith("fin"));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
