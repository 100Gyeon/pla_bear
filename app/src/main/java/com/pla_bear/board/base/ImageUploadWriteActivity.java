package com.pla_bear.board.base;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pla_bear.R;
import com.pla_bear.base.Commons;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

abstract public class ImageUploadWriteActivity extends WriteActivity implements Uploadable {
    protected static final int REQUEST_IMAGE_CAPTURE = 1;
    protected static final int REQUEST_EXTERNAL_CONTENT = 2;
    protected final List<Uri> localImageUri = new ArrayList<>();
    protected final List<Uri> serverImageUri = new ArrayList<>();
    @SuppressWarnings("FieldCanBeLocal")
    final private int PERMISSION_REQUEST_STORAGE = 1000;
    private Uri imageUri;
    protected File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Commons.hasPermissions(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA)) {
            Commons.setPermissions(this,
                    PERMISSION_REQUEST_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.CAMERA);
        }
    }

    @Override
    public void uploadOnServer(String remotePath, String filename) {
        Uri file = Uri.fromFile(new File(filename));
        String lastPathSegment = file.getLastPathSegment();
        String ext = lastPathSegment.substring(lastPathSegment.lastIndexOf(".") + 1);
        String hash = Commons.sha256(file.getLastPathSegment() + System.currentTimeMillis());

        StorageReference ref = storageReference.child(remotePath + "/" + hash + "." + ext);

        UploadTask uploadTask = ref.putFile(file);

        Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw Objects.requireNonNull(task.getException());
            }
            return ref.getDownloadUrl();
        });

        urlTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ImageUploadWriteActivity.this.onUploadComplete(task.getResult());
            } else {
                Log.e("Error", "Remote upload failed.");
            }
        });
    }

    public void onUploadComplete(Uri uri) {
        serverImageUri.add(uri);
    }

    @Override
    public void localSave() {
        final String[] options = new String[] { getString(R.string.review_camera), getString(R.string.review_gallery), getString(R.string.cancel)};

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialog);
        builder.setTitle(R.string.review_pic_select);
        builder.setItems(options, (dialogInterface, i) -> {
            if(options[i].equals(getString(R.string.review_camera))) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                try {
                    photoFile = createImageFile();
                } catch (IOException ignored) {
                }
                if (photoFile != null) {
                    imageUri = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }
            } else if(options[i].equals(getString(R.string.review_gallery))) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_EXTERNAL_CONTENT);
            } else {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREA).format(new Date());
        String imageFileName = getPackageName() + "_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch(requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK) {
                    localImageUri.add(Uri.parse(photoFile.getAbsolutePath()));
                }
                break;
            case REQUEST_EXTERNAL_CONTENT:
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    localImageUri.add(getPath(this, uri));
                }
                break;
        }
    }

    private static Uri getPath(Context context, Uri uri) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
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