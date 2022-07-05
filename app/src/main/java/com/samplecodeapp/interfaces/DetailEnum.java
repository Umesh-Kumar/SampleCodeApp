package com.samplecodeapp.interfaces;

import com.samplecodeapp.R;

import java.util.Random;

public class DetailEnum {

    private static final Random RANDOM = new Random();

    public static int getRandomCheeseDrawable() {
        switch (RANDOM.nextInt(7)) {
            default:
            case 0:
                return R.drawable.krishna_1;
            case 1:
                return R.drawable.krishna_2;
            case 2:
                return R.drawable.krishna_3;
            case 3:
                return R.drawable.krishna_4;
            case 4:
                return R.drawable.krishna_5;
            case 5:
                return R.drawable.krishna_6;
            case 6:
                return R.drawable.krishna_7;
        }
    }

}
