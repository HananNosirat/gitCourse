package maid.cc.library.config.exception;

public class PatronNotFoundException extends RuntimeException{

    public PatronNotFoundException() {
        super("Patron not found");
    }
}
