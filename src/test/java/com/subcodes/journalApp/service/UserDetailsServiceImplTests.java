package com.subcodes.journalApp.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.subcodes.journalApp.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

public class UserDetailsServiceImplTests {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadUserByUsername(){
        when(userService.getUserByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("name10").password("pass10").roles(new ArrayList<>()).build());
        UserDetails user =  userDetailsServiceImpl.loadUserByUsername("name10");
        Assertions.assertNotNull(user);
    }

}
