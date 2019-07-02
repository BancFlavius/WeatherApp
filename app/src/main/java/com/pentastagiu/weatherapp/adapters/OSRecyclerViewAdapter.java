package com.pentastagiu.weatherapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pentastagiu.weatherapp.R;
import com.pentastagiu.weatherapp.holders.City;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class OSRecyclerViewAdapter extends RecyclerView.Adapter<OSRecyclerViewAdapter.OSViewHolder>{
    private final LayoutInflater inflater;
    private List<City> listItems;
    private Context mContext;

    public OSRecyclerViewAdapter(City[] listItems, Context mContext){
        this.listItems = new LinkedList<>(Arrays.asList(listItems));
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public OSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new OSViewHolder(inflater.inflate(R.layout.weather_city_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OSRecyclerViewAdapter.OSViewHolder osViewHolder, final int position) {
        final City rItem = listItems.get(position);

        osViewHolder.cityName.setText(rItem.getName());
        osViewHolder.description.setText(rItem.getWeather());
        osViewHolder.todayTemp.setText(rItem.getCurrentTemperature());
        osViewHolder.minTemp.setText(rItem.getMinimumTemperature());
        osViewHolder.maxTemp.setText(rItem.getMaximumTemperature());
        osViewHolder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItems.remove(position);
                notifyDataSetChanged();
                Toast.makeText(mContext, "Deleted", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    // Provide a reference to the views for each data item
    class OSViewHolder extends RecyclerView.ViewHolder {
        private final TextView description;
        private final TextView cityName;
        private final ImageView deleteItem;
        private final TextView todayTemp;
        private final TextView minTemp;
        private final TextView maxTemp;


        OSViewHolder(final View itemView) {
            super(itemView);
            this.description = itemView.findViewById(R.id.tv_description);
            this.cityName = itemView.findViewById(R.id.tv_cityName);
            this.deleteItem = itemView.findViewById(R.id.iv_deleteItem);
            this.todayTemp = itemView.findViewById(R.id.todayTemperature);
            this.minTemp = itemView.findViewById(R.id.minTemperature);
            this.maxTemp = itemView.findViewById(R.id.maxTemperature);

        }
    }
}
