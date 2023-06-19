package com.example.accounts.controller;

import com.example.accounts.dto.Response;
import com.example.accounts.service.AuthService;
import com.example.accounts.session.TokenCacheManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.HttpServletResponse;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokenCacheManager tokenCacheManager;

    @MockBean
    private AuthService authService;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Test
    public void testLogin_NoActiveSession_Success() throws Exception {
        String username = "testuser";
        String password = "testpassword";
        String expectedToken = "testtoken";
        Mockito.when(tokenCacheManager.retrieveToken(Mockito.anyString())).thenReturn(null);
        Mockito.when(authService.login(Mockito.eq(username), Mockito.eq(password)))
            .thenReturn(new Response(true, "Login successful", expectedToken));

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
            .param("username", username)
            .param("password", password)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Login successful"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(expectedToken));

        Mockito.verify(tokenCacheManager).retrieveToken(Mockito.eq(username));
        Mockito.verify(authService).login(Mockito.eq(username), Mockito.eq(password));
        Mockito.verifyNoMoreInteractions(tokenCacheManager, authService);
    }

    @Test
    public void testLogin_ActiveSession_ReturnsResponse() throws Exception {
        String username = "testuser";
        String password = "testpassword";
        String activeSessionToken = "activeSessionToken";
        Mockito.when(tokenCacheManager.retrieveToken(Mockito.anyString())).thenReturn(activeSessionToken);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
            .param("username", username)
            .param("password", password)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User has an Active session"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist());

        Mockito.verify(tokenCacheManager).retrieveToken(Mockito.eq(username));
        Mockito.verifyNoMoreInteractions(tokenCacheManager, authService);
    }

}