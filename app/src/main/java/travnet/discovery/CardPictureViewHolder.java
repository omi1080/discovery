package travnet.discovery;

import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liangfeizc.avatarview.AvatarView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;



//View Holder for picture cards
public class CardPictureViewHolder extends RecyclerView.ViewHolder {
    View cardView;

    ImageView image;
    View scrim;
    TextView title;
    TextView location;
    ImageButton likeButton;
    TextView noOfLikes;
    ImageButton addToBlButton;
    TextView noOfBucketList;
    TextView activity;
    TextView description;
    AvatarView uploaderPic;
    TextView uploaderName;

    public CardPictureViewHolder(View itemView) {
        super(itemView);
        cardView = itemView;

        image = (ImageView) itemView.findViewById(R.id.image);
        scrim = (View) itemView.findViewById(R.id.scrim);
        title= (TextView) itemView.findViewById(R.id.title);
        location = (TextView) itemView.findViewById(R.id.location);
        likeButton = (ImageButton) itemView.findViewById(R.id.like_button);
        noOfLikes = (TextView) itemView.findViewById(R.id.no_of_likes);
        addToBlButton = (ImageButton) itemView.findViewById(R.id.add_to_bl_button);
        noOfBucketList = (TextView) itemView.findViewById(R.id.no_of_bl);
        activity = (TextView) itemView.findViewById(R.id.activity);
        description = (TextView) itemView.findViewById(R.id.description);
        uploaderName = (TextView) itemView.findViewById(R.id.uploader_name);
        uploaderPic = (AvatarView) itemView.findViewById(R.id.uploader_pp);
    }



    public void poplulatePictureCard (final DataPictureCard dataPictureCard, int position) {
        DisplayImageOptions options= new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();


        ImageLoader.getInstance().displayImage(dataPictureCard.link, this.image, options, new Animations.AnimateFirstDisplayListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                scrim.getLayoutParams().height = loadedImage.getHeight();
            }
        });
        this.title.setText(dataPictureCard.title);
        this.location.setText(dataPictureCard.location);
        this.noOfLikes.setText(String.valueOf(dataPictureCard.likes));
        this.noOfBucketList.setText(String.valueOf(dataPictureCard.noBlucketListed));
        this.activity.setText(dataPictureCard.activity);
        this.description.setText(dataPictureCard.description);
        this.uploaderName.setText(dataPictureCard.dataUploaderBar.uploader_name);
        ImageLoader.getInstance().displayImage(dataPictureCard.dataUploaderBar.uploader_pp, this.uploaderPic, options, null);

        if (dataPictureCard.isLiked == false) {
            this.scrim.setVisibility(View.GONE);
            this.title.setVisibility(View.GONE);
            this.location.setVisibility(View.GONE);
            this.description.setVisibility(View.GONE);
            likeButton.setImageResource(R.drawable.ic_like);
            this.addLikeCallback(dataPictureCard);
        } else {
            this.scrim.setVisibility(View.VISIBLE);
            this.title.setVisibility(View.VISIBLE);
            this.location.setVisibility(View.VISIBLE);
            this.description.setVisibility(View.VISIBLE);
            this.likeButton.setImageResource(R.drawable.ic_liked);
            this.likeButton.setClickable(false);
        }

        if (dataPictureCard.isAddedToBl == false) {
            this.addToBlButton.setImageResource(R.drawable.ic_add_to_bl);
            this.addBucketCallback(dataPictureCard);
        } else {
            this.addToBlButton.setImageResource(R.drawable.ic_added_to_bl);
            this.addToBlButton.setClickable(false);
        }



    }


        public void addLikeCallback(final DataPictureCard dataPictureCard) {
            likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Backend.getInstance().registerLikeCard(dataPictureCard.id);
                dataPictureCard.likes++;
                noOfLikes.setText(String.valueOf(dataPictureCard.likes));
                dataPictureCard.isLiked = true;

                likeButton.setImageResource(R.drawable.ic_liked);
                likeButton.setClickable(false);

                scrim.setVisibility(View.VISIBLE);
                title.setVisibility(View.VISIBLE);
                location.setVisibility(View.VISIBLE);
                description.setVisibility(View.VISIBLE);
                AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
                fadeIn.setDuration(1200);
                fadeIn.setFillAfter(true);
                AlphaAnimation scrimFadeIn = new AlphaAnimation(0.0f, 0.7f);
                scrimFadeIn.setDuration(1200);
                scrimFadeIn.setFillAfter(true);
                title.startAnimation(fadeIn);
                location.startAnimation(fadeIn);
                scrim.startAnimation(scrimFadeIn);
            }
        });
        }

    public void addBucketCallback(final DataPictureCard dataPictureCard) {
        addToBlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Backend.getInstance().registerBucketCard(dataPictureCard.id);
                dataPictureCard.noBlucketListed++;
                noOfBucketList.setText(String.valueOf(dataPictureCard.noBlucketListed));
                dataPictureCard.isAddedToBl = true;

                addToBlButton.setImageResource(R.drawable.ic_added_to_bl);
                addToBlButton.setClickable(false);
            }
        });
    }


}
