package palaczjustyna.library.manager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import palaczjustyna.library.entity.Book;
import palaczjustyna.library.entity.Borrow;
import palaczjustyna.library.entity.User;
import java.util.Date;
import java.util.Optional;

public class BorrowManager {

    private final SessionFactory sessionFactory;

    public BorrowManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<Integer> addBorrow (final User user, final Book book) {
        if (!book.isStatus()){
            return Optional.empty();
        }
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            book.setStatus(false);
            session.merge(book);
            Integer id = (Integer) session.save(new Borrow(user, book, new Date()));

            session.getTransaction().commit();
            return Optional.ofNullable(id);
        }
    }
}
