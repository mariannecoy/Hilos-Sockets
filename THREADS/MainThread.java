/* =============================================================================
 *  Universidad: Pontificia Universidad Javeriana
 *  Facultad:    Ingeniería - Departamento de Ingeniería de Sistemas
 *  Materia:     Sistemas Distribuidos
 *  Taller:      Sockets y Threads en Java
 *  Archivo:     MainThread.java
 *  Autores:     Daniel Diaz y Marianne Coy
 *
 *  Descripción:
 *  ------------
 *  Programa principal en versión CONCURRENTE usando "extends Thread"
 *  (clase CajeraThread). Cada cajera es un hilo independiente que se
 *  lanza con start(), por lo que ambos procesamientos se ejecutan en
 *  paralelo.
 *
 *  Tiempo total esperado:
 *      Cliente 1 -> 2+2+1+5+2+3 = 15 seg
 *      Cliente 2 -> 1+3+5+1+1   = 11 seg
 *      Total concurrente        = ~max(15, 11) = ~15 seg
 *
 *  Comparación contra Main.java (secuencial):
 *      Secuencial   ~ 26 seg
 *      Concurrente  ~ 15 seg
 *      Speed-up     ~ 1.73x
 * ============================================================================= */

package tallerThreads;

public class MainThread {

    public static void main(String[] args) {

        // Definición de clientes y sus carritos.
        Cliente cliente1 = new Cliente("Cliente 1", new int[] { 2, 2, 1, 5, 2, 3 });
        Cliente cliente2 = new Cliente("Cliente 2", new int[] { 1, 3, 5, 1, 1 });

        // Tiempo de referencia común para las trazas de ambos hilos.
        long initialTime = System.currentTimeMillis();

        // Cada cajera ES un hilo (hereda de Thread).
        CajeraThread cajera1 = new CajeraThread("Cajera 1", cliente1, initialTime);
        CajeraThread cajera2 = new CajeraThread("Cajera 2", cliente2, initialTime);

        // start() programa el hilo en el scheduler de la JVM e invoca run()
        // de forma asíncrona. Llamar directamente a run() ejecutaría el
        // código en el hilo principal, perdiéndose la concurrencia.
        cajera1.start();
        cajera2.start();
    }
}
