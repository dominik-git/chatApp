package com.dkolesar.chatapp.ui.chat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dkolesar.chatapp.R;
import com.dkolesar.chatapp.data.ChatMessageAdapter;
import com.dkolesar.chatapp.data.InstantMessage;
import com.dkolesar.chatapp.ui.register.RegisterFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.dkolesar.chatapp.utils.Constants.APPLICATION_TAG;


public class ChatActivity extends AppCompatActivity {

    private String mDisplayName;
    private RecyclerView mChatMessages;
    private EditText mInputText;
    private DatabaseReference mDatabaseReference;
    private ChatMessageAdapter mAdapter;
    private ArrayList<DataSnapshot> mSnapshotList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);

        setupDisplayedName();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("messages");
        mInputText = findViewById(R.id.messageInput);
        ImageButton mSendButton = findViewById(R.id.sendButton);
        mChatMessages = findViewById(R.id.chat_messages);
        mChatMessages.setLayoutManager(new LinearLayoutManager(this));
        this.mSnapshotList = new ArrayList<>();

        mInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                sendMessage();
                return true;
            }
        });
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

    }

    private void setupDisplayedName() {
        SharedPreferences prefs = getSharedPreferences(RegisterFragment.CHAT_PREFS, MODE_PRIVATE);
        mDisplayName = prefs.getString(RegisterFragment.DISPLAY_NAME_KEY, null);
        if (mDisplayName == null) {
            mDisplayName = "";
        }
        System.out.println("hello "+mDisplayName);

    }

    private void sendMessage() {
        Log.d(APPLICATION_TAG, "send message");
        String input = mInputText.getText().toString();
        if (!input.equals("")) {
            InstantMessage message = new InstantMessage(input, mDisplayName);
            mDatabaseReference.push().setValue(message);
            mInputText.setText("");
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter = new ChatMessageAdapter();
        mChatMessages.setAdapter(mAdapter);
        this.mDatabaseReference.addChildEventListener(mListener);

    }

    @Override
    public void onStop() {
        super.onStop();
        mDatabaseReference.removeEventListener(mListener);
    }

    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            mSnapshotList.add(dataSnapshot);
            List<InstantMessage> messages = new ArrayList<>();

            for (int i = 0; i < mSnapshotList.size() ; i++) {
                DataSnapshot snapshot = mSnapshotList.get(i);
                messages.add(snapshot.getValue(InstantMessage.class));
            }
//            for (DataSnapshot item : dataSnapshot.getChildren()) {
//            InstantMessage message = item.getValue(InstantMessage.class);
//               messages.add(message);
//           }

          mAdapter.submitList(messages);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}