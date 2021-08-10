package by.tutin.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "spot")
public class Spot extends AEntity {
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "parent_id")
    private Spot parent;
    private String address;
    private int capacity;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
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
