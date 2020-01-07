package com.example.trident.common;

import android.content.Context;
import android.widget.Toast;

public class Family {
    //TODO:create all family transactions here.  Family,FamilyMember etc.


    private  static Context context;

    public static Family familyInstance;

    private Family(){
        //for getInstance function. so access will be private.
    }

    public static Family getInstance(Context _context)
    {
          context = _context;
          if(familyInstance == null)
              familyInstance = new Family();

        return familyInstance;
    }

   /* public Family(Context _context){
        this.context = _context;
    }*/

    public void setFamilyAdministrator_ON(){
        //TODO: Set as Family Administrator.
        Toast.makeText(context.getApplicationContext(),"set as family Administrator_ON",Toast.LENGTH_SHORT).show();
    }
    public void setFamilyAdministrator_OFF(){
        //TODO: Set as Family Not Administrator.
        Toast.makeText(context.getApplicationContext(),"set as family NOT Administrator_OFF",Toast.LENGTH_SHORT).show();
    }

    public boolean removeFamily()
    {
        //TODO: Remove Family...
        return true; //if removing is success return true,else return false.
    }
    public boolean removeMember(){
        //TODO: Remove family member.
        return true;//if removing is success return true,else return false.
    }


}
