package palaczjustyna.library.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "books")
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column (name = "status")
    private boolean status;

    @OneToMany(mappedBy = "book")
    private List<Borrow> borrowList;

    public Book(String title, String author, boolean status) {
        this.title = title;
        this.author = author;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Book" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", status =" + status;
    }
}
