package kz.attractor.java.HW44;

import FileService.FileBooksService;

import java.util.List;

public class BookDataModel {
    private List<Book> books;

    public BookDataModel() {
        books = FileBooksService.readFile();
    }
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
