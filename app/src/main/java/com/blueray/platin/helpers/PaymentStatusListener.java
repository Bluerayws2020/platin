package com.blueray.platin.helpers;

import androidx.annotation.Nullable;

@FunctionalInterface
public interface PaymentStatusListener {
    void onResult(boolean var1, @Nullable String var2);
}