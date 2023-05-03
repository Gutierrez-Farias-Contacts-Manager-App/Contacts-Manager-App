import java.io.*;
import java.util.Scanner;

public class Contact {
    Scanner scanner = new Scanner(System.in);
    public void optionOne(){
        String fileName = "contacts.txt";//sets txt file to variable
        AsciiBanners asciiBanners = new AsciiBanners();
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
    }
    public void optionTwo(){
        AsciiBanners asciiBanners = new AsciiBanners();
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
    }
    public void optionThree(){
        AsciiBanners asciiBanners = new AsciiBanners();
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
    }
    public void optionFour(){
        AsciiBanners asciiBanners = new AsciiBanners();
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
    }
}
