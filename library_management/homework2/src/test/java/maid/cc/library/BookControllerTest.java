package maid.cc.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import maid.cc.library.dto.AddBookRequest;
import maid.cc.library.dto.UpdateBookRequest;
import maid.cc.library.entity.Book;
import maid.cc.library.repository.BookRepository;
import maid.cc.library.service.BorrowingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class BookControllerTest  extends BaseTest{

    @Autowired
    BookRepository bookRepository;
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testAddBook() throws Exception {
        AddBookRequest addBookRequest = new AddBookRequest();
        addBookRequest.author= "author";
        addBookRequest.isbn= "123123123";
        addBookRequest.title= "Went with the wind";
        addBookRequest.publicationYear= 1996;
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(write(addBookRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Went with the wind"));
    }

    @Test
    public void testUpdateBook() throws Exception {
        AddBookRequest addBookRequest = new AddBookRequest();
        addBookRequest.author= "author1";
        addBookRequest.isbn= "1231231231";
        addBookRequest.title= "Went with the wind1";
        addBookRequest.publicationYear= 1997;

        mockMvc.perform(post("/api/books").contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(write(addBookRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(addBookRequest.title));


        Book byIsbn = bookRepository.findByIsbn(addBookRequest.isbn);
        Assertions.assertNotNull(byIsbn);

        UpdateBookRequest updateBookRequest = new UpdateBookRequest();
        updateBookRequest.author= "author1";
        updateBookRequest.title= "Went with the wind1";
        updateBookRequest.publicationYear= 1998;

        mockMvc.perform(put("/api/books/"+byIsbn.getId()).contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(write(addBookRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(updateBookRequest.title));

        Book byIsbn2 = bookRepository.findByIsbn(addBookRequest.isbn);
        Assertions.assertNotNull(byIsbn2);
        Assertions.assertEquals(byIsbn2.getTitle(), updateBookRequest.title);
        Assertions.assertEquals(byIsbn2.getAuthor(), updateBookRequest.author);


    }


}

