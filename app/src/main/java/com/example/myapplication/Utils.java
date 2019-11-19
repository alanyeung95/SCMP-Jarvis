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
                return 0;
        }
    }
    public String getSectionByFragmentID(int fragmentID) {
        if (fragmentID == R.id.nav_hong_kong) {
            return MainActivity.HONGKONG_SECTION;
        } else if (fragmentID == R.id.nav_china) {
            return MainActivity.CHINA_SECTION;
        }else if (fragmentID == R.id.nav_asia) {
            return MainActivity.ASIA_SECTION;
        }else if (fragmentID == R.id.nav_world) {
            return MainActivity.WORLD_SECTION;
        }else if (fragmentID == R.id.nav_business) {
            return MainActivity.ECONOMY_SECTION;
        }else if (fragmentID == R.id.nav_economy) {
            return MainActivity.ECONOMY_SECTION;
        }else if (fragmentID == R.id.nav_tech) {
            return MainActivity.TECHNOLOGY_SECTION;
        }else
            return MainActivity.HEADLINE_SECTION;
    }

    public int getFragmentIDBySection(String section){
        switch (section) {
         //   case MainActivity.HEADLINE_SECTION:
          //      return R.id.nav_home;
            case MainActivity.HONGKONG_SECTION:
                return R.id.nav_hong_kong;
            case MainActivity.CHINA_SECTION:
                return R.id.nav_china;
            case MainActivity.ASIA_SECTION:
                return R.id.nav_asia;
            case MainActivity.WORLD_SECTION:
                return R.id.nav_world;
            case MainActivity.BUSINESS_SECTION:
                return R.id.nav_business;
            case MainActivity.ECONOMY_SECTION:
                return R.id.nav_economy;
            case MainActivity.TECHNOLOGY_SECTION:
                return R.id.nav_tech;
            default:
                return R.id.nav_home;
        }
    }
}