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
import com.pla_bear.board.base.BoardDTO;
import com.pla_bear.board.base.ImageUploadWriteActivity;
import com.pla_bear.board.base.WriteActivity;

import java.io.File;

public class WriteBoardActivity extends WriteActivity {
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

        auth=FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();

        imageView=(ImageView)findViewById(R.id.write_image_imageView);
        editText=(EditText)findViewById(R.id.write_content_textView);
        name_textView=(TextView)findViewById(R.id.write_name_textView);
        store_btn=(Button)findViewById(R.id.store_content);

        name_textView.setText(auth.getCurrentUser().getDisplayName());

    }
    public void imageClick(View view) {
        if(view==imageView){
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            startActivityForResult(intent, GALLERY_CODE);
        }
    }

    public void storeBtnClick(View view) {
        if(view==store_btn){
            upload(imagePath);
            Intent intent = new Intent(WriteBoardActivity.this, BoardActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE) {

            // try {
            //     if (data != null) {
            imagePath = getPath(data.getData());
            File f = new File(imagePath);
            imageView.setImageURI(Uri.fromFile(f));
            //}
            //   } catch (Exception e) {
            //      e.printStackTrace();
            //   }
        }
    }

    public String getPath(Uri uri) {
        String [] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader=new CursorLoader(this,uri,proj,null,null,null);

        Cursor cursor=cursorLoader.loadInBackground();
        int index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);
    }

    public void upload(String uri) {

        StorageReference storageRef = storage.getReferenceFromUrl("gs://plabear.appspot.com");
        if (uri != null) {
            Uri file = Uri.fromFile(new File(uri));
            StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
            //.child(auth.getCurrentUser().getDisplayName());
            UploadTask uploadTask = riversRef.putFile(file);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // 실패했을 경우
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests")
                    Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl();
                    downloadUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            BoardDTO boardDTO = new BoardDTO();
                      //      boardDTO.ImageUrl = uri.toString();
                            boardDTO.name = name_textView.getText().toString();
                            boardDTO.content = editText.getText().toString();
                            database.getReference().child("images").push().setValue(boardDTO);

                            Toast.makeText(getApplicationContext(),"다 저장 완료",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });

        }
        else {
            BoardDTO boardDTO = new BoardDTO();
       //     boardDTO.ImageUrl = String.valueOf(getResources().getDrawable(R.drawable.ic_gallery));
            boardDTO.name = name_textView.getText().toString();
            boardDTO.content = editText.getText().toString();
            database.getReference().child("images").push().setValue(boardDTO);

            Toast.makeText(getApplicationContext(), "사진을 빼고 저장 완료", Toast.LENGTH_SHORT).show();
        }
//         갤러리에서 사진을 선택안한 경우에 imageUrl에 null값을 준 것


    }

}