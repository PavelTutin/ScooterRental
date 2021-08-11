package by.tutin.model;

import by.tutin.model.enums.Tariff;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends AEntity {
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "scooter_id")
    private Scooter scooter;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;
    private Integer distance;
    @Enumerated(EnumType.STRING)
    private Tariff tariff;
    private Integer price;
    @Column(name = "time_start")
    private LocalDateTime timeStart;
    @Column(name = "time_end")
    private LocalDateTime timeEnd;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "start_spot_id")
    private Spot startSpot;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "end_spot_id")
    private Spot endSpot;

    @Override
    public String toString() {
        return "Order{" +
                "distance=" + distance +
                ", tariff=" + tariff +
                ", price=" + price +
                ", timeStart=" + timeStart +
                ", timeEnd=" + timeEnd +
                '}';
    }
}
