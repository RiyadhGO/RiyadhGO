package sa.edu.yamamh.riyadhgo.data;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import sa.edu.yamamh.riyadhgo.MappingUtils;

public class SelectTransportMethodModel {//This class represents a model for selecting a transportation method.
    // It likely serves as a data structure for filtering and choosing transportation options within a trip
    private String methodType;//e.g., Car, Bus, Scooter, Walking
    private String names;//e.g., Uber, Careem, Riyadh Bus
    private  float minPrice;//Minimum acceptable price for the transportation method
    private float maxPrice;//Maximum acceptable price for the transportation method

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


    public Map<String,Object> toMap(){
        Map<String,Object> data = new HashMap<>();

        data.put("names", this.getNames());
        data.put("methodType", this.getMethodType());
        data.put("maxPrice", this.getMaxPrice());
        data.put("minPrice", this.getMinPrice());
        return  data;
    }

    public static SelectTransportMethodModel fromMap(Map<String,Object> data){
        SelectTransportMethodModel transportMethod = new SelectTransportMethodModel();
        if(data == null)
            return transportMethod;

        transportMethod.setMethodType(MappingUtils.getString("methodType", data));
        transportMethod.setNames(MappingUtils.getString("names", data));
        transportMethod.setMaxPrice(MappingUtils.getFloat("maxPrice", data));
        transportMethod.setMinPrice(MappingUtils.getFloat("minPrice", data));
        return transportMethod;
    }
    public static SelectTransportMethodModel fromJSONObject(JSONObject data){
        SelectTransportMethodModel transportMethod = new SelectTransportMethodModel();
        if(data == null)
            return transportMethod;

        transportMethod.setMethodType(MappingUtils.getString("methodType", data));
        transportMethod.setNames(MappingUtils.getString("names", data));
        transportMethod.setMaxPrice(MappingUtils.getFloat("maxPrice", data));
        transportMethod.setMinPrice(MappingUtils.getFloat("minPrice", data));
        return transportMethod;
    }

}
