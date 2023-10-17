package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.titlescreen.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.titlescreen.data.animations.AbstractAnimatedGraphic;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.titlescreen.data.animations.AnimatedGraphicQueue;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.titlescreen.data.animations.graphicsdata.AnimatedFrostData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.titlescreen.data.animations.graphicsdata.AnimatedGraphicData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.titlescreen.data.animations.graphicsdata.AnimatedHandData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.titlescreen.data.animations.graphicsdata.AnimatedMirrorData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.titlescreen.data.animations.graphicsdata.AnimatedOrbData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.titlescreen.data.animations.graphicsdata.AnimatedWritingData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.BitmapUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.TitlescreenViewModel;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * TitleScreenAnimationView class
 *
 * @author TritiumGamingStudios
 */
public class TitlescreenAnimationView extends View {

    private TitlescreenViewModel titleScreenViewModel = null;
    private BitmapUtils bitmapUtils = null;

    private ArrayList<Integer> bookwritingResId = new ArrayList<>();
    private ArrayList<Integer> handuvResId = new ArrayList<>();

    private final int
            screenW = Resources.getSystem().getDisplayMetrics().widthPixels,
            screenH = Resources.getSystem().getDisplayMetrics().heightPixels;

    private final Paint paint = new Paint();
    private Bitmap
            bitmap_orb = null, bitmap_hand = null, bitmap_writing = null,
            bitmap_frost = null, bitmap_mirror = null,
            bitmap_handRot = null, bitmap_writingRot = null;

    /**
     * @param context The parent Context
     */
    public TitlescreenAnimationView(Context context) {
        super(context);

        initView();
    }

    /**
     * @param context The parent Context
     * @param attrs The attributes given on init
     */
    public TitlescreenAnimationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initView();
    }

    /**
     * @param context The parent Context
     * @param attrs The attributes given on init
     * @param defStyleAttr The style attributes given on init
     */
    public TitlescreenAnimationView(
            Context context,
            @Nullable AttributeSet attrs,
            int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    public void initView() {

    }

    /**
     * @param titleScreenViewModel The TitleScreenViewModel which contains necessary Animation data
     * @param bitmapUtils The BitmapUtils data which is used across all animations
     */
    public void init(
            TitlescreenViewModel titleScreenViewModel,
            BitmapUtils bitmapUtils) {
        this.titleScreenViewModel = titleScreenViewModel;
        this.bitmapUtils = bitmapUtils;

        //Set writing resources
        TypedArray bookwritingArray =
                getResources().obtainTypedArray(R.array.anim_bookwriting_images);
        bookwritingResId = new ArrayList<>();
        for (int i = 0; i < bookwritingArray.length(); i++) {
            bookwritingResId.add(bookwritingArray.getResourceId(i, 0));
        }
        bookwritingArray.recycle();

        //Set writing resources
        TypedArray handUVArray =
                getResources().obtainTypedArray(R.array.anim_hand_images);
        handuvResId = new ArrayList<>();
        for (int i = 0; i < handUVArray.length(); i++) {
            handuvResId.add(handUVArray.getResourceId(i, 0));
        }
        handUVArray.recycle();

        if (titleScreenViewModel != null &&
                titleScreenViewModel.getAnimationData().getSelectedWriting() == -1) {
            titleScreenViewModel.getAnimationData().
                    setSelectedWriting((int) (Math.random() * bookwritingResId.size()));
        }

        if (titleScreenViewModel != null &&
                titleScreenViewModel.getAnimationData().getSelectedHand() == -1) {
            titleScreenViewModel.getAnimationData().
                    setSelectedHand((int) (Math.random() * handuvResId.size()));
        }

        buildImages();
        buildData();
    }

    /**
     *
     */
    public void buildImages() {
        bitmap_orb = bitmapUtils.setResource(R.drawable.anim_ghostorb).
                compileBitmaps(getContext());
        bitmap_frost = bitmapUtils.setResource(R.drawable.anim_frost).
                compileBitmaps(getContext());
        bitmap_mirror = bitmapUtils.setResource(R.drawable.anim_cracked).
                compileBitmaps(getContext());
        /*bitmap_hand = bitmapUtils.setResource(R.drawable.anim_hand).
                compileBitmaps(getContext());*/
        bitmap_hand = bitmapUtils.setResource(
                handuvResId.get(titleScreenViewModel.getAnimationData().getSelectedHand())).
                compileBitmaps(getContext());
        bitmap_writing = bitmapUtils.setResource(
                bookwritingResId.get(titleScreenViewModel.getAnimationData().getSelectedWriting())).
                compileBitmaps(getContext());
    }

    /**
     *
     */
    public void buildData() {

        if (titleScreenViewModel != null) {
            AnimatedGraphicData animationData = titleScreenViewModel.getAnimationData();

            if (animationData.hasData()) {
                for (AbstractAnimatedGraphic animated : animationData.getAllPool()) {
                    if (animated instanceof AnimatedHandData a) {
                        if (BitmapUtils.bitmapExists(bitmap_hand)) {
                            bitmap_handRot = a.rotateBitmap(bitmap_hand);
                        }
                    } else if (animated instanceof AnimatedWritingData a) {
                        if (BitmapUtils.bitmapExists(bitmap_writing)) {
                            bitmap_writingRot = a.rotateBitmap(bitmap_writing);
                        }
                    }
                }
                return;
            }

            short ORB_COUNT = 3, HAND_COUNT = 1,  WRITING_COUNT = 1,
                    MIRROR_COUNT = 1, FROST_COUNT = 1;

            //Add orbs
            for (int i = 0; i < ORB_COUNT; i++) {
                if (BitmapUtils.bitmapExists(bitmap_orb)) {
                    animationData.addToAllPool(new AnimatedOrbData(
                            screenW,
                            screenH));
                }
            }
            //Add hands
            for (int i = 0; i < HAND_COUNT; i++) {
                if (BitmapUtils.bitmapExists(bitmap_hand)) {
                    int bW = bitmap_hand.getWidth();
                    int bH = bitmap_hand.getHeight();
                    animationData.addToAllPool(new AnimatedHandData(
                            screenW,
                            screenH,
                            bW,
                            bH));
                    bitmap_handRot = ((AnimatedHandData) animationData.getLastFromAllPool()).
                            rotateBitmap(bitmap_hand);
                }
            }

            //Add writing
            for (int i = 0; i < WRITING_COUNT; i++) {
                if (BitmapUtils.bitmapExists(bitmap_writing)) {
                    int bW = bitmap_writing.getWidth();
                    int bH = bitmap_writing.getHeight();
                    animationData.addToAllPool(new AnimatedWritingData(
                            screenW,
                            screenH,
                            bW,
                            bH,
                            animationData));
                    bitmap_writingRot = ((AnimatedWritingData) animationData.
                            getLastFromAllPool()).rotateBitmap(bitmap_writing);
                }
            }
            //Add Frost
            for (int i = 0; i < FROST_COUNT; i++) {
                if (BitmapUtils.bitmapExists(bitmap_frost)) {
                    animationData.addToAllPool(
                            new AnimatedFrostData(
                            screenW,
                            screenH));
                }
            }
            //Add Mirror
            for (int i = 0; i < MIRROR_COUNT; i++) {
                if (BitmapUtils.bitmapExists(bitmap_mirror)) {
                    animationData.addToAllPool(
                            new AnimatedMirrorData(
                                    screenW,
                                    screenH));
                }
            }
            //Create Queue
            animationData.setQueue(
                    new AnimatedGraphicQueue(animationData.getAllPoolSize(), 750));
        }
    }

    /**
     *
     */
    public void tick() {

        if (titleScreenViewModel == null)
            return;

        AnimatedGraphicData animationData = titleScreenViewModel.getAnimationData();
        animationData.tick();

        int maxQueue = 3;
        if ((animationData.hasQueue() && animationData.getQueue().canDequeue()) &&
                animationData.getCurrentPoolSize() < maxQueue) {

            AnimatedGraphicQueue animationQueue = animationData.getQueue();

            int index = 0;
            AbstractAnimatedGraphic aTemp = null;
            try {
                index = animationQueue.dequeue();
                aTemp = animationData.getFromAllPool(index);
                animationData.addToCurrentPool(aTemp);
            } catch (IndexOutOfBoundsException e) {
                animationQueue.enqueue(index);
                e.printStackTrace();
            }
            if (aTemp != null) {
                AbstractAnimatedGraphic lastAnimInList = animationData.getLastFromCurrentPool();
                if (lastAnimInList != null) {
                    int frameW = getWidth();
                    int frameH = getHeight();

                    if (lastAnimInList instanceof AnimatedOrbData) {
                        if (BitmapUtils.bitmapExists(bitmap_orb)) {
                            animationData.setToAllPool(index, new AnimatedOrbData(
                                    frameW,
                                    frameH));
                        }
                    } else if (lastAnimInList instanceof AnimatedHandData) {
                        if (BitmapUtils.bitmapExists(bitmap_hand)) {

                            int bitmapW = 0;
                            int bitmapH = 0;
                            try {
                                bitmapW = bitmap_hand.getWidth();
                                bitmapH = bitmap_hand.getHeight();
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }

                            animationData.setToAllPool(index, new AnimatedHandData(
                                    frameW,
                                    frameH,
                                    bitmapW,
                                    bitmapH
                            ));

                            bitmap_handRot = ((AnimatedHandData) animationData.
                                    getLastFromCurrentPool()).rotateBitmap(bitmap_hand);
                        }
                    } else if (lastAnimInList instanceof AnimatedWritingData) {
                        if (BitmapUtils.bitmapExists(bitmap_writing)) {

                            int bitmapW = 0;
                            int bitmapH = 0;
                            try {
                                bitmapW = bitmap_writing.getWidth();
                                bitmapH = bitmap_writing.getHeight();
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }

                            animationData.setToAllPool(index, new AnimatedWritingData(
                                    frameW,
                                    frameH,
                                    bitmapW,
                                    bitmapH,
                                    animationData
                            ));

                            bitmap_writingRot = ((AnimatedWritingData) animationData.
                                    getLastFromCurrentPool()).rotateBitmap(bitmap_writing);
                        }
                    } else if (lastAnimInList instanceof AnimatedFrostData) {
                        if (BitmapUtils.bitmapExists(bitmap_frost)) {
                            animationData.setToAllPool(index, new AnimatedFrostData(
                                    frameW,
                                    frameH
                            ));
                        }
                    } else if (lastAnimInList instanceof AnimatedMirrorData) {
                        if (BitmapUtils.bitmapExists(bitmap_mirror)) {
                            animationData.setToAllPool(index, new AnimatedMirrorData(
                                    frameW,
                                    frameH
                            ));
                        }
                    }
                }
            }
        }

        for (int i = 0; i < animationData.getCurrentPoolSize(); i++) {
            AbstractAnimatedGraphic currentAnim = animationData.getFromCurrentPool(i);
            if (currentAnim != null) {
                currentAnim.tick();

                /*
                 * If the chosen Animated is not alive
                 * remove it from the list
                 * Replace it with a modified item of the same type
                 * Try the next Animated
                 */
                if (!currentAnim.isAlive()) {
                    if (currentAnim instanceof AnimatedHandData data) {
                        animationData.setSelectedHand(
                                (int) (Math.random() * handuvResId.size()));

                        BitmapUtils.destroyBitmap(bitmap_hand);
                        BitmapUtils.destroyBitmap(bitmap_handRot);

                        bitmapUtils.setResource(
                                handuvResId.get(animationData.getSelectedHand()));
                        bitmap_hand = bitmapUtils.compileBitmaps(getContext());

                        if (BitmapUtils.bitmapExists(bitmap_hand)) {
                            bitmap_handRot = data.rotateBitmap(bitmap_hand);
                        }

                        /*
                        AnimatedHandData data = ((AnimatedHandData) currentAnim);
                        BitmapUtils.destroyBitmap(bitmap_handRot);
                        if (BitmapUtils.bitmapExists(bitmap_hand)) {
                            bitmap_handRot = data.rotateBitmap(bitmap_hand);
                        }
                        */
                    } else if (currentAnim instanceof AnimatedWritingData data) {
                        animationData.setSelectedWriting(
                                (int) (Math.random() * bookwritingResId.size()));

                        BitmapUtils.destroyBitmap(bitmap_writing);
                        BitmapUtils.destroyBitmap(bitmap_writingRot);

                        bitmapUtils.setResource(
                                bookwritingResId.get(animationData.getSelectedWriting()));
                        bitmap_writing = bitmapUtils.compileBitmaps(getContext());

                        if (BitmapUtils.bitmapExists(bitmap_writing)) {
                            bitmap_writingRot = data.rotateBitmap(bitmap_writing);
                        }
                    }
                    try {
                        animationData.removeFromCurrentPool(currentAnim);
                        i--;
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }

                    System.gc();
                }
            }
        }
    }

    /**
     * @param canvas The cavas
     */
    @Override
    protected void onDraw(Canvas canvas) {

        if (titleScreenViewModel == null) {
            return;
        }

        super.onDraw(canvas);

        paint.setStyle(Paint.Style.FILL);

        try {
            for (AbstractAnimatedGraphic a : titleScreenViewModel.getAnimationData().getCurrentPool()) {
                if (a != null) {
                    paint.setColorFilter(a.getFilter());
                    if (a instanceof AnimatedMirrorData) {
                        a.draw(canvas, paint, bitmap_mirror);
                    } else if (a instanceof AnimatedWritingData) {
                        a.draw(canvas, paint, bitmap_writingRot);
                    } else if (a instanceof AnimatedHandData) {
                        a.draw(canvas, paint, bitmap_handRot);
                    } else if (a instanceof AnimatedOrbData) {
                        a.draw(canvas, paint, bitmap_orb);
                    } else if (a instanceof AnimatedFrostData) {
                        a.draw(canvas, paint, bitmap_frost);
                    }
                }
            }
        } catch (ConcurrentModificationException ex) {
            ex.printStackTrace();
        }

    }

    /**
     *
     */
    public void recycleBitmaps() {
        BitmapUtils.destroyBitmap(bitmap_orb);
        BitmapUtils.destroyBitmap(bitmap_frost);
        BitmapUtils.destroyBitmap(bitmap_hand);
        BitmapUtils.destroyBitmap(bitmap_writing);
        BitmapUtils.destroyBitmap(bitmap_mirror);
        BitmapUtils.destroyBitmap(bitmap_handRot);
        BitmapUtils.destroyBitmap(bitmap_writingRot);

        bitmap_orb = null;
        bitmap_frost = null;
        bitmap_hand = null;
        bitmap_writing = null;
        bitmap_mirror = null;
        bitmap_handRot = null;
        bitmap_writingRot = null;

        System.gc();
    }

    /*
    *//**
     * @param titleScreenViewModel The TitleScreenViewModel which contains necessary Animation data
     * @param bitmapUtils The BitmapUtils data which is used across all animations
     *//*
    public void init(
            TitlescreenViewModel titleScreenViewModel,
            BitmapUtils bitmapUtils) {
        this.titleScreenViewModel = titleScreenViewModel;
        this.bitmapUtils = bitmapUtils;

        //Set writing resources
        TypedArray bookwritingArray =
                getResources().obtainTypedArray(R.array.anim_bookwriting_images);
        bookwritingResId = new ArrayList<>();
        for (int i = 0; i < bookwritingArray.length(); i++) {
            bookwritingResId.add(bookwritingArray.getResourceId(i, 0));
        }
        bookwritingArray.recycle();

        //Set writing resources
        TypedArray handUVArray =
                getResources().obtainTypedArray(R.array.anim_hand_images);
        handuvResId = new ArrayList<>();
        for (int i = 0; i < handUVArray.length(); i++) {
            handuvResId.add(handUVArray.getResourceId(i, 0));
        }
        handUVArray.recycle();

        if (titleScreenViewModel != null &&
                titleScreenViewModel.getAnimationData().getSelectedWriting() == -1) {
            titleScreenViewModel.getAnimationData().
                    setSelectedWriting((int) (Math.random() * bookwritingResId.size()));
        }

        if (titleScreenViewModel != null &&
                titleScreenViewModel.getAnimationData().getSelectedHand() == -1) {
            titleScreenViewModel.getAnimationData().
                    setSelectedHand((int) (Math.random() * handuvResId.size()));
        }

        buildImages();
        buildData();
    }

    *//**
     *
     *//*
    public void buildImages() {
        bitmap_orb = bitmapUtils.setResource(R.drawable.anim_ghostorb).
                compileBitmaps(getContext());
        bitmap_frost = bitmapUtils.setResource(R.drawable.anim_frost).
                compileBitmaps(getContext());
        bitmap_mirror = bitmapUtils.setResource(R.drawable.anim_cracked).
                compileBitmaps(getContext());
        *//*bitmap_hand = bitmapUtils.setResource(R.drawable.anim_hand).
                compileBitmaps(getContext());*//*
        bitmap_hand = bitmapUtils.setResource(
                handuvResId.get(titleScreenViewModel.getAnimationData().getSelectedHand())).
                compileBitmaps(getContext());
        bitmap_writing = bitmapUtils.setResource(
                bookwritingResId.get(titleScreenViewModel.getAnimationData().getSelectedWriting())).
                compileBitmaps(getContext());
    }

    *//**
     *
     *//*
    public void buildData() {

        if (titleScreenViewModel != null) {
            AnimatedGraphicData animationData = titleScreenViewModel.getAnimationData();

            if (animationData.hasData()) {
                for (AbstractAnimatedGraphic animated : animationData.getAllPool()) {
                    if (animated instanceof AnimatedHandData a) {
                        if (BitmapUtils.bitmapExists(bitmap_hand)) {
                            bitmap_handRot = a.rotateBitmap(bitmap_hand);
                        }
                    } else if (animated instanceof AnimatedWritingData a) {
                        if (BitmapUtils.bitmapExists(bitmap_writing)) {
                            bitmap_writingRot = a.rotateBitmap(bitmap_writing);
                        }
                    }
                }
                return;
            }

            short ORB_COUNT = 3, HAND_COUNT = 1,  WRITING_COUNT = 1,
                    MIRROR_COUNT = 1, FROST_COUNT = 1;

            //Add orbs
            for (int i = 0; i < ORB_COUNT; i++) {
                if (BitmapUtils.bitmapExists(bitmap_orb)) {
                    animationData.addToAllPool(new AnimatedOrbData(
                            screenW,
                            screenH));
                }
            }
            //Add hands
            for (int i = 0; i < HAND_COUNT; i++) {
                if (BitmapUtils.bitmapExists(bitmap_hand)) {
                    int bW = bitmap_hand.getWidth();
                    int bH = bitmap_hand.getHeight();
                    animationData.addToAllPool(new AnimatedHandData(
                            screenW,
                            screenH,
                            bW,
                            bH));
                    bitmap_handRot = ((AnimatedHandData) animationData.getLastFromAllPool()).
                            rotateBitmap(bitmap_hand);
                }
            }

            //Add writing
            for (int i = 0; i < WRITING_COUNT; i++) {
                if (BitmapUtils.bitmapExists(bitmap_writing)) {
                    int bW = bitmap_writing.getWidth();
                    int bH = bitmap_writing.getHeight();
                    animationData.addToAllPool(new AnimatedWritingData(
                            screenW,
                            screenH,
                            bW,
                            bH,
                            animationData));
                    bitmap_writingRot = ((AnimatedWritingData) animationData.
                            getLastFromAllPool()).rotateBitmap(bitmap_writing);
                }
            }
            //Add Frost
            for (int i = 0; i < FROST_COUNT; i++) {
                if (BitmapUtils.bitmapExists(bitmap_frost)) {
                    animationData.addToAllPool(
                            new AnimatedFrostData(
                            screenW,
                            screenH));
                }
            }
            //Add Mirror
            for (int i = 0; i < MIRROR_COUNT; i++) {
                if (BitmapUtils.bitmapExists(bitmap_mirror)) {
                    animationData.addToAllPool(
                            new AnimatedMirrorData(
                                    screenW,
                                    screenH));
                }
            }
            //Create Queue
            animationData.setQueue(
                    new AnimatedGraphicQueue(animationData.getAllPoolSize(), 750));
        }
    }

    *//**
     *
     *//*
    public void tick() {

        if (titleScreenViewModel == null)
            return;

        AnimatedGraphicData animationData = titleScreenViewModel.getAnimationData();
        animationData.tick();

        int maxQueue = 3;
        if ((animationData.hasQueue() && animationData.getQueue().canDequeue()) &&
                animationData.getCurrentPoolSize() < maxQueue) {

            AnimatedGraphicQueue animationQueue = animationData.getQueue();

            int index = 0;
            AbstractAnimatedGraphic aTemp = null;
            try {
                index = animationQueue.dequeue();
                aTemp = animationData.getFromAllPool(index);
                animationData.addToCurrentPool(aTemp);
            } catch (IndexOutOfBoundsException e) {
                animationQueue.enqueue(index);
                e.printStackTrace();
            }
            if (aTemp != null) {
                AbstractAnimatedGraphic lastAnimInList = animationData.getLastFromCurrentPool();
                if (lastAnimInList != null) {
                    int frameW = getWidth();
                    int frameH = getHeight();

                    if (lastAnimInList instanceof AnimatedOrbData) {
                        if (BitmapUtils.bitmapExists(bitmap_orb)) {
                            animationData.setToAllPool(index, new AnimatedOrbData(
                                    frameW,
                                    frameH));
                        }
                    } else if (lastAnimInList instanceof AnimatedHandData) {
                        if (BitmapUtils.bitmapExists(bitmap_hand)) {

                            int bitmapW = 0;
                            int bitmapH = 0;
                            try {
                                bitmapW = bitmap_hand.getWidth();
                                bitmapH = bitmap_hand.getHeight();
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }

                            animationData.setToAllPool(index, new AnimatedHandData(
                                    frameW,
                                    frameH,
                                    bitmapW,
                                    bitmapH
                            ));

                            bitmap_handRot = ((AnimatedHandData) animationData.
                                    getLastFromCurrentPool()).rotateBitmap(bitmap_hand);
                        }
                    } else if (lastAnimInList instanceof AnimatedWritingData) {
                        if (BitmapUtils.bitmapExists(bitmap_writing)) {

                            int bitmapW = 0;
                            int bitmapH = 0;
                            try {
                                bitmapW = bitmap_writing.getWidth();
                                bitmapH = bitmap_writing.getHeight();
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }

                            animationData.setToAllPool(index, new AnimatedWritingData(
                                    frameW,
                                    frameH,
                                    bitmapW,
                                    bitmapH,
                                    animationData
                            ));

                            bitmap_writingRot = ((AnimatedWritingData) animationData.
                                    getLastFromCurrentPool()).rotateBitmap(bitmap_writing);
                        }
                    } else if (lastAnimInList instanceof AnimatedFrostData) {
                        if (BitmapUtils.bitmapExists(bitmap_frost)) {
                            animationData.setToAllPool(index, new AnimatedFrostData(
                                    frameW,
                                    frameH
                            ));
                        }
                    } else if (lastAnimInList instanceof AnimatedMirrorData) {
                        if (BitmapUtils.bitmapExists(bitmap_mirror)) {
                            animationData.setToAllPool(index, new AnimatedMirrorData(
                                    frameW,
                                    frameH
                            ));
                        }
                    }
                }
            }
        }

        for (int i = 0; i < animationData.getCurrentPoolSize(); i++) {
            AbstractAnimatedGraphic currentAnim = animationData.getFromCurrentPool(i);
            if (currentAnim != null) {
                currentAnim.tick();

                *//*
                 * If the chosen Animated is not alive
                 * remove it from the list
                 * Replace it with a modified item of the same type
                 * Try the next Animated
                 *//*
                if (!currentAnim.isAlive()) {
                    if (currentAnim instanceof AnimatedHandData data) {
                        animationData.setSelectedHand(
                                (int) (Math.random() * handuvResId.size()));

                        BitmapUtils.destroyBitmap(bitmap_hand);
                        BitmapUtils.destroyBitmap(bitmap_handRot);

                        bitmapUtils.setResource(
                                handuvResId.get(animationData.getSelectedHand()));
                        bitmap_hand = bitmapUtils.compileBitmaps(getContext());

                        if (BitmapUtils.bitmapExists(bitmap_hand)) {
                            bitmap_handRot = data.rotateBitmap(bitmap_hand);
                        }

                        *//*
                        AnimatedHandData data = ((AnimatedHandData) currentAnim);
                        BitmapUtils.destroyBitmap(bitmap_handRot);
                        if (BitmapUtils.bitmapExists(bitmap_hand)) {
                            bitmap_handRot = data.rotateBitmap(bitmap_hand);
                        }
                        *//*
                    } else if (currentAnim instanceof AnimatedWritingData data) {
                        animationData.setSelectedWriting(
                                (int) (Math.random() * bookwritingResId.size()));

                        BitmapUtils.destroyBitmap(bitmap_writing);
                        BitmapUtils.destroyBitmap(bitmap_writingRot);

                        bitmapUtils.setResource(
                                bookwritingResId.get(animationData.getSelectedWriting()));
                        bitmap_writing = bitmapUtils.compileBitmaps(getContext());

                        if (BitmapUtils.bitmapExists(bitmap_writing)) {
                            bitmap_writingRot = data.rotateBitmap(bitmap_writing);
                        }
                    }
                    try {
                        animationData.removeFromCurrentPool(currentAnim);
                        i--;
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }

                    System.gc();
                }
            }
        }
    }

    *//**
     * @param canvas The cavas
     *//*
    @Override
    protected void onDraw(Canvas canvas) {

        if (titleScreenViewModel == null) {
            return;
        }

        super.onDraw(canvas);

        paint.setStyle(Paint.Style.FILL);

        try {
            for (AbstractAnimatedGraphic a : titleScreenViewModel.getAnimationData().getCurrentPool()) {
                if (a != null) {
                    paint.setColorFilter(a.getFilter());
                    if (a instanceof AnimatedMirrorData) {
                        a.draw(canvas, paint, bitmap_mirror);
                    } else if (a instanceof AnimatedWritingData) {
                        a.draw(canvas, paint, bitmap_writingRot);
                    } else if (a instanceof AnimatedHandData) {
                        a.draw(canvas, paint, bitmap_handRot);
                    } else if (a instanceof AnimatedOrbData) {
                        a.draw(canvas, paint, bitmap_orb);
                    } else if (a instanceof AnimatedFrostData) {
                        a.draw(canvas, paint, bitmap_frost);
                    }
                }
            }
        } catch (ConcurrentModificationException ex) {
            ex.printStackTrace();
        }

    }

    *//**
     *
     *//*
    public void recycleBitmaps() {
        BitmapUtils.destroyBitmap(bitmap_orb);
        BitmapUtils.destroyBitmap(bitmap_frost);
        BitmapUtils.destroyBitmap(bitmap_hand);
        BitmapUtils.destroyBitmap(bitmap_writing);
        BitmapUtils.destroyBitmap(bitmap_mirror);
        BitmapUtils.destroyBitmap(bitmap_handRot);
        BitmapUtils.destroyBitmap(bitmap_writingRot);

        bitmap_orb = null;
        bitmap_frost = null;
        bitmap_hand = null;
        bitmap_writing = null;
        bitmap_mirror = null;
        bitmap_handRot = null;
        bitmap_writingRot = null;

        System.gc();
    }
    */
}
