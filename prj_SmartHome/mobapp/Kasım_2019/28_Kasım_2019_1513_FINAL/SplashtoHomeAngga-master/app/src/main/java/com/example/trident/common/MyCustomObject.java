package com.example.trident.common;

import android.content.ClipData;
import android.view.MotionEvent;

public class MyCustomObject {

    /**
     * !!!!!!!!!!!!!!!!!!! DO NOT USE- !!!!!!!!!!!!!!!!!!!!!!!!!!!
     * ///alt sayfalardan seçim yapılıp üst classlara listener ile veri göndermek için kullanılacak.(Intent'e alternatif)
     *
     *
     * Listener oluşturma:
     * Creating Custom Listener = https://guides.codepath.com/android/Creating-Custom-Listeners
     * Creating custom listener = https://gist.github.com/nesquena/8265f057fef203a2c67e
     * https://guides.codepath.com/android
     * https://riptutorial.com/android/example/5819/custom-listener
     */

    //FOR TEST:
    public interface MyCustomObjectListener {
        /**
         * "ActivityAutomationSelect.java" içinde otomasyon sonuçlarını döndürmek için tasarlandı.
         * @param forWhat  "BetweenActivities.for_what"
         * @param itemNames automation item name
         * @param checkStates automation check state.(selection state)
         */
        public void requestAutomationResult(String forWhat,String[] itemNames,String[] checkStates);



    }

    private String TAG ="[MyCustomObject]";
    // Step 2- This variable represents the listener passed in by the owning object
    // The listener must implement the events interface and passes messages up to the parent.
    private MyCustomObjectListener listener;

    // Constructor where listener events are ignored
    public MyCustomObject() {
        this.listener = null; // set null listener
    }

    public MyCustomObject(MyCustomObjectListener listener) {
        this.listener = listener;
    }


    // Assign the listener implementing events interface that will receive the events (passed in by the owner)
    public void setCustomObjectListener(MyCustomObjectListener listener) {
        this.listener = listener;
    }

    // This is a hypothetical method that gets called when the object is touched
   /* public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            // Step 3 - Fire listener event to communicate to parent
            listener.onTouchUp(event, "UP");
            return false;
        } else {
            // Fire listener event to communicate to parent
            listener.onTouchDown(event, "DOWN");
            return true;
        }
    }*/

    public void onAutomationResult(String for_what,String[] item_names,String[] check_states){
        if(MyLog.DEGUB)MyLog.d(TAG, "listener is null:" + (listener == null));
        if(listener != null) {
            listener.requestAutomationResult(for_what,item_names,check_states);
        }
    }



}
