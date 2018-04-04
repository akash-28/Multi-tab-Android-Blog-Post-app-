package app.com.stapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class PositionalCallFragment extends Fragment {

//    private ProgressDialog progressDialog;

    private View mMainView;

    private RecyclerView mPositionalTipsList;
    private LinearLayoutManager mLinearLayoutManager;
    //database reference
    private DatabaseReference mDatabase;


    public PositionalCallFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_positional_calls, container, false);

       /* progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("loading...");*/

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Positional");
        mDatabase.keepSynced(true);

        mPositionalTipsList = (RecyclerView) mMainView.findViewById(R.id.positional_list);

        ///////////////////
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);

        mPositionalTipsList.setHasFixedSize(true);
        mPositionalTipsList.setLayoutManager(mLinearLayoutManager);

        ////////to test ---- mPositionalTipsList.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inflate the layout for this fragment
        return mMainView;
    }


    @Override
    public void onStart() {
        super.onStart();

        //progressDialog.show();

        FirebaseRecyclerAdapter<Positional,PositionalViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Positional, PositionalViewHolder>(

                Positional.class,
                R.layout.positional_single_row,
                PositionalViewHolder.class,
                mDatabase

        ) {
            @Override
            protected void populateViewHolder(PositionalViewHolder viewHolder, Positional model, int position) {

                //progressDialog.dismiss();

                final String post_key = getRef(position).getKey();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getContext(), model.getImage());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Log.v("switching","intent to detail activity");

                        Intent intent = new Intent(getContext() , PositionalSingleActivity.class);
                        intent.putExtra("post_id",post_key);
                        startActivity(intent);


                    }
                });

            }
        };

        mPositionalTipsList.setAdapter(firebaseRecyclerAdapter);
    }




    public static class PositionalViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public PositionalViewHolder(View itemView) {

            super(itemView);
            mView = itemView;

        }

        public void setTitle(String title){

            TextView post_title = (TextView) mView.findViewById(R.id.post_title);
            post_title.setText(title);

        }

        public void setDesc(String desc){

            TextView post_desc = (TextView) mView.findViewById(R.id.post_desc);
            post_desc.setText(desc);

        }

        public void setImage(Context ctx, String image){

            ImageView post_image = (ImageView) mView.findViewById(R.id.post_image);
            Picasso.with(ctx)
                    .load(image)
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.stock_market)
                    .into(post_image);
        }
    }
}
