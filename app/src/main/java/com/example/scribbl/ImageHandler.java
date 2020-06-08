package com.example.scribbl;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;

import com.google.android.gms.common.util.Base64Utils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageHandler extends AsyncTask<Bitmap, Void, Void> {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private Map<String, Object> docData = new HashMap<>();
    private ByteArrayOutputStream stream = new ByteArrayOutputStream();

    private static final String TAG1 = "Base64: ";


    @Override
    protected Void doInBackground(Bitmap... bitmaps) {
        //Thread activity
        for (int i = 0; i<bitmaps.length; i++) {
            uploadStringToDB(generateBase64(bitmaps[i]));
        }
        return null;
    }


    private String generateBase64(Bitmap map) {
        //convert Bitmap to JPEG to ByteArray
        map.compress(Bitmap.CompressFormat.JPEG, 0, stream);
        byte[] data = stream.toByteArray();
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //convert ByteArray to Base64 String
        return Base64Utils.encode(data);
                //Base64.encodeToString(data, Base64.DEFAULT);
    }


    private void uploadStringToDB(String base64String) {
        //Log.v(TAG1, "string: " + base64String);
        //upload to FireDB
        mDatabase.child("Base64Strings").child("imageID").child("image").setValue(base64String);
        docData.clear();
    }

}

