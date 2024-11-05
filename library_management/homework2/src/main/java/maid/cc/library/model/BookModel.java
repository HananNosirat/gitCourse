package maid.cc.library.model;

import lombok.Data;
import maid.cc.library.entity.Book;

@Data
public class BookModel {
    private Long id;
    private String title;
    private String author;
    private int publicationYear;
    private String isbn;
    public BookModel() {
    }

    public BookModel(Book t) {
        this.id= t.getId();
        this.title= t.getTitle();
        this.author= t.getAuthor();
        this.publicationYear= t.getPublicationYear();
        this.isbn= t.getIsbn();
    }
}
