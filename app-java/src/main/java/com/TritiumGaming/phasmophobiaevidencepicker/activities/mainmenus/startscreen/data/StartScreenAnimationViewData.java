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

    private AnimatedGraphicQueue queue =
            new AnimatedGraphicQueue(10, 500);

    private final ArrayList<AnimatedGraphic>
            allPool = new ArrayList<>(),
            currentPool = new ArrayList<>();

    private int selectedWriting = -1;
    private int selectedHand = -1;

    private float rotWriting, rotHand;

    public void addToAllPool(AnimatedGraphic animatedItem) {
        allPool.add(animatedItem);
    }

    public void setToAllPool(int pos, AnimatedGraphic animated) {
        allPool.set(pos, animated);
    }

    @NonNull
    public ArrayList<AnimatedGraphic> getAllPool() {
        return allPool;
    }

    public AnimatedGraphic getFromAllPool(int i) {
        return allPool.get(i);
    }

    public AnimatedGraphic getLastFromAllPool() {
        return allPool.get(allPool.size() - 1);
    }

    public int getAllPoolSize() {
        return allPool.size();
    }

    public void addToCurrentPool(AnimatedGraphic animatedItem) {
        currentPool.add(animatedItem);
    }

    public AnimatedGraphic getFromCurrentPool(int i) {
        return currentPool.get(i);
    }

    @Nullable
    public AnimatedGraphic getLastFromCurrentPool() throws IndexOutOfBoundsException {
        if (currentPool.size() == 0) {
            return null;
        }
        int index = currentPool.size() - 1;

        return currentPool.get(index);
    }

    public void removeFromCurrentPool(
            AnimatedGraphic animated) {
        currentPool.remove(animated);
    }

    @NonNull
    public ArrayList<AnimatedGraphic> getCurrentPool() {
        return currentPool;
    }

    public int getCurrentPoolSize() {
        return currentPool.size();
    }

    public void setQueue(AnimatedGraphicQueue animationQueue) {
        this.queue = animationQueue;
    }

    public boolean hasQueue() {
        return queue != null;
    }

    public AnimatedGraphicQueue getQueue() {
        return queue;
    }

    public void tick() {
        queue.tick();
    }

    public void setSelectedWriting(int selectedWriting) {
        this.selectedWriting = selectedWriting;
    }

    public int getSelectedWriting() {
        return selectedWriting;
    }

    public void setSelectedHand(int selectedHand) {
        this.selectedHand = selectedHand;
    }

    public int getSelectedHand() {
        return selectedHand;
    }

    public void setRotWriting(float rot) {
        this.rotWriting = rot;
    }

    public float getRotWriting() {
        return rotWriting;
    }

    public void setRotHand(float rot) {
        this.rotHand = rot;
    }

    public float getRotHand() {
        return rotHand;
    }

    public boolean hasData() {
        return !allPool.isEmpty() && hasQueue();
    }

}
