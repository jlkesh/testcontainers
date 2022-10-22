package com.example.testcontainers;

import lombok.*;
import org.hibernate.boot.jaxb.mapping.spi.FetchableAttribute;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SpringBootApplication
public class TestContainersApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestContainersApplication.class, args);
    }

}
