package palaczjustyna.library.manager;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import palaczjustyna.library.entity.Book;
import palaczjustyna.library.entity.Borrow;
import palaczjustyna.library.entity.User;
import palaczjustyna.library.exceptions.NotAvailableBook;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MenuManager {
   final BookManager bookManager;
   final UserManager userManager;
   final BorrowManager borrowManager;


    public MenuManager() {
        SessionFactory factory = new Configuration()
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Borrow.class)
                .buildSessionFactory();

        bookManager = new BookManager(factory);
        userManager = new UserManager(factory);
        borrowManager = new BorrowManager(factory);
    }

    public void mange() {

        menuLoop:
        while (true) {
            System.out.println("----------------------------");
            System.out.println("Hello, please choose action:");
            System.out.println("Press 1 to adding book ");
            System.out.println("Press 2 to print all books");
            System.out.println("Press 3 to find books by author");
            System.out.println("Press 4 to find books by title");
            System.out.println("Press 5 to add user");
            System.out.println("Press 6 to print all users");
            System.out.println("Press 7 to borrow book");
            System.out.println("Press 8 to return book");

            System.out.println("Press Q to exit");

            Scanner scanner = new Scanner(System.in);
            String number = scanner.nextLine().toUpperCase();

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
                case "4":
                    findBookByTitleMenu();
                    break;
                case "5":
                    addUserMenu();
                    break;
                case "6":
                    getUserMenu();
                    break;
                case "7":
                    borrowBookMenu();
                    break;
                case "8":
                    returnBookMenu();
                    break;

                case "Q":
                    break menuLoop;
                default:
                    System.out.println("Sorry, I don't understand. Choose again!");
                    System.out.println("----------------------------------------");
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
        if (idAddedBook.isEmpty()) {
            System.out.println("Error! Book not added, please try again!");
        } else {
            System.out.println("You added book: " + title + " by " + author + " on position: " + idAddedBook.get());
        }
    }

    private void getBookMenu() {
        System.out.println("Books: ");
        printBooks(bookManager.getBooks());
    }

    private void findBookByAuthorMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter author, which books you are looking for: ");
        String partialAuthor = scanner.nextLine();
        printBooks(bookManager.findBooksByAuthor(partialAuthor));
    }

    private void findBookByTitleMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter title, which you are looking for: ");
        String title = scanner.nextLine();
        printBooks(bookManager.findBooksByTitle(title));
    }

    private void addUserMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter first name:");
        String firstName = scanner.nextLine();
        System.out.println("Please enter last name ");
        String lastName = scanner.nextLine();
        System.out.println("Please enter your birth date. Please use format: yyyy-MM-dd");
        String dateOfBirth = scanner.nextLine();
        Optional<Integer> idAddedUser;
        try {
            idAddedUser = userManager.addUser(firstName, lastName, scanToDate(dateOfBirth));
            if (idAddedUser.isEmpty()) {
                System.out.println("Error! User not added, please try again!");
            } else {
                System.out.println("You create user " + firstName + " " + lastName + " on position: " + idAddedUser.get());
            }
        } catch (ParseException e) {
            System.out.println("Wrong date format. Please use format: yyyy-MM-dd. For example: 1985-01-01");
            System.out.println("Please try again");
            addUserMenu();
        }
    }
    private void getUserMenu() {
        System.out.println("Users: ");
        printUsers(userManager.getUsers());
    }

    private void borrowBookMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter user ID: ");
        int userID = scanner.nextInt();
        System.out.println("Please enter book ID: ");
        int bookId = scanner.nextInt();
        User user = userManager.findUserByID(userID);
        if (user==null){
            System.out.println("User not found. Please try again!");
            return;
        }
        Book book = bookManager.findBookByID(bookId);
        if (book == null){
            System.out.println("Book not found. Please try again!");
            return;
        }

        try {
            Optional<Integer> idBorrow = borrowManager.addBorrow(user, book);
            if (idBorrow.isEmpty()) {
                System.out.println("Error! Problem with borrowing a book, please try again!");
            } else {
                System.out.println("User: " + user.getId() + " borrowed book " + bookId + ". Transaction nr: " + idBorrow.get());
            }
        } catch (NotAvailableBook e) {
            System.out.println(e.getMessage());
        }
    }

    private void returnBookMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter user ID: ");
        int userID = scanner.nextInt();
        List<Borrow> borrowList = borrowManager.getBorrowByUserId(userID);
        printBorrow(borrowList);
        System.out.println("Please enter id of the borrowing you wish to return: ");
        int borrowId = scanner.nextInt();
        boolean ifExist = borrowList
                .stream()
                        .anyMatch( borrow -> borrow.getId()==borrowId);
        if (ifExist){
            borrowManager.returnByBorrowId(borrowId);
            System.out.println("Success! The book has been returned.");
        }
        else System.out.println("error");
    }

    public Date scanToDate(String input) throws ParseException {
        try (Scanner scanner = new Scanner(input)) {
            String dateString = scanner.nextLine();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            formatter.setLenient(false);
            return formatter.parse(dateString);
        }
    }

    private void printBooks(List<Book> bookManager) {
        if (bookManager.isEmpty()) {
            System.out.println("No result. Please try again.");
        } else {
            for (Book book : bookManager) {
                System.out.println(book);
            }
        }
    }

    private void printBorrow(List<Borrow> borrowManager) {
        if (borrowManager.isEmpty()) {
            System.out.println("No result. Please try again.");
        } else {
            for (Borrow borrow : borrowManager) {
                System.out.println(borrow);
            }
        }
    }

    private void printUsers(List<User> userManager) {
        if (userManager.isEmpty()) {
            System.out.println("No result. Please try again.");
        } else {
            for (User user : userManager) {
                System.out.println(user);
            }
        }
    }
}
