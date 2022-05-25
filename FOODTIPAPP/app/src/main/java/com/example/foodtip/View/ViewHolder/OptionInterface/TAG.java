package com.example.foodtip.View.ViewHolder.OptionInterface;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef ({TAG.SEARCH,TAG.HISTORY})
@Retention(RetentionPolicy.SOURCE)
public @interface TAG {
    int SEARCH = 0;
    int HISTORY = 1;
}
