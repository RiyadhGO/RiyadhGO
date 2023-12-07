package sa.edu.yamamh.riyadhgo;
import sa.edu.yamamh.riyadhgo.data.TransportMethodTypes;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DistanceUtilsTest {

    @Test
    void getEstimatedTimeInMinutesTest() {
        // Arrange
        double distanceInKM = 10;
        TransportMethodTypes methodType = TransportMethodTypes.CAR;
        double expected = 23;

        // Act
        double actual = DistanceUtils.getEstimatedTimeInMinutes(distanceInKM, methodType);

        // Assert
        assertEquals(expected, actual);

    }
}