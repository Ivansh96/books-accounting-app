package ru.shavshin.booksaccountingapp.dal.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "Book")
@Getter
@Setter
@NoArgsConstructor
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    @NotEmpty(message = "Имя не может быть пустым!")
    private String title;

    @Column(name = "author")
    @NotEmpty(message = "Имя не может быть пустым!")
    @Size(min = 2, max = 100, message = "Имя должно иметь от 2 до 100 символов!")
    private String author;

    @Column(name = "issue_year")
    private Integer issueYear;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    @Transient
    private Boolean expired;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private PersonEntity owner;

    public BookEntity(String title, String author, Integer issueYear) {
        this.title = title;
        this.author = author;
        this.issueYear = issueYear;
    }
}
