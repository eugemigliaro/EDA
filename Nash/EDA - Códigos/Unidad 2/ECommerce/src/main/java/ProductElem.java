public class ProductElem implements Comparable<ProductElem>{

    private final String name;
    private final Double value;
    private final String algo;
    private final Double algoValue;

    public ProductElem(String name, Double value, String algo, Double algoValue){
        this.name = name;
        this.value = value;
        this.algo = algo;
        this.algoValue = algoValue;
    }

    public Double getValue(){
        return value;
    }

    @Override
    public String toString() {
        return "%s - %s - %.2f".formatted(name, algo, algoValue);
    }

    @Override
    public int compareTo(ProductElem other) {
        return Double.compare(this.value, other.value);
    }
}
