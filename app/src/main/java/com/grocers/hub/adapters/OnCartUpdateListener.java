package com.grocers.hub.adapters;

import com.grocers.hub.models.CartResponse;

public interface OnCartUpdateListener {

    public void onCartUpdate(CartResponse cartResponse, String type, int count);
}
