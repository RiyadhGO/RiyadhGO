package sa.edu.yamamh.riyadhgo.data;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import sa.edu.yamamh.riyadhgo.MappingUtils;

public class LocationModel {
    //declare private fields: id, name, longitude, latitude
    private Long id;
    private String name;
    private float longitude;
    private float latitude;

    // Getter and Setter methods for each field
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

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }


    // toMap method converts the LocationModel object to a Map Object
    public Map<String,Object> toMap(){
        Map<String,Object> data = new HashMap<>();
        data.put("id", this.getId());
        data.put("name", this.getName());
        data.put("longitude", this.getLongitude());
        data.put("latitude", this.getLatitude());
        return  data;
    }

    public static LocationModel fromMap(Map<String,Object> data){
        LocationModel location = new LocationModel();
        location.setId(MappingUtils.getLong("id",data));
        location.setName(MappingUtils.getString("name",data));
        location.setLongitude(MappingUtils.getFloat("longitude",data));
        location.setLatitude(MappingUtils.getFloat("latitude",data));
        return location;
    }
    public static LocationModel fromJSONObject(JSONObject data){
        LocationModel location = new LocationModel();
        location.setId(MappingUtils.getLong("id",data));
        location.setName(MappingUtils.getString("name",data));
        location.setLongitude(MappingUtils.getFloat("longitude",data));
        location.setLatitude(MappingUtils.getFloat("latitude",data));
        return location;
    }

    public static LocationModel fromLatLng(LatLng location){
        LocationModel model = new LocationModel();
        model.setLatitude((float)location.latitude);
        model.setLongitude((float)location.longitude);
        return model;
    }


    public LatLng getLatLng() {
        return new LatLng(this.latitude,this.longitude);
    }
}
