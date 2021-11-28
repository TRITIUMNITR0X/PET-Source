package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.ReviewTrackingData;

import java.util.Locale;

public class GlobalPreferencesViewModel extends ViewModel {

    private ReviewTrackingData reviewRequestData;

    private String languageName = Locale.getDefault().getLanguage();

    private int colorSpace = 0;
    private int huntWarningFlashTimeout = -1;

    private boolean isAlwaysOn = false;
    private boolean isHuntAudioAllowed = true;
    private boolean networkPreference = true;

    /**
     * init method
     *
     * @param context
     */
    public void init(Context context) {

        SharedPreferences sharedPref =
                context.getSharedPreferences(context.getResources().getString(R.string.preferences_globalFile_name), Context.MODE_PRIVATE);

        setNetworkPreference(sharedPref.getBoolean(context.getResources().getString(R.string.preference_network), getNetworkPreference()));
        setLanguageName(sharedPref.getString(context.getResources().getString(R.string.preference_language), getLanguageName()));
        setIsAlwaysOn(sharedPref.getBoolean(context.getResources().getString(R.string.preference_isAlwaysOn), getIsAlwaysOn()));
        setHuntWarningAudioAllowed(sharedPref.getBoolean(context.getResources().getString(R.string.preference_isHuntAudioWarningAllowed), getIsHuntAudioAllowed()));
        setHuntWarningFlashTimeout(sharedPref.getInt(context.getResources().getString(R.string.preference_huntWarningFlashTimeout), getHuntWarningFlashTimeout()));
        setColorSpace(sharedPref.getInt(context.getResources().getString(R.string.preference_colorSpace), getColorSpace()));

        reviewRequestData = new ReviewTrackingData(
                sharedPref.getBoolean(context.getResources().getString(R.string.reviewtracking_canRequestReview), false),
                sharedPref.getLong(context.getResources().getString(R.string.reviewtracking_appTimeAlive), 0),
                sharedPref.getInt(context.getResources().getString(R.string.reviewtracking_appTimesOpened), 0)
        );

        saveToFile(context);
    }

    public void setNetworkPreference(boolean preference) {
        this.networkPreference = preference;
    }

    public boolean getNetworkPreference() {
        return networkPreference;
    }

    public void incrementAppOpenCount(Context context) {

        getReviewRequestData().incrementTimesOpened();

        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(
                        R.string.preferences_globalFile_name),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        saveTimesOpened(context, editor);

        editor.apply();
    }

    /**
     * getReviewRequestData method
     *
     * @return reviewRequestData
     */
    public ReviewTrackingData getReviewRequestData() {
        return reviewRequestData;
    }


    /**
     * setLanguageName method
     *
     * @param languageName
     */
    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    /**
     * getLanguageName method
     *
     * @return languageName
     */
    public String getLanguageName() {
        return languageName;
    }

    /**
     * getLanguage
     * <p>
     * Gets the language saved to GlobalPreferences.
     * Defaults to return 'en' if there is no previously saved preference.
     *
     * @return The language specified in the Preferences data, or otherwise English
     */
    public String getLanguage(Context context) {
        String lang = context.getSharedPreferences(
                context.getResources().getString(
                        R.string.preferences_globalFile_name), Context.MODE_PRIVATE).getString(
                "chosenLanguage", "en");

        Log.d("Current Chosen Language", lang);

        return lang;
    }

    /**
     * setLanguage method
     *
     * @param position
     * @param languageNames
     */
    public void setLanguage(int position, String[] languageNames) {
        setLanguageName(languageNames[position]);
    }


    /**
     * getIsAlwaysOn method
     *
     * @return isAlwaysOn
     */
    public boolean getIsAlwaysOn() {
        return isAlwaysOn;
    }

    /**
     * setIsAlwaysOn method
     *
     * @param isAlwaysOn
     */
    public void setIsAlwaysOn(boolean isAlwaysOn) {
        this.isAlwaysOn = isAlwaysOn;
    }

    /**
     * setHuntWarningAudioAllowed method
     *
     * @param isAllowed
     */
    public void setHuntWarningAudioAllowed(boolean isAllowed) {
        isHuntAudioAllowed = isAllowed;
    }

    /**
     * getIsHuntAudioAllowed method
     *
     * @return isHuntAudioAllowed
     */
    public boolean getIsHuntAudioAllowed() {
        return isHuntAudioAllowed;
    }

    /**
     * @return
     */
    public boolean isHuntWarningAudioAllowed() {
        return isHuntAudioAllowed;
    }

    /**
     * setHuntWarningFlashTimeout method
     *
     * @param timeout
     */
    public void setHuntWarningFlashTimeout(int timeout) {
        huntWarningFlashTimeout = timeout;
    }

    /**
     * getHuntWarningFlashTimeout method
     *
     * @return huntWarningFlashTimeout
     */
    public int getHuntWarningFlashTimeout() {
        return huntWarningFlashTimeout;
    }

    /**
     * setColorSpace method
     *
     * @param colorSpace
     */
    public void setColorSpace(int colorSpace) {
        this.colorSpace = colorSpace;
    }

    /**
     * getColorSpace method
     *
     * @return ColorSpace
     */
    public int getColorSpace() {
        return colorSpace;
    }

    public void saveNetworkPreference(Context c, SharedPreferences.Editor editor) {
        editor.putBoolean(
                c.getResources().getString(R.string.preference_network),
                getNetworkPreference());
    }

    public void saveChosenLanguage(Context c, SharedPreferences.Editor editor) {
        editor.putString(
                c.getResources().getString(R.string.preference_language),
                getLanguageName());
    }

    public void saveAlwaysOnState(Context c, SharedPreferences.Editor editor) {
        editor.putBoolean(
                c.getResources().getString(R.string.preference_isAlwaysOn),
                getIsAlwaysOn());
    }

    public void saveHuntWarningAudioAllowed(Context c, SharedPreferences.Editor editor) {
        editor.putBoolean(
                c.getResources().getString(R.string.preference_isHuntAudioWarningAllowed),
                getIsHuntAudioAllowed());
    }

    public void saveHuntWarningFlashTimeout(Context c, SharedPreferences.Editor editor) {
        editor.putInt(
                c.getResources().getString(R.string.preference_huntWarningFlashTimeout),
                getHuntWarningFlashTimeout());
    }

    private void saveColorSpace(Context c, SharedPreferences.Editor editor) {
        editor.putInt(c.getResources().getString(R.string.preference_colorSpace), getColorSpace());
    }

    private void saveAppTimeAlive(Context c, SharedPreferences.Editor editor) {
        editor.putLong(c.getResources().getString(R.string.reviewtracking_appTimeAlive),
                getReviewRequestData().getTimeActive());
    }

    private void saveTimesOpened(Context c, SharedPreferences.Editor editor) {
        editor.putInt(c.getResources().getString(R.string.reviewtracking_appTimesOpened),
                getReviewRequestData().getTimesOpened());
    }

    private void saveCanRequestReview(Context c, SharedPreferences.Editor editor) {
        editor.putBoolean(c.getResources().getString(R.string.reviewtracking_canRequestReview),
                getReviewRequestData().getWasRequested());
    }

    /**
     * saveToFile method
     *
     * @param context
     */
    public void saveToFile(Context context) {

        SharedPreferences sharedPref =
                context.getSharedPreferences(context.getResources().getString(R.string.preferences_globalFile_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        saveNetworkPreference(context, editor);
        saveChosenLanguage(context, editor);
        saveAlwaysOnState(context, editor);
        saveHuntWarningAudioAllowed(context, editor);
        saveHuntWarningFlashTimeout(context, editor);
        saveColorSpace(context, editor);
        saveCanRequestReview(context, editor);
        saveTimesOpened(context, editor);
        saveAppTimeAlive(context, editor);


        editor.apply();
    }

}
