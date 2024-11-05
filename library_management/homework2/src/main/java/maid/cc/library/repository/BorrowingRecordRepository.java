package maid.cc.library.repository;

import maid.cc.library.entity.Book;
import maid.cc.library.entity.BorrowingRecord;
import maid.cc.library.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    BorrowingRecord findByBookAndReturnDateIsNull(Book book);
    BorrowingRecord findTop1ByBookAndPatronOrderByIdDesc(Book book, Patron patron);

}
