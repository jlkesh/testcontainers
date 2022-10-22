package com.example.testcontainers;

import com.example.testcontainers.user.User;
import com.example.testcontainers.user.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestContainersApplicationTests {

    @Autowired
    private UserService service;

    @Container
    public static PostgreSQLContainer postgreSQLContainer =
            new PostgreSQLContainer("postgres:11.17-alpine3.16")
                    .withDatabaseName("postgres")
                    .withUsername("postgres")
                    .withPassword("1")
;


    @DynamicPropertySource
    public static void prop(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    }

    @BeforeAll
    static void setUp() {
        postgreSQLContainer.withReuse(true);
        postgreSQLContainer.start();
    }

    @Test
    @Order(1)
    void test_create_user() throws IOException {
        User user = new User(11, "john@gmail.com", "123");
        User saved_user = service.create(user);
        System.out.println(new ObjectMapper().writeValueAsString(saved_user));
        Assertions.assertEquals(user.getEmail(), saved_user.getEmail());
    }

    @Test
    @Order(2)
    void test_for_not_existing_user_with_email() throws IOException {
        String errorMessage = Assertions.assertThrows(RuntimeException.class, () -> {
            service.findByEmail("email");
        }).getMessage();
        System.out.println(errorMessage);
    }

    @Test
    @Order(3)
    void test_for_users_size() {
        List<User> users = service.findAll();
        Assertions.assertEquals(1, users.size());
        Assertions.assertTrue(users.stream().filter(user -> user.getEmail().equals("john@gmail.com")).findFirst().isPresent());
    }


    @AfterAll
    static void tearDown() {
        postgreSQLContainer.stop();
    }

}
