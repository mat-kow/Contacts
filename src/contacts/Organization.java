package contacts;

import java.util.ArrayList;
import java.util.List;

public class Organization extends Contact {
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Organization(String name) {
        super(name);
    }

    @Override
    public void printInfo() {
        System.out.println("Organization name: " + getName());
        System.out.println("Address: " + getAddress());
        System.out.println("Number: " + getPhoneNumber());
        System.out.println("Time created: " + getCreationTimeStamp());
        System.out.println("Time last edit: " + getLastEditTimeStamp());
    }

    @Override
    public List<String> editable() {
        List<String> editable = new ArrayList<>();
        editable.add("name");
        editable.add("address");
        return editable;
    }

    @Override
    public boolean edit(String field, String newValue) {
        switch (field) {
            case "name":
                setName(newValue);
                return true;
            case "address":
                setAddress(newValue);
                return true;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        return getName();
    }

}
