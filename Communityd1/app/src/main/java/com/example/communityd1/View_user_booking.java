package com.example.communityd1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.widget.TextView;
import android.widget.Toast;

import com.example.communityd1.DataClasses.BookingRequest;
import com.example.communityd1.DataClasses.BookingRequestUserAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class View_user_booking extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    private BookingRequestUserAdapter adapter;
    ArrayList<Pair<BookingRequest, String>> names=new ArrayList<>();
    final String[] currUser = {""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_booking);
//        setTitle("MY BOOKINGS");

        getSupportActionBar().hide();
        TextView title=findViewById(R.id.action_title);
        TextView back=findViewById(R.id.back_button);
        title.setTextColor(Color.parseColor("#FFFFFFFF"));
        title.setText("M Y   B O O K I N G S ");
        back.setOnClickListener(view -> {
            onBackPressed();
        });

        firebaseAuth=FirebaseAuth.getInstance();
        currUser[0]=firebaseAuth.getCurrentUser().getEmail();
        firebaseFirestore=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("loading...");

        recyclerView=findViewById(R.id.recycler_user_booking);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new BookingRequestUserAdapter(this, names);

        progressDialog.show();
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(View_user_booking.this,
                LinearLayoutManager.VERTICAL));
        createListData();
        progressDialog.cancel();

    }

    void createListData() {
        firebaseFirestore.collection("Booking rejected")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for(DocumentChange dc: queryDocumentSnapshots.getDocumentChanges()) {
                            if(dc.getType()==DocumentChange.Type.ADDED) {
                                Map<String, String> mp=(HashMap)dc.getDocument().getData();
                                BookingRequest r=new BookingRequest(mp.get("date"),mp.get("time"), mp.get("purpose"), mp.get("client"), mp.get("roomId"), mp.get("bookingId"),mp.get("faculty"),mp.get("reason"));
//                                BookingRequest r=(BookingRequest) dc.getDocument().toObject(BookingRequest.class);
                                if(r.getClient().equals(currUser[0]))
                                    names.add(new Pair<>(r,"rejected"));
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
        firebaseFirestore.collection("Booking confirmed")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for(DocumentChange dc: queryDocumentSnapshots.getDocumentChanges()) {
                            if(dc.getType()==DocumentChange.Type.ADDED) {
                                Map<String, String> mp=(HashMap)dc.getDocument().getData();
                                BookingRequest r=new BookingRequest(mp.get("date"),mp.get("time"), mp.get("purpose"), mp.get("client"), mp.get("roomId"), mp.get("bookingId"),mp.get("faculty"),mp.get("reason"));
                                if(r.getClient().equals(currUser[0]))
                                    names.add(new Pair<>(r,"accepted"));
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
        firebaseFirestore.collection("Booking requests")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for(DocumentChange dc: queryDocumentSnapshots.getDocumentChanges()) {
                            if(dc.getType()==DocumentChange.Type.ADDED) {
                                Map<String, String> mp=(HashMap)dc.getDocument().getData();
                                BookingRequest r=new BookingRequest(mp.get("date"),mp.get("time"), mp.get("purpose"), mp.get("client"), mp.get("roomId"), mp.get("bookingId"),mp.get("faculty"),mp.get("reason"));
                                if(r.getClient().equals(currUser[0]))
                                    names.add(new Pair<>(r,"pending"));
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });



    }
}