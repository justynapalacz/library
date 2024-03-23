package palaczjustyna.library.manager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import palaczjustyna.library.entity.User;

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

    public User findUserByID (final int id){
        try (Session session = sessionFactory.openSession()) {
            return  session.get(User.class, id);
        }
    }
}
