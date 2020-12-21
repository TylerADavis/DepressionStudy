package edu.ucla.cs.er.depressionstudy.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;

import com.google.gson.Gson;

public class Utils {
    private static final String PREFERENCES_FILE = "materialsample_settings";

    public static Drawable tintMyDrawable(Drawable drawable, int color) {
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, color);
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
        return drawable;
    }


    public static String readSharedSetting(Context ctx, String settingName, String defaultValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }

    public static void saveSharedSetting(Context ctx, String settingName, String settingValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }

    public static void saveSharedSettingObj(Context ctx, String settingName, Object settingValue) {
        Gson gson = new Gson();
        String json = gson.toJson(settingValue);
        saveSharedSetting(ctx, settingName, json);
    }

    public static <T> T readSharedSettingObj(Context ctx, String settingName, T defaultValue, Class<T> classOfT) {
        String json = readSharedSetting(ctx, settingName, null);
        if (json==null) {
            return defaultValue;
        }

        Gson gson = new Gson();
        T obj = gson.fromJson(json, classOfT);

        return obj;
    }
}
