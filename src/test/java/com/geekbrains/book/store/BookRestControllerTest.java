package com.geekbrains.book.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geekbrains.book.store.controllers.rest.BookRestController;
import com.geekbrains.book.store.entities.Book;
import com.geekbrains.book.store.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookRestController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BookRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    private final Book testBook1 = new Book(1L, "Harry Potter", "Description", BigDecimal.valueOf(300L), 2000, Book.Genre.FANTASY);
    private final Book testBook2 = new Book(2L, "Hobbit", "Description", BigDecimal.valueOf(500L), 2013, Book.Genre.FANTASTIC);

//    Без этих моков вылетает ошибка java.lang.AssertionError: Content type not set
    @BeforeEach
    public void init() {
        Mockito.doReturn(testBook1)
                .when(bookService)
                .findById(testBook1.getId());

        Mockito.doReturn(true)
                .when(bookService)
                .existsById(testBook2.getId());
    }
    @Test
    public void getBookByIdTest() throws Exception {
        mockMvc.perform(get("/api/v1/books/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Harry Potter")))
                .andExpect(jsonPath("$.description", is("Description")))
                .andExpect(jsonPath("$.genre", is("FANTASY")))
                .andExpect(jsonPath("$.price", is(300)))
                .andExpect(jsonPath("$.publishYear", is(2000)));
    }

    @Test
    public void createNewBookTest() throws Exception {
        mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBook1)))
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteByIdTest() throws Exception {
        mockMvc.perform(delete("/api/v1/books/{id}", testBook2.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteAllBooksTest() throws Exception {
        mockMvc.perform(delete("/api/v1/books"))
                .andExpect(status().isOk());
    }

    @Test
    void tryToModifyBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBook2)))
                .andExpect(status().isOk());
    }
}
