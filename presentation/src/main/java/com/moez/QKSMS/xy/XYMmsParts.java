package com.moez.QKSMS.xy;
import android.text.TextUtils;

import com.moez.QKSMS.model.Message;
import com.moez.QKSMS.model.MmsPart;
import com.xy.bizport.rcs.ui.utils.ExtralSmailParam;
import com.xy.bizport.rcs.ui.widget.mms.data.MmsParts;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.RealmList;

/**
 * Created by xueyakun on 2019/4/23.
 */

class XYMmsParts extends MmsParts {





    public static final String pattern="-";
    public boolean isUnionMms(Message message) {

        RealmList<com.moez.QKSMS.model.MmsPart> parts = message.getParts();
        String smail="";

        for (com.moez.QKSMS.model.MmsPart part: parts){
            if (MmsParts.isSmailType(part.getType())){
                smail=part.getText();
            }
        }
       return super.isUnionMms(smail);
    }

    /**
     * md5("9ff8c2073faa81838f81b99ec436e4b8" + date).toLowerCase()
     * date为 Integer(Date).toString();
     */
    public boolean verifyMmsCode(Message message){
        RealmList<com.moez.QKSMS.model.MmsPart> parts = message.getParts();
        String smail="";
        for (com.moez.QKSMS.model.MmsPart part: parts){
            if (MmsParts.isSmailType(part.getType())){
                smail=part.getText();
            }
        }
        String date=new  Integer((int) message.getDateSent()).toString();
        String veriftycode=md5(date+"9ff8c2073faa81838f81b99ec436e4b8").toLowerCase();
        return smail.equals(veriftycode)?true:false;
    }

    private  String getPattern(String str) {
        String dest="";
        if (TextUtils.isEmpty(str))
            return null;
        Pattern pattern=Pattern.compile("-");
        Matcher matcher = pattern.matcher(str);
        dest = matcher.replaceAll("");
        return dest;
    }


    void transitionParts(RealmList<com.moez.QKSMS.model.MmsPart> mmsParts, String subject, long contentId) {
        this.subject = subject;
        this.mid = contentId;
        for (com.moez.QKSMS.model.MmsPart part: mmsParts) {
            MmsPart mmsPart = new MmsPart();
            mmsPart.text =  part.getText();
            mmsPart.type = transitionType(part.getType());
            mmsPart.uri = part.getUri();
            mMmsPartList.add(mmsPart);
        }
    }

    private int transitionType(String type) {
        int xyType = UNKNOW_TYPE;
        if(MmsParts.isVideoType(type)){
            xyType = VIDEO_TYPE ;
        }else if(MmsParts.isImageType(type)){
            xyType = IMAGE_TYPE ;
        }else if (MmsParts.isTextType(type)){
            xyType = TEXT_TYPE ;
        }else if(MmsParts.isSmailType(type)){
            xyType = SMAIL_TYPE;
        }
        return xyType;
    }

    public static String md5(String sourceStr) {
        String s = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            //这两行代码的作用是：
            // 将bytes数组转换为BigInterger类型。1，表示 +，即正数。
            BigInteger bigInt = new BigInteger(1, md.digest(sourceStr.getBytes()));
            // 通过format方法，获取32位的十六进制的字符串。032,代表高位补0 32位，X代表十六进制的整形数据。
            //为什么是32位？因为MD5算法返回的时一个128bit的整数，我们习惯于用16进制来表示，那就是32位。
            s = String.format("%032x", bigInt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return s;
    }
}
