package com.example.readbooks;

import androidx.lifecycle.ViewModel;

import com.example.readbooks.Database.Book;

import java.util.List;

import static com.example.readbooks.MainActivity.bookRepository;
import static com.example.readbooks.MainActivity.booksarein;

public class BookViewModel extends ViewModel {


    static List<Book> books;


    public BookViewModel() {


        if (booksarein){
            booksarein = false;
            bookRepository.deleteAll();
            bookRepository.insert(new Book(R.drawable.elprincipito,"EL Principito", "Antoine de Saint-Exupéry",0));
            bookRepository.insert(new Book(R.drawable.elalquimista,"EL Alquimista", "Paulo Coelho",0));
            bookRepository.insert(new Book(R.drawable.lospilaresdelatierra,"Los Pilares de la tierra", "Ken Follett",0));
            bookRepository.insert(new Book(R.drawable.lacatedraldelamar,"La catedral del mar", "Lldefonso Falcones",0));
        }else {

        }



        books = bookRepository.getAll();
    }
}
