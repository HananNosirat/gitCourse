package maid.cc.library.service;

import maid.cc.library.config.exception.BookIsAlreadyBorrowedException;
import maid.cc.library.config.exception.BookNotFoundException;
import maid.cc.library.config.exception.PatronNotFoundException;
import maid.cc.library.model.BorrowingRecordModel;
import maid.cc.library.repository.BookRepository;
import maid.cc.library.repository.BorrowingRecordRepository;
import maid.cc.library.repository.PatronRepository;
import maid.cc.library.entity.Book;
import maid.cc.library.entity.BorrowingRecord;
import maid.cc.library.entity.Patron;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BorrowingService {

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PatronRepository patronRepository;

    @Transactional
    public BorrowingRecordModel borrowBook(Long bookId, Long patronId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException());
        Patron patron = patronRepository.findById(patronId).orElseThrow(() -> new PatronNotFoundException());

        BorrowingRecord record = borrowingRecordRepository.findByBookAndReturnDateIsNull(book);
        if(record!=null)
            throw new BookIsAlreadyBorrowedException();

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());
        borrowingRecord= borrowingRecordRepository.save(borrowingRecord);

        return new BorrowingRecordModel(borrowingRecord);
    }

    @Transactional
    public void returnBook(Long requestId) {
        BorrowingRecord record = borrowingRecordRepository.findById(requestId).orElseThrow(()-> new BookNotFoundException());
        record.setReturnDate(LocalDate.now());
        borrowingRecordRepository.save(record);
    }

    public Object getAllBorrowingRecords() {
        return borrowingRecordRepository.findAll();
    }

}
