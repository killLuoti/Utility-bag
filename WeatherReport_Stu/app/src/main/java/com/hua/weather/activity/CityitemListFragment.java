package com.hua.weather.activity;

import android.os.Build;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hua.weather.R;
import com.hua.weather.activity.cityholder.CityholderContent;
import com.hua.weather.activity.cityholder.SimpleItemRecyclerViewAdapter;
import com.hua.weather.databinding.CityitemListContentBinding;
import com.hua.weather.databinding.FragmentCityitemListBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * A fragment representing a list of city_items. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link CityitemDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class CityitemListFragment extends Fragment {


    /**
     * Method to intercept global key events in the
     * item list fragment to trigger keyboard shortcuts
     * Currently provides a toast when Ctrl + Z and Ctrl + F
     * are triggered
     */
    ViewCompat.OnUnhandledKeyEventListenerCompat unhandledKeyEventListenerCompat = (v, event) -> {
        if (event.getKeyCode() == KeyEvent.KEYCODE_Z && event.isCtrlPressed()) {
            Toast.makeText(
                    v.getContext(),
                    "Undo (Ctrl + Z) shortcut triggered",
                    Toast.LENGTH_LONG
            ).show();
            return true;
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_F && event.isCtrlPressed()) {
            Toast.makeText(
                    v.getContext(),
                    "Find (Ctrl + F) shortcut triggered",
                    Toast.LENGTH_LONG
            ).show();
            return true;
        }
        return false;
    };

    private FragmentCityitemListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCityitemListBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onResume() {
        super.onResume();
        requireView();
    }


    RecyclerView recyclerView;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewCompat.addOnUnhandledKeyEventListener(view, unhandledKeyEventListenerCompat);
        recyclerView = binding.cityitemList;

        // Leaving this not using view binding as it relies on if the view is visible the current
        // layout configuration (layout, layout-sw600dp)
        View itemDetailFragmentContainer = view.findViewById(R.id.cityitem_detail_nav_container);

        /* Click Listener to trigger navigation based on if you have
         * a single pane layout or two pane layout
         */
        View.OnClickListener onClickListener = itemView -> {
            CityholderContent.CityHolderItem item =
                    (CityholderContent.CityHolderItem) itemView.getTag();
            Bundle arguments = new Bundle();
            Log.d("HHHH", "CityItemListFragmeng onViewCreated: "
                    + item.cityName + "cityCode " + item.cityCode);
            arguments.putString(CityitemDetailFragment.ARG_CITY_NAME, item.cityName);
            arguments.putString(CityitemDetailFragment.ARG_CITY_CODE, item.cityCode);
            if (itemDetailFragmentContainer != null) {
                Navigation.findNavController(itemDetailFragmentContainer)
                        .navigate(R.id.fragment_cityitem_detail, arguments);

            } else {
                Navigation.findNavController(itemView).navigate(R.id.show_cityitem_detail, arguments);
                checkOptionMenu.checkOptionMenu(false);
            }
        };

        /*
         * Context click listener to handle Right click events
         * from mice and trackpad input to provide a more native
         * experience on larger screen devices
         */
        View.OnContextClickListener onContextClickListener = itemView -> {
            CityholderContent.CityHolderItem item =
                    (CityholderContent.CityHolderItem) itemView.getTag();
            Toast.makeText(
                    itemView.getContext(),
                    "Context click of item " + item.cityName,
                    Toast.LENGTH_LONG
            ).show();
            return true;
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(new MainActivity(), RecyclerView.VERTICAL, false));
        setupRecyclerView(recyclerView, onClickListener, onContextClickListener);
//        new CityitemDetailHostActivity().setUserRecycler(CityholderContent.ITEMS, onClickListener, onContextClickListener);

    }

    public void setupRecyclerView(
            RecyclerView recyclerView,
            View.OnClickListener onClickListener,
            View.OnContextClickListener onContextClickListener
    ) {
        recyclerView.setAdapter(  SimpleItemRecyclerViewAdapter.getInstance(

                onClickListener,
                onContextClickListener
        ));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }





}