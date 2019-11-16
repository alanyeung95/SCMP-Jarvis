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



/**
 navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
@Override public boolean onNavigationItemSelected(MenuItem item) {
Log.e("123", String.valueOf(item.getItemId()));
//getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, HongKongFragment.newInstance("123")).commit();
drawer.closeDrawer(Gravity.LEFT);
return true;
}
});
 **/

/**
 navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
@Override public boolean onNavigationItemSelected(MenuItem item) {
getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, HongKongFragment.newInstance("123")).commit();
drawer.closeDrawer(Gravity.LEFT);
return true;
}
}); //hoi

 ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
 R.string.navigation_drawer_open, R.string.navigation_drawer_close);
 drawer.addDrawerListener(toggle);
 toggle.syncState();

 getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
 new HomeFragment()).commit();
 navigationView.setCheckedItem(R.id.nav_home);
 **/

/**
 Intent installIntent = new Intent();
 installIntent.setAction(
 TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
 startActivity(installIntent);

 **/

/**
 Set<String> a=new HashSet<>();
 a.add("female");//here you can give male if you want to select male voice.
 Voice v= null;
 if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
 v = new Voice("en-us-x-sfg#female_2-local",new Locale("en","US"),800,200,true,a);
 textToSpeech.setVoice(v);
 Log.e("asdf", "setting");
 Log.e("asdf", textToSpeech.getVoice().getFeatures().toString());

 }
 **/