package com.appcia.moviemap;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ShowMovieDetails extends AppCompatActivity {

    private ImageView mov_img;
    private Toolbar mToolbar;
    private FloatingActionButton share_button;
    private TextView mov_name,mov_rating,mov_popularity,mov_genre,mov_release_date,mov_overview;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private MovieData mMovieData;
    private AppBarLayout mAppBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie_details);

        mov_img = findViewById(R.id.mov_image);
        share_button = findViewById(R.id.share_button);
        mov_name = findViewById(R.id.mov_name);
        mov_rating = findViewById(R.id.mov_rating);
        mov_popularity = findViewById(R.id.mov_popularity);
        mov_genre = findViewById(R.id.mov_genre);
        mov_release_date = findViewById(R.id.mov_release_date);
        mov_overview = findViewById(R.id.mov_overview);
        mCollapsingToolbarLayout = findViewById(R.id.c_tool_bar);
        mAppBarLayout = findViewById(R.id.app_bar_layout);




        Intent intent = getIntent();
        if(intent.getExtras()!=null)
        {
            int position = intent.getIntExtra("position",0);

            MovieData movieData = MainActivity.mMovieData.get(position);
            mMovieData = movieData;
            StringBuffer stringBuffer = new StringBuffer("http://image.tmdb.org/t/p/");
            stringBuffer.append(MainActivity.IMAGE_QUALITY);
            stringBuffer.append(movieData.getImage());
            Glide.with(ShowMovieDetails.this)
                    .applyDefaultRequestOptions(new RequestOptions()
                            .placeholder(R.drawable.nic)
                            .error(R.drawable.nic))
                    .load(stringBuffer.toString()).into(mov_img);
            mCollapsingToolbarLayout.setTitle(movieData.getTitle());

            mov_name.setText(movieData.getOriginal_title());
            mov_rating.setText(movieData.getRating());
            mov_popularity.setText(movieData.getPopularity());
            mov_genre.setText("");
            int i=0;
            for(i=0;i<movieData.getGenre_ids().length-1;i++)
            {
                mov_genre.append(MainActivity.genre.get(movieData.getGenre_ids()[i]) + " | ");

            }
            mov_genre.append(MainActivity.genre.get(movieData.getGenre_ids()[i]));

            mov_release_date.setText(movieData.getRelease_date());
            mov_overview.setText(movieData.getOverview());

        }

        share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent si = new Intent();
                si.setAction(Intent.ACTION_SEND);
                si.putExtra(Intent.EXTRA_TEXT,mMovieData.getTitle() + " -"+ mMovieData.getOverview());
                si.setType("text/plain");
                startActivity(si);
            }
        });


    }
}
