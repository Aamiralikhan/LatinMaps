package com.latinmaps.app.interfaces;

/**
 * Created by Administrator on 1/18/2016.
 */
public interface DrawableClickListener {
    public static enum DrawablePosition { TOP, BOTTOM, LEFT, RIGHT };
    public void onClick(DrawablePosition target);
}
