import java.io.*;
import java.util.Scanner;

public class ContactApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isContinued = true;//used to exit the application
        AsciiBanners asciiBanners = new AsciiBanners();//banner from AsciiBanners class
        asciiBanners.hello();
        do {
            System.out.println("1. View contacts.\n" +
                    "2. Add a new contact.\n" +
                    "3. Search a contact by name.\n" +
                    "4. Delete an existing contact.\n" +
                    "5. Exit.\n" +
                    "Enter an option (1, 2, 3, 4 or 5):");//Application functions
            String userInput = scanner.nextLine();//Prompts user for response
//<-------------------------------View Contacts----------------------------------------------------------------->
            if (userInput.equals("1")) {
                String fileName = "contacts.txt";//sets txt file to variable
                asciiBanners.contactListBanner();
                System.out.println("Name        | Phone      ");
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
                    asciiBanners.trollBanner();
                }
//<-------------------------------Add Contact----------------------------------------------------------------->
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
                    asciiBanners.contactAddedBanner();
                    System.out.println(name + " - " + phone);
                } catch (IOException e) {
                    System.err.println("Error writing to file: " + e.getMessage());
                    asciiBanners.trollBanner();
                }
//<-------------------------------Search Contacts----------------------------------------------------------------->
            } else if (userInput.equals("3")) {
                asciiBanners.monopolyBanner();
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
                    asciiBanners.trollBanner();
                }

                if (phone != null) {
                    System.out.println(name + " - " + phone);
                } else {
                    System.out.println("Contact not found: " + name);
                    asciiBanners.trollBanner();
                }
//<-------------------------------Delete Contacts----------------------------------------------------------------->
            } else if (userInput.equals("4")) {
                String fileName = "contacts.txt";
                System.out.println("Contact name to delete.");
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
                    asciiBanners.trollBanner();
                }

                if (contactDeleted) {
                    File oldFile = new File(fileName);
                    File newFile = new File(fileName + ".tmp");
                    if (oldFile.delete() && newFile.renameTo(oldFile)) {
                        asciiBanners.contactDeletedBanner();
                        System.out.println(nameToDelete);
                    } else {
                        System.err.println("Error deleting contact: " + nameToDelete);
                    }
                } else {
                    System.out.println("Contact not found: " + nameToDelete);
                }
//<-------------------------------Exit App----------------------------------------------------------------->
            } else if (userInput.equals("5")) {
                asciiBanners.endBanner();
                isContinued = false;
            }
            else {
                System.out.println("Not a Valid response.....\n Try again!");
            }
        } while (isContinued);
    }
}
