package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "departments")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "department_id")
    private String id;
    @Column(name = "department_name")
    private String name;
    private String location;

    @OneToOne
    @JoinColumn(name = "manager_id")
    private Person manager;

    @OneToMany(mappedBy = "department")
    private Set<Facility> facilities;

    public Department() {
    }

    public Department(String id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }


}
