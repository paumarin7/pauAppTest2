package com.example.readbooks.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookDao {

    @Query("SELECT * FROM book")
    List<Book> getAll();

    @Insert
    void insert(Book q);


    //EXTRA
    @Update
    void update(Book q);

    @Delete
    void delete(Book q);

    @Query("SELECT * FROM book WHERE book_id == :book_id")
    Book findById(int book_id);

    @Query("DELETE FROM book")
    void deleteAll();
}