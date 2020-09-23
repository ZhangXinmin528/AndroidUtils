package com.zxm.utils.core.sp;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

/**
 * Created by ZhangXinmin on 2018/8/18.
 * Copyright (c) 2018 . All rights reserved.
 * The util class for {@link SharedPreferences}.
 */
public final class SharedPreferencesUtil {

    public static final String CONFIG_NAME = "sp_config";

    private SharedPreferencesUtil() {
        throw new UnsupportedOperationException("You con't do it!");
    }

    /**
     * save special information to painter_config.xml
     *
     * @param context
     * @param key     key value
     * @param object  the special information
     */
    public static void put(@NonNull Context context, @NonNull String key, @NonNull Object object) {
        final SharedPreferences sp = context.getSharedPreferences(CONFIG_NAME,
                Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.apply();
    }

    /**
     * get the target information by the key
     *
     * @param context
     * @param key           the key value
     * @param defaultObject the default value
     * @return
     */
    public static Object get(@NonNull Context context, @NonNull String key, @NonNull Object defaultObject) {
        final SharedPreferences sp = context.getSharedPreferences(CONFIG_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return defaultObject;
    }


    /**
     * Removing a preference value using the key.
     *
     * @param context The context.
     * @param key     The name of the preference to remove.
     */
    public static void remove(@NonNull Context context, @NonNull String key) {
        final SharedPreferences sp = context.getSharedPreferences(CONFIG_NAME,
                Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

}
