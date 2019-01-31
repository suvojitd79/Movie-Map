package com.appcia.moviemap;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.andexert.library.RippleView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private ArrayList<MovieData> mMovieData;
    private OnClickMoviePoster mOnClickMoviePoster;
    private WeakReference<MainActivity> mMainActivityWeakReference;

    public MovieAdapter(MovieInfo movieInfo, OnClickMoviePoster onClickMoviePoster,MainActivity mainActivity) {
        mMovieData  = movieInfo.getMovieData();
        mOnClickMoviePoster = onClickMoviePoster;
        mMainActivityWeakReference = new WeakReference<>(mainActivity);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.movie_grid,viewGroup,false);
        int width = viewGroup.getWidth();
        int height = viewGroup.getHeight();

        if(width>height) {
            v.getLayoutParams().height = height;
            v.getLayoutParams().width = width/3;
        }
        else{
            v.getLayoutParams().height = height/2;
            v.getLayoutParams().width = width / 2;
        }



        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.setPoster(mMovieData.get(i).getImage());

    }

    @Override
    public int getItemCount() {
        return mMovieData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private RippleView mRippleView;
        private ImageView movie_poster;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movie_poster = itemView.findViewById(R.id.movie_poster);
            mRippleView = itemView.findViewById(R.id.movie_poster_rv);
            mRippleView.setOnClickListener(this);
        }

        private void setPoster(String image)
        {
            StringBuffer stringBuffer = new StringBuffer("http://image.tmdb.org/t/p/");
            stringBuffer.append(MainActivity.IMAGE_QUALITY);
            stringBuffer.append(image);
            final MainActivity mainActivity = mMainActivityWeakReference.get();
            if(mainActivity!=null)
                Glide.with(mainActivity)
                    .applyDefaultRequestOptions(new RequestOptions()
                            .placeholder(R.drawable.nic)
                            .error(R.drawable.nic))
                    .load(stringBuffer.toString())
                    .into(movie_poster);

        }

        @Override
        public void onClick(View v) {

            MainActivity mainActivity = mMainActivityWeakReference.get();
            if(mainActivity!=null)
            {
                int click = getAdapterPosition();
                mainActivity.clickPoster(click);
            }


        }


    }


}
