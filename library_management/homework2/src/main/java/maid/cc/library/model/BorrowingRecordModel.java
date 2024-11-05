package maid.cc.library.model;

import lombok.Data;
import maid.cc.library.entity.BorrowingRecord;

import java.time.LocalDate;

@Data
public class BorrowingRecordModel {
    private Long id;

    private BookModel book;

    private PatronModel patron;

    private LocalDate borrowDate;
    private LocalDate returnDate;

    public BorrowingRecordModel() {}
    public BorrowingRecordModel(BorrowingRecord borrowingRecord) {
        id = borrowingRecord.getId();
        book = new BookModel(borrowingRecord.getBook());
        patron = new PatronModel(borrowingRecord.getPatron());
        returnDate = borrowingRecord.getReturnDate();
        borrowDate = borrowingRecord.getBorrowDate();
    }
}
