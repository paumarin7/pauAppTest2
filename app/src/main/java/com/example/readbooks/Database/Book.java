package com.example.readbooks.Database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "book")
public class Book implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "book_id")
    int book_id;
    @ColumnInfo(name = "imageView")
    int imageView;
    @ColumnInfo(name = "title")
    String title;
    @ColumnInfo(name = "author")
    String author;
    @ColumnInfo(name = "status")
    int status;
    @ColumnInfo(name = "star")
    float star;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
     byte[] bm;

    public byte[] getBm() {
        return bm;
    }

    public void setBm(byte[] bm) {
        this.bm = bm;
    }

    public Book(int imageView, String title, String author, int status ) {
        this.imageView = imageView;
        this.title = title;
        this.author = author;
        this.status = status;
    }

    public Book() {

    }


    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }
}
