package com.example.readbooks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.example.readbooks.Database.Book;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.readbooks.BookListFragment.adapter;
import static com.example.readbooks.MainActivity.bookRepository;

public class BookFragment extends Fragment {
    ImageView bookImage;
    TextView bookName;
    TextView authorName;
    Spinner spinnerBook;
    RatingBar stars;
    Book book;
    Bitmap bm;
    FloatingActionButton fbt;

    boolean bandera = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                book = (Book) bundle.getSerializable("book");
                bookName.setText(book.getTitle());
                authorName.setText(book.getAuthor());
                spinnerBook.setSelection(book.getStatus());
                stars.setRating(book.getStar());
                if (book.getImageView() == 0){
                    if (book.getBm() != null){
                        bm = BitmapFactory.decodeByteArray(book.getBm(), 0, book.getBm().length);
                        bookImage.setImageBitmap(bm);
                    }

                }else {
                    bookImage.setImageResource(book.getImageView());
                }
                fbt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Navigation.findNavController(getActivity(),R.id.fragment).navigate(R.id.bookListFragment);
                    }
                });
                spinnerBook.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 2){
                            stars.setRating(book.getStar());
                            stars.setVisibility(View.VISIBLE);
                        }else {
                            book.setStar(0);
                            bookRepository.update(book);
                            stars.setVisibility(View.INVISIBLE);
                        }
                        book.setStatus(position);
                        bookRepository.update(book);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });



                stars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        book.setStar(rating);
                        bookRepository.update(book); }});

            }

        });

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.book_fragment, container , false);
        bookImage = v.findViewById(R.id.imageBook);
        bookName = v.findViewById(R.id.bookName);
        authorName = v.findViewById(R.id.authorName);
        spinnerBook = v.findViewById(R.id.spinnerBook);
        Spinner spinner = v.findViewById(R.id.spinnerBook);
        ArrayAdapter<CharSequence> adapterspinner = ArrayAdapter.createFromResource(v.getContext(),R.array.spinner, R.layout.support_simple_spinner_dropdown_item);
        adapterspinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapterspinner);
        stars = v.findViewById(R.id.stars);
        fbt = v.findViewById(R.id.returnButtonFragment);
        return v;
    }

}
