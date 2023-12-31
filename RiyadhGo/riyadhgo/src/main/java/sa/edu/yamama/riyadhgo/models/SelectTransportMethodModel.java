package sa.edu.yamama.riyadhgo.models;

//The class Select Transport Method Model represents what is displayed to the user once a method is selected. That includes method name, minimum and maximum price estimates.
public class SelectTransportMethodModel {

    public String methodType;
    public String names;
    public float minPrice;
    public float maxPrice;

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(float maxPrice) {
        this.maxPrice = maxPrice;
    }

}
