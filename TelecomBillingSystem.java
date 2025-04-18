import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class BillingRecord implements Serializable {
    private String name;
    private String phoneNumber;
    private float billingAmount;

    public BillingRecord(String name, String phoneNumber, float billingAmount) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.billingAmount = billingAmount;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public float getBillingAmount() {
        return billingAmount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBillingAmount(float billingAmount) {
        this.billingAmount = billingAmount;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n" +
               "Phone Number: " + phoneNumber + "\n" +
               "Billing Amount: " + String.format("%.2f", billingAmount) + "\n" +
               "----------------------------";
    }
}

public class TelecomBillingSystem {
    private static final String FILENAME = "billing_records.dat";
    private static List<BillingRecord> records = loadRecords();

    // Function to load records from the file
    private static List<BillingRecord> loadRecords() {
        List<BillingRecord> loadedRecords = new ArrayList<>();
        File file = new File(FILENAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Object obj = ois.readObject();
                if (obj instanceof List) {
                    loadedRecords = (List<BillingRecord>) obj;
                } else {
                    System.err.println("Error: File contains incompatible data. Starting with an empty record list.");
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading records from file: " + e.getMessage());
            }
        }
        return loadedRecords;
    }

    // Function to save records to the file
    private static void saveRecords() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            oos.writeObject(records);
        } catch (IOException e) {
            System.err.println("Error saving records to file: " + e.getMessage());
        }
    }

    // Function to add a new record
    public static void addNewRecord(Scanner scanner) {
        System.out.print("Enter customer's name: ");
        String name = scanner.nextLine();

        System.out.print("Enter customer's phone number: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Enter billing amount: ");
        float billingAmount = scanner.nextFloat();
        scanner.nextLine(); // Consume newline

        BillingRecord newRecord = new BillingRecord(name, phoneNumber, billingAmount);
        records.add(newRecord);
        saveRecords();
        System.out.println("Record added successfully!");
    }

    // Function to display all records
    public static void displayRecords() {
        if (records.isEmpty()) {
            System.out.println("No customer records found.");
            return;
        }
        System.out.println("\nCustomer Records:");
        for (BillingRecord record : records) {
            System.out.println(record);
        }
    }

    // Function to modify a specific record
    public static void modifyRecord(Scanner scanner) {
        System.out.print("Enter the phone number of the record to modify: ");
        String phoneNumberToModify = scanner.nextLine();

        boolean found = false;
        for (BillingRecord record : records) {
            if (record.getPhoneNumber().equals(phoneNumberToModify)) {
                found = true;
                System.out.println("Record found. Enter new details:");

                System.out.print("Enter new name: ");
                record.setName(scanner.nextLine());

                System.out.print("Enter new phone number: ");
                record.setPhoneNumber(scanner.nextLine());

                System.out.print("Enter new billing amount: ");
                record.setBillingAmount(scanner.nextFloat());
                scanner.nextLine(); // Consume newline

                saveRecords();
                System.out.println("Record updated successfully!");
                break;
            }
        }

        if (!found) {
            System.out.println("Record with phone number " + phoneNumberToModify + " not found.");
        }
    }

    // Function to view the payment details of all records
    public static void viewPaymentDetails() {
        if (records.isEmpty()) {
            System.out.println("No customer records found.");
            return;
        }
        System.out.println("\nPayment Details:");
        for (BillingRecord record : records) {
            System.out.println("Name: " + record.getName() +
                               ", Phone Number: " + record.getPhoneNumber() +
                               ", Billing Amount: " + String.format("%.2f", record.getBillingAmount()));
        }
    }

    // Function to search for records based on name or phone number
    public static void searchRecords(Scanner scanner) {
        System.out.print("Enter name or phone number to search: ");
        String searchTerm = scanner.nextLine();

        boolean found = false;
        System.out.println("\nSearch Results:");
        for (BillingRecord record : records) {
            if (record.getName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                record.getPhoneNumber().contains(searchTerm)) {
                System.out.println(record);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No records found for '" + searchTerm + "'.");
        }
    }

    // Function to delete a record
    public static void deleteRecord(Scanner scanner) {
        System.out.print("Enter the phone number of the record to delete: ");
        String phoneNumberToDelete = scanner.nextLine();

        boolean found = false;
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getPhoneNumber().equals(phoneNumberToDelete)) {
                records.remove(i);
                saveRecords();
                System.out.println("Record with phone number " + phoneNumberToDelete + " deleted successfully.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Record not found for phone number " + phoneNumberToDelete + ".");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0; // Initialize choice

        do {
            System.out.println("\nTelecom Billing System");
            System.out.println("1. Add New Record");
            System.out.println("2. View List of Records");
            System.out.println("3. Modify Record");
            System.out.println("4. View Payment Details");
            System.out.println("5. Search Records");
            System.out.println("6. Delete Record");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addNewRecord(scanner);
                    break;
                case 2:
                    displayRecords();
                    break;
                case 3:
                    modifyRecord(scanner);
                    break;
                case 4:
                    viewPaymentDetails();
                    break;
                case 5:
                    searchRecords(scanner);
                    break;
                case 6:
                    deleteRecord(scanner);
                    break;
                case 7:
                    System.out.println("Exiting the system.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 7);

        scanner.close();
    }
}