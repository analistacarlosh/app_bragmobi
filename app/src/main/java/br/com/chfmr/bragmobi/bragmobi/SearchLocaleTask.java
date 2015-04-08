package br.com.chfmr.bragmobi.bragmobi;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.util.List;
import java.util.Locale;

/**
 * Created by carlosfm on 4/1/15.
 */
public class SearchLocaleTask extends AsyncTaskLoader<List<Address>> {

    Context mContext;
    String mLocale;
    List<Address> mAddressSearched;

    public SearchLocaleTask(Context activity, String locale){

        super(activity);
        mContext = activity;
        mLocale = locale;
    }

    @Override
    protected void onStartLoading(){
        if(mAddressSearched == null){
            forceLoad();
        } else {
            deliverResult(mAddressSearched);
        }
    }

    @Override
    public List<Address> loadInBackground(){

        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());

        try{
            mAddressSearched = geocoder.getFromLocationName(mLocale, 10);
        } catch (Exception e){
            e.printStackTrace();
        }

        return mAddressSearched;
    }
}
