package com.example.trident.common;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.trident.smart.ActivityDetailsOfDevice;
import com.example.trident.smart.R;

public class Alert {


    public Alert(){}


    /*
     Exp: use case:
                      Alert.MessageDialog messageDialog = new Alert().new MessageDialog();
                AlertDialog.Builder dialogBuilder = messageDialog.show(ActivityFamilyMembers.this,
                        getString(R.string.remove_member_dialog_title),getString(R.string.remove_member_dialog_info));

                dialogBuilder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                       //Todo: Remove Member.!!!!!!!!!!!!!!!!!!!!
                    }
                });
                dialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //pass. do nothing
                    }
                });
                dialogBuilder.create().show();
     */

    public class MessageDialog {

        /**
         * This AlertDialog consist of one TextView.(so not TextView for dialog title).
         * @param context
         * @param message
         * @return
         */
        public  AlertDialog.Builder show(Context context,String message){
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            dialogBuilder.setMessage(message);
            return dialogBuilder;
        }


        /**
         * this AlertDialog consist of 2 TextView (1.for title and 2.for information.)
         *
         * @param context     ActivityName.this.
         * @param title       for dialog title.
         * @param message for status description.
         * @return
         */
        public AlertDialog.Builder show(Context context, String title, String message) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            //TextView Above(custom title):@{
            final TextView textView_Title = new TextView(context.getApplicationContext());
            textView_Title.setText(title);
            textView_Title.setTextColor(Color.BLACK);
            textView_Title.setPadding(40, 35, 40, 40);
            textView_Title.setGravity(Gravity.CENTER);
            textView_Title.setTypeface(textView_Title.getTypeface(), Typeface.BOLD);
            textView_Title.setTextSize(17);
            dialogBuilder.setCustomTitle(textView_Title);
            //@}
            //TextView info:@{
            final TextView textView_Info = new TextView(context.getApplicationContext());
            textView_Info.setText(message);
            textView_Info.setTextColor(context.getResources().getColor(R.color.color_Gray));
            textView_Info.setPadding(40, 10, 40, 40);
            textView_Info.setGravity(Gravity.CENTER);
            dialogBuilder.setView(textView_Info);
            //@}
            return dialogBuilder;
        }//show

        /**
         * show success dialog.
         * @param activity  this activity.
         */
        public void showSuccessDialog(Activity activity) {
            //before inflating the custom alert dialog layout, we will get the current activity viewgroup
            ViewGroup viewGroup = activity.findViewById(android.R.id.content);
            //then we will inflate the custom alert dialog xml that we created
            View dialogView = LayoutInflater.from(activity).inflate(R.layout.custom_dialog_for_success, viewGroup, false);
            //Now we need an AlertDialog.Builder object
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            //setting the view of the builder to our custom view that we already inflated
            builder.setView(dialogView);
            //finally creating the alert dialog and displaying it
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
            Button button = (Button)dialogView.findViewById(R.id.button_Success);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();//close AlertDialog window.
                }
            });
        }//showCustomDialog()

    }//class ForMsgDialog

/*************************************************************************************/
    public class InputDialog{

        //##########################!!!!!!!!!!!!!!!!!!!!!!! DO NOT USE ##########################!!!!!!!!!!!!!!!!!!!!!!!

        public AlertDialog.Builder show(Context context,String title){
          return show(context,title,"");
        }

        private EditText editText_Content;

    /**
     * Show Edit Dialog.
     * @param context  this(ActivityName.this OR ClassName.this)
     * @param title    Title of "Dialog Window"
     * @param content  Content of EditText on the Dailog Window.
     * @return
     */
        public AlertDialog.Builder show(Context context,String title,String content){
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            /*final EditText */editText_Content = new EditText(context.getApplicationContext());
            //TextView Above(custom title):@{
            final TextView textView_Title = new TextView(context.getApplicationContext());
            textView_Title.setText(title);
            textView_Title.setTextColor(Color.BLACK);
            textView_Title.setPadding(50,35,0,40);
            textView_Title.setGravity(Gravity.CENTER);
            textView_Title.setTypeface(textView_Title.getTypeface(), Typeface.BOLD);
            textView_Title.setTextSize(17);
            dialogBuilder.setCustomTitle(textView_Title);
            //@}
            editText_Content.setTextColor(Color.BLACK);
            editText_Content.setSingleLine();
            editText_Content.setText(content);
            editText_Content.setSelection(content.length());
            dialogBuilder.setView(editText_Content);
            return dialogBuilder;
        }


    /**
     * Show Edit Dialog.
     * @param context  this(ActivityName.this OR ClassName.this)
     * @param title    Title of "Dialog Window"
     * @param hint     Hint of EditText on the Dialog Window. If you sent null,"content" will be evaluated.
     * @param content  Content of EditText on the Dailog Window. if you send "hint" value, please send null for "content".
     * @return
     */
    public AlertDialog.Builder show(Context context,String title,String hint,String content){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        /*final EditText */editText_Content = new EditText(context.getApplicationContext());
        //TextView Above(custom title):@{
        final TextView textView_Title = new TextView(context.getApplicationContext());
        textView_Title.setText(title);
        textView_Title.setTextColor(Color.BLACK);
        textView_Title.setPadding(50,35,0,40);
        textView_Title.setGravity(Gravity.CENTER);
        textView_Title.setTypeface(textView_Title.getTypeface(), Typeface.BOLD);
        textView_Title.setTextSize(17);
        dialogBuilder.setCustomTitle(textView_Title);
        //@}
        editText_Content.setTextColor(Color.BLACK);
        editText_Content.setSingleLine();
        editText_Content.setHint(hint);
        if(content !=null && content.length() > 0)
            editText_Content.setText(content);
        editText_Content.setSelection(editText_Content.length());
        editText_Content.setHintTextColor(Color.GRAY);
        dialogBuilder.setView(editText_Content);
        return dialogBuilder;
    }


    /**
     * To obtain content of EditText on the AlertDialog Window.
     * @return
     */
    public String getContentOfEditTextOnAlertDialog(){
        return  editText_Content.getText().toString();
    }

    }//class DialogForEdit

}
