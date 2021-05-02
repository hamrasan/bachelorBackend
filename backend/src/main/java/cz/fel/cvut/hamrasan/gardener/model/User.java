package cz.fel.cvut.hamrasan.gardener.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "APP_USER")
@NamedQueries({
        @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email AND u.deleted_at is null")
})
public class User extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false, length = 30)
    @Size(max = 30, min = 1, message = "First name is in incorrect format.")
    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @Basic(optional = false)
    @Column(nullable = false)
    @Size(max = 30, min = 1, message = "Last name is in incorrect format.")
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Basic(optional = false)
    @Column(nullable = false)
    @Size(max = 255, min = 6, message = "Password is in incorrect format.")
    @NotBlank(message = "Password cannot be blank")
    private String password;

    @Email(message = "Email should be valid")
    @Basic(optional = false)
    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Garden> gardens;

    @OneToMany(mappedBy = "user")
    private List<Valve> valves;

    public User() {
        this.gardens = new ArrayList<>();
        this.valves = new ArrayList<>();
    }


    public User(@Size(max = 30, min = 1, message = "First name is in incorrect format.") @NotBlank(message = "First name cannot be blank") String firstName, @Size(max = 30, min = 1, message = "Last name is in incorrect format.") @NotBlank(message = "Last name cannot be blank") String lastName,
                @Size(max = 255, min = 6, message = "Password is in incorrect format.") @NotBlank(message = "Password cannot be blank") String password,
                @Email(message = "Email should be valid") @NotBlank(message = "Email cannot be blank") String email, List<Garden> gardens) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.gardens = gardens;
    }


    public User(@Size(max = 30, min = 1, message = "First name is in incorrect format.") @NotBlank(message = "First name cannot be blank") String firstName, @Size(max = 30, min = 1, message = "Last name is in incorrect format.") @NotBlank(message = "Last name cannot be blank") String lastName, @Size(max = 255, min = 6, message = "Password is in incorrect format.") @NotBlank(message = "Password cannot be blank") String password, @Email(message = "Email should be valid") @NotBlank(message = "Email cannot be blank") String email) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
//        this.plants = new ArrayList<Plant>();
        this.gardens = new ArrayList<>();
        this.valves = new ArrayList<>();
    }


    public User(@Email(message = "Email should be valid") String email,
                @Size(max = 255, min = 6, message = "Password is in incorrect format.") String password) {
        this.email = email;
        this.password = password;
//        this.plants = new ArrayList<Plant>();
        this.gardens = new ArrayList<>();
        this.valves = new ArrayList<>();
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void erasePassword() {
        this.password = null;
    }

    public String getEmail() {
        return email;
    }

    public void encodePassword() {
        this.password = new BCryptPasswordEncoder().encode(password);
    }


    public List<Garden> getGardens() {

        return gardens;
    }


    public void setGardens(List<Garden> gardens) {

        this.gardens = gardens;
    }


    public List<Valve> getValves() {

        return valves;
    }


    public void setValves(List<Valve> valves) {

        this.valves = valves;
    }

    public void addValve(Valve valve) {

        this.valves.add(valve);
    }


    @Override
    public String toString() {

        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", valves=" + valves +
                '}';
    }
}
