package palaczjustyna.library;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.Date;
import java.util.Optional;

public class UserManager {
    private final SessionFactory sessionFactory;

    public UserManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<Integer> addUser(final String firstName, final String lastName, Date dateOfBirth) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Integer id = (Integer) session.save(new User(firstName, lastName, dateOfBirth));
            session.getTransaction().commit();
            return Optional.ofNullable(id);
        }
    }
}
