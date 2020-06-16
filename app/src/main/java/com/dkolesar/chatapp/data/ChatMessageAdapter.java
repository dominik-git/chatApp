package com.dkolesar.chatapp.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.dkolesar.chatapp.R;

public class ChatMessageAdapter extends ListAdapter<InstantMessage, ChatMessageAdapter.ChatMessageItemViewHolder> {

    public ChatMessageAdapter() {
        super(DIFF_CALLBACK);
    }

    @Override
    @NonNull
    public ChatMessageItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_chat_message, parent, false);
        return new ChatMessageItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMessageItemViewHolder holder, int position) {
        InstantMessage message = getItem(position);
        if (message != null) {
            holder.bindTo(message);
        }
    }

    static class ChatMessageItemViewHolder extends RecyclerView.ViewHolder {
        TextView authorName, message;

        public ChatMessageItemViewHolder(View itemView) {
            super(itemView);
            authorName = itemView.findViewById(R.id.author);
            message = itemView.findViewById(R.id.message);
        }

        public void bindTo(InstantMessage message) {
            authorName.setText(message.getAuthor());
            this.message.setText(message.getMessage());

        }
    }

    public static final DiffUtil.ItemCallback<InstantMessage> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<InstantMessage>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull InstantMessage oldUser, @NonNull InstantMessage newUser) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    // FIXME: return message.getId() == message.getId();
                    return false;
                }

                @Override
                public boolean areContentsTheSame(
                        @NonNull InstantMessage oldUser, @NonNull InstantMessage newUser) {
                    return oldUser.equals(newUser);
                }
            };
}
