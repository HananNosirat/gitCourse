package maid.cc.library.service;

import com.google.common.base.Strings;
import maid.cc.library.config.exception.BookNotFoundException;
import maid.cc.library.dto.AddBookRequest;
import maid.cc.library.dto.UpdateBookRequest;
import maid.cc.library.model.BookModel;
import maid.cc.library.repository.BookRepository;
import maid.cc.library.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<BookModel> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookModel> bookModels = books.stream().map(book -> new BookModel(book)).toList();

        return bookModels;
    }

    public BookModel getBookById(Long id) {

        Optional<Book> bookOpt = bookRepository.findById(id);

        if (bookOpt.isEmpty()) throw new BookNotFoundException();

        return new BookModel(bookOpt.get());
    }

    public BookModel addBook(AddBookRequest bookRequest) {
        Book book = new Book();
        book.setIsbn(bookRequest.isbn);
        book.setTitle(bookRequest.title);
        book.setAuthor(bookRequest.author);
        book.setPublicationYear(bookRequest.publicationYear);

        book = bookRepository.save(book);
        return new BookModel(book);
    }

    public BookModel updateBook(Long id, UpdateBookRequest updateBookRequest) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isEmpty()) throw new BookNotFoundException();

        Book book = bookOpt.get();
        if(!Strings.isNullOrEmpty(updateBookRequest.title))
            book.setTitle(updateBookRequest.title);

        if(!Strings.isNullOrEmpty(updateBookRequest.author))
            book.setAuthor(updateBookRequest.author);

        if (updateBookRequest.publicationYear != null)
            book.setPublicationYear(updateBookRequest.publicationYear);

        book = bookRepository.save(book);

        return new BookModel(book);

    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(()->new BookNotFoundException());
        bookRepository.delete(book);
    }
}

