package data.model.lecture_4;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Vehicle.ElectricVehicle.class, name = "ELECTRIC_VEHICLE"),
        @JsonSubTypes.Type(value = Vehicle.FuelVehicle.class, name = "FUEL_VEHICLE")
})
@Getter
@Setter
public class Vehicle {

    public String type;

    @Getter
    @Setter
    public static class ElectricVehicle extends Vehicle {
        String autonomy;
        String chargingTime;
    }

    @Getter
    @Setter
    public static class FuelVehicle extends Vehicle {
        String fuelType;
        String transmissionType;
    }
}
