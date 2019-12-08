package usecase;


public class Passenger {

    private String DNI , name , surname;

    public Passenger() {
    }

    public Passenger(String DNI, String name, String surname) {
        this.DNI = DNI;
        this.name = name;
        this.surname = surname;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
