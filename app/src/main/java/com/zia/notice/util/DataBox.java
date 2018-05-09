package com.zia.notice.util;

import com.zia.notice.database.drug.Drug;
import com.zia.notice.database.electric.Electric;
import com.zia.notice.database.food.Food;

/**
 * Created by zia on 2018/5/8.
 */
public class DataBox {

    private static boolean d;
    private static boolean e;
    private static boolean f;

    private static Drug drug;
    private static Electric electric;
    private static Food food;

    public static Drug getDrug() {
        if (d) {
            d = false;
            return drug;
        } else
            return null;
    }

    public static void setDrug(Drug drug) {
        DataBox.drug = drug;
        d = true;
    }

    public static Electric getElectric() {
        if (e){
            d = false;
            return electric;
        }else
            return null;
    }

    public static void setElectric(Electric electric) {
        DataBox.electric = electric;
        e = true;
    }

    public static Food getFood() {
        if (f){
            f = false;
            return food;
        }else
            return null;
    }

    public static void setFood(Food food) {
        DataBox.food = food;
        f = true;
    }
}
