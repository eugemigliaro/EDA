package core;

import java.io.*;             
import java.net.URL;
import java.util.HashMap;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class DataAnalysis {
    public static void main(String[] args) throws IOException {
    	
	    // leemos el archivo
    	
    	/*
       	// opcion 1:  probar con  / o sin barra
	    URL resource = DataAnalysis.class.getClassLoader().getResource("co_1980_alabama.csv");
	    Reader in = new FileReader(resource.getFile());
    	*/
    	
    	
    	/*
    	// opcion 2:  probar con  / o sin barra
        URL resource= DataAnalysis.class.getResource("/co_1980_alabama.csv");
   	    Reader in = new FileReader(resource.getFile());
    	*/
    	
    	/*
    	// opcion 3: probar con / o sin barra
    	String fileName= "/co_1980_alabama.csv";
    	InputStream is = DataAnalysis.class.getClass().getResourceAsStream(fileName);
    	Reader in = new InputStreamReader(is);
    	*/
    	
    	/*
  		// opcion 4 
 		String fileName= "/co_1980_alabama.csv"; 
 		InputStream is = DataAnalysis.class.getResourceAsStream(fileName );
 		Reader in = new InputStreamReader(is); 	
    	 */
    	
    	
    	// opcion 5 
   		String fileName= "co_1980_alabama.csv"; 
   		InputStream is = DataAnalysis.class.getClassLoader().getResourceAsStream(fileName);
   		Reader in = new InputStreamReader(is);
    	
    	
 		CSVFormat theCSV = CSVFormat.DEFAULT.builder()
   				.setHeader()
   				.setSkipHeaderRecord(true)
   			    .get();

   		Iterable<CSVRecord> records = theCSV.parse(in);
   		
   		// Genero las estructuras
   		
	    // Coleccion de valores
	    HashMap<Long, CSVRecord> datos= new HashMap<>();
	    
	    IndexParametricService<IdxRecord<Double, Long>> indexPolucion = new IndexGenerics<>();
	    
	    // Indice sobre polucion sin reflection
//	    indexPolucion= new IndexWithDuplicates<>();
	    
	    // Indice sobre polucion con reflection
//	    indexPolucion = new IndexWithDuplicates<>(IdxRecord.class);

	    
	    
	    // Pueblo con los valores ambas estructuras
	    
	    // coleccion de datos
	    for (CSVRecord record : records) {
	    	// insertamos en la coleccionIdxRecord<Double, Long>IdxRecord<Double, Long>
	    	datos.put(record.getRecordNumber(), record);
	    	
	    	// insertamos lo minimo en el indice
	        String value = record.get("daily_max_8_hour_co_concentration");
	        long id = record.getRecordNumber();
	        indexPolucion.insert(new IdxRecord<>( Double.valueOf(value), id ));
	    }

	    in.close();

		System.out.println(indexPolucion.getMax());
	    
	    /*Decir para las siguientes operaciones, cuáles tomarían ventaja del uso del Index y sobre
        qué atributo habría que crearlo, luego implementarlas:
        - Buscar el promedio de la polución registrada.
        - Imprimir ascendentemente la info disponible, pero ordenada por polución.
        - Averiguar si existió una polución cuyo valor fuera 2.8
        - Buscar el valor numérico de la mínima polución registrada.
		- Buscar la info disponible en que se dio la mínima polución registrada.
		- Conocer qué valores numéricos de polución se registraron entre [3.65, 3.84]
		- Conocer la info disponible en la que la polución registrada fue entre [3.65, 3.84)
		- Conocer la info disponible en la que la polución registrada fue [10.5, 12]*/

		// Buscar el promedio de la polución registrada.
		IdxRecord<Double, Long>[] poluciones = indexPolucion.range(new IdxRecord<>(3.65, 0L), new IdxRecord<>(3.84, 0L), false, true);

		double promedio = 0;
		for (IdxRecord<Double, Long> idxRecord : poluciones) {
			promedio += idxRecord.getKey();
		}
		promedio = promedio / poluciones.length;
		System.out.println("Promedio de poluciones: " + promedio);

		// Imprimir ascendentemente la info disponible, pero ordenada por polución.
		IdxRecord<Double, Long>[] polucionesAscendente = indexPolucion.range(new IdxRecord<>(indexPolucion.getMin().getKey(), 0L), new IdxRecord<>(indexPolucion.getMax().getKey(), 0L), true, true);
		for (IdxRecord<Double, Long> idxRecord : polucionesAscendente) {
			System.out.println(idxRecord.getRowID() + " - " + idxRecord.getKey() + " - " + datos.get(idxRecord.getRowID()));
		}

		// Averiguar si existió una polución cuyo valor fuera 2.8
		IdxRecord<Double, Long> idxRecord = new IdxRecord<>(2.8, 0L);
		System.out.println("Existen poluciones de 2.8? " + indexPolucion.search(idxRecord));

		// Buscar el valor numérico de la mínima polución registrada.
		System.out.println("Minima polucion: " + indexPolucion.getMin().getKey());

		// Buscar la info disponible en que se dio la mínima polución registrada.
		System.out.println("Info de la minima polucion: " + datos.get(indexPolucion.getMin().getRowID()));

		// Conocer qué valores numéricos de polución se registraron entre [3.65, 3.84]
		IdxRecord<Double, Long>[] polucionesEntre = indexPolucion.range(new IdxRecord<>(3.65, 0L), new IdxRecord<>(3.84, 0L), true, true);
		System.out.println("Poluciones en [3.65, 3.84]: ");
		for (IdxRecord<Double, Long> idxRecord2 : polucionesEntre) {
			System.out.println(idxRecord2.getKey());
		}

		// Conocer la info disponible en la que la polución registrada fue entre [3.65, 3.84)
		IdxRecord<Double, Long>[] polucionesEntre2 = indexPolucion.range(new IdxRecord<>(3.65, 0L), new IdxRecord<>(3.84, 0L), true, false);
		System.out.println("Info de poluciones en [3.65, 3.84): ");
		for (IdxRecord<Double, Long> idxRecord2 : polucionesEntre2) {
			System.out.println(idxRecord2.getRowID() + " - " + idxRecord2.getKey() + " - " + datos.get(idxRecord2.getRowID()));
		}

		// Conocer la info disponible en la que la polución registrada fue [10.5, 12]
		IdxRecord<Double, Long>[] polucionesEntre3 = indexPolucion.range(new IdxRecord<>(10.5, 0L), new IdxRecord<>(12d, 0L), true, true);
		System.out.println("Info de poluciones en [10.5, 12]: ");
		for (IdxRecord<Double, Long> idxRecord2 : polucionesEntre3) {
			System.out.println(idxRecord2.getRowID() + " - " + idxRecord2.getKey() + " - " + datos.get(idxRecord2.getRowID()));
		}
    }
}
