package exam.hotel;


import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.Period;

public class Guest {
    private StringProperty name;
    private StringProperty surname;
    private IntegerProperty age;
    private StringProperty room;
    private ObjectProperty<LocalDate> birth;

    public Guest(String name, String surname, LocalDate birth, String room) {
        this.name = new SimpleStringProperty();
        this.surname = new SimpleStringProperty();
        this.room = new SimpleStringProperty();
        this.birth = new SimpleObjectProperty<>();
        this.age = new SimpleIntegerProperty();
        this.birth.set(birth);
        this.name.set(name);
        this.surname.set(surname);
        this.room.set(room);
        this.age.set(calculateAge(birth));
    }
    public static int calculateAge(LocalDate dob)
    {
        LocalDate curDate = LocalDate.now();

        if (dob != null)
        {
            return Period.between(dob, curDate).getYears();
        }
        else
        {
            return 0;
        }
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public int getAge() {
        return age.get();
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public LocalDate getBirth() {
        return birth.get();
    }

    public ObjectProperty<LocalDate> birthProperty() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth.set(birth);
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public String getRoom() {
        return room.get();
    }

    public StringProperty roomProperty() {
        return room;
    }

    public void setRoom(String room) {
        this.room.set(room);
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
}
