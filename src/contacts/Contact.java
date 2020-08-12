package contacts;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Contact implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String phoneNumber;
    private final LocalDateTime creationTimeStamp;
    private LocalDateTime lastEditTimeStamp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasNumber() {
        return !phoneNumber.isEmpty();
    }

    public boolean setPhoneNumber(String phoneNumber) {
        if (checkNumber(phoneNumber)) {
            this.phoneNumber = phoneNumber;
            return true;
        }
        this.phoneNumber = "";
        return false;
    }

    public Contact(String name) {
        this.name = name;
        this.phoneNumber = "";
        creationTimeStamp = LocalDateTime.now();
        lastEditTimeStamp = LocalDateTime.now();
    }

    public LocalDateTime getLastEditTimeStamp() {
        return lastEditTimeStamp;
    }

    public void setLastEditTimeStamp(LocalDateTime lastEditTimeStamp) {
        this.lastEditTimeStamp = lastEditTimeStamp;
    }

    public LocalDateTime getCreationTimeStamp() {
        return creationTimeStamp;
    }

    private boolean checkNumber(String number) {
        String regex = "\\+?([\\da-zA-Z]+" +
                "|(\\([\\da-zA-Z]+\\)|[\\da-zA-Z]+)([ \\-][\\da-zA-Z]{2,})*" +
                "|[\\da-zA-Z]+[\\- ]?\\([\\da-zA-Z]{2,}\\)([ \\-][\\da-zA-Z]{2,})*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

//    @Override
//    public String toString() {
//        return String.format("%s %s, %s", name, surName, phoneNumber.isEmpty() ? "[no number]" : phoneNumber);
//    }

    public abstract void printInfo();
    public abstract List<String> editable();
    public abstract boolean edit(String field, String newValue);

    public String getPhoneNumber() {
        if (phoneNumber.isEmpty()) {
            return "[no number]";
        }
        return phoneNumber;
    }
}
