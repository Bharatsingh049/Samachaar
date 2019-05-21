package com.nickelfox.mvp.samachaar.allsamachaar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.nickelfox.mvp.samachaar.FragmentHorizontalRecyclerView;
import com.nickelfox.mvp.samachaar.R;
import com.nickelfox.mvp.samachaar.data.repositoriy.SamachaarRepository;
import com.nickelfox.mvp.samachaar.data.repositoriy.local.SamachaarDatabase;
import com.nickelfox.mvp.samachaar.data.repositoriy.local.SamachaarLocalRepository;
import com.nickelfox.mvp.samachaar.data.repositoriy.model.SamachaarArticle;
import com.nickelfox.mvp.samachaar.data.repositoriy.remote.SamachaarRemoteRepository;
import com.nickelfox.mvp.samachaar.databinding.ActivityMainBinding;
import com.nickelfox.mvp.samachaar.viewmodel.SamachaarViewModel;

import java.util.List;

//import androidx.lifecycle.ViewModelProviders;


public class AllSamachaarActivity extends AppCompatActivity implements FragmentHorizontalRecyclerView.OnFragmentInteractionListener {

    private SamachaarViewModel samachaarViewModel;

    private SwipeRefreshLayout samachaarLayout;

    private List<SamachaarArticle> tempList;

    private LiveData<List<SamachaarArticle>> samachaarLiveList;

    private ProgressDialog mProgressDialog;

    private static ActivityMainBinding binding;

    private final String[] categories = {"business", "entertainment", "health", "science", "sports", "technology"};

    @SuppressLint("StaticFieldLeak")
    private static AllSamachaarRecyclerViewAdapter businessAdapter, entertainmentAdapter, healthAdapter, scienceAdapter, sportsAdapter, technologyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setIsLoading(true);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        samachaarViewModel = ViewModelProviders.of(this).get(SamachaarViewModel.class);
        samachaarViewModel.init(SamachaarRepository.getInstance(SamachaarLocalRepository.getInstance(SamachaarDatabase.getInstance(getApplicationContext()).samachaarDao())
                , SamachaarRemoteRepository.getInstance()));
        setFragments();
        samachaarViewModel.getAllSamachaarFromRemote().observe(this, new Observer<List<SamachaarArticle>>() {
            @Override
            public void onChanged(List<SamachaarArticle> list) {
                if (list != null) {
                    binding.setIsLoading(false);
                    mProgressDialog.dismiss();
                }
            }
        });
        /*samachaarViewModel.getAllSamachaarFromRemote().observe(this, new Observer<List<SamachaarArticle>>() {
            @Override
            public void onChanged(List<SamachaarArticle> list) {
                /*
                List<SamachaarArticle> samachaarArticlesList = new ArrayList<>();

                for (SamachaarArticle samachaarArticle : list) {
                    if (TextUtils.equals(samachaarArticle.getCategory(), categories[i])) {

                        samachaarArticle.setTitle(samachaarArticle.getTitle());
                        samachaarArticle.setDescription(samachaarArticle.getDescription());
                        samachaarArticle.setUrlToImage(samachaarArticle.getUrlToImage());
                        samachaarArticle.setUrl(samachaarArticle.getUrl());
                        samachaarArticlesList.add(samachaarArticle);
                    }
                }

                switch (i) {
                    case 0:
                        int temp = samachaarArticlesList.size();
                        Log.e( "onChanged: ",temp+"" );
                        //samachaarView.showBusinessList(samachaarArticlesList);

                        break;
                    case 1:
                        int temp1 = samachaarArticlesList.size();
                        Log.e( "onChanged: ",temp1+"" );
                        //samachaarView.showEntertainmentList(samachaarArticlesList);
                        break;
                    case 2:
                        int temp2 = samachaarArticlesList.size();
                        Log.e( "onChanged: ",temp2+"" );
                        //samachaarView.showHealthList(samachaarArticlesList);
                        break;
                    case 3:
                        int temp3 = samachaarArticlesList.size();
                        Log.e( "onChanged: ",temp3+"" );
                        //samachaarView.showScienceList(samachaarArticlesList);
                        break;
                    case 4:
                        int temp4 = samachaarArticlesList.size();
                        Log.e( "onChanged: ",temp4+"" );
                        //samachaarView.showSportsList(samachaarArticlesList);
                        break;
                    case 5:
                        int temp5 = samachaarArticlesList.size();
                        Log.e( "onChanged: ",temp5+"" );
                        //samachaarView.showTechnologyList(samachaarArticlesList);
                        break;


                }

            }


        });*/


        //setAdapters();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setTitle("Please wait");
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        //initRecyclerView();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @BindingAdapter("visibleGone")
    public static void setVisibleGone(ProgressBar progressBar, boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    /*public void setAdapters() {
        tempList = new ArrayList<>();
        businessAdapter = new AllSamachaarRecyclerViewAdapter(tempList);
        entertainmentAdapter = new AllSamachaarRecyclerViewAdapter(tempList);
        healthAdapter = new AllSamachaarRecyclerViewAdapter(tempList);
        scienceAdapter = new AllSamachaarRecyclerViewAdapter(tempList);
        sportsAdapter = new AllSamachaarRecyclerViewAdapter(tempList);
        technologyAdapter = new AllSamachaarRecyclerViewAdapter(tempList);

        binding.setBusinessAdapter(businessAdapter);
        binding.setEntertainmentAdapter(entertainmentAdapter);
        binding.setHealthAdapter(healthAdapter);
        binding.setScienceAdapter(scienceAdapter);
        binding.setSportsAdapter(sportsAdapter);
        binding.setTechnologyAdapter(technologyAdapter);

    }*/

    public void setFragments() {
        FragmentHorizontalRecyclerView businessFragment = FragmentHorizontalRecyclerView.newInstance(samachaarViewModel, categories[0], binding);
        FragmentHorizontalRecyclerView entertainmentFragment = FragmentHorizontalRecyclerView.newInstance(samachaarViewModel, categories[1], binding);
        FragmentHorizontalRecyclerView healthFragment = FragmentHorizontalRecyclerView.newInstance(samachaarViewModel, categories[2], binding);
        FragmentHorizontalRecyclerView scienceFragment = FragmentHorizontalRecyclerView.newInstance(samachaarViewModel, categories[3], binding);
        FragmentHorizontalRecyclerView sportsFragment = FragmentHorizontalRecyclerView.newInstance(samachaarViewModel, categories[4], binding);
        FragmentHorizontalRecyclerView technologyFragment = FragmentHorizontalRecyclerView.newInstance(samachaarViewModel, categories[5], binding);

        getSupportFragmentManager().beginTransaction().add(R.id.business_frame, businessFragment, null).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.entertainment_frame, entertainmentFragment, null).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.health_frame, healthFragment, null).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.science_frame, scienceFragment, null).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.sports_frame, sportsFragment, null).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.technology_frame, technologyFragment, null).commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {
        super.onResume();
        //SamachaarViewModel.start(this);
        //SamachaarViewModel.fetchSamachaar();
    }


    @Override
    protected void onStop() {
        super.onStop();
        //SamachaarViewModel.onDestroy();
    }

    /*private void initRecyclerView() {
        RecyclerView businessRecyclerView = findViewById(R.id.business_recyclerView);
        RecyclerView entertainmentRecyclerView = findViewById(R.id.entertainment_recyclerView);
        RecyclerView healthRecyclerView = findViewById(R.id.health_recyclerView);
        RecyclerView scienceRecyclerView = findViewById(R.id.science_recyclerView);
        RecyclerView sportsRecyclerView = findViewById(R.id.sports_recyclerView);
        RecyclerView technologyRecyclerView = findViewById(R.id.technology_recyclerView);

        businessRecyclerView.setAdapter(businessAdapter);
        entertainmentRecyclerView.setAdapter(entertainmentAdapter);
        healthRecyclerView.setAdapter(healthAdapter);
        scienceRecyclerView.setAdapter(scienceAdapter);
        sportsRecyclerView.setAdapter(sportsAdapter);
        technologyRecyclerView.setAdapter(technologyAdapter);

    }*/


    /*public void showBusinessList(@NonNull List<SamachaarArticle> businessList) {
        int temp = businessList.size();
        businessAdapter.setList(businessList);
        if (temp <= 0) {
            Log.d("BusinessList: ", temp + "");
        }
        binding.setBusinessAdapter(businessAdapter);
    }


    public void showEntertainmentList(@NonNull List<SamachaarArticle> entertainmentList) {
        int temp = entertainmentList.size();
        entertainmentAdapter.setList(entertainmentList);
        binding.setEntertainmentAdapter(entertainmentAdapter);
        Log.d("EntertainmentList: ", temp + "");
    }


    public void showHealthList(@NonNull List<SamachaarArticle> healthList) {
        int temp = healthList.size();
        healthAdapter.setList(healthList);
        binding.setHealthAdapter(healthAdapter);
        Log.d("HealthList: ", temp + "");
    }


    public void showScienceList(@NonNull List<SamachaarArticle> scienceList) {
        int temp = scienceList.size();
        scienceAdapter.setList(scienceList);
        binding.setScienceAdapter(scienceAdapter);
        Log.d("ScienceList: ", temp + "");
    }


    public void showSportsList(@NonNull List<SamachaarArticle> sportsList) {
        int temp = sportsList.size();
        sportsAdapter.setList(sportsList);
        binding.setSportsAdapter(sportsAdapter);
        Log.d("SportsList: ", temp + "");
    }


    public void showTechnologyList(@NonNull List<SamachaarArticle> technologyList) {
        int temp = technologyList.size();
        technologyAdapter.setList(technologyList);
        binding.setTechnologyAdapter(technologyAdapter);
        Log.d("TechnologyList: ", temp + "");
    }*/


    public void showLoading() {
        mProgressDialog.show();
    }


    public void hideLoading() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    public void showError(@NonNull String errorMessage) {
        Toast.makeText(this, "Error : " + errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /*@Override
    public void setSamachaarPresenter(AllSamachaarContract.Presenter SamachaarViewModel) {
        this.SamachaarViewModel = SamachaarViewModel;
    }*/
}
