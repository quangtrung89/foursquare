package com.trung.foursquare.view;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.trung.foursquare.R;
import com.trung.foursquare.data.interaction.VenuesInteractionImpl;
import com.trung.foursquare.data.model.Venue;
import com.trung.foursquare.helper.PermissionHelper;
import com.trung.foursquare.presenter.MainPresenter;
import com.trung.foursquare.presenter.MainPresenterImpl;
import com.trung.foursquare.service.location.LocationService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {
    private VenueAdapter mVenuesAdapter;
    private List<Venue> mData;
    private Location mCurrentLocation;

    private MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionHelper.checkLocationPermission(this);
        mData = new ArrayList<>();
        mVenuesAdapter = new VenueAdapter(this, mData);
        mMainPresenter = new MainPresenterImpl(this, new VenuesInteractionImpl());

        EditText edtSearch = (EditText)findViewById(R.id.edt_search);
        ListView listView = (ListView)findViewById(R.id.lv_venues);
        listView.setAdapter(mVenuesAdapter);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(TextUtils.isEmpty(charSequence)) {
                    return;
                }
                if(mCurrentLocation == null) {
                    Toast.makeText(getApplicationContext(), R.string.msg_check_location, Toast.LENGTH_SHORT).show();
                    PermissionHelper.checkLocationPermission(MainActivity.this);
                    mCurrentLocation = LocationService.getInstance().getLocation();
                    return;
                }
                mMainPresenter.search(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void showData(List<Venue> data) {
        mData.clear();
        if(data == null || data.isEmpty()) {
            Toast.makeText(this, R.string.msg_no_data, Toast.LENGTH_SHORT).show();
        } else {
            mData.addAll(data);
        }
        mVenuesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PermissionHelper.REQUEST_PERMISSIONS_LOCATION) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mCurrentLocation = LocationService.getInstance().getLocation();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();
    }
}
