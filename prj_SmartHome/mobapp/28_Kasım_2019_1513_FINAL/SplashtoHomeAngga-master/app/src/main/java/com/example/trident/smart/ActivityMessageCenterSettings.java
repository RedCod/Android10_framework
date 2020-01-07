package com.example.trident.smart;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Switch;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.GUI;
import com.example.trident.common.MyLog;
import com.example.trident.common.Static;
import com.example.trident.smart.custom.CustomListAdapter_MessageCenterSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivityMessageCenterSettings extends AppCompatActivity {

    /**
     * "Mesaj Merkezi Ayarları"
     * //Mesaj Merkezi için ayarların bulunduğu sayfa.
     * "ActivityMessageCenter.java"
     *
     */

    ScrollView scrollView_Based;
    LinearLayout linearLayout_TimeDivision;
    LinearLayout linearLayout_GoToAddTimeSegment;
    ListView listView_TimeDivision;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_center_settings);
        //

        LinearLayout linearLayout_Back               = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                           = (Button)findViewById(R.id.button_Back);
        /*ScrollView*/ scrollView_Based                = (ScrollView)findViewById(R.id.scrollView_Based);
        Switch switch_DoNotDisturb                   = (Switch)findViewById(R.id.switch_DoNotDisturb);
        linearLayout_TimeDivision                    = (LinearLayout)findViewById(R.id.linearLayout_TimeDivision);
        listView_TimeDivision                        = (ListView)findViewById(R.id.listView_TimeDivision);
        linearLayout_GoToAddTimeSegment              = (LinearLayout)findViewById(R.id.linearLayout_GoToAddTimeSegment);

        ///Default:@{
        /**
         * todo: Veritabanından içerikleri çek ve "listView_TimeDivison" listesine aktar.
         * -Veritabanından çekilen parametrelere göre Switch konumu vb. işlemleri uygula.
         * -NOT: VERİTABANINDAN ÇEKİLEN İÇERİKLER,AYARLAR,PARAMETRELER VB. TAMAMI JSON FORMATINDA ÇEKİLECEK.
         *       BU İÇERİKLER MEVCUT SAYFA VE ALT SAYFAYLA İLİŞKİSEL OLARAK ÇALIŞIYORSA,MEVCUT SAYFADA KULLANILACAKLARI BU SAYFADA PARSE EDEREK UYGULA.
         *       ANCAK ALT SAYFAYA GİDECEK İÇERİKLER VE PAREMETRELERİ,ALT SAYFAYA SADECE İLGİLİ KAYDIN İNDEX(json satırına ait) DEĞERİNİ GÖNDER VE ALT SAYFADA BU İÇERİĞE ULAŞ VE SAYFAYA GÖRÜNTÜLE/UYGULA.
         *       ÖRNEĞİN BU SAYFADA HANGİ CİHAZLARIN NE ZAMAN ARALIKLARINDA RAHATSIZ EDİLMEYECEĞİ İLE İLGİLİ KAYITLAR BAŞLIK OLARAK LİSTELENİYOR.
         *       İŞLETME 1:LİSTVİEW ITEM TIKLANDIĞINDA,İLGİLİ ITEM'a AİT HEM BAŞLIK HEM DE itemDesc(2.item. saat bilgisi,Tekrarla bilgisi,rahatsız edilmeyecek cihazların isim listesi vb bilgisi) ALINIP ALT SAYFAYA GÖNDERİLEBİLİR.
         *       İŞLETME 2:LİSTVİEW ITEM TIKLANDIĞINDA,İLGİLİ İTEM'a AİT VERİTABANI İNDEX DEĞERİ ALINIR VE SADECE BU DEĞER ALT SAYFAYA GÖNDERİLİR.ALT SAYFADA BU İNDEX'e GÖRE DİĞER BİLGİLER ORADAN ELDE EDİLİR.
         *       DİKKAT:YUKARIDAKİ HER İKİ İŞLETME DE DEĞERLENDİRİLECEK VE KULLANIŞLILIĞA GÖRE BİRİ SEÇİLİP UYGULANACAK.BU YÖNTEM TÜM APP. SAYFALARI İÇİN GEÇERLİDİR.
         */
        visibilityLinearLayoutTimeDivision(switch_DoNotDisturb.isChecked());


        ///Default:@}

        ///Events:@{
        linearLayout_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBack();
            }
        });
        button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBack();
            }
        });
        switch_DoNotDisturb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ///todo: değişen switch durumunu kaydet.

                visibilityLinearLayoutTimeDivision(b);
            }
        });
        linearLayout_GoToAddTimeSegment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddTimeSegment(-1,null);//for add.
            }
        });

        listView_TimeDivision.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //String slct_item = customListAdapterMessageCenterSettings.getItem(i).toString();
                String slct_itemDesc = customListAdapterMessageCenterSettings.getItemDesc(i).toString();
                /**
                 * NOT:
                 */
                Static.CURRENT_SCROLL_POSITION = scrollView_Based.getScrollY();
                goToAddTimeSegment(i,slct_itemDesc);//go to for edit.
            }
        });
        //events:@}



        //for scrollView position[081120191655]:@{
        scrollView_Based.post(new Runnable() {
            @Override
            public void run() {
                if(Static.CURRENT_SCROLL_POSITION == 0)
                    scrollView_Based.pageScroll(View.FOCUS_UP);
                else
                    scrollView_Based.scrollTo(0,Static.CURRENT_SCROLL_POSITION);
            }//run
        });
        //for scrollView position[081120191655]:@}

    }//onCreate


    CustomListAdapter_MessageCenterSettings customListAdapterMessageCenterSettings;
    private void loadListViewTimeDivision(){
        ///todo: these(item,itemDesc) array get from database or JSON.
        String[] item = {"Zaman Bölümünü Rahatsız Etmeyin1",
                         "Zaman Bölümünü Rahatsız Etmeyin2",
                         "Zaman Bölümünü Rahatsız Etmeyin3",
                         "Zaman Bölümünü Rahatsız Etmeyin4",
                         "Zaman Bölümünü Rahatsız Etmeyin5",
                         "Zaman Bölümünü Rahatsız Etmeyin6",
                         "Zaman Bölümünü Rahatsız Etmeyin7",
                         "Zaman Bölümünü Rahatsız Etmeyin8",
                         "Zaman Bölümünü Rahatsız Etmeyin9",
                         "Zaman Bölümünü Rahatsız Etmeyin10"
        };
        String[] itemDesc = {"23:00 - 07:00",//1
                             "20:10 - 07:15",
                             "20:22 - 07:16",//3
                             "20:30 - 06:45",
                             "21:00 - 06:00",//5
                             "22:15 - 05:00",
                             "22:15 - 05:00",//7
                             "22:15 - 05:00",
                             "22:15 - 05:00",//9
                             "22:15 - 05:00"
        };
        String[] repeat = {"Her Gün","Cumartesi,Pazar","Salı"};//not:Ancak bu içerikler string.xml içindeki formata göre alınmalıdır ki Dil değiştiğinde uygun olarak hareket etsin.
        /** todo:!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
         * NOT:Yukarıdaki bilgilerin yanısıra aşağıda yer alan başlıklarda da veri elde edilecek.
         * -"Tekrarla(Repeat)" verileri.(hangi günler).
         * -Bu verileri; seçenek1:Bu sayfada bir liste içinde tut.
         *               seçenek2: "CustomListAdapter_MessageCenterSettings" sayfasına gönder orada liste içinde tut.
         *               Çünkü "ActivityTimeSegment.java" sayfasına "Edit" için gittiğimizde yanımızda götürmemiz gerek.
         *
         */
        List<String> listItem = Arrays.asList(item);
        List<String> listItemDesc = Arrays.asList(itemDesc);
        /*CustomListAdapter_MessageCenterSettings*/ customListAdapterMessageCenterSettings = new CustomListAdapter_MessageCenterSettings(getApplicationContext(),listItem,listItemDesc);
        listView_TimeDivision.setAdapter(customListAdapterMessageCenterSettings);
        //resizeLayoutTimeDivisionHeight(customListAdapterMessageCenterSettings.getCount());
        int count = customListAdapterMessageCenterSettings.getCount();
        GUI gui = new GUI();
        gui.dynamicallySizingLayout(linearLayout_TimeDivision,listView_TimeDivision,customListAdapterMessageCenterSettings,count,count + 20);

    }
   private void clearListViewTimeDivision(){
        listView_TimeDivision.setAdapter(null);
   }

    private void visibilityLinearLayoutTimeDivision(boolean b){
        if(b) {
            linearLayout_TimeDivision.setVisibility(View.VISIBLE);
            linearLayout_GoToAddTimeSegment.setVisibility(View.VISIBLE);
            loadListViewTimeDivision();
        }else {
            linearLayout_TimeDivision.setVisibility(View.GONE);
            linearLayout_GoToAddTimeSegment.setVisibility(View.GONE);
            clearListViewTimeDivision();
        }
    }

    /**
     * Resize LayoutTimeDivisionHeight.
     * @param count listView_TimeDivision item count.
     */
    private void resizeLayoutTimeDivisionHeight(int count){
        ///for Layout Resize:@{
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) linearLayout_TimeDivision.getLayoutParams();
        final int dimension = count + 20;//90;//ATTENTION:do not touch this value. default = 90
        int total_height = dimension;
        int item_count = count;
        View item;
        for(int i=0; i<item_count;i++){
            item = customListAdapterMessageCenterSettings.getView(i,null,listView_TimeDivision);
            item.measure(0,0);
            total_height += item.getMeasuredHeight();
        }
        layoutParams.height = total_height + (1 * (item_count));
        linearLayout_TimeDivision.setLayoutParams(layoutParams);
        linearLayout_TimeDivision.requestLayout();
        layoutParams = (ConstraintLayout.LayoutParams) linearLayout_TimeDivision.getLayoutParams();
        ///for Layout Resize:@}
        //
        ///for Listview resize:@{
        ViewGroup.LayoutParams params2 = listView_TimeDivision.getLayoutParams();
        params2.height = layoutParams.height;
        listView_TimeDivision.setLayoutParams(params2);
        listView_TimeDivision.requestLayout();
        ////for Listview resize:@}

    }

    /**
     * Time Segment eklemek için gidiliyorsa position:-1,slct_value:null olmalıdır.
     * Time Segment düzenlemek(edit) için gidiliyorsa position:ilgili kaydın index değeri,slct_value:seçilen kayıt.
     *
     * @param position
     * @param slct_value
     */
    private void goToAddTimeSegment(int position,String slct_value){
        Intent intent = new Intent(getApplicationContext(),ActivityTimeSegment.class);
        if(slct_value !=null)
            intent.putExtra(BetweenActivities.selected_value,slct_value);//for edit.

        startActivity(intent);
    }

    private void goToBack(){
        Intent intent = new Intent(getApplicationContext(),ActivityMessageCenter.class);
        startActivity(intent);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            goToBack();
        }
        else
            return super.onKeyDown(keyCode, event);

        return false;
    }
}
