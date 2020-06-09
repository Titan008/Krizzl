package com.example.scribbl;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class PopUpActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private String id;
    private boolean canDraw=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);


        id = getIntent().getStringExtra("SignIn_ID");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*0.4));

        db = FirebaseFirestore.getInstance();

        getCanDraw();

    }

    //disable back button
    @Override
    public void onBackPressed() {
        return;
    }


    private boolean getCanDraw() {

        final DocumentReference docRef = db.collection("players").document(id);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("TAG", "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d("canDraw", "Current data: " + snapshot.getBoolean("canDraw"));
                    canDraw = snapshot.getBoolean("canDraw");
                    if(canDraw){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("SignIn_ID", id);
                        startActivity(intent);
                    }
                } else {
                    Log.d("TAG", "Current data: null");
                }
            }

        });

        return canDraw;
    }
}
