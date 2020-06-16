package com.dkolesar.chatapp.ui.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.dkolesar.chatapp.data.ChatListAdapter;
import com.dkolesar.chatapp.data.InstantMessage;
import com.dkolesar.chatapp.R;
import com.dkolesar.chatapp.ui.register.RegisterActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainChatActivity extends AppCompatActivity {

    private String mDisplayName;
    private ListView mChatListView;
    private EditText mInputText;

    private DatabaseReference mDatabaseReference;
    private ChatListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);

        setupDisplayedName();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mInputText = findViewById(R.id.messageInput);
        ImageButton mSendButton = findViewById(R.id.sendButton);
        mChatListView = findViewById(R.id.chat_list_view);

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
        SharedPreferences prefs = getSharedPreferences(RegisterActivity.CHAT_PREFS, MODE_PRIVATE);
        mDisplayName = prefs.getString(RegisterActivity.DISPLAY_NAME_KEY, null);
        if (mDisplayName == null) {
            mDisplayName = "";
        }

    }

    private void sendMessage() {
        System.out.println("send message");
        String input = mInputText.getText().toString();
        if (!input.equals("")) {
            InstantMessage message = new InstantMessage(input, mDisplayName);
            mDatabaseReference.child("messages").push().setValue(message);
            mInputText.setText("");
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter = new ChatListAdapter(this, mDatabaseReference, mDisplayName);
        mChatListView.setAdapter(mAdapter);

    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.cleanup();
    }
}