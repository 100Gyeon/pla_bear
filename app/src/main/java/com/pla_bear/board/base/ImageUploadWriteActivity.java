package com.pla_bear.board.base;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pla_bear.R;
import com.pla_bear.base.Commons;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

abstract public class ImageUploadWriteActivity extends WriteActivity implements Uploadable {
    protected static final int IMAGE_CAPTURE = 1;
    protected static final int EXTERNAL_CONTENT = 2;
    protected List<Uri> localImageUri = new ArrayList<>();
    protected List<Uri> serverImageUri = new ArrayList<>();
    final private int PERMISSION_REQUEST_STORAGE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!Commons.hasPermissions(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA)) {
            Commons.setPermissions(this,
                    PERMISSION_REQUEST_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.CAMERA);
            return;
        }
    }

    @Override
    public void uploadOnServer(String remotePath, String filename) {
        Uri file = Uri.fromFile(new File(filename));
        StorageReference ref = storageReference.child(remotePath + "/" + file.getLastPathSegment());

        UploadTask uploadTask = ref.putFile(file);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return ref.getDownloadUrl();
            }
        });

        urlTask.addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    ImageUploadWriteActivity.this.onUploadComplete(task.getResult());
                } else {
                    Log.e("Error", "Remote upload failed.");
                }
            }
        });
    }

    public void onUploadComplete(Uri uri) {
        serverImageUri.add(uri);
    }

    @Override
    public void localSave() {
        final String[] options = new String[] { getString(R.string.review_camera), getString(R.string.review_gallery), getString(R.string.cancel)};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.review_pic_select);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(options[i].equals(getString(R.string.review_camera))) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = new File(getExternalFilesDir(null).toString());
                    // 미완성

                    startActivityForResult(intent, IMAGE_CAPTURE);
                } else if(options[i].equals(getString(R.string.review_gallery))) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, EXTERNAL_CONTENT);
                } else {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch(requestCode) {
            case IMAGE_CAPTURE:
                if (resultCode == RESULT_OK && intent != null) {
                    // 미완성
                    localImageUri.add(intent.getData());
                }
                break;
            case EXTERNAL_CONTENT:
                if (resultCode == RESULT_OK && intent != null) {
                    Uri uri = intent.getData();
                    localImageUri.add(getPath(this, uri));
                }
                break;
        }
    }

    private static Uri getPath(Context context, Uri uri) {
        String result = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if(cursor != null){
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        return Uri.parse(result);
    }
}