package com.example.krizzl;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.krizzl.Paint.PaintView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private PaintView paintView;
    private FirebaseFirestore db;
    private String id;
    private boolean canDraw = true;
    private boolean blackSelected = true;
    private boolean pinkSelected = true;
    private boolean greenSelected = true;
    private boolean yellowSelected = true;
    private boolean redSelected = true;
    private boolean orangeSelected = true;
    private boolean blueSelected = true;
    private TextView timer;
    private TextView player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));
        actionBar.setTitle(Html.fromHtml("<font color=\"#8ED4E3\">" + "Let´s draw" + "</font>"));

        setContentView(R.layout.activity_main);

        id = getIntent().getStringExtra("SignIn_ID");
        timer = findViewById(R.id.textTime);
        player = findViewById(R.id.textPlayer);

        db = FirebaseFirestore.getInstance();

        getCanDraw();
        getPlayer();

        if (canDraw) {
            getTimer();
        }

        paintView = findViewById(R.id.paintView);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);

        initBtns();
    }


    @Override
    public void onBackPressed() {
        return;
    }

    private void initBtns() {

        final Button btn_Black = findViewById(R.id.btnBlack);
        final Button btn_Pink = findViewById(R.id.btnPink);
        final Button btn_Red = findViewById(R.id.btnRed);
        final Button btn_Green = findViewById(R.id.btnGreen);
        final Button btn_Blue = findViewById(R.id.btnBlue);
        final Button btn_Yellow = findViewById(R.id.btnYellow);
        final Button btn_Orange = findViewById(R.id.btnOrange);


        btn_Black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (blackSelected) {
                    paintView.blackColor();
                    btn_Pink.setBackgroundResource(R.drawable.btn_pink_borderless);
                    btn_Black.setBackgroundResource(R.drawable.btn_black_border);
                    btn_Blue.setBackgroundResource(R.drawable.btn_blue_borderless);
                    btn_Green.setBackgroundResource(R.drawable.btn_green_borderless);
                    btn_Red.setBackgroundResource(R.drawable.btn_red_borderless);
                    btn_Yellow.setBackgroundResource(R.drawable.btn_yellow_borderless);
                    btn_Orange.setBackgroundResource(R.drawable.btn_orange_borderless);
                    orangeSelected = true;
                    blackSelected = false;
                    blueSelected = true;
                    greenSelected = true;
                    redSelected = true;
                    yellowSelected = true;
                    pinkSelected = true;
                } else btn_Black.setBackgroundResource(R.drawable.btn_pink_borderless);
                blackSelected = true;
            }
        });


        btn_Red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (redSelected) {
                    paintView.redColor();
                    btn_Pink.setBackgroundResource(R.drawable.btn_pink_borderless);
                    btn_Black.setBackgroundResource(R.drawable.btn_black_borderless);
                    btn_Blue.setBackgroundResource(R.drawable.btn_blue_borderless);
                    btn_Green.setBackgroundResource(R.drawable.btn_green_borderless);
                    btn_Red.setBackgroundResource(R.drawable.btn_red_border);
                    btn_Yellow.setBackgroundResource(R.drawable.btn_yellow_borderless);
                    btn_Orange.setBackgroundResource(R.drawable.btn_orange_borderless);
                    orangeSelected = true;
                    blackSelected = true;
                    blueSelected = true;
                    greenSelected = true;
                    redSelected = false;
                    yellowSelected = true;
                    pinkSelected = true;
                } else btn_Red.setBackgroundResource(R.drawable.btn_red_borderless);
                redSelected = true;
            }
        });


        btn_Green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (greenSelected) {
                    paintView.greenColor();
                    btn_Pink.setBackgroundResource(R.drawable.btn_pink_borderless);
                    btn_Black.setBackgroundResource(R.drawable.btn_black_borderless);
                    btn_Blue.setBackgroundResource(R.drawable.btn_blue_borderless);
                    btn_Green.setBackgroundResource(R.drawable.btn_green_border);
                    btn_Red.setBackgroundResource(R.drawable.btn_red_borderless);
                    btn_Yellow.setBackgroundResource(R.drawable.btn_yellow_borderless);
                    btn_Orange.setBackgroundResource(R.drawable.btn_orange_borderless);
                    orangeSelected = true;
                    blackSelected = true;
                    blueSelected = true;
                    greenSelected = false;
                    redSelected = true;
                    yellowSelected = true;
                    pinkSelected = true;
                } else btn_Green.setBackgroundResource(R.drawable.btn_green_borderless);
                greenSelected = true;
            }
        });


        btn_Blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (blueSelected) {
                    paintView.blueColor();
                    btn_Pink.setBackgroundResource(R.drawable.btn_pink_borderless);
                    btn_Black.setBackgroundResource(R.drawable.btn_black_borderless);
                    btn_Blue.setBackgroundResource(R.drawable.btn_blue_border);
                    btn_Green.setBackgroundResource(R.drawable.btn_green_borderless);
                    btn_Red.setBackgroundResource(R.drawable.btn_red_borderless);
                    btn_Yellow.setBackgroundResource(R.drawable.btn_yellow_borderless);
                    btn_Orange.setBackgroundResource(R.drawable.btn_orange_borderless);
                    orangeSelected = true;
                    blackSelected = true;
                    blueSelected = false;
                    greenSelected = true;
                    redSelected = true;
                    yellowSelected = true;
                    pinkSelected = true;
                } else btn_Blue.setBackgroundResource(R.drawable.btn_blue_borderless);
                blueSelected = true;
            }
        });


        btn_Yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yellowSelected) {
                    paintView.yellowColor();
                    btn_Pink.setBackgroundResource(R.drawable.btn_pink_borderless);
                    btn_Black.setBackgroundResource(R.drawable.btn_black_borderless);
                    btn_Blue.setBackgroundResource(R.drawable.btn_blue_borderless);
                    btn_Green.setBackgroundResource(R.drawable.btn_green_borderless);
                    btn_Red.setBackgroundResource(R.drawable.btn_red_borderless);
                    btn_Yellow.setBackgroundResource(R.drawable.btn_yellow_border);
                    btn_Orange.setBackgroundResource(R.drawable.btn_orange_borderless);
                    orangeSelected = true;
                    blackSelected = true;
                    blueSelected = true;
                    greenSelected = true;
                    redSelected = true;
                    yellowSelected = false;
                    pinkSelected = true;
                } else btn_Yellow.setBackgroundResource(R.drawable.btn_yellow_borderless);
                yellowSelected = true;
            }
        });


        btn_Orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orangeSelected) {
                    paintView.orangeColor();
                    btn_Pink.setBackgroundResource(R.drawable.btn_pink_borderless);
                    btn_Black.setBackgroundResource(R.drawable.btn_black_borderless);
                    btn_Blue.setBackgroundResource(R.drawable.btn_blue_borderless);
                    btn_Green.setBackgroundResource(R.drawable.btn_green_borderless);
                    btn_Red.setBackgroundResource(R.drawable.btn_red_borderless);
                    btn_Yellow.setBackgroundResource(R.drawable.btn_yellow_borderless);
                    btn_Orange.setBackgroundResource(R.drawable.btn_orange_border);
                    orangeSelected = false;
                    blackSelected = true;
                    blueSelected = true;
                    greenSelected = true;
                    redSelected = true;
                    yellowSelected = true;
                    pinkSelected = true;
                } else btn_Orange.setBackgroundResource(R.drawable.btn_orange_borderless);
                orangeSelected = true;
            }
        });


        btn_Pink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pinkSelected) {
                    paintView.pinkColor();
                    btn_Pink.setBackgroundResource(R.drawable.btn_pink_border);
                    btn_Black.setBackgroundResource(R.drawable.btn_black_borderless);
                    btn_Blue.setBackgroundResource(R.drawable.btn_blue_borderless);
                    btn_Green.setBackgroundResource(R.drawable.btn_green_borderless);
                    btn_Red.setBackgroundResource(R.drawable.btn_red_borderless);
                    btn_Yellow.setBackgroundResource(R.drawable.btn_yellow_borderless);
                    btn_Orange.setBackgroundResource(R.drawable.btn_orange_borderless);
                    pinkSelected = false;
                    blackSelected = true;
                    blueSelected = true;
                    greenSelected = true;
                    redSelected = true;
                    yellowSelected = true;
                    orangeSelected = true;
                } else btn_Pink.setBackgroundResource(R.drawable.btn_pink_borderless);
                pinkSelected = true;
            }
        });


        final Button btn_Rubber = findViewById(R.id.btnRubber);
        btn_Rubber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paintView.rubber();
                btn_Pink.setBackgroundResource(R.drawable.btn_pink_borderless);
                btn_Black.setBackgroundResource(R.drawable.btn_black_borderless);
                btn_Blue.setBackgroundResource(R.drawable.btn_blue_borderless);
                btn_Green.setBackgroundResource(R.drawable.btn_green_borderless);
                btn_Red.setBackgroundResource(R.drawable.btn_red_borderless);
                btn_Yellow.setBackgroundResource(R.drawable.btn_yellow_borderless);
                btn_Orange.setBackgroundResource(R.drawable.btn_orange_borderless);
                pinkSelected = true;
                blackSelected = true;
                blueSelected = true;
                greenSelected = true;
                redSelected = true;
                yellowSelected = true;
                orangeSelected = true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.small:
                paintView.small();
                return true;
            case R.id.normal:
                paintView.normal();
                return true;
            case R.id.big:
                paintView.big();
                return true;
            case R.id.clear:
                paintView.clear();
                return true;
            case R.id.logout:
                paintView.clear();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
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
                    if (!canDraw) {
                        Intent intent = new Intent(getApplicationContext(), PopUpActivity.class);
                        intent.putExtra("SignIn_ID", id);
                        paintView.clear();
                        startActivity(intent);
                    }
                } else {
                    Log.d("TAG", "Current data: null");
                }
            }

        });
        return canDraw;
    }


    private void getTimer() {
        final String[] data = new String[1];
        final DocumentReference docRef = db.collection("time").document("timeID");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("TAG", "Listen failed.", e);
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    Map timeData = snapshot.getData();
                    data[0] = timeData.get("time").toString();
                    if (data[0].equals("TIME IS OVER!")) {
                        timer.setText("Time: Over!");
                    } else {
                        timer.setText("Time: " + timeData.get("time"));
                    }
                }
            }
        });
    }


    private void getPlayer() {

        final DocumentReference docRef = db.collection("players").document(id);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    Log.d("DATA", "Current data: " + snapshot.getString("name"));
                    String name = snapshot.getString("name");
                    long points = snapshot.getLong("points");
                    player.setText("Player: " + name + " | " + points);
                }
            }
        });
    }
}
