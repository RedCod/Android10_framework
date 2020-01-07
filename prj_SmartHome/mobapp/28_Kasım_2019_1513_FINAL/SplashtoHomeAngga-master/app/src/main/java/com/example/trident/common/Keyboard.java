package com.example.trident.common;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class Keyboard {

    private static Context context;
    private static Activity activity;
    private static Keyboard keyboard = new Keyboard();
    private Keyboard(){}

    /**
     *
     * @param _context
     * @return
     */
    public static Keyboard getInstance(Context _context){
        context = _context;
        return keyboard;
    }

    /**
     *
     * @param _activity
     * @return
     */
   /* public static Keyboard getInstance(Activity _activity){
        activity = _activity;
        return  keyboard;
    }*/

    /**
     * if we use this function,we must call getInstance(getApplicationContext()) like this.
     * Show Keyboard.
     */
    public void showKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * if we use this function,we must call getInstance(getApplicationContext()) like this.
     * Hide Keyboard.
     */
    public void hideKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    /**DISABLED
     * if we use this function,we must call getInstance(this) like this.
     *Open Keyboard over the Layout.
     */
    private void openKeyboardOverLayout(){
        if(activity !=null)
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
    }

}
