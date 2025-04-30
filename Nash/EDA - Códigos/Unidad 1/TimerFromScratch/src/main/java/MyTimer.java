
public class MyTimer {

    protected long startTime, endTime;

    public MyTimer(){
        startTime = System.currentTimeMillis();
    }

    public MyTimer(long startTime){
        this.startTime = startTime;
    }

    public void stop(){
        endTime = System.currentTimeMillis();
    }

    public void stop(long endTime){
        this.endTime = endTime;
    }

    public float getMs(){
        return endTime - startTime;
    }

    public double getSecs(){
        double secs = getMs() / (double)1000;
        return secs % 60;
    }

    public int getMins(){
        float mins = (getMs()/1000) / 60;
        return (int) (mins % 60);
    }

    public int getHs(){
        float hs = (getMs()/1000) / (60 * 60);
        return (int) (hs % 24);
    }

    public int getDays(){
        return (int) (getMs()/1000) / (60 * 60 * 24);
    }


    @Override
    public String toString(){
        if (endTime < startTime){
            throw new RuntimeException();
        }

        //return "(%l ms) %d día %d hs %d min %f seg".formatted();
        return "(%.3f ms) %d día(s) %d horas %d mins %.3f seg".formatted(getMs(), getDays(), getHs(), getMins(), getSecs());
    }


    public static void main(String[] args) {
        MyTimer t1= new MyTimer();
        MyTimer t2= new MyTimer(6000);

        // bla bla bla
        t1.stop();

        // bla bla bla
        t2.stop(7000);

        System.out.println(t1);
        System.out.println(t2);


        t1= new MyTimer();

        // bla bla bla

        t1.stop(6000);

        t2= new MyTimer(6000);

        // bla bla bla

        t2.stop();
    }

}

