import java.io.*;
import java.util.Scanner;

public class ContactApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isContinued = true;//used to exit the application
        Contact contact = new Contact();
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
            switch (userInput) {
                case "1" -> contact.optionOne();

//<-------------------------------Add Contact----------------------------------------------------------------->
                case "2" -> contact.optionTwo();

//<-------------------------------Search Contacts----------------------------------------------------------------->
                case "3" -> contact.optionThree();

//<-------------------------------Delete Contacts----------------------------------------------------------------->
                case "4" -> contact.optionFour();

//<-------------------------------Exit App----------------------------------------------------------------->
                case "5" -> {
                    asciiBanners.endBanner();
                    isContinued = false;
                }
//<-------------------------------Input Error----------------------------------------------------------------->
                default -> System.out.println("Not a Valid response.....\n Try again!");
            }
        } while (isContinued);
    }
}
