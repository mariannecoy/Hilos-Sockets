/* =============================================================================
 *  Universidad: Pontificia Universidad Javeriana
 *  Facultad:    Ingeniería - Departamento de Ingeniería de Sistemas
 *  Materia:     Sistemas Distribuidos
 *  Taller:      Sockets y Threads en Java
 *  Archivo:     MainRunnable.java
 *  Autores:     Daniel Diaz y Marianne Coy
 *
 *  Descripción:
 *  ------------
 *  Programa principal en versión CONCURRENTE usando "implements Runnable".
 *  A diferencia de MainThread, aquí la lógica de la cajera (clase Cajera)
 *  está DESACOPLADA del mecanismo de concurrencia: la clase Cajera es un
 *  POJO normal y esta clase MainRunnable se encarga de "envolverla" en un
 *  Runnable y entregársela a un new Thread(...).
 *
 *  Ventajas frente a "extends Thread":
 *      - La clase de negocio puede heredar de OTRAS clases (Java no
 *        permite herencia múltiple).
 *      - Mejor separación de responsabilidades (SRP): el negocio no
 *        depende del mecanismo de hilos.
 *      - El mismo Runnable puede ejecutarse en distintos hilos o ser
 *        enviado a un ExecutorService / ThreadPool.
 *
 *  Tiempo total esperado:
 *      Cliente 1 -> 2+2+1+5+2+3 = 15 seg
 *      Cliente 2 -> 1+3+5+1+1   = 11 seg
 *      Total concurrente        = ~max(15, 11) = ~15 seg
 * ============================================================================= */

package tallerThreads;

public class MainRunnable implements Runnable {

    private Cliente cliente;
    private Cajera cajera;
    private long initialTime;

    /**
     * @param cliente     cliente a procesar.
     * @param cajera      cajera que procesará al cliente.
     * @param initialTime tiempo de referencia compartido.
     */
    public MainRunnable(Cliente cliente, Cajera cajera, long initialTime) {
        this.cajera = cajera;
        this.cliente = cliente;
        this.initialTime = initialTime;
    }

    public static void main(String[] args) {

        // Definición de clientes.
        Cliente cliente1 = new Cliente("Cliente 1", new int[] { 2, 2, 1, 5, 2, 3 });
        Cliente cliente2 = new Cliente("Cliente 2", new int[] { 1, 3, 5, 1, 1 });

        // Definición de cajeras (clase Cajera, NO CajeraThread).
        Cajera cajera1 = new Cajera("Cajera 1");
        Cajera cajera2 = new Cajera("Cajera 2");

        // Tiempo de referencia común.
        long initialTime = System.currentTimeMillis();

        // Cada Runnable encapsula una "tarea": (cliente, cajera, t0).
        Runnable proceso1 = new MainRunnable(cliente1, cajera1, initialTime);
        Runnable proceso2 = new MainRunnable(cliente2, cajera2, initialTime);

        // Se entrega cada Runnable a un Thread distinto y se arrancan.
        new Thread(proceso1).start();
        new Thread(proceso2).start();
    }

    /**
     * Cuerpo del hilo: delega en el método procesarCompra() de la cajera.
     * Esta inversión de control es el patrón clásico cuando se usa Runnable.
     */
    @Override
    public void run() {
        this.cajera.procesarCompra(this.cliente, this.initialTime);
    }
}
