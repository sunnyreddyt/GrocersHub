package com.grocers.hub.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.grocers.hub.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by yakub on 08/10/15.
 */
public class ABUtil {

    public static ABUtil util;
    Context context;
    static Typeface lightFont;
    Typeface regularFont;
    Typeface mediumFont;
    private ProgressDialog progressdialog;

    private ABUtil(Context context) {

        this.context = context;


    }

    public static ABUtil getInstance(Context context) {
        if (util == null)
            util = new ABUtil(context);

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
        Log.d("ABUtil", "Device token saved");
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

    public void setCompaniesList(CompaniesListResponse companiesListResponse) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(companiesListResponse); // myObject - instance of MyObject
        prefsEditor.putString("companiesListResponse", json);
        prefsEditor.commit();
    }


    public CompaniesListResponse getCompaniesList(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = preferences.getString("companiesListResponse", "");
        CompaniesListResponse companiesListResponse = gson.fromJson(json, CompaniesListResponse.class);
        return companiesListResponse;
    }*/


   /* public void setCitiesList(GetSectorsResponse getCitiesResponse) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(getCitiesResponse); // myObject - instance of MyObject
        prefsEditor.putString("getCitiesResponse", json);
        prefsEditor.commit();
    }


    public GetSectorsResponse getCitiesList(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = preferences.getString("getCitiesResponse", "");
        GetSectorsResponse getCitiesResponse = gson.fromJson(json, GetSectorsResponse.class);
        return getCitiesResponse;
    }*/

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


  /*  public void showAdvertisement(Context con, String fileType) {

try {
    final Dialog dialog = new Dialog(con, R.style.Theme_D1NoTitleDim);
    //  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(R.layout.adv_popup);
    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


    //  fileType = "video";
    JWPlayerView mPlayerView;
    JWEventHandler mEventHandler;
    RelativeLayout jwPlayerLayout, viewpagerLayout;
    final LinearLayout sector_llDots;
    final ViewPagerAdapter viewPagerAdapter;
    AutoScrollViewPager autoScrollViewPager;
    int resources[] = {R.drawable.homedecorbig, R.drawable.homedecordown, R.drawable.homedecorup};

    mPlayerView = (JWPlayerView) dialog.findViewById(R.id.jwplayer);
    jwPlayerLayout = (RelativeLayout) dialog.findViewById(R.id.jwPlayerLayout);
    viewpagerLayout = (RelativeLayout) dialog.findViewById(R.id.viewpagerLayout);
    autoScrollViewPager = (AutoScrollViewPager) dialog.findViewById(R.id.sector_pager);
    sector_llDots = (LinearLayout) dialog.findViewById(R.id.sector_llDots);

    if (fileType.equals("image")) {

        viewpagerLayout.setVisibility(View.VISIBLE);
        jwPlayerLayout.setVisibility(View.GONE);

    } else if (fileType.equals("video")) {

        viewpagerLayout.setVisibility(View.GONE);
        jwPlayerLayout.setVisibility(View.VISIBLE);

    }

    new KeepScreenOnHandler(mPlayerView, dialog.getWindow());
    // mEventHandler = new JWEventHandler(mPlayerView, outputTextView);

    //viewpager setting adapter
    viewPagerAdapter = new ViewPagerAdapter(context, resources);
    autoScrollViewPager.setAdapter(viewPagerAdapter);
    autoScrollViewPager.startAutoScroll();
    autoScrollViewPager.getInterval();


    for (int in = 0; in < viewPagerAdapter.getCount(); in++) {
        ImageButton imgDot = new ImageButton(context);
        imgDot.setTag(in);
        imgDot.setImageResource(R.drawable.dot_selector);
        imgDot.setBackgroundResource(0);
        imgDot.setPadding(2, 2, 2, 2);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
        imgDot.setLayoutParams(params);
        if (in == 0)
            imgDot.setSelected(true);

        sector_llDots.addView(imgDot);
    }

    autoScrollViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int pos) {
            Log.e("", "Page Selected is ===> " + pos);
            for (int i = 0; i < viewPagerAdapter.getCount(); i++) {
                if (i != pos) {
                    ((ImageView) sector_llDots.findViewWithTag(i)).setSelected(false);
                }
            }
            ((ImageView) sector_llDots.findViewWithTag(pos)).setSelected(true);
        }

        @Override
        public void onPageScrolled(int pos, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    });

    PlaylistItem pi = new PlaylistItem.Builder()
            .file("http://10.10.10.215/DT/HappyGiffy/happygiffy/backend/web/assets/images/marketing/video/20170214_114856.mp4")
            .title("BipBop")
            .description("A video player testing video.")
            .build();
    mPlayerView.load(pi);
    dialog.show();
}
catch (Exception e){}
    }
*/
}
