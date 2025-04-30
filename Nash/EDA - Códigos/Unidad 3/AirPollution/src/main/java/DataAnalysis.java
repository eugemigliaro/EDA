
import java.io.FileReader;        
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;



public class DataAnalysis {
    public static void main(String[] args) throws IOException {
    	
	    // Leemos el archivo
    	
    	/*
       	// opcion 1:  probar con  / o sin barra
	    URL resource = DataAnalysis.class.getClassLoader().getResource("co_1980_alabama.csv");
	    Reader in = new FileReader(resource.getFile());

    	// opcion 2:  probar con  / o sin barra
        URL resource= DataAnalysis.class.getResource("/co_1980_alabama.csv");
   	    Reader in = new FileReader(resource.getFile());

    	// opcion 3: probar con / o sin barra
    	String fileName= "/co_1980_alabama.csv";
    	InputStream is = DataAnalysis.class.getClass().getResourceAsStream(fileName);
    	Reader in = new InputStreamReader(is);

  		// opcion 4 
 		String fileName= "/co_1980_alabama.csv"; 
 		InputStream is = DataAnalysis.class.getResourceAsStream(fileName );
 		Reader in = new InputStreamReader(is); 	
    	 */
    	
    	// opcion 5 
   		String fileName= "co_1980_alabama.csv"; 
   		InputStream is = DataAnalysis.class.getClassLoader().getResourceAsStream(fileName);
   		Reader in = new InputStreamReader(is); 			
    	
    	// Solo sirve para recorrer UNA vez
	    Iterable<CSVRecord> records = CSVFormat.DEFAULT
	      .withFirstRecordAsHeader()
	      .parse(in);

		/*
	    // Imprimimos los registros con todos sus valores
	    for (CSVRecord record : records) {
	        String value = record.get("daily_max_8_hour_co_concentration");
	         System.out.println(String.format("%s, %s", value, record.toString()));
	    
	    }
	    */

		// Coleccion de valores
		HashMap<Long, CSVRecord> datos= new HashMap<>();

		// indice sobre polucion o los que deseemos
		@SuppressWarnings("unchecked")
		Class<IdxRecord<Double, Long>> recordClass = (Class<IdxRecord<Double, Long>>)(Class<?>) IdxRecord.class;
		IndexParametricService<IdxRecord<Double, Long>> indexPollution= new IndexWithDuplicates<>(recordClass);

		// Coleccion de datos
		for (CSVRecord record : records) {
			// Insertamos en la colección
			datos.put(record.getRecordNumber(), record);

			// Insertamos en el índice
			IdxRecord<Double, Long> info = new IdxRecord<>(
					Double.parseDouble(record.get("daily_max_8_hour_co_concentration")),
					record.getRecordNumber()
			);
			indexPollution.insert(info);

		}

		// Buscamos si existio una polucion de 2.8
		IdxRecord<Double, Long> search = new IdxRecord<>(2.8);
		System.out.println(indexPollution.search(search));

		// Buscamos el valor numerico de la minima polucion registrada
		System.out.println(indexPollution.getMin().getKey());

		// Imprimimos toda la informacion del registro de menor polucion
		IdxRecord<Double, Long> min = new IdxRecord<>(indexPollution.getMin().getKey());

		IdxRecord<Double, Long>[] range  = indexPollution.range(min, min, true, true);
		for(IdxRecord<Double, Long> value : range){
			System.out.println(datos.get(value.getRow()));
		}

		// Imprimimos los valores de polución ordenados ascendentemente
		indexPollution.sortedPrint();

		// Imprimir TODA la info pero ascendentemente ordenada por polución
		IdxRecord<Double, Long> min2 = new IdxRecord<>(indexPollution.getMin().getKey());
		IdxRecord<Double, Long> max2 = new IdxRecord<>(indexPollution.getMax().getKey());

		IdxRecord<Double, Long>[] range2  = indexPollution.range(min2, max2, true, true);
		for(IdxRecord<Double, Long> value : range2){
			System.out.println(datos.get(value.getRow()));
		}

		// Vemos qué valores numéricos de polución se registraron entre [3.65, 3.84]
		IdxRecord<Double, Long> min3 = new IdxRecord<>(3.65);
		IdxRecord<Double, Long> max3 = new IdxRecord<>(3.84);

		IdxRecord<Double, Long>[] range3  = indexPollution.range(min3, max3, true, true);
		for(IdxRecord<Double, Long> value : range3){
			System.out.println(datos.get(value.getRow()));
		}

		// Vemos qué valores numéricos de polución se registraron entre [3.65, 3.84)

		IdxRecord<Double, Long>[] range4 = indexPollution.range(min3, max3, true, false);
		for(IdxRecord<Double, Long> value : range4){
			System.out.println(datos.get(value.getRow()));
		}

		// Vemos qué valores numéricos de polución se registraron entre [10.5, 12]
		IdxRecord<Double, Long> min4 = new IdxRecord<>(10.5);
		IdxRecord<Double, Long> max4 = new IdxRecord<>(12.0);

		IdxRecord<Double, Long>[] range5  = indexPollution.range(min4, max4, true, true);
		for(IdxRecord<Double, Long> value : range5){
			System.out.println(datos.get(value.getRow()));
		}

		in.close();
    }
  
}
