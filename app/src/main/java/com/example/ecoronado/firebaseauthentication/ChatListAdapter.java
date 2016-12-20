package com.example.ecoronado.firebaseauthentication;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by llbean on 12/19/16.
 */

public class ChatListAdapter  extends RecyclerView.Adapter<ChatListAdapter.ChatViewHolder> {
    ArrayList<Message> messages;

    Context mContext;

    /*ChatListAdapter(Context context, ArrayList messages){
        this.messages = messages;
        this.mContext = context;
    }
*/
    public ChatListAdapter(Context context, ArrayList<Message> mMessages) {
        this.messages = mMessages;
        this.mContext = context;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        ChatViewHolder cvh = new ChatViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        holder.personAge.setText(messages.get(position).getText());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        // TODO
        TextView personAge;


        ChatViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personAge = (TextView)itemView.findViewById(R.id.person_age);

        }
    }

}
