package app.com.stapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.util.PriorityQueue;

public class PostActivity extends AppCompatActivity {

    private ImageButton mSelectImage;
    private EditText mTitleField;
    private EditText mDescField;
    private Button mSubmitButton;

    private Uri mImageURI = null;

    private static final int GALLERY_REQUEST = 1;

    private StorageReference mStorage;
    private DatabaseReference mDatabase;

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Positional");


        mSelectImage = (ImageButton) findViewById(R.id.imageSelect);

        mTitleField = (EditText) findViewById(R.id.titleField);
        mDescField = (EditText) findViewById(R.id.descField);

        mSubmitButton = (Button) findViewById(R.id.submitButton);

        mProgress = new ProgressDialog(PostActivity.this);


        //for loading image from gallery
        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);

            }
        });

        //for putting the data to database after pressing submit button;
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPosting();
            }
        });
    }

    //method for posting data to server
    private void startPosting() {

        mProgress.setMessage("uploading...");
        mProgress.show();

        final String title_val = mTitleField.getText().toString().trim();
        final String desc_val = mDescField.getText().toString().trim();

        if(!TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(desc_val) && mImageURI != null ) {

            StorageReference filepath = mStorage.child("Images").child(mImageURI.getLastPathSegment());
            filepath.putFile(mImageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    DatabaseReference newPost = mDatabase.push();
                    newPost.child("title").setValue(title_val);
                    newPost.child("desc").setValue(desc_val);
                    newPost.child("image").setValue(downloadUrl.toString());

                    mProgress.dismiss();
                    Toast.makeText(getApplicationContext(),"uploaded ", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    //for loading image from gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GALLERY_REQUEST && resultCode== RESULT_OK ){

            mImageURI = data.getData();

            mSelectImage.setImageURI(mImageURI);

        }

    }
}
