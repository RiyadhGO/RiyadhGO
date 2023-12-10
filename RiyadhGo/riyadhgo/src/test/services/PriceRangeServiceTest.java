 import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import sa.edu.yamama.riyadhgo.domain.TransportMethod;
import sa.edu.yamama.riyadhgo.domain.TransportMethodTypes;
import sa.edu.yamama.riyadhgo.models.SelectTransportMethodModel;
   

public class PriceRangeServiceTests{
    @Test
    public void testCollectRangesSize(){
        // arrange 
        List<TransportMethod> data = new ArrayList<>();
        TransportMethod car1 = new TransportMethod();
        car1.setMethodType(TransportMethodTypes.CAR);
        car1.setCostPerUnit(15);
        data.add(car1);
        PriceRangeService service = new PriceRangeService(); 
        
        // act
        List<SelectTransportMethodModel> result = service.collectRanges(data);
        //assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }
 @Test
    public void testCollectRangesPrices(){
        // arrange 
        List<TransportMethod> data = new ArrayList<>();
        TransportMethod car1 = new TransportMethod();
        car1.setMethodType(TransportMethodTypes.CAR);
        car1.setCostPerUnit(15);
        data.add(car1);
         TransportMethod car2 = new TransportMethod();
        car2.setMethodType(TransportMethodTypes.CAR);
        car2.setCostPerUnit(20);
        data.add(car2);
        PriceRangeService service = new PriceRangeService(); 
        
        // act
        List<SelectTransportMethodModel> result = service.collectRanges(data);
        //assert
        assertNotNull(result);
        assertEquals(15, result.get(0).getMinPrice());
        assertEquals(20, result.get(0).getMaxPrice());
    }

}
    

