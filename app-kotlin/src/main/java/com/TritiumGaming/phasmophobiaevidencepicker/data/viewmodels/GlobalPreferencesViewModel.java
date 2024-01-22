package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.ReviewTrackingData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming.CustomTheme;
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming.subsets.ColorThemeControl;
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming.subsets.FontThemeControl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/** @noinspection SameParameterValue*/
public class GlobalPreferencesViewModel extends ViewModel {

    // Review Tracker
    private ReviewTrackingData reviewRequestData;

    // Language
    private String languageName = Locale.getDefault().getLanguage();

    // Persistent Styles
    private FontThemeControl fontThemeControl;
    private ColorThemeControl colorThemeControl;

    // Generic settings
    private int huntWarningFlashTimeout = -1;
    private boolean isAlwaysOn = false;
    private boolean isHuntAudioAllowed = true;
    private boolean networkPreference = true;
    private boolean isLeftHandSupportEnabled = false;

    // Title screen increments
    private boolean canShowIntroduction = true;

    /**
     * init method
     *
     * @param context
     */
    public void init(Context context) {

        SharedPreferences sharedPref = getSharedPreferences(context);

        setNetworkPreference(sharedPref.getBoolean(context.getResources().getString(R.string.preference_network), getNetworkPreference()));
        setLanguageName(sharedPref.getString(context.getResources().getString(R.string.preference_language), getLanguageName()));
        setIsAlwaysOn(sharedPref.getBoolean(context.getResources().getString(R.string.preference_isAlwaysOn), getIsAlwaysOn()));
        setHuntWarningAudioAllowed(sharedPref.getBoolean(context.getResources().getString(R.string.preference_isHuntAudioWarningAllowed), getIsHuntAudioAllowed()));
        setHuntWarningFlashTimeout(sharedPref.getInt(context.getResources().getString(R.string.preference_huntWarningFlashTimeout), getHuntWarningFlashTimeout()));

        setLeftHandSupportEnabled(sharedPref.getBoolean(context.getResources().getString(R.string.preference_isLeftHandSupportEnabled), getIsLeftHandSupportEnabled()));
        setCanShowIntroduction(sharedPref.getBoolean(context.getResources().getString(R.string.tutorialTracking_canShowIntroduction), getCanShowIntroduction()));

        reviewRequestData = new ReviewTrackingData(
                sharedPref.getBoolean(context.getResources().getString(R.string.reviewtracking_canRequestReview), false),
                sharedPref.getLong(context.getResources().getString(R.string.reviewtracking_appTimeAlive), 0),
                sharedPref.getInt(context.getResources().getString(R.string.reviewtracking_appTimesOpened), 0)
        );

        fontThemeControl = new FontThemeControl(context);
        /*fontThemeControl.init(
                sharedPref.getInt(
                        context.getResources().getString(R.string.preference_fontType),
                        getFontTheme()
                )
        );*/
        fontThemeControl.init(
                sharedPref.getString(
                        context.getResources().getString(R.string.preference_savedFont),
                        getFontThemeID()
                )
        );

        colorThemeControl = new ColorThemeControl(context);
        colorThemeControl.init(
                sharedPref.getString(
                        context.getResources().getString(R.string.preference_savedTheme),
                        getColorThemeID()
            )
        );

        saveToFile(context);
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(
                context.getResources().getString(R.string.preferences_globalFile_name),
                Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(
                        R.string.preferences_globalFile_name),
                Context.MODE_PRIVATE);
        return sharedPref.edit();
    }

    public void setLeftHandSupportEnabled(boolean isLeftHandSupportEnabled) {
        this.isLeftHandSupportEnabled = isLeftHandSupportEnabled;
    }

    public boolean getIsLeftHandSupportEnabled() {
        return isLeftHandSupportEnabled;
    }

    public void setNetworkPreference(boolean preference) {
        this.networkPreference = preference;
    }

    public boolean getNetworkPreference() {
        return networkPreference;
    }

    public void incrementAppOpenCount(Context context) {

        getReviewRequestData().incrementTimesOpened();

        saveTimesOpened(context, getEditor(context), true);

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

    public int getLanguageIndex(ArrayList<String> languageNames) {
        for(int i = 0; i < languageNames.size(); i++) {
            if(getLanguageName().equalsIgnoreCase(languageNames.get(i))) {
                return i;
            }
        }
        return 0;
    }

    /**
     * setLanguage method
     *
     * @param position
     * @param languageNames
     */
    public void setLanguage(int position, String[] languageNames) {
        if(position < 0 || position >= languageNames.length) { return; }

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
     * getColorSpace method
     *
     * @return ColorSpace
     */
    /*
    public int getColorTheme() {
        return colorThemeControl.getSavedIndex();
    }
    */
    public String getColorThemeID() {
        return colorThemeControl.getID();
    }
    public CustomTheme getColorTheme() {
        return colorThemeControl.getCurrentTheme();
    }

    /**
     * getFontType method
     *
     * @return fontType
     */
    /*
    public int getFontTheme() {
        return fontThemeControl.getSavedIndex();
    }
    */
    public String getFontThemeID() {
        return fontThemeControl.getID();
    }
    public CustomTheme getFontTheme() {
        return fontThemeControl.getCurrentTheme();
    }

    public FontThemeControl getFontThemeControl() {
        return fontThemeControl;
    }

    public ColorThemeControl getColorThemeControl() {
        return colorThemeControl;
    }

    public void setCanShowIntroduction(boolean canShowIntroduction) {
        this.canShowIntroduction = canShowIntroduction;
    }

    public boolean getCanShowIntroduction() {
        return canShowIntroduction;
    }

    public boolean canShowIntroduction() {
        return canShowIntroduction && reviewRequestData.getTimesOpened() <= 1;
    }

    /**
     *
     * @param c
     * @param editor
     * @param localApply
     */
    private void saveNetworkPreference(
            Context c, SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null) {
            editor = getEditor(c);
        }

        editor.putBoolean(
                c.getResources().getString(R.string.preference_network),
                getNetworkPreference());

        if(localApply) {
            editor.apply();
        }
    }

    /**
     *
     * @param c
     * @param editor
     * @param localApply
     */
    private void saveChosenLanguage(
            Context c, SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null) {
            editor = getEditor(c);
        }

        editor.putString(
                c.getResources().getString(R.string.preference_language),
                getLanguageName());

        if(localApply) {
            editor.apply();
        }
    }

    /**
     *
     * @param c
     * @param editor
     * @param localApply
     */
    private void saveAlwaysOnState(
            Context c, SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null) {
            editor = getEditor(c);
        }

        editor.putBoolean(
                c.getResources().getString(R.string.preference_isAlwaysOn),
                getIsAlwaysOn());

        if(localApply) {
            editor.apply();
        }
    }

    /**
     *
     * @param c
     * @param editor
     * @param localApply
     */
    private void saveHuntWarningAudioAllowed(
            Context c, SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null) {
            editor = getEditor(c);
        }

        editor.putBoolean(
                c.getResources().getString(R.string.preference_isHuntAudioWarningAllowed),
                getIsHuntAudioAllowed());

        if(localApply) {
            editor.apply();
        }
    }

    /**
     *
     * @param c
     * @param editor
     * @param localApply
     */
    private void saveHuntWarningFlashTimeout(
            Context c, SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null) {
            editor = getEditor(c);
        }

        editor.putInt(
                c.getResources().getString(R.string.preference_huntWarningFlashTimeout),
                getHuntWarningFlashTimeout());

        if(localApply) {
            editor.apply();
        }
    }

    /**
     *
     * @param c
     * @param editor
     * @param localApply
     */
    public void saveColorSpace(Context c) {
        saveColorSpace(c, getEditor(c), false);
    }

    /**
     *
     * @param c
     * @param editor
     * @param localApply
     */
    private void saveColorSpace(
            Context c, SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null) {
            editor = getEditor(c);
        }

        editor.putString(c.getResources().getString(R.string.preference_savedTheme), getColorThemeID());

        if(localApply) {
            editor.apply();
        }
    }

    /**
     *
     * @param c
     * @param editor
     * @param localApply
     */
    private void saveFontType(
            Context c, SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null) {
            editor = getEditor(c);
        }

        editor.putString(c.getResources().getString(R.string.preference_savedFont), getFontThemeID());

        if(localApply) {
            editor.apply();
        }
    }

    /**
     *
     * @param c
     * @param editor
     * @param localApply
     */
    private void saveAppTimeAlive(
            Context c, SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null) {
            editor = getEditor(c);
        }

        editor.putLong(c.getResources().getString(R.string.reviewtracking_appTimeAlive),
                getReviewRequestData().getTimeActive());

        if(localApply) {
            editor.apply();
        }
    }

    /**
     *
     * @param c
     * @param editor
     * @param localApply
     */
    private void saveTimesOpened(
            Context c, SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null) {
            editor = getEditor(c);
        }

        editor.putInt(c.getResources().getString(R.string.reviewtracking_appTimesOpened),
                getReviewRequestData().getTimesOpened());

        if(localApply) {
            editor.apply();
        }
    }

    /**
     *
     * @param c
     * @param editor
     * @param localApply
     */
    private void saveCanRequestReview(
            Context c, SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null) {
            editor = getEditor(c);
        }

        editor.putBoolean(c.getResources().getString(R.string.reviewtracking_canRequestReview),
                getReviewRequestData().getWasRequested());

        if(localApply) {
            editor.apply();
        }
    }

    /**
     *
     * @param c
     * @param editor
     * @param localApply
     */
    private void saveIsLeftHandSupportEnabled(
            Context c, SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null) {
            editor = getEditor(c);
        }

        editor.putBoolean(c.getResources().getString(R.string.preference_isLeftHandSupportEnabled),
                getIsLeftHandSupportEnabled());

        if(localApply) {
            editor.apply();
        }
    }

    /**
     *
     * @param c
     * @param editor
     * @param localApply
     */
    public void saveCanShowIntroduction(
            Context c, SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null) {
            editor = getEditor(c);
        }

        editor.putBoolean(c.getResources().getString(R.string.tutorialTracking_canShowIntroduction),
                getCanShowIntroduction());

        if(localApply) {
            editor.apply();
        }
    }

    /**
     * saveToFile method
     *
     * @param context The Activity context.
     */
    public void saveToFile(Context context) {

        SharedPreferences.Editor editor = getEditor(context);

        saveNetworkPreference(context, editor, false);
        saveChosenLanguage(context, editor, false);
        saveAlwaysOnState(context, editor, false);
        saveHuntWarningAudioAllowed(context, editor, false);
        saveHuntWarningFlashTimeout(context, editor, false);
        saveColorSpace(context, editor, false);
        saveFontType(context, editor, false);
        saveIsLeftHandSupportEnabled(context, editor, false);
        saveCanRequestReview(context, editor, false);
        saveTimesOpened(context, editor, false);
        saveAppTimeAlive(context, editor, false);
        saveCanShowIntroduction(context, editor, false);

        editor.apply();
    }

    @NonNull
    public HashMap<String, String> getDataAsList() {
        HashMap<String, String> settings = new HashMap<>();
        settings.put("network_pref", getNetworkPreference()+"");
        settings.put("language", getLanguageName());
        settings.put("always_on", getIsAlwaysOn()+"");
        settings.put("warning_enabled", getIsHuntAudioAllowed()+"");
        settings.put("warning_timeout", getHuntWarningFlashTimeout()+"");
        settings.put("color_theme", getColorThemeID()+"");
        settings.put("font_type", getFontThemeID()+"");
        settings.put("left_support", getIsLeftHandSupportEnabled()+"");
        settings.put("can_show_intro", getCanShowIntroduction()+"");
        if(getReviewRequestData() != null) {
            settings.put("review_request", getReviewRequestData().canRequestReview()+"");
            settings.put("times_opened", getReviewRequestData().getTimesOpened()+"");
            settings.put("active_time", getReviewRequestData().getTimeActive()+"");
        }

        return settings;
    }

    /**
     *
     * @param context
     */
    public void printFromFile(Context context) {
        SharedPreferences sharedPref = getSharedPreferences(context);

        Log.d("GlobalPreferencesFile",
                "NetworkPreference: " + sharedPref.getBoolean(context.getResources().getString(R.string.preference_network), getNetworkPreference()) +
                "; Language: " + sharedPref.getString(context.getResources().getString(R.string.preference_language), getLanguageName()) +
                "; Always On: " + sharedPref.getBoolean(context.getResources().getString(R.string.preference_isAlwaysOn), getIsAlwaysOn()) +
                "; Is Hunt Audio Allowed: " + sharedPref.getBoolean(context.getResources().getString(R.string.preference_isHuntAudioWarningAllowed), getIsHuntAudioAllowed()) +
                "; Hunt Warning Flash Timeout: " + sharedPref.getInt(context.getResources().getString(R.string.preference_huntWarningFlashTimeout), getHuntWarningFlashTimeout()) +
                "; Color Space: " + sharedPref.getString(context.getResources().getString(R.string.preference_savedTheme), getColorThemeID()) +
                "; ReviewRequestData: [" +
                "Time Alive: " + sharedPref.getLong(context.getResources().getString(R.string.reviewtracking_appTimeAlive), 0) +
                "; Times Opened: " + sharedPref.getInt(context.getResources().getString(R.string.reviewtracking_appTimesOpened), 0) +
                "; Can Request Review: " + sharedPref.getBoolean(context.getResources().getString(R.string.reviewtracking_canRequestReview), false) + "]" +
                "; Can Show Introduction: " + sharedPref.getBoolean(context.getResources().getString(R.string.tutorialTracking_canShowIntroduction), false));
    }

    /**
     *
     */
    public void printFromVariables() {
        Log.d("GlobalPreferencesVars",
                "NetworkPreference: " + getNetworkPreference() +
                "; Language: " + getLanguageName() +
                "; Always On: " + getIsAlwaysOn() +
                "; Is Hunt Audio Allowed: " + getIsHuntAudioAllowed() +
                "; Hunt Warning Flash Timeout: " + getHuntWarningFlashTimeout() +
                "; Color Space: " + getColorThemeID() +
                "; Can Show Introduction: " + getCanShowIntroduction() +
                "; ReviewRequestData: [" + getReviewRequestData().toString() + "]");
    }

}
