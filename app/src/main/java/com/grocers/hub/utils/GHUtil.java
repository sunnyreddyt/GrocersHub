package com.grocers.hub.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.grocers.hub.R;
import com.grocers.hub.library.style.Circle;
import com.grocers.hub.models.CategoryModel;
import com.grocers.hub.models.ShippingResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by yakub on 08/10/15.
 */
public class GHUtil {

    public static GHUtil util;
    Context context;
    static Typeface lightFont;
    Typeface regularFont;
    Typeface mediumFont;
    private ProgressDialog progressdialog;
    private static final Pattern[] inputRegexes = new Pattern[4];
    Dialog progressDialog;
    Circle mCircleDrawable;

    private GHUtil(Context context) {
        this.context = context;
    }

    public static GHUtil getInstance(Context context) {
        if (util == null)
            util = new GHUtil(context);

        inputRegexes[0] = Pattern.compile(".*[A-Z].*");
        inputRegexes[1] = Pattern.compile(".*[a-z].*");
        inputRegexes[2] = Pattern.compile(".*\\d.*");
        inputRegexes[3] = Pattern.compile(".*[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?].*");

        return util;
    }

    //show progress dialog
    public void showProgressDialog(Activity act, String msg) {
        Log.d("Progress", "showProgressDialog");
        if (progressdialog != null)
            if (progressdialog.isShowing())
                progressdialog.dismiss();
        progressdialog = new ProgressDialog(act);
        progressdialog.setMessage(msg);
        /* progressdialog.setContentView(R.layout.pop_loadprogress);
        AnimatedGifImageView pGif = (AnimatedGifImageView) progressdialog.findViewById(R.id.viewGif);
        pGif.setAnimatedGif(R.raw.anim_loading, AnimatedGifImageView.TYPE.FIT_CENTER);*/
        progressdialog.setCancelable(false);
        progressdialog.setProgress(0);
        progressdialog.setProgressStyle(R.style.AppTheme);
        progressdialog.setIndeterminate(true);
        progressdialog.show();
    }

    //dismiss progress dialog
    public void dismissProgressDialog() {
        Log.d("Progress", "dismissProgressDialog");
        if (progressdialog != null)
            if (progressdialog.isShowing())
                progressdialog.dismiss();
    }

    public void getPermission() {
    }

    /*
     * Sets the font on all TextViews in the ViewGroup. Searches
     * recursively for all inner ViewGroups as well. Just add a
     * check for any other views you want to set as well (EditText,
     * etc.)
     */
    public void setFont(ViewGroup group, Typeface font) {
        int count = group.getChildCount();
        View v;
        for (int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if (v instanceof TextView || v instanceof Button /*etc.*/)
                ((TextView) v).setTypeface(font);
            else if (v instanceof ViewGroup)
                setFont((ViewGroup) v, font);
        }
    }

    public String toUpperCase(String inputString) {
        String result = "";
        for (int i = 0; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            char currentCharToUpperCase = Character.toUpperCase(currentChar);
            result = result + currentCharToUpperCase;
        }
        return result;
    }

    //hide keyboard
    public void hideKeyboard(Context ctx) {
        try {
            InputMethodManager inputManager = (InputMethodManager) ctx
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            // check if no view has focus:
            View v = ((Activity) ctx).getCurrentFocus();
            if (v == null)
                return;

            inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String toLowerCase(String inputString) {
        String result = "";
        for (int i = 0; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            char currentCharToLowerCase = Character.toLowerCase(currentChar);
            result = result + currentCharToLowerCase;
        }
        return result;
    }

    public String toToggleCase(String inputString) {
        String result = "";
        for (int i = 0; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            if (Character.isUpperCase(currentChar)) {
                char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result = result + currentCharToLowerCase;
            } else {
                char currentCharToUpperCase = Character.toUpperCase(currentChar);
                result = result + currentCharToUpperCase;
            }
        }
        return result;
    }

    public String toCamelCase(String inputString) {
        String result = "";
        if (inputString == null || inputString.length() == 0) {
            return result;
        }
        char firstChar = inputString.charAt(0);
        char firstCharToUpperCase = Character.toUpperCase(firstChar);
        result = result + firstCharToUpperCase;
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            char previousChar = inputString.charAt(i - 1);
            if (previousChar == ' ') {
                char currentCharToUpperCase = Character.toUpperCase(currentChar);
                result = result + currentCharToUpperCase;
            } else {
                char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result = result + currentCharToLowerCase;
            }
        }
        return result;
    }

    public static String toSentenceCase(String inputString) {
        String result = "";
        if (inputString.length() == 0) {
            return result;
        }
        char firstChar = inputString.charAt(0);
        char firstCharToUpperCase = Character.toUpperCase(firstChar);
        result = result + firstCharToUpperCase;
        boolean terminalCharacterEncountered = false;
        char[] terminalCharacters = {'.', '?', '!'};
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            if (terminalCharacterEncountered) {
                if (currentChar == ' ') {
                    result = result + currentChar;
                } else {
                    char currentCharToUpperCase = Character.toUpperCase(currentChar);
                    result = result + currentCharToUpperCase;
                    terminalCharacterEncountered = false;
                }
            } else {
                char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result = result + currentCharToLowerCase;
            }
            for (int j = 0; j < terminalCharacters.length; j++) {
                if (currentChar == terminalCharacters[j]) {
                    terminalCharacterEncountered = true;
                    break;
                }
            }
        }
        return result;
    }

    public boolean isConnectingToInternet() {

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }


    public static void overrideFonts(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            } else if (v instanceof TextView) {
                ((TextView) v).setTypeface(lightFont);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // ignore
        }
    }


    public String getIMEI() {
       /* TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
       return telephonyManager.getDeviceId();*/
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);//get mobile unique id
    }

    public String getVersion() {
//        PackageInfo pInfo = null;
        String version = "10"; // Changed to Version 10 for after 2.1 release.
        /*try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }*/
        return version;
    }

    public String getParseDeviceToken() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("parseDeviceToken", "");
    }

    public void saveParseDeviceToken(String parseDeviceToken) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("parseDeviceToken", parseDeviceToken);
        edit.commit();
        Log.d("GHUtil", "Device token saved");
    }

    public void setFirstTimeInstallation() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putBoolean("installed", true);
        edit.commit();
    }

    public boolean getIsFirstTimeInstallation() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        return preferences.getBoolean("installed", false);
    }

    public Typeface getRobotoEdittextFont() {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        return font;
    }

    public Typeface getRobotoButtontextFont() {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        return font;
    }





   /* public boolean signOut(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().remove("User").commit();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String value = preferences.getString("User",null);
        if (value == null) {
            try {
                ArrayList<RegisteredDevice> dEviceArrayList = (ArrayList) RegisteredDevice.listAll(RegisteredDevice.class);
                if(dEviceArrayList!=null && dEviceArrayList.size()>0) {
                    RegisteredDevice.deleteAll(RegisteredDevice.class);
                }
            }
            catch (Exception e) {
            }
            return true; // user signed out
        } else {
            return false; // user not signed out
        }
    }*/


   /* public User isUserLoggedin(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = preferences.getString("User", "");
        User user = gson.fromJson(json, User.class);
        return user;
    }

    public void loggedIn(User user) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user); // myObject - instance of MyObject
        prefsEditor.putString("User", json);
        prefsEditor.commit();
    }

    */

    public void setShippingResponse(ShippingResponse shippingResponse) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(shippingResponse); // myObject - instance of MyObject
        prefsEditor.putString("shippingResponse", json);
        prefsEditor.commit();
    }


    public ShippingResponse getShippingResponse() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = preferences.getString("shippingResponse", "");
        ShippingResponse shippingResponse = gson.fromJson(json, ShippingResponse.class);
        return shippingResponse;
    }

    public void setcategoryModel(CategoryModel categoryModel) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(categoryModel); // myObject - instance of MyObject
        prefsEditor.putString("categoryModel", json);
        prefsEditor.commit();
    }


    public CategoryModel getcategoryModel() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = preferences.getString("categoryModel", "");
        CategoryModel categoryModel = gson.fromJson(json, CategoryModel.class);
        return categoryModel;
    }

    public String getMobile() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        //Gson gson = new Gson();
        String mobile = preferences.getString("Mobile", null);
        return mobile;
    }

    public void setMobile(String mobile) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putString("Mobile", mobile);
        prefsEditor.commit();
    }

    public boolean getLogin() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        // Gson gson = new Gson();
        boolean value = preferences.getBoolean("Login", true);
        return value;
    }

    public void setLogin(Boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putBoolean("Login", value);
        prefsEditor.commit();
    }


    // validating email id
    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);

        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

        // return matcher.matches();
    }


    //by madhu
    public static boolean isValidEmail2(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public static boolean isPasswordValid(String input) {
        boolean inputMatches = true;
        for (Pattern inputRegex : inputRegexes) {
            if (!inputRegex.matcher(input).matches()) {
                inputMatches = false;
            }
        }
        return inputMatches;
    }

    public void showDialog(Activity act) {
        try {
            progressDialog = new Dialog(act, R.style.Theme_D1NoTitleDim);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            ProgressBar progressBar = progressDialog.findViewById(R.id.progress);

            mCircleDrawable = new Circle();
            mCircleDrawable.setBounds(0, 0, 30, 30);
            mCircleDrawable.setColor(Color.WHITE);
            progressBar.setIndeterminateDrawable(mCircleDrawable);
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissDialog() {
        try {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
