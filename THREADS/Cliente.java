/* =============================================================================
 *  Universidad: Pontificia Universidad Javeriana
 *  Facultad:    Ingeniería - Departamento de Ingeniería de Sistemas
 *  Materia:     Sistemas Distribuidos
 *  Taller:      Sockets y Threads en Java
 *  Archivo:     Cliente.java
 *  Autores:     Daniel Diaz y Marianne Coy
 *
 *  Descripción:
 *  ------------
 *  Modelo de dominio que representa a un cliente del supermercado. Cada
 *  cliente tiene un nombre y un "carro de compra" representado como un
 *  arreglo de enteros, donde cada entero indica el tiempo (en segundos)
 *  que la cajera tarda en procesar ese producto.
 *
 *  Esta clase es un POJO inmutable (no expone setters) y se utiliza tanto
 *  en la versión secuencial como en las versiones concurrentes del taller.
 * ============================================================================= */

package tallerThreads;

public class Cliente {

    private String nombre;
    private int[] carroCompra;

    /**
     * Construye un cliente con su nombre y su carrito de productos.
     *
     * @param nombre       nombre identificador del cliente.
     * @param carroCompra  arreglo con el tiempo de procesamiento de cada producto.
     */
    public Cliente(String nombre, int[] carroCompra) {
        this.nombre = nombre;
        this.carroCompra = carroCompra;
    }

    /** @return el nombre del cliente. */
    public String getNombre() {
        return nombre;
    }

    /** @return el arreglo de tiempos de procesamiento de los productos. */
    public int[] getCarroCompra() {
        return carroCompra;
    }
}
