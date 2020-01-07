package com.example.trident.common;

public class EMail {
    //email related transactions:

    public EMail(){}


    public final static boolean isValid(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

}
