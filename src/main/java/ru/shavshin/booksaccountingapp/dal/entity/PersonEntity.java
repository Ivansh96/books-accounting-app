package ru.shavshin.booksaccountingapp.dal.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Person")
@Getter
@Setter
@NoArgsConstructor
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotEmpty(message = "Имя не может быть пустым!")
    @Size(min = 2, max = 100, message = "Имя должно иметь от 2 до 100 символов!")
    @Column(name = "full_name")
    private String fullName;

    @Min(value = 1900, message = "Год не может быть меньше 1900!")
    @Column(name = "year_of_birth")
    private Integer yearOfBirth;

    @OneToMany(mappedBy = "owner")
    private List<BookEntity> bookList;


    public PersonEntity(String fullName, Integer yearOfBirth) {
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }
}
