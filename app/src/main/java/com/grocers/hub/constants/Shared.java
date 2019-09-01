package com.grocers.hub.constants;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.multidex.MultiDex;

/**
 * Created by ctel-cpu-50 on 6/1/2016.
 */
public class Shared extends Application {


    SharedPreferences preference;
    SharedPreferences.Editor editor;
    Context mContext;

    public Shared() {

    }

    public Shared(Context context) {
        // TODO Auto-generated constructor stub
        mContext = context;

        preference = mContext.getSharedPreferences("grocersHub", Context.MODE_PRIVATE);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    public void clearPreferences() {
        preference = mContext.getSharedPreferences("grocersHub", Context.MODE_PRIVATE);
        editor = preference.edit();
        editor.clear();
        editor.apply();
    }

    public String getCategoriesResponse() {

        return preference.getString("CategoriesResponse", "");
    }

    public void setCategoriesResponse(String CategoriesResponse) {
        editor = preference.edit();
        editor.putString("CategoriesResponse", CategoriesResponse);
        editor.commit();
    }

    public String getUserMobile() {

        return preference.getString("UserMobile", "");
    }

    public void setUserMobile(String UserMobile) {
        editor = preference.edit();
        editor.putString("UserMobile", UserMobile);
        editor.commit();
    }

    public String getUserName() {

        return preference.getString("UserName", "");
    }

    public void setUserName(String UserName) {
        editor = preference.edit();
        editor.putString("UserName", UserName);
        editor.commit();
    }

    public String getUserEmail() {

        return preference.getString("UserEmail", "");
    }

    public void setUserEmail(String UserEmail) {
        editor = preference.edit();
        editor.putString("UserEmail", UserEmail);
        editor.commit();
    }

    public String getUserLocation() {

        return preference.getString("UserLocation", "");
    }

    public void setUserLocation(String UserLocation) {
        editor = preference.edit();
        editor.putString("UserLocation", UserLocation);
        editor.commit();
    }

    public String getUserLatitude() {

        return preference.getString("UserLatitude", "");
    }

    public void setUserLatitude(String UserLatitude) {
        editor = preference.edit();
        editor.putString("UserLatitude", UserLatitude);
        editor.commit();
    }

    public String getUserLongitude() {

        return preference.getString("UserLongitude", "");
    }

    public void setUserLongitude(String UserLongitude) {
        editor = preference.edit();
        editor.putString("UserLongitude", UserLongitude);
        editor.commit();
    }

    public String getToken() {

        return preference.getString("Token", "");
    }

    public void setToken(String Token) {
        editor = preference.edit();
        editor.putString("Token", Token);
        editor.commit();
    }

    public String getUserFirstName() {
        return preference.getString("UserFirstName", "");
    }

    public void setUserFirstName(String UserFirstName) {
        editor = preference.edit();
        editor.putString("UserFirstName", UserFirstName);
        editor.commit();
    }

    public String getUserID() {
        return preference.getString("UserID", "");
    }

    public void setUserID(String UserID) {
        editor = preference.edit();
        editor.putString("UserID", UserID);
        editor.commit();
    }

    public String getUserLastName() {
        return preference.getString("UserLastName", "");
    }

    public void setUserLastName(String UserLastName) {
        editor = preference.edit();
        editor.putString("UserLastName", UserLastName);
        editor.commit();
    }


    public String getCity() {
        return preference.getString("City", "");
    }

    public void setCity(String City) {
        editor = preference.edit();
        editor.putString("City", City);
        editor.commit();
    }

    public String getZipCode() {
        return preference.getString("ZipCode", "");
    }

    public void setZipCode(String ZipCode) {
        editor = preference.edit();
        editor.putString("ZipCode", ZipCode);
        editor.commit();
    }

}

