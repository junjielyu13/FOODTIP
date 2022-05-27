package com.example.foodtip.View.ViewHolder.OptionInterface;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({CMD.DELETE,CMD.ADD})
@Retention(RetentionPolicy.SOURCE)
public @interface CMD {
    int ADD = 0;
    int DELETE = 1;
}
