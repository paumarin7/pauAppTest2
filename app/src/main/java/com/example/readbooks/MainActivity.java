package com.example.readbooks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.readbooks.Database.AppDatabase;
import com.example.readbooks.Database.BookDao;
import com.example.readbooks.Database.BookRepository;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.example.readbooks.NewBookFragment.GET_FROM_GALLERY;
import static com.example.readbooks.NewBookFragment.book;
import static com.example.readbooks.NewBookFragment.newImageView;

public class MainActivity extends AppCompatActivity {

    String USERNAME = "Pau";
    String PASSWORD = "Pau";


    static boolean booksarein = true;
    static AppDatabase db;
    static BookDao bookDao;
    static BookRepository bookRepository;
    public static Context contextOfApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contextOfApplication = getApplicationContext();
        db = AppDatabase.getInstance(this);
        bookDao = db.bookDao();
        bookRepository = new BookRepository(bookDao);



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        for (Fragment fragment : getSupportFragmentManager().getPrimaryNavigationFragment().getChildFragmentManager().getFragments())
        {
            fragment.onActivityResult(requestCode, resultCode, data);
        }

        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                newImageView.setImageBitmap(bitmap);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                bitmap.recycle();
                book.setBm(byteArray);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }
}