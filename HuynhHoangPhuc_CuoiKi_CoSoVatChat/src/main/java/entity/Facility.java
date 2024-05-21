package entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@Table(name = "facilities")
public class Facility implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "facility_id")
    private String id;
    @Column(name = "facility_name")
    private String name;
    private String description;
    @Column(name = "purchase_date")
    private LocalDate purchaseDate;
    private double price;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String location;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;



    public Facility() {
    }

    public Facility(String id, String name, String description, LocalDate purchaseDate, double price, Status status, String location) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.purchaseDate = purchaseDate;
        this.price = price;
        this.status = status;
        this.location = location;
    }

    public Facility(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Facility{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", price=" + price +
                ", status=" + status +
                ", location='" + location + '\'' +
                '}';
    }


}
