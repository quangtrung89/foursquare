package com.trung.foursquare.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.trung.foursquare.R;
import com.trung.foursquare.data.model.Venue;
import com.trung.foursquare.helper.ViewHelper;

import java.util.List;

class VenueAdapter extends BaseAdapter{
    private List<Venue> mData;
    private Context mContext;

    VenueAdapter(Context context, List<Venue> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        if(mData == null) {
            return 0;
        }
        return mData.size();
    }

    @Override
    public Venue getItem(int i) {
        if(mData == null) {
            return null;
        }
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if(view == null) {
            view = View.inflate(mContext, R.layout.item_venue, null);
            holder = new Holder();
            holder.TvName = (TextView) view.findViewById(R.id.tv_venue_name);
            holder.TvAddress = (TextView) view.findViewById(R.id.tv_venue_address);
            holder.TvDistance = (TextView) view.findViewById(R.id.tv_venue_distance);
            view.setTag(holder);
        } else {
            holder = (Holder)view.getTag();
        }
        Venue venue = mData.get(i);
        holder.TvName.setText(venue.getName());
        if(TextUtils.isEmpty(venue.getAddress())) {
            holder.TvAddress.setVisibility(View.GONE);
        } else {
            holder.TvAddress.setVisibility(View.VISIBLE);
            holder.TvAddress.setText(venue.getAddress());
        }
        holder.TvDistance.setText(ViewHelper.formatDistance(mContext.getResources(), venue.getDistance()));
        return view;
    }

    private static class Holder {
        TextView TvName;
        TextView TvAddress;
        TextView TvDistance;
    }
}
