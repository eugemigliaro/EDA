package ej4;

import org.joda.time.Instant;
import org.joda.time.Period;
import org.joda.time.Duration;
import org.joda.time.format.PeriodFormatterBuilder;
import org.joda.time.format.PeriodFormatter;

public class MyTimer {
    private Instant start;
    private Instant end;
    private boolean stopped = false;

    /**
     * Constructor que inicia el timer automáticamente con el tiempo actual
     */
    public MyTimer() {
        this.start = new Instant();
    }

    /**
     * Constructor que inicia el timer con un valor específico en milisegundos
     * @param inicio tiempo de inicio en milisegundos
     */
    public MyTimer(long inicio) {
        this.start = new Instant(inicio);
    }

    /**
     * Detiene el timer con el tiempo actual
     * @throws RuntimeException si el timer ya fue detenido previamente
     */
    public void stop() {
        if (stopped) {
            throw new RuntimeException("Timer already stopped");
        }
        this.end = new Instant();
        this.stopped = true;
    }

    /**
     * Detiene el timer con un valor específico en milisegundos
     * @param fin tiempo de fin en milisegundos
     * @throws RuntimeException si el timer ya fue detenido o si el tiempo de fin es menor al de inicio
     */
    public void stop(long fin) {
        if (stopped) {
            throw new RuntimeException("Timer already stopped");
        }
        Instant endInstant = new Instant(fin);
        if (endInstant.isBefore(this.start)) {
            throw new RuntimeException("End time cannot be before start time");
        }
        this.end = endInstant;
        this.stopped = true;
    }

    /**
     * Obtiene la duración del intervalo en milisegundos
     * @return duración en milisegundos
     * @throws RuntimeException si el timer no ha sido detenido
     */
    public long getDurationMillis() {
        if (!stopped) {
            throw new RuntimeException("Timer must be stopped before getting duration");
        }
        return new Duration(this.start, this.end).getMillis();
    }

    /**
     * Obtiene los días de la duración
     * @return número de días
     */
    public int getDays() {
        if (!stopped) {
            throw new RuntimeException("Timer must be stopped before getting days");
        }
        return new Period(this.start, this.end).getDays();
    }

    /**
     * Obtiene las horas de la duración (sin contar los días)
     * @return número de horas
     */
    public int getHours() {
        if (!stopped) {
            throw new RuntimeException("Timer must be stopped before getting hours");
        }
        return new Period(this.start, this.end).getHours();
    }

    /**
     * Obtiene los minutos de la duración (sin contar días ni horas)
     * @return número de minutos
     */
    public int getMinutes() {
        if (!stopped) {
            throw new RuntimeException("Timer must be stopped before getting minutes");
        }
        return new Period(this.start, this.end).getMinutes();
    }

    /**
     * Obtiene los segundos de la duración (sin contar días, horas ni minutos)
     * @return número de segundos con tres decimales
     */
    public double getSeconds() {
        if (!stopped) {
            throw new RuntimeException("Timer must be stopped before getting seconds");
        }
        Period period = new Period(this.start, this.end);
        return period.getSeconds() + (period.getMillis() / 1000.0);
    }

    /**
     * Devuelve la representación textual de la duración del intervalo
     * @return cadena con el formato "(X ms) D día(s) H hs M min S,SSS s"
     */
    @Override
    public String toString() {
        if (!stopped) {
            throw new RuntimeException("Timer must be stopped before converting to string");
        }

        long millis = getDurationMillis();
        int days = getDays();
        int hours = getHours();
        int minutes = getMinutes();
        double seconds = getSeconds();

        // Formato con tres decimales para los segundos
        String secondsStr = String.format("%.3f", seconds).replace('.', ',');

        return String.format("(%d ms) %d día%s %d hs %d min %s s",
                millis,
                days,
                (days == 1 ? "" : "s"),
                hours,
                minutes,
                secondsStr);
    }

    /**
     * Método principal para pruebas básicas
     */
    /*public static void main(String[] args) {
        // Ejemplo de prueba: crear un intervalo de 1 día, 2 horas, 0 minutos, 23.040 segundos
        MyTimer timer = new MyTimer(0);
        timer.stop(93623040); // 1 día, 2 horas, 0 minutos, 23.040 segundos en ms
        System.out.println(timer);

        // Ejemplo con inicio y fin automáticos
        MyTimer timer2 = new MyTimer();
        try {
            Thread.sleep(100); // Esperar 100ms
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer2.stop();
        System.out.println(timer2);
    }*/
}