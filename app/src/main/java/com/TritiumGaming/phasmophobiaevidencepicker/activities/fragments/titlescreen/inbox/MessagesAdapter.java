package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.inbox;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private ArrayList<InboxMessage> messages;
    private OnMessageListener onMessageListener;

    public MessagesAdapter(ArrayList<InboxMessage> messages, OnMessageListener onMessageListener) {
        this.messages = messages;
        this.onMessageListener = onMessageListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public AppCompatTextView label_messageTitle;
        private OnMessageListener onMessageListener;

        public ViewHolder(View view, OnMessageListener onMessageListener) {
            super(view);
            label_messageTitle = itemView.findViewById(R.id.textView_messageListName);
            view.setOnClickListener(this);
            this.onMessageListener = onMessageListener;
        }

        @Override
        public void onClick(View v) {
            this.onMessageListener.onNoteClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public MessagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View messageView = inflater.inflate(R.layout.item_msginbox_message, parent, false);
        return new ViewHolder(messageView, this.onMessageListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView textView = holder.label_messageTitle;
        textView.setText(messages.get(position).getTitle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textView.setAutoSizeTextTypeUniformWithConfiguration(12, 24, 1, AppCompatTextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public interface OnMessageListener {
        void onNoteClick(int position);
    }

}