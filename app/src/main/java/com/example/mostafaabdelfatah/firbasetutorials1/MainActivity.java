package com.example.mostafaabdelfatah.firbasetutorials1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int userNum=0;
    ListView usersList;
    ListAdapter  listAdapter;
    ArrayList<User> users = new ArrayList<>();
    private EditText nametext,ageText,addressText;

    private static String device_token;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nametext    = (EditText) findViewById(R.id.nameText);
        ageText     = (EditText) findViewById(R.id.ageText);
        addressText = (EditText) findViewById(R.id.addressText);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("notifications");
        device_token = FirebaseInstanceId.getInstance().getToken();


        usersList = (ListView) findViewById(R.id.usersList);
        // default adapter list
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , users);
        // Custom Adapter
        listAdapter = new ListAdapter(this,users);
        usersList.setAdapter(listAdapter);

        /**
         * Read Data
         * */
        /*
        DatabaseReference dr = databaseReference.getRoot();
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    String name    = data.child("name").getValue().toString();
                    String age     = data.child("age").getValue().toString();
                    String address = data.child("address").getValue().toString();
                    users.add(new User(name, age, address));
                    //ser user = data.getValue(User.class);
                    //users.add(user);
                    listAdapter.notifyDataSetChanged();
                }
                Toast.makeText(getApplicationContext(),"Successful Read",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        /**
         * Read Data
         * */

        DatabaseReference dr = databaseReference.getRoot().child("notifications");
        dr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                try{

                    String id      = dataSnapshot.getKey();
                    String name    = dataSnapshot.child("name").getValue().toString();
                    String age     = dataSnapshot.child("age").getValue().toString();
                    String address = dataSnapshot.child("address").getValue().toString();
                    users.add(new User(id,name, age, address));
                    listAdapter.notifyDataSetChanged();

                }catch (Exception e){}

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                String id      = dataSnapshot.getKey();
                String name    = dataSnapshot.child("name").getValue().toString();
                String age     = dataSnapshot.child("age").getValue().toString();
                String address = dataSnapshot.child("address").getValue().toString();
                User user = new User(id,name, age, address);
                if(!user.containInArrayList(users)){
                    users.add(user);
                }
                listAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),"Successful Read",Toast.LENGTH_SHORT).show();

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
        });
    }

    public void saveDataToFirebase(View view) {

        // to save one variable
        // 1 - Create Child in root Object
        // 2 - assign some value to the child Object
        //databaseReference.child("name").setValue(nametext.getText().toString());
        //databaseReference.push().setValue(nametext.getText().toString());
    }

    public void saveAllDataToFirebase(View view) {
        /*userNum++;
        databaseReference.child("User_00"+ userNum).child("name").setValue(nametext.getText().toString());
        databaseReference.child("User_00"+ userNum).child("age").setValue(ageText.getText().toString());
        databaseReference.child("User_00"+ userNum).child("address").setValue(addressText.getText().toString());*/
        HashMap<String,String>  hashMap = new HashMap<String,String>();

        hashMap.put("device_token",device_token);

        hashMap.put("name",nametext.getText().toString().trim());
        hashMap.put("age",ageText.getText().toString().trim());
        hashMap.put("address",addressText.getText().toString().trim());
        //databaseReference.push().setValue(new User("Mostafa Abd ElFatah","23","Hagaza - Ques - Qena"));
        databaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    Toast.makeText(getApplicationContext(),"Successful Insert",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"Not Successful Insert",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void readDataFromFirebase(View view) {
        /// https://firbasetutorials1.firebaseio.com/


    }

    public void signOutbtn(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent page = new Intent(MainActivity.this,SignActivity.class);
        startActivity(page);
    }


    public  static void sendRegistrationToServer(String refreshedToken){
        device_token = refreshedToken;
        Log.d("device Token::", "sendRegistrationToServer: "+ refreshedToken);
    }
}
