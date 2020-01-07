package com.example.trident.common;

import android.widget.Toast;

public class Server {  //MQTT and normal SERVER
    /**
     * Server operations
     */

    public Server()
    {
        //Constructor
    }


    /**
     * Upload "Profile Photo" to Server.
     * @return successfuly state
     */
    public boolean uploadPofilePhotoToServer(){
        //TODO:Upload "Profile Photo" to Server. Using AsyncTask.
        ///+Upload photo to Server.
        ///+Insert or Update to database;depends on the sutiation(first account or update profile state).
        ///
        return true;//return upload successfuly state.
    }

    /**
     * Send the last selected "Color Point" to the Mqtt Server.
     * @param x
     * @param y
     */
    public static void sendLastSelectedColorPoint(float x,float y){ //Called from "ColorPickerView.java > loadListeners() > MotionEvent.ACTION_UP"
        /*//TODO(raptiye):save the last selected "Color Point" to the database.
        +todo: because when the user first opens the "Lamp control page",we will apply the last chosen color.
        +todo: for fast processing,we will send the value as mqtt to the server instead of sending it directly to the database.Then we will save it to the database by the server
        */
    }

}
