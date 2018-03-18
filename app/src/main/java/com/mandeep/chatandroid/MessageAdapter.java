package com.mandeep.chatandroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nimit Arora on 3/14/2018.
 */

public class
MessageAdapter extends ArrayAdapter<ChatMessage> {
    Context mContext;
    ArrayList<ChatMessage> messageArrayList=new ArrayList<>();
    public MessageAdapter(@NonNull Context context, int resource, @NonNull List<ChatMessage> objects) {

        super(context, 0, objects);
        mContext=context;
        messageArrayList= (ArrayList<ChatMessage>) (ArrayList<ChatMessage>) objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem=convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.message_item,parent,false);

        ChatMessage currentMessage=messageArrayList.get(position);

        TextView name=(TextView) listItem.findViewById(R.id.nameField);
        name.setText(currentMessage.getName());

        TextView message=(TextView) listItem.findViewById(R.id.messageField);
        message.setText(currentMessage.getMessage());

        return listItem;
    }
}
