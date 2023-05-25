package ru.shavshin.booksaccountingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BooksAccountingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooksAccountingAppApplication.class, args);
    }

}
