package com.hua.weather.activity.cityholder;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.constraintlayout.motion.widget.KeyTimeCycle;
import androidx.recyclerview.widget.RecyclerView;

import com.hua.weather.activity.CityitemListFragment;
import com.hua.weather.databinding.CityitemListContentBinding;
import com.hua.weather.databinding.FragmentCityitemListBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.hua.weather.activity.MainActivity.TAG;

public class SimpleItemRecyclerViewAdapter
        extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>  {

    private static SimpleItemRecyclerViewAdapter instance = null;
    private static List<CityholderContent.CityHolderItem> mValues2;
    private static View.OnClickListener mOnClickListener;
    private static View.OnContextClickListener mOnContextClickListener;
    private static List<CityholderContent.CityHolderItem> mValues;



    public  SimpleItemRecyclerViewAdapter(View.OnClickListener onClickListener,
                                         View.OnContextClickListener onContextClickListener) {

        mValues2  = CityholderContent.ITEMS;
        mValues = mValues2;
        mOnClickListener = onClickListener;
        mOnContextClickListener = onContextClickListener;
    }


    @Override
    public @NotNull ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        CityitemListContentBinding binding =
                CityitemListContentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // holder.tvCityName  设置城市名
        // holder.tvCityCode  设置城市码

        holder.tvCityName.setText(mValues.get(position).cityName);
        holder.tvCityCode.setText(mValues.get(position).cityCode);


        holder.itemView.setTag(mValues.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.itemView.setOnContextClickListener(mOnContextClickListener);
        }
    }



    @Override
    public int getItemCount() {
        return mValues.size();
    }
public static final class ViewHolder extends RecyclerView.ViewHolder {


        final TextView tvCityName;
        final TextView tvCityCode;
        List<String> moviesList = new ArrayList<>();
        List<String> moviesListAll;


        public ViewHolder(CityitemListContentBinding binding) {
            super(binding.getRoot());
            tvCityName = binding.tvCityName;
            tvCityCode = binding.tvCityCode;

        }


    }


    public  Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String Key = charSequence.toString();
//                Log.d(TAG, "performFiltering: "+ Key);
                List<CityholderContent.CityHolderItem> lstFiltered = new ArrayList<>();
                if (Key.isEmpty()) {
                    mValues = mValues2;
                } else {
                    for (int i = 0; i < getItemCount(); i++) {
//                         Log.d(TAG, "performFiltering: "+mValues.get(i).cityName);
                        if (mValues.get(i).cityName.contains(Key)) {
                            lstFiltered.add(mValues.get(i));
//                            Log.d(TAG, "performFiltering: " + mValues.get(i).cityName);
                        }
                    }
                    mValues = lstFiltered;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.count = mValues.size();
                filterResults.values = mValues;
//                Log.d(TAG, "performFiltering: "+lstFiltered);

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mValues = (List<CityholderContent.CityHolderItem>) filterResults.values;
//                Log.d(TAG, "publishResults: "+mValues);
                notifyDataSetChanged();
            }

        };

    }
    public static SimpleItemRecyclerViewAdapter getInstance(View.OnClickListener onClickListener,
                                                            View.OnContextClickListener onContextClickListener) {
        if (instance == null) {
            instance = new SimpleItemRecyclerViewAdapter(onClickListener,onContextClickListener);
        }
        return instance;
    }

}
