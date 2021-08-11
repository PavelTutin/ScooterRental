package by.tutin.model;

import by.tutin.model.enums.ScooterStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "scooter")
public class Scooter extends AEntity {
    @Column(name = "serial_number")
    private String serialNumber;
    private String model;
    private int price;
    private int battery;
    @Column(name = "battery_actual")
    private int batteryActual;
    @Column(name = "max_speed")
    private int maxSpeed;
    @Enumerated(EnumType.STRING)
    private ScooterStatus status;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "spot_id")
    private Spot spot;


    @Override
    public String toString() {
        return "Scooter{" +
                "serialNumber='" + serialNumber + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", battery=" + battery +
                ", batteryActual=" + batteryActual +
                ", maxSpeed=" + maxSpeed +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Scooter scooter = (Scooter) o;
        return serialNumber.equals(scooter.serialNumber) && model.equals(scooter.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), serialNumber, model);
    }
}
