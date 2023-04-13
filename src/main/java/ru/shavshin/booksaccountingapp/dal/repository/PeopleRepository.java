package ru.shavshin.booksaccountingapp.dal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shavshin.booksaccountingapp.dal.entity.PersonEntity;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<PersonEntity, Integer> {

        Optional<PersonEntity> findByFullName(String fullName);


}
