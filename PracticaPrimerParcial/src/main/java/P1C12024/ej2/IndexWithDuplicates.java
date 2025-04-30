package P1C12024.ej2;

import java.util.Arrays;

/**
 * @author dpenaloza
 *
 */
public class IndexWithDuplicates  {

	final static private int chunksize= 5;

	private int[] indexedData;
	private int cantElems;


	public IndexWithDuplicates() {
		indexedData= new int[chunksize];
		cantElems= 0;
	}

	public void initialize(int[] unsortedElements) {

		if (unsortedElements == null)
			throw new RuntimeException("Problem: null data collection");

		indexedData= unsortedElements;
		Arrays.sort(indexedData);
		cantElems= indexedData.length;
	}


	public int[] getIndexedData() {
		return indexedData;
	}

	public void print() {
		System.out.print("[");
		for (int i : indexedData)
			System.out.print(i + " ") ;
		System.out.println("]");

	}

	public void merge(IndexWithDuplicates other) {
		if (other == null || other.cantElems == 0) {
			return; // Nothing to merge
		}

		// Create a new array to hold all elements from both indices
		int totalSize = this.cantElems + other.cantElems;
		int[] mergedData = new int[totalSize];

		int i = 0; // Index for this.indexedData
		int j = 0; // Index for other.indexedData
		int k = 0; // Index for mergedData

		// Merge the two sorted arrays
		while (i < this.cantElems && j < other.cantElems) {
			if (this.indexedData[i] <= other.indexedData[j]) {
				mergedData[k++] = this.indexedData[i++];
			} else {
				mergedData[k++] = other.indexedData[j++];
			}
		}

		// Copy remaining elements from this.indexedData, if any
		while (i < this.cantElems) {
			mergedData[k++] = this.indexedData[i++];
		}

		// Copy remaining elements from other.indexedData, if any
		while (j < other.cantElems) {
			mergedData[k++] = other.indexedData[j++];
		}

		// Update this.indexedData and cantElems
		this.indexedData = mergedData;
		this.cantElems = totalSize;
	}

}
