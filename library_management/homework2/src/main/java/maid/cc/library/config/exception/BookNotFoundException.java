package maid.cc.library.config.exception;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException() {
        super("Book not found");
    }
}
