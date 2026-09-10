package lecture_4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.model.lecture_4.Vehicle;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;


public class TypeHandlingTest {

    @Test
    public void whenResponseTypeIsElectricVehicle() throws JsonProcessingException {
        String json = """
                {"type":"ELECTRIC_VEHICLE","autonomy":"500","chargingTime":"200"}
                """;

        Vehicle.ElectricVehicle vehicle = new ObjectMapper().readerFor(Vehicle.class).readValue(json);

        System.out.println(vehicle.getType());
        System.out.println("vehicle.getAutonomy() = " + vehicle.getAutonomy());
        System.out.println("vehicle.getChargingTime() = " + vehicle.getChargingTime());
        assertEquals(Vehicle.ElectricVehicle.class, vehicle.getClass());
    }

    @Test
    public void whenResponseTypeIsFuelVehicle() throws JsonProcessingException {
        String json = """
                {"type":"FUEL_VEHICLE","fuelType":"500","transmissionType":"200"}
                """;
        Vehicle.FuelVehicle vehicle = new ObjectMapper().readerFor(Vehicle.class).readValue(json);

        System.out.println(vehicle.getType());
        System.out.println("vehicle.getFuelType() = " + vehicle.getFuelType());
        System.out.println("vehicle.getTransmissionType() = " + vehicle.getTransmissionType());
        assertEquals(Vehicle.FuelVehicle.class, vehicle.getClass());
    }
}
