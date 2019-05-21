package com.nickelfox.mvp.samachaar.allsamachaar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nickelfox.mvp.samachaar.R;
import com.nickelfox.mvp.samachaar.data.repositoriy.model.SamachaarArticle;
import com.nickelfox.mvp.samachaar.databinding.HorizontalCardLayoutBinding;

import java.util.List;

;

/**
 * {@link RecyclerView.Adapter} that can display a {@link SamachaarArticle} and makes a call to the
 * TODO: Replace the implementation with code for your data type.
 */
public class AllSamachaarRecyclerViewAdapter extends RecyclerView.Adapter<AllSamachaarRecyclerViewAdapter.ViewHolder> implements AllSamachaarCustomClickListener {

    private List<SamachaarArticle> samachaarArticleList;
    private Context MyContext;

    public AllSamachaarRecyclerViewAdapter(List<SamachaarArticle> items) {
        samachaarArticleList = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        HorizontalCardLayoutBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.horizontal_card_layout, parent, false);
        MyContext = parent.getContext();
        /*View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horizontal_card_layout, parent, false);*/
        //return new ViewHolder(binding);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        SamachaarArticle article = samachaarArticleList.get(position);
        holder.setBinding(article);
        holder.binding.setItemClickListener(this);
        /*holder.mItem = samachaarArticleList.get(position);
        Glide.with(MyContext)
                .load(samachaarArticleList.get(position).getUrlToImage())
                .crossFade()
                .into(holder.imageView);
        holder.titleView.setText(samachaarArticleList.get(position).getTitle());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    @BindingAdapter("imageURl")
    public static void setImageUrl(ImageView imageView, String imageURL) {
        if (imageURL == null) {
            imageView.setImageDrawable(null);
        } else {
            Glide.with(imageView.getContext())
                    .load(imageURL)
                    .crossFade()
                    .into(imageView);
            //MyImageLoader.loadInto(imageView, url);
        }
    }



    public void setList(List<SamachaarArticle> list) {
        samachaarArticleList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return samachaarArticleList.size();
    }

    @Override
    public void cardClicked(SamachaarArticle f) {
        Toast.makeText(MyContext, f.getTitle() + " is clicked", Toast.LENGTH_SHORT).show();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        HorizontalCardLayoutBinding binding;

        ViewHolder(HorizontalCardLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setBinding(SamachaarArticle article) {
            binding.setArticle(article);
        }
        /*final View mView;
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
        }*/
    }
}
