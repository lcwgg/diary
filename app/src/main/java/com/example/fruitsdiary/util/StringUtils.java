package com.example.fruitsdiary.util;

import android.content.Context;
import android.content.res.Resources;

import com.example.fruitsdiary.R;

public final class StringUtils {

    public static final String EMPTY_STRING = "";

    public static final int NO_STRING_DEFINED = -1;

    private StringUtils(){}

    public static String getCorrectFruitSpelling(Context context, int quantity, String fruitName){
        if (isFruitNameException(fruitName) && quantity > 0){
            return getFruitExceptionSpelling(fruitName);
        }
        Resources res = context.getResources();
        return res.getQuantityString(R.plurals.fruits, quantity, fruitName);
    }

    private static String getFruitExceptionSpelling(String fruitName){
        if (fruitName.equals("cherry")){
            return "cherries";
        }
        return EMPTY_STRING;
    }

    private static boolean isFruitNameException(String fruitName){
        if (fruitName.equals("cherry")){
            return true;
        }
        return false;
    }
}
