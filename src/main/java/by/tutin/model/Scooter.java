package by.tutin.model;

import by.tutin.model.enums.ScooterStatus;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "scooter")
public class Scooter extends AEntity{
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
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
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
}
