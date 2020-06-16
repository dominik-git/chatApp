package com.example.myapplication.data;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ChatListAdapter extends BaseAdapter {

    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private String mDisplayName;
    private ArrayList<DataSnapshot> mSnapshotList;


    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            mSnapshotList.add(dataSnapshot);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


    public ChatListAdapter(Activity activity, DatabaseReference ref, String name) {
        this.mActivity = activity;
        this.mDatabaseReference = ref.child("messages");
        this.mDatabaseReference.addChildEventListener(mListener);
        this.mDisplayName = name;
        this.mSnapshotList = new ArrayList<>();
    }

    static class ViewHolder {
        TextView authorName;
        TextView body;
        ImageView image;
        LinearLayout.LayoutParams params;
    }

    @Override
    public int getCount() {

        return mSnapshotList.size();
    }

    @Override
    public InstantMessage getItem(int i) {
        DataSnapshot snapshot = mSnapshotList.get(i);

        return snapshot.getValue(InstantMessage.class);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_msg_row, viewGroup, false);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.authorName = (TextView) view.findViewById(R.id.author);
            viewHolder.body = (TextView) view.findViewById(R.id.message);
            viewHolder.params = (LinearLayout.LayoutParams) viewHolder.authorName.getLayoutParams();
            view.setTag(viewHolder);
        }
        final InstantMessage message = getItem(i);
        final ViewHolder viewHolder = (ViewHolder) view.getTag();
        String author = message.getAuthor();
        viewHolder.authorName.setText(author);
        String msg = message.getMessage();
        viewHolder.body.setText(msg);

        return view;
    }

    public void cleanup() {
        mDatabaseReference.removeEventListener(mListener);
    }
}
