package sa.edu.yamamh.riyadhgo.data;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import sa.edu.yamamh.riyadhgo.MappingUtils;

public class FavoriteLocationModel {

    public long id;
    public String name;
    public float longitude;
    public float latitude;
    public long userId;
    public String userName;

    public FavoriteLocationModel() {
    }

    public FavoriteLocationModel(long id, String name, float longitude, float latitude, long userId, String userName) {
        this.id = id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.userId = userId;
        this.userName = userName;
    }

    //setters and getters


    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Map<String,Object> toMap(){
        Map<String,Object> data = new HashMap<>();
        data.put("id", this.getId());
        data.put("name", this.getName());
        data.put("longitude", this.getLongitude());
        data.put("latitude", this.getLatitude());
        data.put("userId", this.getUserId());
        data.put("userName", this.getUserName());
        return  data;
    }

    public static FavoriteLocationModel fromMap(Map<String,Object> data){
        FavoriteLocationModel location = new FavoriteLocationModel();
        location.setId(MappingUtils.getLong("id",data));
        location.setName(MappingUtils.getString("name",data));
        location.setLongitude(MappingUtils.getFloat("longitude",data));
        location.setLatitude(MappingUtils.getFloat("latitude",data));
        location.setUserId(MappingUtils.getLong("userId",data));
        location.setUserName(MappingUtils.getString("userName",data));
        return location;
    }

    public static FavoriteLocationModel fromJSONObject(JSONObject data){
        FavoriteLocationModel location = new FavoriteLocationModel();
        location.setId(MappingUtils.getLong("id",data));
        location.setName(MappingUtils.getString("name",data));
        location.setLongitude(MappingUtils.getFloat("longitude",data));
        location.setLatitude(MappingUtils.getFloat("latitude",data));
        location.setUserId(MappingUtils.getLong("userId",data));
        location.setUserName(MappingUtils.getString("userName",data));
        return location;
    }
    public LatLng toLatLng()
    {
        return new LatLng(this.latitude,this.longitude);
    }
}
