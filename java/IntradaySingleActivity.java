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

public class IntradaySingleActivity extends AppCompatActivity {


    private DatabaseReference mDatabase;

    private String mPostKey = null;

    private ImageView mIntradaySingleImage;
    private TextView mIntradaySingleTitle;
    private TextView mIntradaySingleDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intraday_single);

        mIntradaySingleTitle = (TextView) findViewById(R.id.intraday_singletitle);
        mIntradaySingleDesc = (TextView) findViewById(R.id.intraday_singledesc);
        mIntradaySingleImage = (ImageView) findViewById(R.id.intraday_singleimage);


        mPostKey = getIntent().getExtras().getString("post_id");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Intraday");

        //retrieve post details using post key
        mDatabase.child(mPostKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String post_title = (String) dataSnapshot.child("intra_title").getValue();
                String post_desc = (String)dataSnapshot.child("intra_desc").getValue();
                String post_image = (String)dataSnapshot.child("intra_image").getValue();

                //setting values
                mIntradaySingleTitle.setText(post_title);
                mIntradaySingleDesc.setText(post_desc);

                Picasso.with(getApplicationContext())
                        .load(post_image)
                        .fit()
                        .placeholder(R.drawable.stock_detail_image)
                        .into(mIntradaySingleImage);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(),"Something went wrong...",Toast.LENGTH_SHORT).show();

            }
        });

    }

}
