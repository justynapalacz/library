package palaczjustyna.library.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "borrow")
@NoArgsConstructor
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "date_of_borrow")
    private Date dateOfBorrow;
    @Column(name = "date_of_return")
    private Date dateOfReturn;

    @ManyToOne()
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne()
    @JoinColumn(name="book_id", nullable=false)
    private Book book;

    public Borrow(User user, Book book, Date dateOfBorrow) {
        this.user = user;
        this.book = book;
        this.dateOfBorrow = dateOfBorrow;
    }

    @Override
    public String toString() {
        return "Borrow" +
                "id=" + id +
                ", dateOfBorrow=" + dateOfBorrow +
                ", dateOfReturn=" + dateOfReturn +
                ", user=" + user.getLastName() +
                ", book=" + book.getTitle() ;
    }
}
