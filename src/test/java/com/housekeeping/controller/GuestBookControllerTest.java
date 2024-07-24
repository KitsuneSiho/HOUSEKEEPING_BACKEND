package com.housekeeping.controller;

import com.housekeeping.entity.Guestbook;
import com.housekeeping.service.GuestBookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GuestBookController.class)
public class GuestBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GuestBookService guestbookService;


    @Test
    public void testPostGuestbookWrite() throws Exception {
        Guestbook newEntry = new Guestbook();
        newEntry.setGuestbookContent("Hello, world!");

        Mockito.when(guestbookService.save(any(Guestbook.class))).thenReturn(newEntry);

        mockMvc.perform(MockMvcRequestBuilders.post("/guestbook/write")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"guestbookContent\": \"Hello, world!\", \"guestbookOwner\": {\"userId\": 1}, \"guestbookWriter\": {\"userId\": 2}}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.guestbookContent").value("Hello, world!"));
    }

    @Test
    public void testDeleteGuestbookDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/guestbook/delete/1"))
                .andExpect(status().isOk());
    }
}
