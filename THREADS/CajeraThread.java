/* =============================================================================
 *  Universidad: Pontificia Universidad Javeriana
 *  Facultad:    Ingeniería - Departamento de Ingeniería de Sistemas
 *  Materia:     Sistemas Distribuidos
 *  Taller:      Sockets y Threads en Java
 *  Archivo:     CajeraThread.java
 *  Autores:     Daniel Diaz y Marianne Coy
 *
 *  Descripción:
 *  ------------
 *  Versión concurrente de la cajera implementada mediante HERENCIA de la
 *  clase java.lang.Thread. Cada instancia es a la vez un objeto cajera
 *  y un hilo de ejecución independiente.
 *
 *  Limitaciones de "extends Thread":
 *      - Java no permite herencia múltiple, por lo que esta clase no
 *        puede extender de ninguna otra clase.
 *      - Acopla la lógica de negocio (cajera) con el mecanismo de
 *        concurrencia (Thread), lo cual rompe el principio SRP.
 *
 *  Por estas razones, en este mismo taller se incluye una versión
 *  alternativa basada en Runnable (ver MainRunnable.java).
 * ============================================================================= */

package tallerThreads;

public class CajeraThread extends Thread {

    private String nombre;
    private Cliente cliente;
    private long initialTime;

    /**
     * @param nombre      nombre de la cajera/hilo.
     * @param cliente     cliente que esta cajera atenderá.
     * @param initialTime instante de inicio de la simulación, compartido
     *                    entre todas las cajeras para mostrar tiempos
     *                    relativos coherentes.
     */
    public CajeraThread(String nombre, Cliente cliente, long initialTime) {
        this.nombre = nombre;
        this.cliente = cliente;
        this.initialTime = initialTime;
    }

    /**
     * Cuerpo del hilo: lo ejecuta la JVM cuando se invoca start().
     * Procesa cada producto del carrito durmiendo el hilo el tiempo
     * indicado, e imprime trazas relativas al instante inicial.
     */
    @Override
    public void run() {
        System.out.println("La cajera " + this.nombre +
                " comienza a procesar la compra del cliente " + cliente.getNombre() +
                " en el tiempo: " + (System.currentTimeMillis() - this.initialTime) / 1000 + "seg");

        for (int i = 0; i < cliente.getCarroCompra().length; i++) {
            this.esperarXsegundos(cliente.getCarroCompra()[i]);
            System.out.println("Procesado el producto " + (i + 1) +
                    " del cliente " + cliente.getNombre() +
                    " ->Tiempo: " + (System.currentTimeMillis() - this.initialTime) / 1000 + "seg");
        }

        System.out.println("La cajera " + this.nombre +
                " ha terminado de procesar " + cliente.getNombre() +
                " en el tiempo: " + (System.currentTimeMillis() - this.initialTime) / 1000 + "seg");
    }

    /**
     * Bloquea el hilo durante la cantidad de segundos indicada.
     * Restaura la bandera de interrupción si se interrumpe el sleep.
     *
     * @param segundos tiempo de espera en segundos.
     */
    private void esperarXsegundos(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
