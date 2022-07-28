package com.wei.blogservice.utils;

import java.util.Random;

public class ImgUtil {

    private static String[] avatars = new String[]{
            "http://m.imeitou.com/uploads/allimg/210816/3-210Q6104144-lp.jpg",
    "http://m.imeitou.com/uploads/allimg/210717/3-210GG54017-lp.jpg",
    "http://m.imeitou.com/uploads/allimg/210717/3-210GG61447-lp.jpg",
    "http://m.imeitou.com/uploads/allimg/210713/3-210G3094G0-lp.jpg",
    "http://m.imeitou.com/uploads/allimg/210707/3-210FG53133-lp.jpg",
    "http://m.imeitou.com/uploads/allimg/210604/3-210604105216-lp.jpg",
    "http://m.imeitou.com/uploads/allimg/210604/3-210604155539-lp.jpg",
    "http://m.imeitou.com/uploads/allimg/210604/3-210604154032-lp.jpg",
    "http://m.imeitou.com/uploads/allimg/210604/3-210604153H5-lp.jpg"
    };

    private static Random random = new Random();

    public static String getAvatar(){
        return avatars[random.nextInt(avatars.length)];
    }

}
