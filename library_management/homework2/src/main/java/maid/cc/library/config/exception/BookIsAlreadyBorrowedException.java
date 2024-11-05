package maid.cc.library.config.exception;

public class BookIsAlreadyBorrowedException extends RuntimeException{

    public BookIsAlreadyBorrowedException() {
        super("Book is already borrowed");
    }
}
