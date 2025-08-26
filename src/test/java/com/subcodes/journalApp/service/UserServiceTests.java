package com.subcodes.journalApp.service;

import com.subcodes.journalApp.model.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;

//    @BeforeEach
//    @BeforeAll
//    @AfterEach
//    @AfterAll

    @ParameterizedTest
    @ValueSource(strings ={         // can also create custom sources
            "name1",
            "name2",
            "admin1"
    })
    public void testFindUserByUsername(String name){
        assertNotNull(userService.getUserByUserName(name));
    }
}
