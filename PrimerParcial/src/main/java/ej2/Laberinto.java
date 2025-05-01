package ej2;

import java.util.LinkedList;
import java.util.Queue;

public class Laberinto {

    public static boolean existeCamino(int[][] laberinto, int filaInicio, int columnaInicio, int filaFin, int columnaFin) {
        int filas = laberinto.length;
        int columnas = laberinto[0].length;

        if (laberinto[filaInicio][columnaInicio] != 0 || laberinto[filaFin][columnaFin] != 0)
            return false;

        int[][] direcciones = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        Queue<int[]> cola = new LinkedList<>();
        cola.add(new int[]{filaInicio, columnaInicio});
        laberinto[filaInicio][columnaInicio] = -1;

        while (!cola.isEmpty()) {
            int[] pos = cola.remove();
            int fila = pos[0], col = pos[1];

            if (fila == filaFin && col == columnaFin)
                return true;

            for (int[] dir : direcciones) {
                int nuevaFila = fila + dir[0];
                int nuevaCol = col + dir[1];

                if (nuevaFila >= 0 && nuevaFila < filas && nuevaCol >= 0 && nuevaCol < columnas &&
                        laberinto[nuevaFila][nuevaCol] == 0) {
                    cola.add(new int[]{nuevaFila, nuevaCol});
                    laberinto[nuevaFila][nuevaCol] = -1;
                }
            }
        }

        return false;
    }


    public static void main(String[] args) {
        int[][] laberinto = {
                {0, 0, 1, 0},
                {1, 0, 1, 0},
                {0, 0, 0, 0},
                {0, 1, 1, 1}
        };

        boolean existe = existeCamino(laberinto, 0, 0, 3, 0);
        if (existe) {
            System.out.println("Existe un camino en el laberinto.");
        } else {
            System.out.println("No existe un camino en el laberinto.");
        }
        System.out.println("Caminos recorridos:");
        imprimirLaberinto(laberinto);

        int[][] laberintoSinSalida = {
                {0, 0, 1, 0},
                {1, 0, 1, 1},
                {0, 0, 0, 0},
                {0, 1, 1, 1}
        };
        boolean existeSinSalida = existeCamino(laberintoSinSalida, 0, 0, 0, 3);
        if (existeSinSalida) {
            System.out.println("Existe un camino en el laberinto sin salida (¡error!).");
        } else {
            System.out.println("No existe un camino en el laberinto sin salida.");
        }
        System.out.println("Caminos recorridos:");
        imprimirLaberinto(laberintoSinSalida);
    }

    public static void imprimirLaberinto(int[][] laberinto) {
        for (int[] fila : laberinto) {
            for (int celda : fila) {
                System.out.print(celda + " ");
            }
            System.out.println();
        }
    }
}
