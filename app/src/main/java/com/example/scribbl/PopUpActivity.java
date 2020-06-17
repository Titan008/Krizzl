package com.example.scribbl;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Map;

public class PopUpActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private String id;
    private boolean canDraw = false;
    private boolean close = false;

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

        getWindow().setLayout((int) (width * .8), (int) (height * 0.4));

        db = FirebaseFirestore.getInstance();

        closePopUp();
    }


    @Override
    public void onBackPressed() {
        return;
    }


    private void closePopUp() {
        final String[] data = new String[1];
        final DocumentReference docRef = db.collection("time").document("timeID");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                if (snapshot != null && snapshot.exists() && !close) {
                    Map timeData = snapshot.getData();
                    data[0] = timeData.get("time").toString();
                    if (!(data[0].equals("TIME IS OVER!"))) {
                        close = true;
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("SignIn_ID", id);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }
}
