package maid.cc.library;

import maid.cc.library.controller.BorrowingController;
import maid.cc.library.dto.BorrowBookRequest;
import maid.cc.library.dto.ReturnBookRequest;
import maid.cc.library.entity.Book;
import maid.cc.library.entity.BorrowingRecord;
import maid.cc.library.entity.Patron;
import maid.cc.library.model.BorrowingRecordModel;
import maid.cc.library.repository.BookRepository;
import maid.cc.library.repository.BorrowingRecordRepository;
import maid.cc.library.repository.PatronRepository;
import maid.cc.library.service.BorrowingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.util.AssertionErrors;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class BorrowingRecordControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PatronRepository patronRepository;
    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Test
    public void testBorrowBook() throws Exception {
        Book book = new Book();
        book.setAuthor("auther");
        book.setTitle("title111");
        book.setIsbn("1231312312");
        book.setPublicationYear(1898);
        bookRepository.save(book);

        Patron patron = new Patron();
        patron.setContactInfo("contactinfo");
        patron.setName("name");
        patronRepository.save(patron);

        BorrowBookRequest request = new BorrowBookRequest();
        request.bookId = book.getId();
        request.patronId = patron.getId();

        mockMvc.perform(post("/api/borrow")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(write(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.borrowDate").isString())
                .andExpect(jsonPath("$.returnDate").isEmpty());


        mockMvc.perform(post("/api/borrow")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(write(request)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void testReturnBook() throws Exception {
        Book book = new Book();
        book.setAuthor("auther");
        book.setTitle("title111");
        book.setIsbn("12313123121");
        book.setPublicationYear(1898);
        bookRepository.save(book);

        Patron patron = new Patron();
        patron.setContactInfo("contactinfo1");
        patron.setName("name");
        patronRepository.save(patron);

        BorrowBookRequest request = new BorrowBookRequest();
        request.bookId = book.getId();
        request.patronId = patron.getId();

        mockMvc.perform(post("/api/borrow")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(write(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.borrowDate").isString())
                .andExpect(jsonPath("$.returnDate").isEmpty());

        BorrowingRecord borrowingRecord = borrowingRecordRepository.findTop1ByBookAndPatronOrderByIdDesc(book, patron);

        Assertions.assertNotNull(borrowingRecord);

        ReturnBookRequest returnRequest = new ReturnBookRequest();
        returnRequest.borrowingRecordId = borrowingRecord.getId();

        mockMvc.perform(post("/api/borrow/return")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(write(returnRequest)))
                .andExpect(status().isOk());



        mockMvc.perform(post("/api/borrow")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(write(request)))
                .andExpect(jsonPath("$.borrowDate").isString())
                .andExpect(jsonPath("$.returnDate").isEmpty());
    }


}

