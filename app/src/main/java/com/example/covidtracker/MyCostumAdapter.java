package com.example.covidtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MyCostumAdapter extends ArrayAdapter<CountryModel> {

    private final Context context;
    private final List<CountryModel> countryModelList;
    private List<CountryModel> countryModelListfilter;



    public MyCostumAdapter(@NonNull Context context, List<CountryModel> countryModelList) {
        super(context, R.layout.listcostumitem,countryModelList);

        this.context = context;
        this.countryModelList = countryModelList;
        this.countryModelListfilter = countryModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listcostumitem,null,true);
        TextView tvCountryName = view.findViewById(R.id.tvCountryName);
        ImageView imageView = view.findViewById(R.id.imageFlag);

        tvCountryName.setText(countryModelListfilter.get(position).getCountry());
        Glide.with(context).load(countryModelListfilter.get(position).getFlag()).into(imageView);

        return view;
    }

    @Override
    public int getCount() {
        return countryModelListfilter.size();
    }

    @Nullable
    @Override
    public CountryModel getItem(int position) {
        return countryModelListfilter.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // This code is all for filter module

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint==null || constraint.length()==0) {
                    filterResults.count = countryModelList.size();
                    filterResults.values = countryModelList;
                }else {
                    List<CountryModel> resultModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for (CountryModel itemsModel:countryModelList){
                        if (itemsModel.getCountry().toLowerCase().contains(searchStr)){
                            resultModel.add(itemsModel);
                        }
                        filterResults.count = resultModel.size();
                        filterResults.values = resultModel;
                }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                countryModelListfilter = (List<CountryModel>) results.values;
                AffectedCountries.countryModelList = (List<CountryModel>) results.values;
                notifyDataSetChanged();


            }
        };
    }
}
