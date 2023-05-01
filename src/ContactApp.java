import java.io.*;
import java.util.Scanner;

public class ContactApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isContinued = true;
        do {

            System.out.println("1. View contacts.\n" +
                    "2. Add a new contact.\n" +
                    "3. Search a contact by name.\n" +
                    "4. Delete an existing contact.\n" +
                    "5. Exit.\n" +
                    "Enter an option (1, 2, 3, 4 or 5):");
            String userInput = scanner.nextLine();
            if (userInput.equals("1")) {
                String fileName = "contacts.txt";
                try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] contactInfo = line.split(",");
                        String name = contactInfo[0];
                        String phone = contactInfo[1];
                        String formattedContact = ContactFormatter.format(name, phone);
                        System.out.println(formattedContact);
                    }
                } catch (IOException e) {
                    System.err.println("Error reading file: " + e.getMessage());
                }
            } else if (userInput.equals("2")) {
                String fileName = "contacts.txt";
                System.out.println("Enter New Contact Name: ");
                String name = scanner.nextLine();
                System.out.println("Enter New Contact Number: ");
                String phone = scanner.nextLine();
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
                    String contactLine = name + "," + phone;
                    bw.write(contactLine);
                    bw.newLine();
                    System.out.println("Contact added: " + name + " - " + phone);
                } catch (IOException e) {
                    System.err.println("Error writing to file: " + e.getMessage());
                }
            } else if (userInput.equals("3")) {
                String fileName = "contacts.txt";
                System.out.println("Enter Search Name: ");
                String name = scanner.nextLine();
                String phone = null;
                try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length == 2 && parts[0].equals(name)) {
                            phone = parts[1];
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Error reading file: " + e.getMessage());
                }

                if (phone != null) {
                    System.out.println(name + " - " + phone);
                } else {
                    System.out.println("Contact not found: " + name);
                }
            } else if (userInput.equals("4")) {
                String fileName = "contacts.txt";
                String nameToDelete = scanner.nextLine();
                boolean contactDeleted = false;
                try (BufferedReader br = new BufferedReader(new FileReader(fileName));
                     BufferedWriter bw = new BufferedWriter(new FileWriter(fileName + ".tmp"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length == 2 && !parts[0].equals(nameToDelete)) {
                            bw.write(line);
                            bw.newLine();
                        } else {
                            contactDeleted = true;
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Error reading or writing file: " + e.getMessage());
                }

                if (contactDeleted) {
                    File oldFile = new File(fileName);
                    File newFile = new File(fileName + ".tmp");
                    if (oldFile.delete() && newFile.renameTo(oldFile)) {
                        System.out.println("Contact deleted: " + nameToDelete);
                    } else {
                        System.err.println("Error deleting contact: " + nameToDelete);
                    }
                } else {
                    System.out.println("Contact not found: " + nameToDelete);
                }
            } else if (userInput.equals("5")) {
                isContinued = false;
            }
        } while (isContinued);
    }
}
