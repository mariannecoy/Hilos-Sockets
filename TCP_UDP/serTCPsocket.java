/* =============================================================================
 *  Universidad: Pontificia Universidad Javeriana
 *  Facultad:    Ingeniería - Departamento de Ingeniería de Sistemas
 *  Materia:     Sistemas Distribuidos
 *  Taller:      Sockets y Threads en Java
 *  Archivo:     serTCPsocket.java
 *  Autores:     Daniel Diaz y Marianne Coy
 *
 *  Descripción:
 *  ------------
 *  Servidor TCP que escucha en el puerto 6001. A diferencia de UDP, este
 *  servidor establece una sesión con el cliente mediante el handshake de
 *  tres vías y mantiene un canal bidireccional confiable.
 *
 *  El método accept() es bloqueante y solo retorna cuando un cliente se
 *  conecta exitosamente. Una vez aceptada la conexión, el servidor lee
 *  cadenas UTF-8 enviadas por el cliente hasta que la conexión se cierra
 *  o se interrumpe el proceso.
 *
 *  Uso:
 *  ----
 *      java serTCPsocket
 * ============================================================================= */

import java.net.*;
import java.io.*;

public class serTCPsocket {

    public static void main(String argv[]) {

        System.out.println("\n\n\t=**SOCKETS TCP <<SERVIDOR>>");

        ServerSocket socket;          // Socket "de escucha" en el servidor

        try {
            // 1) Creación del socket de escucha en el puerto 6001.
            socket = new ServerSocket(6001);

            // 2) accept() bloquea hasta que llega un cliente; devuelve un
            //    socket dedicado a esa conexión específica.
            Socket socket_cli = socket.accept();

            // 3) Flujo de entrada orientado a datos primitivos (incluye readUTF).
            DataInputStream in =
                    new DataInputStream(socket_cli.getInputStream());

            // 4) Bucle de recepción.
            //    NOTA: el ciclo while(1>0) es intencionalmente infinito en
            //    el código original; en producción se debe manejar la
            //    desconexión (EOFException) o un mensaje "fin" para terminar.
            do {
                String mensaje = in.readUTF();
                System.out.println(mensaje);
            } while (1 > 0);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
