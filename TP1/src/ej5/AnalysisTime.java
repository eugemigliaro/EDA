package ej5;

import ej4.MyTimer;

public class AnalysisTime {
    public static void main(String[] args) {
        // Creamos un MyTimer con un tiempo de inicio específico
        MyTimer myCrono = new MyTimer(10);

        // Detenemos el MyTimer con un tiempo específico
        // 10 + 93623040 = 93623050
        // Esto debería representar 1 día, 2 horas, 0 minutos, 23.040 segundos
        myCrono.stop(10 + 93623040);

        // Imprimimos la representación de cadena del MyTimer
        System.out.println(myCrono);
    }
}
