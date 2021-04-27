package com.example.readbooks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readbooks.Database.AppDatabase;
import com.example.readbooks.Database.Book;
import com.example.readbooks.Database.BookDao;
import com.example.readbooks.Database.BookRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import static com.example.readbooks.BookViewModel.books;
import static com.example.readbooks.MainActivity.bookRepository;

public class BookListFragment extends Fragment {
    RecyclerView recyclerView;
    BookViewModel bookViewModel;
    public static Bundle bundle = new Bundle();
    public  static  BookAdapter adapter;
    FloatingActionButton fab;
    Book deletedBook = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        bookViewModel =  new ViewModelProvider(this).get(BookViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.book_list_fragment, container, false);

        fab = v.findViewById(R.id.addNewList);
        recyclerView = v.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BookAdapter(books);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putSerializable("book",books.get(recyclerView.getChildAdapterPosition(v)));
                getParentFragmentManager().setFragmentResult("key",bundle);
                Navigation.findNavController(getActivity(),R.id.fragment).navigate(R.id.bookFragment);
            }
        });
        recyclerView.setAdapter(adapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(),R.id.fragment).navigate(R.id.newBookFragment);
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                final int position = viewHolder.getAdapterPosition();

                    switch (direction){
                        case ItemTouchHelper.LEFT:
                            deletedBook = books.get(position);
                            books.remove(position);
                            adapter.notifyItemRemoved(position);
                            Snackbar.make(getActivity().findViewById(R.id.cordinator), deletedBook.getTitle() , Snackbar.LENGTH_LONG)
                                    .setAction("Undo", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            books.add(position,deletedBook);
                                            adapter.notifyItemInserted(position);
                                        }
                                    }).show();
                        case ItemTouchHelper.RIGHT:
                            break;
                    }
            }
        }).attachToRecyclerView(recyclerView);

        return v;
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
