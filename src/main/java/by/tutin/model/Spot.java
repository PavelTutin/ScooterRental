package by.tutin.model;

import by.tutin.model.enums.SpotStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "spot")
public class Spot extends AEntity {
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "parent_id")
    private Spot parent;
    private String address;
    private int capacity;
    @Enumerated(EnumType.STRING)
    private SpotStatus status;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "maintainer_id")
    private User maintainer;


    @Override
    public String toString() {
        return "Spot{" +
                "address='" + address + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}
