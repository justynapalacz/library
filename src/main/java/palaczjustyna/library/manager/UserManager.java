package palaczjustyna.library.manager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import palaczjustyna.library.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class UserManager {
    private final SessionFactory sessionFactory;

    public UserManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<Integer> addUser(final String firstName, final String lastName, LocalDate dateOfBirth) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Integer id = (Integer) session.save(new User(firstName, lastName, dateOfBirth));
            session.getTransaction().commit();
            return Optional.ofNullable(id);
        }
    }

    public List<User> getUsers() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<User> users = session.createQuery("FROM User", User.class).list();
            session.getTransaction().commit();
            return users;
        }
    }

    public User findUserByID (final int id){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.getTransaction().commit();
            return  user;
        }
    }
}
