package sa.edu.yamama.riyadhgo.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//The creation of table "transport_methods" in the database
@Entity
@Table(name="transport_methods")
public class TransportMethod implements Serializable {
 
//The creation of the variables method's ID, name, unit cost, measure unit, carbon emitted, link url and method type in the "transport_methods" table    
    @Id
    @GeneratedValue
    @Column(name="method_id")
    private Long id;

    @Column(name="method_name", length =  100, unique =  true)
    private String name;

    @Column(name="unit_cost")
    private float costPerUnit;

    @Column(name="measure_unit")
    private MeasureUnit measureUnit;

    @Column(name="carbon_emitted")
    private boolean carbonEmitted;
//link url is created in case a method that require redirection is selected. For example the selection of car will result in a redirection to the Uber Application
    @Column(name="link_url")
    private String link;

    @Column(name = "method_type")
    private TransportMethodTypes methodType;

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

    public void setLink(String link) {
        this.link = link;
    }
    public String getLink(){
        return this.link;
    }

    public TransportMethodTypes getMethodType() {
        return methodType;
    }

    public void setMethodType(TransportMethodTypes methodType) {
        this.methodType = methodType;
    }


}
