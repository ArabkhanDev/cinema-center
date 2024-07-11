package group.aist.cinema.enums;

import lombok.Getter;

@Getter
public enum SeatType {


    AVAILABLE("available"),
    SELECTED("selected"),
    BOOKED("booked");

    private final String value;

    SeatType(String value) {
        this.value = value;
    }


    public static SeatType fromString(String name) {
        for (SeatType type : SeatType.values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with name: " + name);
    }

}
