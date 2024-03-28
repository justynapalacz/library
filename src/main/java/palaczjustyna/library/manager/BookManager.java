package palaczjustyna.library.manager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import palaczjustyna.library.entity.Book;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class BookManager {
    private final SessionFactory sessionFactory;

    public BookManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<Integer> addBook(final String title, final String author) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Integer id = (Integer) session.save(new Book(title, author, true));
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

    public List<Book> findBooksByAuthor(final String author) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
            Root<Book> root = criteriaQuery.from(Book.class);
            criteriaQuery.select(root).
                    where(criteriaBuilder.like(root.get("author"), "%" + author + "%"));
            List<Book> bookList =  session.createQuery(criteriaQuery).list();
            session.getTransaction().commit();
            return bookList;
        }
    }

    public List<Book> findBooksByTitle(final String title) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Book> bookList = session
                    .createQuery("FROM Book WHERE title = :title", Book.class)
                    .setParameter("title", title).list();
            session.getTransaction().commit();
            return bookList;
        }
    }

    public Book findBookByID(final int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Book book = session.get(Book.class, id);
            session.getTransaction().commit();
            return book;
        }
    }
}
