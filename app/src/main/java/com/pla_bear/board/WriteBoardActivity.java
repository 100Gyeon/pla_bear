package com.pla_bear.board;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.pla_bear.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;

public class WriteBoardActivity extends AppCompatActivity{
    ImageView imageView;
    EditText editText;
    TextView name_textView;

    Button store_btn;

    private File tempFile;

    private FirebaseAuth auth;
    private FirebaseStorage storage;
    private FirebaseDatabase database;

    private String imagePath;

    private static final int GALLERY_CODE=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_board);


}}