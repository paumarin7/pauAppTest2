package com.example.readbooks;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readbooks.Database.Book;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static com.example.readbooks.MainActivity.bookRepository;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> implements View.OnClickListener{
        List<Book> books;
        private View.OnClickListener listener;
        public BookAdapter(List<Book> books ){
            this.books = books;
        }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item,parent,false);

        v.setOnClickListener(this);

        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.bindData(books.get(position));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
            this.listener=listener;

    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
            ImageView bookImage;
            TextView bookName;
            TextView authorName;
            Spinner spinnerBook;
            RatingBar stars;
            byte[] b;
            Bitmap bm;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            bookImage = itemView.findViewById(R.id.imageBook);
            bookName = itemView.findViewById(R.id.bookName);
            authorName = itemView.findViewById(R.id.authorName);
            spinnerBook = itemView.findViewById(R.id.spinnerBook);

            ArrayAdapter<CharSequence> adapterspinner = ArrayAdapter.createFromResource(itemView.getContext(),R.array.spinner, R.layout.support_simple_spinner_dropdown_item);
            adapterspinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spinnerBook.setEnabled(false);

            spinnerBook.setAdapter(adapterspinner);

            stars = itemView.findViewById(R.id.stars);

        }
        public void bindData(final Book book){
            if (book.getImageView() == 0){
                if (book.getBm() != null){
                    bm = BitmapFactory.decodeByteArray(book.getBm(), 0, book.getBm().length);
                    bookImage.setImageBitmap(bm);
                }
            }else {
                bookImage.setImageResource(book.getImageView());
            }
            bookName.setText( book.getTitle());
            authorName.setText(book.getAuthor());
            spinnerBook.setSelection(book.getStatus());
            if (book.getStatus() == 2){
                stars.setIsIndicator(true);
                stars.setRating(book.getStar());
                stars.setVisibility(View.VISIBLE);
            }else {
                stars.setIsIndicator(true);
                book.setStar(0);
                stars.setVisibility(View.INVISIBLE);
            }
        }
    }
    public void notifyData(){
        notifyDataSetChanged();
    }
}


