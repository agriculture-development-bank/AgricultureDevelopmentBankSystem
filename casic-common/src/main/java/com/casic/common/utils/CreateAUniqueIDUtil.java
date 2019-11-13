package com.casic.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CreateAUniqueIDUtil {

    /**
     * 图片id
     *
     * @param subtype
     * @return
     */
    public String ImageID(String subtype) {

        /** 调用雪花算法生成18位唯一id **/
        SnowFlakeUtil snowFlake = new SnowFlakeUtil(2, 3);

        /** 18位唯一id  */
        long randomNumber18 = snowFlake.nextId();

        /** 生成随机数 */
        Random rand = new Random();
        String cardNnumer = "";
        for (int a = 0; a < 2; a++) {
            cardNnumer += rand.nextInt(10);//生成2位随机数
        }
//        String subtype = "01";//01代表的是对应应用中解析时的参数，当前01代表 人员
        String randomNumberString = "";
        for (int a = 0; a < 5; a++) {
            randomNumberString += rand.nextInt(10);//生成5位数字
        }
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = df.format(date);
        String ImageID = randomNumber18 + cardNnumer + subtype + format + randomNumberString;
        return ImageID;
    }

    /**
     * 用户id
     *
     * @param subtype
     * @return
     */
    public String PersonID(String subtype) {
        SnowFlakeUtil snowFlake = new SnowFlakeUtil(2, 3);//调用雪花算法生成18位唯一id
        long randomNumber18 = snowFlake.nextId();//18位唯一id
        Random rand = new Random();//生成随机数
        String cardNnumer = "";
        for (int i = 0; i < 23; i++) {
            cardNnumer += rand.nextInt(10);//生成23位随机数
        }
//        String subtype = "01";//01-人员
        String randomNumberString = "";
        for (int a = 0; a < 5; a++) {
            randomNumberString += rand.nextInt(10);//生成5位数字
        }
        String PersonID = randomNumber18 + cardNnumer + subtype + randomNumberString;
        return PersonID;
    }

    public String SourceID(String subtype) {
        SnowFlakeUtil snowFlake = new SnowFlakeUtil(2, 3);//调用雪花算法生成18位唯一id
        long randomNumber18 = snowFlake.nextId();//18位唯一id
        Random rand = new Random();//生成随机数
        String cardNnumer = "";
        for (int i = 0; i < 2; i++) {
            cardNnumer += rand.nextInt(10);//生成2位随机数
        }
//        String subtype = "02";//01-人员02-机动车03-非机动车04-物品05-场景06-人脸等
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = df.format(date);
        String randomNumberString = "";
        for (int a = 0; a < 5; a++) {
            randomNumberString += rand.nextInt(10);//生成5位数字
        }
        String SourceID = randomNumber18 + cardNnumer + subtype + format + randomNumberString;
        return SourceID;
    }
}
