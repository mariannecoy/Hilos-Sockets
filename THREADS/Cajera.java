/* =============================================================================
 *  Universidad: Pontificia Universidad Javeriana
 *  Facultad:    Ingeniería - Departamento de Ingeniería de Sistemas
 *  Materia:     Sistemas Distribuidos
 *  Taller:      Sockets y Threads en Java
 *  Archivo:     Cajera.java
 *  Autores:     Daniel Diaz y Marianne Coy
 *
 *  Descripción:
 *  ------------
 *  Representa una cajera "secuencial": no implementa Thread ni Runnable.
 *  Su método procesarCompra() simplemente itera sobre los productos del
 *  cliente y duerme el hilo principal el tiempo correspondiente. Por lo
 *  tanto, si el método main() invoca a varias cajeras en serie, sus
 *  tiempos se SUMAN (no se solapan) y la ejecución total es la suma de
 *  todos los tiempos individuales.
 *
 *  Esta clase se usa en:
 *      - Main.java        (uso secuencial, sin hilos).
 *      - MainRunnable.java (envuelta en un Runnable para concurrencia).
 * ============================================================================= */

package tallerThreads;

public class Cajera {

    private String nombre;

    /**
     * Construye una cajera con su nombre identificador.
     *
     * @param nombre nombre de la cajera (p.ej. "Cajera 1").
     */
    public Cajera(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Procesa el carro de compra del cliente uno a uno, esperando el
     * tiempo asociado a cada producto. Imprime trazas con el timestamp
     * relativo al inicio de la simulación.
     *
     * @param cliente   cliente cuya compra se va a procesar.
     * @param timeStamp tiempo de referencia (System.currentTimeMillis() inicial).
     */
    public void procesarCompra(Cliente cliente, long timeStamp) {
        System.out.println("La cajera " + this.nombre +
                " comienza a procesar la compra del cliente " + cliente.getNombre() +
                " en el tiempo: " + (System.currentTimeMillis() - timeStamp) / 1000 + "seg");

        for (int i = 0; i < cliente.getCarroCompra().length; i++) {
            this.esperarXsegundos(cliente.getCarroCompra()[i]);
            System.out.println("Procesado el producto " + (i + 1) +
                    " del cliente " + cliente.getNombre() +
                    " ->Tiempo: " + (System.currentTimeMillis() - timeStamp) / 1000 + "seg");
        }

        System.out.println("La cajera " + this.nombre +
                " ha terminado de procesar " + cliente.getNombre() +
                " en el tiempo: " + (System.currentTimeMillis() - timeStamp) / 1000 + "seg");
    }

    /**
     * Bloquea el hilo actual durante la cantidad de segundos indicada.
     * Si el hilo es interrumpido, restaura la bandera de interrupción.
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
