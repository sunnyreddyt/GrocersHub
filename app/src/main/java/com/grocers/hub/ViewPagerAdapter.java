package com.grocers.hub;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.grocers.hub.constants.Constants;
import com.grocers.hub.models.HomeResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<HomeResponse> categoryProducts;

    public ViewPagerAdapter(Context context,
                            ArrayList<HomeResponse> categoryProducts) {
        this.context = context;
        this.categoryProducts = categoryProducts;
    }

    @Override
    public int getCount() {
        return categoryProducts.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.viewpager_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

        Picasso.get().load(Constants.BANNER_IMAGE__BASE_URL + categoryProducts.get(position).getBannerimage()).into(imageView);

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}