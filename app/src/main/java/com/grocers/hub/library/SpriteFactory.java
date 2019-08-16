package com.grocers.hub.library;

import com.grocers.hub.library.sprite.Sprite;
import com.grocers.hub.library.style.Circle;

/**
 * Created by ybq.
 */
public class SpriteFactory {

    public static Sprite create(Style style) {
        Sprite sprite = null;
        switch (style) {
            case CIRCLE:
                sprite = new Circle();
                break;

            default:
                break;
        }
        return sprite;
    }
}
