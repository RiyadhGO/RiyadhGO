package sa.edu.yamamh.riyadhgo;

import static org.junit.Assert.*;
import org.junit.Test;

public class DistanceUtilsTest {

    @Test
    public void testGetEstimatedTimeInMinutes() {
        // Test for WALK method
        double walkTime = DistanceUtils.getEstimatedTimeInMinutes(5.0, transportMethod.WALK);
        assertEquals(75.0, walkTime, 0.0);

        // Test for SCOOTER method
        double scooterTime = DistanceUtils.getEstimatedTimeInMinutes(10.0, transportMethod.SCOOTER);
        assertEquals(50.0, scooterTime, 0.0);

        // Test for BUS method
        double busTime = DistanceUtils.getEstimatedTimeInMinutes(15.0, TransportMethodTypes.BUS);
        assertEquals(109.5, busTime, 0.0);

        // Test for CAR method
        double carTime = DistanceUtils.getEstimatedTimeInMinutes(20.0, TransportMethodTypes.CAR);
        assertEquals(46.0, carTime, 0.0);

        // Test for default case
        double defaultTime = DistanceUtils.getEstimatedTimeInMinutes(25.0, null);
        assertEquals(57.5, defaultTime, 0.0);
    }
}
