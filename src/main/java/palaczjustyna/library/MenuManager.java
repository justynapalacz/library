package palaczjustyna.library;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class MenuManager {

    BookManager bookManager;

    public MenuManager() {

        SessionFactory factory = new Configuration()
                .addAnnotatedClass(Book.class)
                .buildSessionFactory();

        bookManager = new BookManager(factory);
    }

    public void mange() {


        while (true) {
            System.out.println("Hello, please choose action:");
            System.out.println("For adding book press 1");
            System.out.println("For checking all available books press 2");

            System.out.println("For exit press Q");

            Scanner scanner = new Scanner(System.in);
            String number = scanner.nextLine();

            if (number.equals("1")) {
                addBookMenu();
            } else if (number.equals("2")) {
                getBookMenu();
            } else if (number.equals("Q")) {
                break;
            }

        }

    }

    private void addBookMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the title of the book: ");
        String title = scanner.nextLine();
        System.out.println("Please enter the author of the book: ");
        String author = scanner.nextLine();
        bookManager.addBook(title, author);
        System.out.println("You added book: " + title + " by " + author);
    }

    private void getBookMenu() {
        System.out.println("Available books: ");
        System.out.println(bookManager.getBooks());
    }

}
