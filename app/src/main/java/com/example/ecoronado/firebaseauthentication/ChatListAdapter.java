package com.example.ecoronado.firebaseauthentication;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by llbean on 12/19/16.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatViewHolder> {
    ArrayList<Message> messages;

    Context mContext;
    String mSender;

    /*ChatListAdapter(Context context, ArrayList messages){
        this.messages = messages;
        this.mContext = context;
    }
*/
    public ChatListAdapter(Context context, ArrayList<Message> mMessages, String sender) {
        this.messages = mMessages;
        this.mContext = context;
        this.mSender = sender;
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
        if(messages.get(position).getSender().equalsIgnoreCase(mSender))
        {

            RelativeLayout.LayoutParams params =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            holder.personAge.setTextColor(Color.RED);
            holder.personAge.setTypeface(null, Typeface.BOLD);
            holder.personAge.setLayoutParams(params);
        }
        else{
            holder.personAge.setTextColor(Color.GRAY);
            holder.personAge.setTypeface(null, Typeface.BOLD_ITALIC);
        }
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
        TextView personAge;

        ChatViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personAge = (TextView)itemView.findViewById(R.id.person_age);

        }
    }

}
