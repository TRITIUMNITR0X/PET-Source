package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.AnimatedGraphic;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.AnimatedGraphicQueue;

import java.util.ArrayList;

/**
 * AnimationData class
 *
 * @author TritiumGamingStudios
 */
public class StartScreenAnimationViewData {

    private AnimatedGraphicQueue queue = new AnimatedGraphicQueue(10, 500);
    private final ArrayList<AnimatedGraphic> allPool = new ArrayList<>(),
            currentPool = new ArrayList<>();

    private int selectedWriting = -1;
    private int selectedHand = -1;

    private float rotWriting, rotHand;

    /**
     * @param animatedItem
     */
    public void addToAllPool(AnimatedGraphic animatedItem) {
        allPool.add(animatedItem);
    }

    /**
     * @param pos
     * @param animated
     */
    public void setToAllPool(int pos, AnimatedGraphic animated) {
        allPool.set(pos, animated);
    }

    /**
     * @return
     */
    @NonNull
    public ArrayList<AnimatedGraphic> getAllPool() {
        return allPool;
    }

    /**
     * @param i
     * @return
     */
    public AnimatedGraphic getFromAllPool(int i) {
        return allPool.get(i);
    }

    /**
     * @return
     */
    public AnimatedGraphic getLastFromAllPool() {
        return allPool.get(allPool.size() - 1);
    }

    /**
     * @return
     */
    public int getAllPoolSize() {
        return allPool.size();
    }

    /**
     * @param animatedItem
     */
    public void addToCurrentPool(AnimatedGraphic animatedItem) {
        currentPool.add(animatedItem);
    }

    /**
     * @param i
     * @return
     */
    public AnimatedGraphic getFromCurrentPool(int i) {
        return currentPool.get(i);
    }

    /**
     * @return
     */
    @Nullable
    public AnimatedGraphic getLastFromCurrentPool() throws IndexOutOfBoundsException {
        if (currentPool.size() == 0) {
            return null;
        }
        int index = currentPool.size() - 1;

        return currentPool.get(index);
    }

    /**
     * @param animated
     */
    public void removeFromCurrentPool(
            AnimatedGraphic animated) {
        currentPool.remove(animated);
    }

    /**
     * @return
     */
    @NonNull
    public ArrayList<AnimatedGraphic> getCurrentPool() {
        return currentPool;
    }

    /**
     * @return
     */
    public int getCurrentPoolSize() {
        return currentPool.size();
    }

    /**
     * @param animationQueue
     */
    public void setQueue(AnimatedGraphicQueue animationQueue) {
        this.queue = animationQueue;
    }

    /**
     * @return
     */
    public boolean hasQueue() {
        return queue != null;
    }

    /**
     * @return
     */
    public AnimatedGraphicQueue getQueue() {
        return queue;
    }

    /**
     *
     */
    public void tick() {
        queue.tick();
    }

    /**
     * @param selectedWriting
     */
    public void setSelectedWriting(int selectedWriting) {
        this.selectedWriting = selectedWriting;
    }

    /**
     * @return
     */
    public int getSelectedWriting() {
        return selectedWriting;
    }

    /**
     * @param selectedHand
     */
    public void setSelectedHand(int selectedHand) {
        this.selectedHand = selectedHand;
    }

    /**
     * @return
     */
    public int getSelectedHand() {
        return selectedHand;
    }

    /**
     * @param rot
     */
    public void setRotWriting(float rot) {
        this.rotWriting = rot;
    }

    /**
     * @return
     */
    public float getRotWriting() {
        return rotWriting;
    }

    /**
     * @param rot
     */
    public void setRotHand(float rot) {
        this.rotHand = rot;
    }

    /**
     * @return
     */
    public float getRotHand() {
        return rotHand;
    }

    /**
     * @return
     */
    public boolean hasData() {
        return !allPool.isEmpty() && hasQueue();
    }

}
