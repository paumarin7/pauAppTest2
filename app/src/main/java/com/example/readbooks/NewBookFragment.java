package com.example.readbooks;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.Navigation;

import com.example.readbooks.Database.Book;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static com.example.readbooks.MainActivity.bookRepository;
import static com.example.readbooks.MainActivity.contextOfApplication;
import static com.example.readbooks.MainActivity.getContextOfApplication;

public class NewBookFragment extends Fragment {

    public static final int GET_FROM_GALLERY = 3;
    Button bookImage;
    static ImageView newImageView;
    EditText bookName;
    EditText authorName;
    Spinner spinnerBook;
    RatingBar stars;
    static Book book = new Book();
    FloatingActionButton fbt;
    FloatingActionButton nfbt;
    static Bitmap bitmap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




//        book.setTitle(bookName.getText().toString());
//        book.setAuthor(authorName.getText().toString());
//        book.setStar(stars.getRating());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_book_fragment, container , false);
        bookImage = v.findViewById(R.id.newImageBook);
        nfbt = v.findViewById(R.id.newAddButton);
        bookName = v.findViewById(R.id.newBookName);
        authorName = v.findViewById(R.id.newAuthorName);
        spinnerBook = v.findViewById(R.id.newSpinnerBook);
        final ArrayAdapter<CharSequence> adapterspinner = ArrayAdapter.createFromResource(v.getContext(),R.array.spinner, R.layout.support_simple_spinner_dropdown_item);
        adapterspinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerBook.setAdapter(adapterspinner);
        stars = v.findViewById(R.id.newStars);
        newImageView = v.findViewById(R.id.newImageView);
        fbt = v.findViewById(R.id.newReturnButtonFragment);
        fbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(),R.id.newBookFragment).navigate(R.id.bookListFragment);

            }
        });
        bookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });


        

        spinnerBook.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerBook.getSelectedItemPosition() == 2){
                    stars.setRating(book.getStar());
                    stars.setVisibility(View.VISIBLE);
                }else {
                    book.setStar(0);
                    bookRepository.update(book);
                    stars.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        nfbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bookName != null){
                    book.setTitle(bookName.getText().toString());
                }else  {
                    Toast.makeText(getContextOfApplication(),"Insert Book Title",Toast.LENGTH_SHORT).show();
                }
                if (authorName != null){
                    book.setAuthor(authorName.getText().toString());
                }else {
                    Toast.makeText(getParentFragment().getContext(),"Insert Author Name",Toast.LENGTH_SHORT).show();
                }

                book.setStatus(spinnerBook.getSelectedItemPosition());

                book.setStar(stars.getRating());

                if (bitmap != null){
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    bitmap.recycle();
                    if (bitmap != null && !bitmap.isRecycled()) {
                        bitmap.recycle();
                        bitmap = null;
                    }
                    book.setBm(byteArray);
                }else {
                    Toast.makeText(getContextOfApplication(),"Insert CoverPage",Toast.LENGTH_SHORT).show();
                }

                bookRepository.insert(book);

                Navigation.findNavController(getActivity(),R.id.fragment).navigate(R.id.bookListFragment);
            }
        });


        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
             bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contextOfApplication.getContentResolver(), selectedImage);
                newImageView.setImageBitmap(bitmap);


            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}



