package com.example.myapplication;

import android.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;


public class News {
    private String title;
    private String content;

    public News(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle(){
        return this.title;
    }

    public String getContent(){
        return this.content;
    }

    public static HashMap<String, News[]>  getNews() {
        HashMap<String, News[]> map = new HashMap<>();

        News[] headlineNews = new News[]{
                new News("Need for national security law in Hong Kong ‘becoming urgent’ ", "Steps must also be taken to prevent ‘foreign forces’ from interfering in city’s affairs, director of Chinese central government’s Hong Kong and Macau Affairs Office says. Rise of independence forces linked to city’s failure to enact Article 23 of the Basic Law, he says"),
                new News("Condition worsens for Hong Kong student injured in car park fall ", "Medical source says pressure formed inside skull of Chow Tsz-lok on the impact of fall has reached a life-threatening level."),
                new News("Wife of man who sparked massive financial probe hurt in knife attack ", "Fifty-five-year-old woman was in a car when another vehicle rammed hers and a masked attacker emerged."),
        };

        News[] chinaNews = new News[]{
                new News("Mainland Chinese students flee Hong Kong fearing they will be attacked next", "Exodus of Hong Kong University of Science and Technology students follows violent reaction from protesters to death of undergraduate Chow Tsz-lok. Mainland Chinese businesses have been vandalised and students from across the border are afraid they will be targeted too"),
                new News("‘Financial war’ is coming next for China, US, ex-minister says",  "Conflict will be characterised by Washington’s use of ‘long-arm jurisdiction’, such as blocking firms like Huawei and ZTE, Lou Jiwei tells forum in Beijing."),
        };

        News[] hongkongNews = new News[]{
                new News("Thousands take advantage of free ferry to escape Tai Po ", "Home Affairs Department lays on service to take people from Tai Po to Wu Kai Sha after protests left town cut off and residents stranded."),
                new News("PLA soldiers sent onto Hong Kong streets for first time since protests began ", "One soldier says action has nothing to do with the Hong Kong government. More than 400 soldiers were deployed just over a year ago to help clear-up operation following Typhoon Mangkhut"),
        };

        News[] techNews = new News[]{
                new News(  "Singles’ Day a litmus test of China’s economic health amid trade war ",  "This year analysts will be watching the headline sales data very closely to see if the world’s second-biggest economy is feeling the strain."),
                new News( "China’s vision for Nasdaq-style tech board draws praises, disdain ",  "The meteoric rise and recent stumble of China’s budding technology board in Shanghai has captivated investors since its beginning one year ago, minting billionaires and burning investors in its infancy.")
        };

        map.put("headline", headlineNews);
        map.put("hong kong", hongkongNews);
        map.put("china", chinaNews);
        map.put("technology", techNews);

        return map;
    }

    // yes sir -> hong kong, headline
    // STATE_HELP , ask section list, ask headline list -> ask section, headline
    // (currentSection) STATE_ASK_NEWS  which news -> 1,2,3, all, hong headline
    // STATE_READING_ONE_NEWS title, content -> next, previous, section
    // STATE_READING_BATCH_NEWS -> (continue)
    // STATE_SETTING
    // STATE_PLAYING_ADV -> (continue)
}

