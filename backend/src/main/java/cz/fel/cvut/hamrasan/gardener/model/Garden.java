package cz.fel.cvut.hamrasan.gardener.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "APP_GARDEN")
@NamedQueries({
        @NamedQuery(name = "Garden.findByName", query = "SELECT g FROM Garden g WHERE g.name = :name AND g.deleted_at is null")
})
public class Garden extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false, length = 100)
    @Size(max = 100, min = 1, message = "Name is in incorrect format.")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    private String location;

    @OneToMany(mappedBy = "garden")
    private List<Pressure> pressures;

    @OneToMany(mappedBy = "garden")
    private List<Temperature> temperatures;

    @OneToMany(mappedBy = "garden")
    private List<Humidity> humidities;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "plants_garden",
            joinColumns = @JoinColumn(name = "garden_id"),
            inverseJoinColumns = @JoinColumn(name = "plant_id"))
    private List<Plant> plants;


    public Garden(@Size(max = 100, min = 1, message = "Name is in incorrect format.") @NotBlank(message = "Name cannot be blank") String name,
                  String location, List<Pressure> pressures, List<Temperature> temperatures, List<Humidity> humidities,
                  User user, List<Plant> plants) {

        this.name = name;
        this.location = location;
        this.pressures = pressures;
        this.temperatures = temperatures;
        this.humidities = humidities;
        this.user = user;
        this.plants = plants;
    }

    public Garden() {
        this.humidities = new ArrayList<Humidity>();
        this.plants = new ArrayList<Plant>();
        this.pressures = new ArrayList<Pressure>();
        this.temperatures = new ArrayList<Temperature>();
    }


    public Garden(@Size(max = 100, min = 1, message = "Name is in incorrect format.") @NotBlank(message = "Name cannot be blank") String name, User user) {

        this.name = name;
        this.user = user;
        this.humidities = new ArrayList<Humidity>();
        this.plants = new ArrayList<Plant>();
        this.pressures = new ArrayList<Pressure>();
        this.temperatures = new ArrayList<Temperature>();
    }


    public String getName() {

        return name;
    }


    public void setName(String name) {

        this.name = name;
    }


    public String getLocation() {

        return location;
    }


    public void setLocation(String location) {

        this.location = location;
    }


    public List<Pressure> getPressures() {

        return pressures;
    }


    public void setPressures(List<Pressure> pressures) {

        this.pressures = pressures;
    }


    public List<Temperature> getTemperatures() {

        return temperatures;
    }


    public void setTemperatures(List<Temperature> temperatures) {

        this.temperatures = temperatures;
    }


    public List<Humidity> getHumidities() {

        return humidities;
    }


    public void setHumidities(List<Humidity> humidities) {

        this.humidities = humidities;
    }


    public User getUser() {

        return user;
    }


    public void setUser(User user) {

        this.user = user;
    }


    public List<Plant> getPlants() {

        return plants;
    }


    public void setPlants(List<Plant> plants) {

        this.plants = plants;
    }
}