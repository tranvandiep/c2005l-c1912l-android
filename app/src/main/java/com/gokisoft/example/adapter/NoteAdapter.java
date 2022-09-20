package com.gokisoft.example.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gokisoft.example.R;
import com.gokisoft.example.models.Note;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder>{
    Activity mActivity;
    List<Note> dataList;

    public NoteAdapter(Activity activity, List<Note> dataList) {
        this.mActivity = activity;
        this.dataList = dataList;
    }

    public List<Note> getDataList() {
        return dataList;
    }

    public void setDataList(List<Note> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mActivity.getLayoutInflater().inflate(R.layout.item_note, null);

        NoteHolder holder = new NoteHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note note = dataList.get(position);

        Log.d(NoteAdapter.class.getName(), note.getThumbnail());
        Picasso.with(mActivity).load(note.getThumbnail()).into(holder.thumbnailView);

        holder.titleView.setText(note.getTitle());;
        holder.updatedView.setText(note.getUpdatedAt());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class NoteHolder extends RecyclerView.ViewHolder {
        ImageView thumbnailView;
        TextView titleView, updatedView;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            thumbnailView = itemView.findViewById(R.id.in_thumbnail);
            titleView = itemView.findViewById(R.id.in_title);
            updatedView = itemView.findViewById(R.id.in_update);
        }
    }
}
