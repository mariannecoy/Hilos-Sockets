/* =============================================================================
 *  Universidad: Pontificia Universidad Javeriana
 *  Facultad:    Ingeniería - Departamento de Ingeniería de Sistemas
 *  Materia:     Sistemas Distribuidos
 *  Taller:      Sockets y Threads en Java
 *  Archivo:     Main.java
 *  Autores:     Daniel Diaz y Marianne Coy
 *
 *  Descripción:
 *  ------------
 *  Programa principal en versión SECUENCIAL (sin hilos). Se crean dos
 *  cajeras y dos clientes; cada cajera procesa su cliente, pero los
 *  llamados a procesarCompra() son síncronos: la segunda cajera no
 *  empieza hasta que la primera termina por completo.
 *
 *  Tiempo total esperado:
 *      Cliente 1 -> 2+2+1+5+2+3 = 15 seg
 *      Cliente 2 -> 1+3+5+1+1   =  11 seg
 *      Total secuencial         = ~26 seg
 *
 *  Esta clase sirve como BASELINE para comparar contra las versiones
 *  concurrentes (MainThread y MainRunnable), donde el total debe
 *  reducirse a ~max(15, 11) = ~15 seg.
 * ============================================================================= */

package tallerThreads;

public class Main {

    public static void main(String[] args) {

        // Definición de clientes y sus carritos (tiempo en segundos por producto).
        Cliente cliente1 = new Cliente("Cliente 1", new int[] { 2, 2, 1, 5, 2, 3 });
        Cliente cliente2 = new Cliente("Cliente 2", new int[] { 1, 3, 5, 1, 1 });

        // Definición de las cajeras.
        Cajera cajera1 = new Cajera("Cajera 1");
        Cajera cajera2 = new Cajera("Cajera 2");

        // Tiempo de referencia para todas las trazas.
        long initialTime = System.currentTimeMillis();

        // Procesamiento SECUENCIAL: cajera2 espera a que cajera1 termine.
        cajera1.procesarCompra(cliente1, initialTime);
        cajera2.procesarCompra(cliente2, initialTime);
    }
}
