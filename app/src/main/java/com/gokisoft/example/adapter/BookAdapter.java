package com.gokisoft.example.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gokisoft.example.R;
import com.gokisoft.example.models.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {
    Activity mActivity;
    List<Book> dataList;

    public BookAdapter(Activity activity, List<Book> dataList) {
        this.mActivity = activity;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mActivity.getLayoutInflater().inflate(R.layout.item_book, null);

        BookHolder holder = new BookHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {
        Book book = dataList.get(position);

        holder.booknameView.setText(book.getBookName());
        holder.authronameView.setText(book.getAuthorName());
        holder.nxbView.setText(book.getNxb());
        holder.priceView.setText(book.getPrice() + "");
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class BookHolder extends RecyclerView.ViewHolder {
        TextView booknameView, authronameView, nxbView, priceView;

        public BookHolder(@NonNull View itemView) {
            super(itemView);

            booknameView = itemView.findViewById(R.id.ib_bookname);
            authronameView = itemView.findViewById(R.id.ib_authorname);
            nxbView = itemView.findViewById(R.id.ib_nxb);
            priceView = itemView.findViewById(R.id.ib_price);
        }
    }
}
