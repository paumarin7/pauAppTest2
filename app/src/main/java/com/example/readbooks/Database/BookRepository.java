package com.example.readbooks.Database;


import java.util.List;

public class BookRepository {
    BookDao bookDao;

    public BookRepository(BookDao bookDao) {
        this.bookDao = bookDao ;
    }

    public List<Book> getAll() {
        return bookDao.getAll();
    }

    public void insert(Book q) {
        this.bookDao.insert(q);
    }

    public void update(Book q) {
        this.bookDao.update(q);
    }

    public void delete(Book q) {
        this.bookDao.delete(q);
    }

    public Book findById(int book_id) {
        return bookDao.findById(book_id);
    }

    public void deleteAll() {
        this.bookDao.deleteAll();
    }
}
