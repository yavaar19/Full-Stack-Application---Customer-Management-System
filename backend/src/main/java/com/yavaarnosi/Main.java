package com.yavaarnosi;

import com.github.javafaker.Faker;
import com.yavaarnosi.customer.Customer;
import com.yavaarnosi.customer.CustomerRepository;
import com.yavaarnosi.customer.Gender;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {

        return args -> {

            var faker = new Faker();
            var name = faker.name();

            Random random = new Random();

            Gender[] genderValues = Gender.values();
            Gender gender = genderValues[random.nextInt(genderValues.length)];

            String firstName = name.firstName();
            String lastName = name.lastName();

            Customer customer = new Customer( firstName + " " + lastName, firstName.toLowerCase() + "." + lastName.toLowerCase() + "@example.com", random.nextInt(16, 99), gender );

            customerRepository.save(customer);

        };

    }

}
