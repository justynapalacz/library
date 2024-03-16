package palaczjustyna.library;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Optional;
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

        menuLoop:
        while (true) {
            System.out.println("Hello, please choose action:");
            System.out.println("Press 1 for adding book ");
            System.out.println("Press 2 for checking all available books");
            System.out.println("Press 3 for finding books by author");

            System.out.println("Press Q for exit");

            Scanner scanner = new Scanner(System.in);
            String number = scanner.nextLine();

            switch (number) {
                case "1":
                    addBookMenu();
                    break;
                case "2":
                    getBookMenu();
                    break;
                case "3":
                    findBookByAuthorMenu();
                    break;

                case "Q":
                    break menuLoop;
            }
        }
    }

    private void addBookMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the title of the book: ");
        String title = scanner.nextLine();
        System.out.println("Please enter the author of the book: ");
        String author = scanner.nextLine();
        Optional<Integer> idAddedBook = bookManager.addBook(title, author);
        if(idAddedBook.isEmpty()) {
            System.out.println("Error! Book not added, please try again!");
        } else {
            System.out.println("You added book: " + title + " by " + author + "on position: " + idAddedBook.get());
        }
    }

    private void getBookMenu() {
        System.out.println("Available books: ");
        for (Book book: bookManager.getBooks()) {
            System.out.println(book);
        }
    }

    private void findBookByAuthorMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter author, which books you are looking for: ");
        String author = scanner.nextLine();
        for (Book book : bookManager.findBookByAuthor(author))
            System.out.println(book);
    }

}
