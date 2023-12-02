package sa.edu.yamama.riyadhgo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RiyadhgoApplicationTests {

	@Test
	void contextLoads() {
	}
   @Test
    public void testValidLogin() {
        LoginValidator loginValidator = new LoginValidator();
        boolean result = loginValidator.isValidLogin("validUsername", "validPassword");
        assertTrue(result);
    }
    @Test
    public void testInvalidLogin() {
        LoginValidator loginValidator = new LoginValidator();
        boolean result = loginValidator.isValidLogin("invalidUsername", "invalidPassword");
        assertFalse(result);
    }

    @Test
    public void testEmptyUsernameOrPassword() {
        LoginValidator loginValidator = new LoginValidator();
        boolean result = loginValidator.isValidLogin("", "validPassword");
        assertFalse(result);
    }
	import org.junit.Test;

import static org.junit.Assert.*;

public class MapActivityTest {

    @Test
    public void testSetPickupPoint() {
        MapActivity mapActivity = new MapActivity();
        LatLng pickupPoint = new LatLng(37.7749, -122.4194); // Replace with your actual coordinates
        mapActivity.setPickupPoint(pickupPoint);

        // Check if the pickup point is correctly set
        assertEquals(pickupPoint, mapActivity.getSelectedPickupPoint());
    }

    @Test
    public void testSetDestinationPoint() {
        MapActivity mapActivity = new MapActivity();
        LatLng destinationPoint = new LatLng(37.7749, -122.4194); // Replace with your actual coordinates
        mapActivity.setDestinationPoint(destinationPoint);

        // Check if the destination point is correctly set
        assertEquals(destinationPoint, mapActivity.getSelectedDestinationPoint());
    }

    @Test
    public void testUserCanPickPoints() {
        MapActivity mapActivity = new MapActivity();

        // Simulate user interaction to set pickup point
        LatLng pickupPoint = new LatLng(37.7749, -122.4194); // Replace with your actual coordinates
        mapActivity.setPickupPoint(pickupPoint);

        // Simulate user interaction to set destination point
        LatLng destinationPoint = new LatLng(37.7749, -122.4194); // Replace with your actual coordinates
        mapActivity.setDestinationPoint(destinationPoint);

        // Check if both pickup and destination points are correctly set
        assertEquals(pickupPoint, mapActivity.getSelectedPickupPoint());
        assertEquals(destinationPoint, mapActivity.getSelectedDestinationPoint());
    }
}

} 
