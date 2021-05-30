package com.anmentone.my_poster;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Set;

import static android.content.Intent.URI_INTENT_SCHEME;

public class IntentLogger {

    private static final String TAG = "IntentLogger";

    public static void logFullContent(Intent intent) {
        Log.v(TAG, "Package: " + intent.getPackage());
        Log.v(TAG, "Action: " + intent.getAction());
        Log.v(TAG, "Type: " + intent.getType());
        printCategories(intent.getCategories());
        Log.v(TAG, "Component: " + intent.getComponent());
        Log.v(TAG, "Data String: " + intent.getDataString());
        printExtras(intent.getExtras());
    }

    private static void printExtras(Bundle extras) {
        if (extras == null) {
            Log.v(TAG, "Extras: null");
        } else {
            if (extras.isEmpty()) {
                Log.v(TAG, "Extras: not null, but empty");
            } else {
                for (String extraKey : extras.keySet()) {
                    Log.v(TAG, "Extra: " + extraKey + ": " + extras.get(extraKey));
                }
            }
        }
    }

    private static void printCategories(Set<String> categories) {
        if (categories == null) {
            Log.v(TAG, "Categories: null");
        } else {
            if (categories.isEmpty()) {
                Log.v(TAG, "Categories: not null, but empty");
            } else {
                for (String category : categories) {
                    Log.v(TAG, "Category: " + category);
                }
            }
        }
    }
}