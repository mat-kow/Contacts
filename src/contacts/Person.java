package contacts;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Person extends Contact {
    private String surName;
    private Gender gender;
    private LocalDate birthDate;

    public String getBirthDate() {
        if (birthDate == null) {
            return "[no data]";
        }
        return birthDate.toString();
    }

    public boolean setBirthDate(String birthDate) {
        String[] date = birthDate.split("-");
        if (date.length != 3) {
            return false;
        }
        //todo validate
        this.birthDate = LocalDate.parse(birthDate);
        return true;
    }

    public Person(String name, String surName) {
        super(name);
        this.surName = surName;
    }

    public String getGender() {
        if (gender == null) {
            return "[no data]";
        }
        return gender.toString();
    }

    public boolean setGender(String gender) {
        if (gender.equals("F")) {
            this.gender = Gender.FEMALE;
            return true;
        }
        if (gender.equals("M")) {
            this.gender = Gender.MALE;
            return true;
        }
        return false;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public Person(String name) {
        super(name);
    }
    @Override
    public void printInfo() {
        System.out.println("Name: " + getName());
        System.out.println("Surname: " + getSurName());
        System.out.println("Birth date: " + getBirthDate());
        System.out.println("Gender: " + getGender());
        System.out.println("Number: " + getPhoneNumber());
        System.out.println("Time created: " + getCreationTimeStamp());
        System.out.println("Time last edit: " + getLastEditTimeStamp());
    }

    @Override
    public List<String> editable() {
        List<String> editable = new ArrayList<>();
        editable.add("name");
        editable.add("surname");
        editable.add("gender");
        editable.add("birth date");
        return editable;
    }

    @Override
    public boolean edit(String field, String newValue) {
        switch (field) {
            case "name":
                setName(newValue);
                return true;
            case "surname":
                setSurName(newValue);
                return true;
            case "gender":
                return setGender(newValue);
            case "birth date":
                return setBirthDate(newValue);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        return getName() + " " + surName;
    }
}
