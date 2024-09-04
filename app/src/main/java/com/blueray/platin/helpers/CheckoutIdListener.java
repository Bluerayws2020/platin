package com.blueray.platin.helpers;

import androidx.annotation.Nullable;

@FunctionalInterface
public interface CheckoutIdListener {
    void onResult(@Nullable String var1, @Nullable String var2);
}