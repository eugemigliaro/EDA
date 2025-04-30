import java.util.ArrayList;


public class TirarDados {
    public static void main(String[] args) {
        new TirarDados().solve(3, 6);
    }

    public void solve(int cantDadosPendientes, int umbral) {
        ArrayList<Integer> auxi= new ArrayList<>();
        solveHelper(cantDadosPendientes, auxi, umbral);
    }

    private void solveHelper(int cantDadosPendientes, ArrayList<Integer> auxi, int umbral) {

        if (cantDadosPendientes == 0)
        {
            int sum = 0;
            for(Integer value : auxi){
                sum += value;
            }
            if(sum + cantDadosPendientes * 1 > umbral){
                return;
            }
            if(cantDadosPendientes == 0){
                System.out.println(auxi);
                return;
            }
        }

        int prev = auxi.isEmpty() ? 1 : auxi.get(auxi.size()    -1);
        for(int rec = prev; rec <= 6; rec++) {
            auxi.add(rec);					// resolver un caso pendiente

            solveHelper(cantDadosPendientes-1, auxi, umbral);  // explorar nuevos pendientes

            auxi.remove( auxi.size() - 1);  // quitar el pendiente generado
        }
    }
}