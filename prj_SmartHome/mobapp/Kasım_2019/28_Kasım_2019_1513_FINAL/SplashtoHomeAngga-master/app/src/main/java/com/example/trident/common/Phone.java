package com.example.trident.common;

import android.util.Patterns;

public class Phone {
    public Phone(){}

    public static boolean isValidPhoneNumber(String _pnumber){
        if (_pnumber == null)
            return false;

        return Patterns.PHONE.matcher(_pnumber).matches();
    }
}
