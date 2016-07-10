/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keti.tas.soft;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author ChenNan
 */
public class KoreaTimeZone {
    public static String getDisplayTimeNow(){
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String strTime = format.format(new Date());
        return  strTime;
    }
    
    public static String getTimeNowNoSpace(){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String strTime = format.format(new Date());
        return  strTime;
    }
}
