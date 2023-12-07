package sa.edu.yamamh.riyadhgo.data;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import sa.edu.yamamh.riyadhgo.MappingUtils;

public class TransportMethodModel {//This class represents a model for describing transportation methods within a trip
    private Long id;//Unique identifier for the transportation method
    private String name;// Name of the transportation method (e.g., Uber, Bus).
    private float costPerUnit;//Cost per unit of measure for the transport method (e.g., price per kilometer, per ride)
    private MeasureUnit measureUnit;//Unit of measure used for the cost per unit (e.g., Meter, Kilometer)
    private boolean carbonEmitted;//Boolean indicating whether the method emits carbon dioxide
    private String link;//Optional link to a website or booking platform related to the method
    private TransportMethodTypes methodType;//Enumerated value representing the type of transportation (e.g., Car, Bus, Scooter, Walking)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCostPerUnit() {
        return costPerUnit;
    }

    public void setCostPerUnit(float costPerUnit) {
        this.costPerUnit = costPerUnit;
    }

    public MeasureUnit getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(MeasureUnit measureUnit) {
        this.measureUnit = measureUnit;
    }

    public boolean isCarbonEmitted() {
        return carbonEmitted;
    }

    public void setCarbonEmitted(boolean carbonEmitted) {
        this.carbonEmitted = carbonEmitted;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    public Map<String,Object> toMap(){
        Map<String,Object> data = new HashMap<>();
        data.put("id", this.getId());
        data.put("name", this.getName());
        data.put("costPerUnit", this.getCostPerUnit());
        data.put("measureUnit", this.getMeasureUnit());
        data.put("carbonEmitted", this.isCarbonEmitted());
        data.put("link", this.getLink());
        data.put("methodType", this.getMethodType());
        return  data;
    }

    public static TransportMethodModel fromMap(Map<String,Object> data){
        TransportMethodModel transportMethod = new TransportMethodModel();
        if(data == null)
            return transportMethod;

        transportMethod.setId(MappingUtils.getLong("id", data));
        transportMethod.setName(MappingUtils.getString("name", data));
        transportMethod.setCostPerUnit(MappingUtils.getFloat("costPerUnit", data));
        transportMethod.setMeasureUnit(MappingUtils.getMeasureUnit("measureUnit", data));
        transportMethod.setCarbonEmitted(MappingUtils.getBoolean("carbonEmitted", data));
        transportMethod.setLink(MappingUtils.getString("link", data));
        transportMethod.setMethodType(MappingUtils.getTransportMethodType("methodType", data));
        return transportMethod;
    }
    public static TransportMethodModel fromJSONObject(JSONObject data){
        TransportMethodModel transportMethod = new TransportMethodModel();
        if(data == null)
            return transportMethod;

        transportMethod.setId(MappingUtils.getLong("id", data));
        transportMethod.setName(MappingUtils.getString("name", data));
        transportMethod.setCostPerUnit(MappingUtils.getFloat("costPerUnit", data));
        transportMethod.setMeasureUnit(MappingUtils.getMeasureUnit("measureUnit", data));
        transportMethod.setCarbonEmitted(MappingUtils.getBoolean("carbonEmitted", data));
        transportMethod.setLink(MappingUtils.getString("link", data));
        transportMethod.setMethodType(MappingUtils.getTransportMethodType("methodType", data));
        return transportMethod;
    }

    public TransportMethodTypes getMethodType() {
        return methodType;
    }

    public void setMethodType(TransportMethodTypes methodType) {
        this.methodType = methodType;
    }
}
