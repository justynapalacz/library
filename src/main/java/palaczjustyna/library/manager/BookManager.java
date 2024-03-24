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
            return session
                    .createQuery("FROM Book WHERE author = :author", Book.class)
                    .setParameter("author", author).list();
        }
    }

    public List<Book> findBooksByPartialAuthor(final String partialAuthor) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
            Root<Book> root = criteriaQuery.from(Book.class);
            criteriaQuery.select(root).
                    where(criteriaBuilder.like(root.get("author"), "%" + partialAuthor + "%"));
            return session.createQuery(criteriaQuery).list();
        }
    }

    public List<Book> findBooksByTitle(final String title) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("FROM Book WHERE title = :title", Book.class)
                    .setParameter("title", title).list();
        }
    }

    public Book findBookByID(final int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Book.class, id);
        }
    }
}
