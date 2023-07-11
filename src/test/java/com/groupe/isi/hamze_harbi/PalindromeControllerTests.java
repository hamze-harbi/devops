package com.groupe.isi.hamze_harbi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupe.isi.hamze_harbi.controllerRest.PalindromeRequest;
import com.groupe.isi.hamze_harbi.controllerRest.PalindromeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PalindromeController.class)
public class PalindromeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PalindromeController palindromeController;

    @Test
    public void testPalindrome() throws Exception {
        PalindromeRequest request = new PalindromeRequest();
        request.setMotAverifier("kayak");

        PalindromeResponse response = new PalindromeResponse(true);
        given(palindromeController.checkPalindrome(any(PalindromeRequest.class)))
                .willReturn(ResponseEntity.status(HttpStatus.ACCEPTED).body(response));

        mockMvc.perform(post("/api/v1/checkpalindrome")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isPalindrome", is(true)));
    }

    @Test
    public void testNonPalindrome() throws Exception {
        PalindromeRequest request = new PalindromeRequest();
        request.setMotAverifier("hello");

        PalindromeResponse response = new PalindromeResponse(false);
        given(palindromeController.checkPalindrome(any(PalindromeRequest.class)))
                .willReturn(ResponseEntity.status(HttpStatus.OK).body(response));

        mockMvc.perform(post("/api/v1/checkpalindrome")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isPalindrome", is(false)));
    }
}
