package palaczjustyna.library;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class BookManager {
    private SessionFactory sessionFactory;
    public BookManager (SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<Integer> addBook(String title, String author) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Integer id = (Integer) session.save(new Book(title, author));
            session.getTransaction().commit();
            return Optional.ofNullable(id);
        }
    }

    public List<Book> getBooks() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Book> books = session.createQuery("FROM Book", Book.class).list();
            session.getTransaction().commit();
            return books;
        }
    }




}
