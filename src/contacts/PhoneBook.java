package contacts;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneBook {
    private List<Contact> contacts;
    private String fileName;

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public PhoneBook() {
        contacts = new ArrayList<>();
    }
    public PhoneBook(String fileName) {
        this.fileName = fileName;
        try {
            contacts = (List<Contact>) SerializationUtils.deserialize(fileName);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            contacts = new ArrayList<>();
        }
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        saveContacts();
    }
    public void removeContact(int index) {
        contacts.remove(index);
        saveContacts();
    }
    public void removeContact(Contact contact) {
        contacts.remove(contact);
        saveContacts();
    }
    public void editContact(Contact contact, int index) {
        contact.setLastEditTimeStamp(LocalDateTime.now());
        contacts.set(index, contact);
        saveContacts();
    }

    public int getNumberOfRecords() {
        return contacts.size();
    }

    public void printRecords() {
        for (int i = 0; i < contacts.size(); i++) {
            System.out.println(i + 1 + ". " + contacts.get(i).toString());
        }
    }

    private void saveContacts() {
        if (fileName != null) {
            try {
                SerializationUtils.serialize(contacts, fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Contact> search(String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        List<Contact> resultList = new ArrayList<>();
        for (Contact c : contacts) {
            Matcher matcher = pattern.matcher(c.toString() + c.getPhoneNumber());
            if (matcher.find()) {
                resultList.add(c);
            }
        }
        return resultList;
    }
}
