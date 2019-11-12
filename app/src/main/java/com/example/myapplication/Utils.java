package com.example.myapplication;

public class Utils {
    public int convertStringToInt(String number){
        switch (number) {
            case "one":
                return 1;
            case "two":
                return 2;
            case "three":
                return 3;
            default:
                return 1;
        }
    }
}
