package maid.cc.library.controller;
import maid.cc.library.dto.BorrowBookRequest;
import maid.cc.library.dto.ReturnBookRequest;
import maid.cc.library.model.BorrowingRecordModel;
import maid.cc.library.service.BorrowingService;
import maid.cc.library.entity.BorrowingRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrow")
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingService;

    @PostMapping()
    public ResponseEntity<BorrowingRecordModel> borrowBook(@RequestBody BorrowBookRequest request) {
        BorrowingRecordModel record = borrowingService.borrowBook(request.bookId, request.patronId);
        return ResponseEntity.ok(record);
    }

    @PostMapping("/return")
    public ResponseEntity<BorrowingRecord> returnBook(@RequestBody ReturnBookRequest request) {
         borrowingService.returnBook(request.borrowingRecordId);
        return ResponseEntity.ok().build();
    }
}
