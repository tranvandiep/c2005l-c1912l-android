package com.gokisoft.example.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.gokisoft.example.R;
import com.gokisoft.example.models.NewNote;

public class NewNoteAdapter extends CursorAdapter {
    Activity mActivity;

    public NewNoteAdapter(Activity activity, Cursor c) {
        super(activity, c);
        mActivity = activity;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = mActivity.getLayoutInflater().inflate(R.layout.list_item, null);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        View colorView = view.findViewById(R.id.li_color);
        TextView titleView = view.findViewById(R.id.li_title);
        TextView datetimeView = view.findViewById(R.id.li_datetime);

        NewNote note = new NewNote();
        note.setData(cursor);

        if(note.isQuantrong()) {
            colorView.setBackgroundColor(Color.RED);
        } else {
            colorView.setBackgroundColor(Color.GREEN);
        }

        titleView.setText(note.getNoidung());
        datetimeView.setText(note.getNgaytaoStr());
    }
}
