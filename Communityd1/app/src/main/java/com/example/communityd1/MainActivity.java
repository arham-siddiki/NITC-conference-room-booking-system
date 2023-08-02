package com.example.communityd1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setTitle("USER PANEL");

        getSupportActionBar().hide();
        TextView title=findViewById(R.id.action_title);
        TextView back=findViewById(R.id.back_button);
        title.setTextColor(Color.parseColor("#FFFFFFFF"));
        title.setText("U S E R   P A N E L ");
        back.setVisibility(View.GONE);

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        CardView viewRooms=findViewById(R.id.view_room_card);
        CardView myBookings=findViewById(R.id.view_booking_card);
        CardView signout=findViewById(R.id.signout_card);
        CardView editProfile=findViewById(R.id.view_profile_card);

//        setContentView(R.layout.activity_main);
//        Button viewRooms=findViewById(R.id.view_rooms);
//        Button myBookings=findViewById(R.id.view_my_bookings);
//        Button editProfile=findViewById(R.id.editProfile);
//        Button signout=findViewById(R.id.signout);

        viewRooms.setOnClickListener(view -> {
            Intent i=new Intent(MainActivity.this,ViewRoom.class);
            i.putExtra("userType","User");
            startActivity(i);
        });

        myBookings.setOnClickListener(view -> {
            Intent i=new Intent(MainActivity.this,View_user_booking.class);
            startActivity(i);
        });

        editProfile.setOnClickListener(view -> {
            Intent i=new Intent(MainActivity.this, Profile.class);
            i.putExtra("userType","User");
            startActivity(i);
        });

        signout.setOnClickListener(view -> {
            firebaseAuth.signOut();
            Intent i=new Intent(MainActivity.this,Login.class);
            startActivity(i);
            finish();
        });

    }
}