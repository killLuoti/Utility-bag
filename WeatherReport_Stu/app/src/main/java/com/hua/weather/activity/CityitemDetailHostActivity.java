package com.hua.weather.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.hua.weather.activity.cityholder.CityholderContent;
import com.hua.weather.activity.cityholder.SimpleItemRecyclerViewAdapter;
import com.hua.weather.databinding.ActivityCityitemDetailBinding;
import com.hua.weather.R;
import com.hua.weather.databinding.FragmentCityitemListBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class CityitemDetailHostActivity extends AppCompatActivity {

     private boolean optionMenuOn = false;  //标示是否要显示optionmenu

    CharSequence search="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCityitemDetailBinding binding = ActivityCityitemDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_cityitem_detail);
        NavController navController = navHostFragment.getNavController();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.
                Builder(navController.getGraph())
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new checkOptionMenu().getamenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem item = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new     SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(CityitemDetailHostActivity.this,query,Toast.LENGTH_SHORT).show();
//                CityitemDetailFragment.gouniba = query;
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                SimpleItemRecyclerViewAdapter.getInstance(null, null).getFilter().filter(newText, new Filter.FilterListener() {
                    @Override
                    public void onFilterComplete(int count) {
                        Log.d("HHHH", "onFilterComplete: " + count);
                    }
                });
                search = newText;
                return false;
            }
        });
        return true;

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_cityitem_detail);
        checkOptionMenu.checkOptionMenu(true);
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}