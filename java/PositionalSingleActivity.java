package app.com.stapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PositionalSingleActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private String mPostKey = null;

    private ImageView mPositionalSingleImage;
    private TextView mPositionalSingleTitle;
    private TextView mPositionalSingleDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_positional_single);

        mPositionalSingleTitle = (TextView) findViewById(R.id.positional_singletitle);
        mPositionalSingleDesc = (TextView) findViewById(R.id.positional_singledesc);
        mPositionalSingleImage = (ImageView) findViewById(R.id.positional_singleimage);


        mPostKey = getIntent().getExtras().getString("post_id");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Positional");

        //retrieve post details using post key
        mDatabase.child(mPostKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String post_title = (String) dataSnapshot.child("title").getValue();
                String post_desc = (String)dataSnapshot.child("desc").getValue();
                String post_image = (String)dataSnapshot.child("image").getValue();

                //setting values
                mPositionalSingleTitle.setText(post_title);
                mPositionalSingleDesc.setText(post_desc);

                Picasso.with(getApplicationContext())
                        .load(post_image)
                        .fit()
                        .placeholder(R.drawable.stock_detail_image)
                        .into(mPositionalSingleImage);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(),"Something went wrong...",Toast.LENGTH_SHORT).show();

            }
        });


    }
}
