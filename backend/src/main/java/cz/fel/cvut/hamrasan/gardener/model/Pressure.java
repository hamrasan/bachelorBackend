package cz.fel.cvut.hamrasan.gardener.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "APP_PRESSURE")
@NamedQueries({
        @NamedQuery(name = "Pressure.findLatest", query = "SELECT p FROM Pressure p WHERE p.deleted_at is null ORDER BY p.date DESC ")
})
public class Pressure extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private LocalDateTime date;

    @Basic(optional = false)
    @Column(nullable = false)
    private float value;

    @ManyToOne
    @JoinColumn(name = "garden_id", nullable = false)
    private Garden garden;


    public Pressure(LocalDateTime date, float value, Garden garden) {

        this.date = date ;
        this.value = value;
        this.garden = garden;
    }


    public Pressure() { }


    public LocalDateTime getDate() {

        return date;
    }


    public void setDate(LocalDateTime localDate) {

        this.date = localDate;
    }


    public float getValue() {

        return value;
    }


    public void setValue(float value) {

        this.value = value;
    }


    public Garden getGarden() {

        return garden;
    }


    public void setGarden(Garden garden) {

        this.garden = garden;
    }
}
