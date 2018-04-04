package app.com.stapp;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class IntradayCallFragment extends Fragment {

    private ProgressDialog progressDialog;

    private View mMainView;

    private RecyclerView mIntradayTipsList;
    private LinearLayoutManager mLinearLayoutManager;

    //database reference
    private DatabaseReference mDatabase;


    public IntradayCallFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView = inflater.inflate(R.layout.fragment_intraday_call, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("loading...");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Intraday");
        mDatabase.keepSynced(true);


        mIntradayTipsList = (RecyclerView) mMainView.findViewById(R.id.intraday_list);
        // set Linear Layout
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);

        mIntradayTipsList.setHasFixedSize(true);
        mIntradayTipsList.setLayoutManager(mLinearLayoutManager);

        //mIntradayTipsList.setLayoutManager(new LinearLayoutManager(getContext()));

        return mMainView;
    }

    @Override
    public void onStart() {
        super.onStart();

        progressDialog.show();

        FirebaseRecyclerAdapter<Intraday,IntradayViewHolder> mfirebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Intraday, IntradayViewHolder>(

                Intraday.class,
                R.layout.intraday_single_row,
                IntradayViewHolder.class,
                mDatabase

        ) {
            @Override
            protected void populateViewHolder(IntradayViewHolder viewHolder, Intraday model, int position) {

                progressDialog.dismiss();

                final String post_key = getRef(position).getKey();

                viewHolder.setTitle(model.getIntra_title());
                viewHolder.setDesc(model.getIntra_desc());
                viewHolder.setImage(getContext(), model.getIntra_image());


                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Log.v("intraday","intent to detail activity");
                        //Toast.makeText(getContext(),post_key,Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(getContext() , IntradaySingleActivity.class);
                        i.putExtra("post_id",post_key);
                        startActivity(i);

                    }
                });

            }
        };

        mIntradayTipsList.setAdapter(mfirebaseRecyclerAdapter);

    }


    //Viewholder class
    public static class IntradayViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public IntradayViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }


        public void setTitle(String title){

            TextView post_title = (TextView) mView.findViewById(R.id.intraday_title);
            post_title.setText(title);

        }

        public void setDesc(String desc){

            TextView post_desc = (TextView) mView.findViewById(R.id.intraday_detail);
            post_desc.setText(desc);

        }

        public void setImage(Context ctx, String image){

            ImageView post_image = (ImageView) mView.findViewById(R.id.intraday_image);
            Picasso.with(ctx)
                    .load(image)
                    .fit()
                    .placeholder(R.drawable.stock_market_icon)
                    .into(post_image);
        }
    }

}
