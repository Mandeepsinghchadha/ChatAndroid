package com.mandeep.chatandroid;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 123;
    private Button send;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private EditText messageText;
    private ChildEventListener mChildEventListener;
    private ListView messageListView;
    private MessageAdapter messageAdapter;
    private ArrayList<ChatMessage> messageArrayList;
    @Override
    protected void onStart() {
        FirebaseApp.initializeApp(MainActivity.this);
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=FirebaseDatabase.getInstance().getReference().child("message");
        messageListView=findViewById(R.id.messageList);
        messageArrayList=new ArrayList<>();
        messageAdapter=new MessageAdapter(MainActivity.this,0,messageArrayList);
        messageListView.setAdapter(messageAdapter);
        messageText=findViewById(R.id.messagetext);
        send=findViewById(R.id.send);

        mAuth=FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser()!=null){
            Toast.makeText(MainActivity.this,"User Signed in",Toast.LENGTH_LONG).show();


        }else {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.EmailBuilder().build(),
                                    new AuthUI.IdpConfig.GoogleBuilder().build()
                            ))
                            .build(),
                    RC_SIGN_IN);

        }


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatMessage message=new ChatMessage(mAuth.getCurrentUser().getDisplayName(),messageText.getText().toString().trim(),null);
                mDatabaseReference.push().setValue(message);
                messageText.setText("");
            }
        });

        mChildEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatMessage newMessage=dataSnapshot.getValue(ChatMessage.class);
                messageAdapter.add(newMessage);

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
        mDatabaseReference.addChildEventListener(mChildEventListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sign_out, menu);
        return true;

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sign_out:
                AuthUI.getInstance()
                        .signOut(MainActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // user is now signed out
                                Toast.makeText(MainActivity.this,"User Signed out",Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
