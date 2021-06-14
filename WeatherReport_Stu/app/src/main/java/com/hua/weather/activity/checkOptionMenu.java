package com.hua.weather.activity;

import android.view.Menu;

import java.util.HashMap;

public  class checkOptionMenu {
    private static Menu aMenu;
    public void getamenu(Menu menu){
         aMenu = menu;
    }
    public static   void checkOptionMenu(boolean op ){
        if(null != aMenu){
            if(op){
                for (int i = 0; i < aMenu.size(); i++){
                    aMenu.getItem(i).setVisible(true);
                    aMenu.getItem(i).setEnabled(true);
                }
            }else{
                for (int i = 0; i < aMenu.size(); i++){
                    aMenu.getItem(i).setVisible(false);
                    aMenu.getItem(i).setEnabled(false);
                }
            }
        }
    }
}
