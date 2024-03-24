package palaczjustyna.library.manager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import palaczjustyna.library.entity.Book;
import palaczjustyna.library.entity.Borrow;
import palaczjustyna.library.entity.User;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
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

    public List<Borrow> getBorrowByUserId (final int userId){
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Borrow> criteriaQuery = criteriaBuilder.createQuery(Borrow.class);
            Root<Borrow> root = criteriaQuery.from(Borrow.class);
            criteriaQuery.select(root).
                    where(criteriaBuilder.and(
                                    criteriaBuilder.equal(root.get("user").get("id"), userId),
                                    criteriaBuilder.isNull(root.get("dateOfReturn"))
                            )
                    );
            return  session.createQuery(criteriaQuery).list();
        }
    }

    public void returnByBorrowId (final int id){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Borrow borrow = session.get(Borrow.class, id);
            borrow.getBook().setStatus(true);
            borrow.setDateOfReturn(new Date());

            session.merge(borrow);
            session.getTransaction().commit();
        }
    }
}
