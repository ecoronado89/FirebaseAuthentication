package com.example.ecoronado.firebaseauthentication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by crrf on 12/19/2016.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder>{

    private List<Message> messages;
    private Context context;

    public MessageAdapter(Context pContext, List<Message> messages){
        this.messages = messages;
        this.context = pContext;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public int getItemCount() { return messages.size(); }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_row_layout, parent, false);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageHolder infoHolder, int position) {
        Message message = messages.get(position);
    }

    public static class MessageHolder extends RecyclerView.ViewHolder {
        public TextView txt_message;

        public MessageHolder(View infoView) {
            super(infoView);
            txt_message = (TextView) itemView.findViewById(R.id.txt_message);
            txt_message.setGravity(Gravity.LEFT);

        }
    }
}
