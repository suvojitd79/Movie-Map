package com.appcia.moviemap;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements OnClickMoviePoster{

    public static ArrayList<MovieData> mMovieData;
    private RecyclerView mRecyclerView;
    private final String BASE_URL_MOVIE_DB = "https://api.themoviedb.org/";
    public final String TAG = "Test";
    private Toolbar mToolbar;
    private LinearLayout mLinearLayout_setting;
    private boolean isVisible;
    private MovieApi movieApi;
    private RadioGroup mRadioGroup; //movies
    private String MOVIE_TYPE = "TOP_RATED"; //default
    private Switch mSwitch;  //image-quality
    public static String IMAGE_QUALITY = "w185/";
    public static Map<Integer,String> genre; //store genre data
    private ImageView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMovieData = new ArrayList<>();
        mRecyclerView = findViewById(R.id.movie_recycle_view);
        mToolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);
        mLinearLayout_setting = findViewById(R.id.linear_setting_part);
        isVisible = false;
        mRadioGroup = findViewById(R.id.movie_section_radio_group);
        mSwitch = findViewById(R.id.image_quality);
        genre = new HashMap<>();
        loading = findViewById(R.id.loading_gif);

        Glide.with(this).load(R.drawable.loading).into(loading);

        Retrofit retrofit = new Retrofit
                .Builder().baseUrl(BASE_URL_MOVIE_DB)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieApi = retrofit.create(MovieApi.class);



        Call<Genre> genreCall = movieApi.getGenre(getResources().getString(R.string.api_key));

        genreCall.enqueue(new Callback<Genre>() {
            @Override
            public void onResponse(Call<Genre> call, Response<Genre> response) {

//                Log.d(TAG,response.code()+"");

                if(response.isSuccessful())
                {
                    Genre genre1 = response.body();
                    ArrayList<KeyVal> keyVals = genre1.getGenreData();
                    for(int i=0;i<keyVals.size();i++)
                        genre.put(keyVals.get(i).getKey(),keyVals.get(i).getValue());

                }

            }

            @Override
            public void onFailure(Call<Genre> call, Throwable t) {


            }

        });



        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = mRadioGroup.findViewById(checkedId);
                int index = mRadioGroup.indexOfChild(radioButton);

                if(index==0)
                {
                    MOVIE_TYPE = "TOP_RATED";
                    mToolbar.setSubtitle(getResources().getString(R.string.tootbar_sub_title));
                }
                else if(index==1) {
                    MOVIE_TYPE = "MOST_POPULAR";
                    mToolbar.setSubtitle(getResources().getString(R.string.toolbar_sub_title_));
                }
                loadTopRatedMovies(MOVIE_TYPE);


            }
        });

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                    IMAGE_QUALITY = "original/";
                else
                    IMAGE_QUALITY = "w185/";

                loadTopRatedMovies(MOVIE_TYPE);
            }
        });




        loadTopRatedMovies(MOVIE_TYPE);

    }




    @Override
    public void clickPoster(int position) {

        Intent intent = new Intent(MainActivity.this,ShowMovieDetails.class);
        intent.putExtra("position",position);
        startActivity(intent);

    }



    //inflate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    //handle the click events
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {

            case R.id.setting_icon:
            {
                if(!isVisible) {
                    mLinearLayout_setting.setVisibility(View.VISIBLE);
                    menuItem.setIcon(R.drawable.cancel_svg);
                }else {
                    mLinearLayout_setting.setVisibility(View.GONE);
                    menuItem.setIcon(R.drawable.setting_icon);
                }
                isVisible = !isVisible;
            }
            case R.id.refresh:
            {

                loadTopRatedMovies(MOVIE_TYPE);

            }

            default:
                return super.onOptionsItemSelected(menuItem);

        }

    }





    private void loadTopRatedMovies(String TYPE)
    {
        Call<MovieInfo> movieInfoCall;

        if(TYPE=="TOP_RATED")
            movieInfoCall = movieApi.getTopRatedMovies(getResources().getString(R.string.api_key));
        else if(TYPE=="MOST_POPULAR")
            movieInfoCall = movieApi.getPopularMovies(getResources().getString(R.string.api_key));
        else
            return;

        movieInfoCall.enqueue(new Callback<MovieInfo>() {
            @Override
            public void onResponse(Call<MovieInfo> call, Response<MovieInfo> response) {

                // Log.d(TAG,Integer.toString(response.code()));
                //  Log.d(TAG,response.raw().request().url().toString());

                if(response.isSuccessful())
                {

                    MovieInfo movieInfo = response.body();

                    mMovieData = movieInfo.getMovieData(); //store the reference

                    MovieAdapter movieAdapter = new MovieAdapter(movieInfo,MainActivity.this,MainActivity.this);
                    GridLayoutManager gridLayoutManager;

                    //portrait mode
                    if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
                        gridLayoutManager = new GridLayoutManager(MainActivity.this,2);
                    else  //landscape
                        gridLayoutManager = new GridLayoutManager(MainActivity.this,3);

                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setLayoutManager(gridLayoutManager);
                    mRecyclerView.setAdapter(movieAdapter);


                }


            }

            @Override
            public void onFailure(Call<MovieInfo> call, Throwable t) {

            }
        });

    }




}





