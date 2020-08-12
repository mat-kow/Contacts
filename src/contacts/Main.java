package contacts;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String MENU = "[menu] Enter action (add, list, search, count, exit): ";
    private static final Scanner scanner = new Scanner(System.in);
    private static PhoneBook phoneBook;

    public static void main(String[] args) {
        if (args.length >= 1) {
            phoneBook = new PhoneBook(args[0]);
        } else {
            System.out.println("It can take file name as program argument, to save/load list of contacts");
            phoneBook = new PhoneBook();
        }

        String action;
        while (true) {
            System.out.print(MENU);
            action = scanner.nextLine();
            if (action.equals("exit")) {
                break;
            }
            switch (action) {
                case "add":
                    add();
                    break;
                case "count":
                    count();
                    break;
                case "list":
                    list();
                    break;
                case "search":
                    search();
                    break;
            }
            System.out.println();
        }
    }

    private static void search() {
        while (true) {
            System.out.print("Enter search query: ");
            String regex = scanner.nextLine();
            List<Contact> searchResult = phoneBook.search(regex);
            System.out.printf("Found %d results:\n", searchResult.size());
            for (int i = 0; i < searchResult.size(); i++) {
                System.out.println(i + 1 + ". " + searchResult.get(i));
            }
            System.out.print("\n[search] Enter action ([number], back, again): ");
            String action = scanner.nextLine();
            if (action.matches("\\d+")) {
                int index = Integer.parseInt(action) - 1;
                Contact contact = searchResult.get(index);
                record(contact);
                return;
            } else if (action.equals("back")) {
                return;
            } else if (action.equals("again")) {
                continue;
            } else {
                System.out.println("Wrong action!");
            }
        }
    }

    private static void list() {
        if (phoneBook.getNumberOfRecords() == 0) {
            System.out.println("No records to list!");
            return;
        }
        phoneBook.printRecords();
        System.out.print("\n[list] Enter action ([number], back): ");
        String action = scanner.nextLine();
        if (action.matches("\\d+")) {
            int index = Integer.parseInt(action) - 1;
            Contact contact = phoneBook.getContacts().get(index);
            record(contact);
        } else if (action.equals("back")) {
            return;
        } else {
            System.out.println("Wrong command!");
            return;
        }

    }

    private static void record(Contact contact) {
        while (true) {
            contact.printInfo();
            System.out.print("\n[record] Enter action (edit, delete, menu): ");
            String action = scanner.nextLine();
            switch (action) {
                case "edit":
                    List<String> editable = contact.editable();
                    System.out.print("Select a field " + editable.toString()
                            .replaceAll("\\[", "(").replaceAll("\\]", "]") + ": ");
                    String field = scanner.nextLine();
                    System.out.print("Enter " + field + ": ");
                    String newValue = scanner.nextLine();
                    if (!contact.edit(field, newValue)) {
                        System.out.println("Wrong " + field);
                    }
                    System.out.println("Saved");
                    break;
                case "delete":
                    phoneBook.removeContact(contact);
                    System.out.println("The record removed!");
                    return;
                case "menu":
                    return;
                default:
                    System.out.println("Wrong command");
                    return;
            }
        }
    }

    private static void count() {
        String answer = String.format("The Phone Book has %d records.", phoneBook.getNumberOfRecords());
        System.out.println(answer);
    }

    private static void add() {
        System.out.print("Enter the type (person, organization): ");
        String type = scanner.nextLine();
        switch (type) {
            case "person":
                System.out.print("Enter the name: ");
                String name = scanner.nextLine();
                System.out.print("Enter the surname: ");
                String surName = scanner.nextLine();
                Person person = new Person(name, surName);
                System.out.print("Enter the birth date(rrrr-mm-dd): ");
                String birthDate = scanner.nextLine();
                if (!person.setBirthDate(birthDate)) {
                    System.out.println("Bad birth date!");
                }
                System.out.print("Enter gender: ");
                String gender = scanner.nextLine();
                if (!person.setGender(gender)) {
                    System.out.println("Bad gender!");
                }
                System.out.print("Enter the number: ");
                String phoneNumber = scanner.nextLine();
                if (!person.setPhoneNumber(phoneNumber)) {
                    System.out.println("Wrong number format!");
                }
                phoneBook.addContact(person);
                System.out.println("The record added.");
                break;
            case "organization":
            case "org":
                System.out.print("Enter organization name: ");
                String orgName = scanner.nextLine();
                Organization organization = new Organization(orgName);
                System.out.println("Enter the address:");
                String address = scanner.nextLine();
                organization.setAddress(address);
                System.out.print("Enter the number:");
                phoneNumber = scanner.nextLine();
                if (!organization.setPhoneNumber(phoneNumber)) {
                    System.out.println("Wrong number format!");
                }
                phoneBook.addContact(organization);
                System.out.println("The record added.");
                break;
        }
    }
}
