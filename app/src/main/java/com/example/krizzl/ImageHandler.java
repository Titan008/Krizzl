package com.example.krizzl;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.google.android.gms.common.util.Base64Utils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageHandler extends AsyncTask<Bitmap, Void, Void> {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private Map<String, Object> docData = new HashMap<>();
    private ByteArrayOutputStream stream = new ByteArrayOutputStream();


    @Override
    protected Void doInBackground(Bitmap... bitmaps) {
        for (int i = 0; i < bitmaps.length; i++) {
            uploadStringToDB(generateBase64(bitmaps[i]));
        }
        return null;
    }


    private String generateBase64(Bitmap map) {
        map.compress(Bitmap.CompressFormat.JPEG, 0, stream);
        byte[] data = stream.toByteArray();
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64Utils.encode(data);
    }


    private void uploadStringToDB(String base64String) {
        mDatabase.child("Base64Strings").child("imageID").child("image").setValue(base64String);
        docData.clear();
    }
}

