package com.example.ecoronado.firebaseauthentication;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecoronado.firebaseauthentication.usuarios.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by llbean on 12/4/16.
 */

public class Chat extends AppCompatActivity implements View.OnClickListener,
        MessageDataSource.MessagesCallbacks{

    private ArrayList<Message> mMessages;
    private Utility mUtility = new Utility();
    private String mConvoId;
    private MessageDataSource.MessagesListener mListener;
    private String mSender = "Eder"; // Replace these values to the correct ones
    private String mRecipient = "Didier";
    ChatListAdapter adapter;
    private Bundle bundle;
    private InfoChat infoChat;

    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list_activity);

        mMessages = new ArrayList<>();

        Button sendMessage = (Button)findViewById(R.id.send_message);
        sendMessage.setOnClickListener(this);

        bundle = getIntent().getExtras();
        infoChat = bundle.getParcelable(com.example.ecoronado.firebaseauthentication.usuarios.Constants.INFO_CHAT);

        mRecipient = infoChat.getReceiver();
        mSender = infoChat.getSender();

        // order the values alphabetically
        if(mSender.compareTo(mRecipient) > 0){
            String exchangeValue = mSender;
            mSender= mRecipient;
            mRecipient = exchangeValue;
        }

        // define this order base on alphabetic to keep the consistensy across the devices
      //  String[] ids = {mRecipient,"-", mSender};
        String[] ids = {mSender,"-", mRecipient};
        Arrays.sort(ids);
        mConvoId = ids[0]+ids[1]+ids[2];

        mListener = MessageDataSource.addMessagesListener(mConvoId, this);

        rv =(RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);


        adapter = new ChatListAdapter(this,mMessages, mSender);
        rv.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }




    public void onClick(View v) {
        EditText newMessageView = (EditText)findViewById(R.id.new_message);
        String newMessage = newMessageView.getText().toString();
        newMessageView.setText("");
        Message msg = new Message();
        msg.setDate(new Date());
        msg.setText(newMessage);
        msg.setSender(mSender);
        if(mUtility.isNetworkAvailable(this))
            MessageDataSource.saveMessage(msg, mConvoId);
        else
        {
            CharSequence text = "The message will be sent when the internet connection is back";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
            MessageDataSource.saveMessage(msg, mConvoId);
        }
    }

    @Override
    public void onMessageAdded(Message message) {
        mMessages.add(message);
        //rv.notifyDataSetChanged();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MessageDataSource.stop(mListener);
    }

}
