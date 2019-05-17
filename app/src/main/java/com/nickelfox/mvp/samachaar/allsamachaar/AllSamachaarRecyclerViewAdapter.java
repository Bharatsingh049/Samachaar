package com.nickelfox.mvp.samachaar.allsamachaar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nickelfox.mvp.samachaar.R;
import com.nickelfox.mvp.samachaar.data.repositoriy.model.SamachaarArticle;


import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link SamachaarArticle} and makes a call to the
 * TODO: Replace the implementation with code for your data type.
 */
public class AllSamachaarRecyclerViewAdapter extends RecyclerView.Adapter<AllSamachaarRecyclerViewAdapter.ViewHolder> {

    private List<SamachaarArticle> mValues;
    private Context MyContext;

    public AllSamachaarRecyclerViewAdapter(List<SamachaarArticle> items) {
        mValues = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horizontal_card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Glide.with(MyContext)
                .load(mValues.get(position).getUrlToImage())
                .crossFade()
                .into(holder.imageView);
        holder.titleView.setText(mValues.get(position).getTitle());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setList(List<SamachaarArticle> list) {
        mValues = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final ImageView imageView;
        final TextView titleView;
        SamachaarArticle mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            imageView = view.findViewById(R.id.samachaar_Image);
            titleView = view.findViewById(R.id.samachaar_title);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + titleView.getText() + "'";
        }
    }
}
