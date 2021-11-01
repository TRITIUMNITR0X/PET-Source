package com.TritiumGaming.phasmophobiaevidencepicker.data.animations.data;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import androidx.annotation.NonNull;

/**
 * GhostWritingData class
 *
 * @author TritiumGamingStudios
 */
public class GhostWritingData extends Animated{

    /**
     *
     * @param screenW
     * @param screenH
     * @param bitmapW
     * @param bitmapH
     * @param animationData
     */
    public GhostWritingData(int screenW, int screenH, int bitmapW, int bitmapH, AnimationData animationData){
        super(screenW, screenH);

        MAX_ALPHA = 200;
        MAX_SIZE = 3;
        MIN_SIZE = 2;
        MAX_ROTATION = 45;
        MAX_TICK = 500;

        scale = (Math.random() * (MAX_SIZE - MIN_SIZE) + MIN_SIZE) * .1;

        setScale((Math.random() * (MAX_SIZE - MIN_SIZE) + MIN_SIZE) * .1);
        setRotation((float) (Math.random() * (MAX_ROTATION * 2) - MAX_ROTATION));
        setWidth(bitmapW);
        setHeight(bitmapH);
        setX();
        setY();

        setTickMax((int) ((Math.random() * (this.MAX_TICK - (this.MAX_TICK * .5))) + (this.MAX_TICK * .5)));

        animationData.setRotWriting(rotation);
    }

    /**
     *
     * @param tickMax
     */
    public void setTickMax(int tickMax){
        this.MAX_TICK = tickMax;
    }

    /**
     *
     */
    public void setX(){
        this.x = Math.random() * SCREENW;
        if(this.x+getScaledWidth() > SCREENW )
            this.x -= getScaledWidth();
        else if(this.x < getScaledWidth()*-1)
            this.x = 0;
    }

    /**
     *
     */
    public void setY(){
        this.y = Math.random() * SCREENH;
        if(this.y+getScaledHeight() > SCREENH )
            this.y -= getScaledHeight();
        else if(this.y < getScaledHeight()*-1)
            this.y = 0;
    }

    /**
     *
     * @param w
     */
    public void setWidth(double w){
        this.width = w;
    }

    /**
     *
     * @param h
     */
    public void setHeight(double h){
        this.height = h;
    }

    /**
     *
     * @param scale
     */
    public void setScale(double scale){
        this.scale = scale;
    }

    /**
     *
     * @return
     */
    public double getScaledWidth(){
        return this.scale * width;
    }

    /**
     *
     * @return
     */
    public double getScaledHeight(){
        return this.scale * height;
    }

    /**
     *
     */
    public void setRect(){
        r.set((int)x, (int)y, (int)(x+getScaledWidth()), (int)(y+getScaledHeight()));
    }

    /**
     * rotateBitmap
     *
     * Creates a rotated copy of the original Bitmap
     *
     * @param original- original Bitmap
     * @return new rotated Bitmap
     */
    public Bitmap rotateBitmap(Bitmap original) {
        int width = original.getWidth();
        int height = original.getHeight();
        Matrix matrix = new Matrix();
        matrix.preRotate(rotation);

        return Bitmap.createBitmap(original, 0, 0, width, height, matrix, true);
    }

    /**
     *
     */
    public void tick(){
        setRect();
        if(currentTick >= 0)
            currentTick += tickIncrementDirection;
        else
            isAlive = false;
        if(currentTick >= this.MAX_TICK)
            tickIncrementDirection *= -1;
        setAlpha();
    }

    /**
     *
     * @return
     */
    public PorterDuffColorFilter getFilter(){
        return new PorterDuffColorFilter(Color.argb(alpha, 100, 100, 100), PorterDuff.Mode.MULTIPLY);
    }

}
