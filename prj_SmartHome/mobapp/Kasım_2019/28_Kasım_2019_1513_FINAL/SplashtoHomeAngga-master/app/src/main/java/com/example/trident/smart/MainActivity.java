package com.example.trident.smart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.trident.common.Ini;
import com.example.trident.common.Preferences;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    ImageView imageView_bgapp, imageView_clover;
    LinearLayout linearLayout_textsplash, linearLayout_texthome, linearLayout_menus,linearlayout_textviews;
   // Animation frombottom;///!!!!!!!! ANİMASYONU ŞİMDİLİK KAPALI TUT !!!!!!!!!!!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ///
        ///DEFAULT:@{
        //otomatik login:
         if(Preferences.loadAsString(getApplicationContext(),"Settings","login_username").equals("null") == false){
             goToMainPage();
         }
        ///DEFAULT:@}



        //frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);//ANİMASYON

        imageView_bgapp = (ImageView) findViewById(R.id.imageView_bgapp);
        imageView_clover = (ImageView) findViewById(R.id.imageView_clover);
        linearLayout_textsplash = (LinearLayout) findViewById(R.id.linearlayout_textsplash);
        linearLayout_texthome = (LinearLayout) findViewById(R.id.linearlayout_texthome);
        linearLayout_menus = (LinearLayout) findViewById(R.id.linearlayout_menus);
        linearlayout_textviews = (LinearLayout)findViewById(R.id.linearlayout_textviews);

        /*//original
        imageView_bgapp.animate().translationY(-1500).setDuration(800).setStartDelay(300);
        imageView_clover.animate().alpha(0).setDuration(800).setStartDelay(600);
        linearLayout_textsplash.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(300);
        */

        /**///add for test: //ANİMASYON'u işlet.
        imageView_bgapp.animate().translationY(-1500).setDuration(800).setStartDelay(1);
        imageView_clover.animate().alpha(0).setDuration(800).setStartDelay(110);
        linearLayout_textsplash.animate().translationY(140).alpha(0).setDuration(1).setStartDelay(1);
        /**/
        /*//ANİMASYON
        linearLayout_texthome.startAnimation(frombottom);
        linearLayout_menus.startAnimation(frombottom);
        linearlayout_textviews.startAnimation(frombottom);
        */
        ///
        ///Controls:@{
        final EditText editText_UserName     = (EditText)findViewById(R.id.editText_UserName);
        final EditText editText_Password     = (EditText)findViewById(R.id.editText_Password);
        CheckBox checkbox_showandhidepasswod = (CheckBox)findViewById(R.id.checkbox_showandhidepasswod);
        Button button_Login                  = (Button)findViewById(R.id.button_Login);
        TextView textView_CreateAnAccount    = (TextView)findViewById(R.id.textview_CreateAnAccount);
        TextView textView_ForgetYourPassword = (TextView)findViewById(R.id.textview_ForgetYourPassword);
        //Click:@{
        checkbox_showandhidepasswod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean _isChecked) {
                if(_isChecked){//show EditText content.
                    //editText_Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    editText_Password.setInputType(InputType.TYPE_CLASS_TEXT);
                    editText_Password.setSelection(editText_Password.length());
                }
                else{//hide EditText content.
                    //editText_Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    editText_Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    editText_Password.setSelection(editText_Password.length());
                }
            }
        });


        button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //login process:

                /**todo:
                 * //Telefon numarasıyla veya e-mail ile login olunabilir.
                 * 1.Bu nedenle telefon no ve e-mail format-regex kontrolü yapılacak.
                 * 2.Php file ile login işlemi.
                 * 3.Login başarılı: > "Ana activity(Listele:Cihazlar,Senaryolar,Odalar,Cihaz Ekle,vb. sayfası)"
                 */

                //temsili değerler
                String user_name = editText_UserName.getText().toString();
                String password  = editText_Password.getText().toString();
                //todo: bilgileri doğrula.
                //..
                //is user login information is currect,then save as preferences for automation login:
                Preferences.saveAsString(getApplicationContext(),"Settings","login_username",user_name);
                Preferences.saveAsString(getApplicationContext(),"Settings","login_password",password);

                //is login successful,go to "ActivityMainPage.class":
                goToMainPage();

            }
        });
        textView_CreateAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create an Account Process:
                Intent intent = new Intent(getApplicationContext(),ActivityCreateAccount.class);
                startActivity(intent);
            }
        });
        textView_ForgetYourPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Forget your Password process:
                Intent intent = new Intent(getApplicationContext(),ActivityForgotPassword.class);
                startActivity(intent);
            }
        });
        //click:@}
        ///controls:@}

    }//onCreate

    private void goToMainPage(){
        Init();
        Intent intent = new Intent(getApplicationContext(),ActivityMainPage.class);
        startActivity(intent);
    }

    /**
     * Standart olarak uygulama ile birlikte gelecek sürecler.
     */
    private void Init(){
        Preferences.saveAsString(getApplicationContext(),"Settings","version", Ini.VERSION);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
             //bypass KEYCODE_BACK.
        }
        else
            return super.onKeyDown(keyCode, event);

        return false;
    }
}
