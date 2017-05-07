package com.thaumatech.astronomicon;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PhotoHolder> {

    private ArrayList<Photo> mPhotos;

    //Made the class extend RecyclerView.ViewHolder, allowing it to be used as a ViewHolder for the adapter
    public static class PhotoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Added a list of references to the lifecycle of the object to allow the ViewHolder to hang on to your ImageView and TextView, so it doesn’t have to repeatedly query the same information
        private ImageView mItemImage;
        private TextView mItemDate;
        private TextView mItemDescription;
        private Photo mPhoto;

        //Added a key for easier reference to the particular item being used to launch your RecyclerView
        private static final String PHOTO_KEY = "PHOTO";

        //Set up a constructor to handle grabbing references to various subviews of the photo layout
        public PhotoHolder(View v) {
            super(v);

            mItemImage = (ImageView) v.findViewById(R.id.item_image);
            mItemDate = (TextView) v.findViewById(R.id.item_date);
            mItemDescription = (TextView) v.findViewById(R.id.item_description);
            v.setOnClickListener(this);
        }

        //Implemented the required method for View.OnClickListener since ViewHolders are responsible for their own event handling
        @Override
        public void onClick(View v) {

            Context context = itemView.getContext();
            Intent showPhotoIntent = new Intent(context, PhotoActivity.class);
            showPhotoIntent.putExtra(PHOTO_KEY, mPhoto);
            context.startActivity(showPhotoIntent);

        }

        public void bindPhoto(Photo photo) {
            mPhoto = photo;
            Picasso.with(mItemImage.getContext()).load(photo.getUrl()).into(mItemImage);
            mItemDate.setText(photo.getHumanDate());
            mItemDescription.setText(photo.getExplanation());
        }
    }

    public RecyclerAdapter(ArrayList<Photo> photos) {
        mPhotos = photos;
    }

    @Override
    public RecyclerAdapter.PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_row, parent, false);
        return new PhotoHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.PhotoHolder holder, int position) {
        Photo itemPhoto = mPhotos.get(position);
        holder.bindPhoto(itemPhoto);
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }
}
