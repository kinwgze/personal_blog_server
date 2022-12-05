package zeee.blog.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wz
 * @date 2022/8/24
 *
 * 注意：此类里面的所有方法使用的时间戳都是以**毫秒**为单位！！！
 */
public class TimeUtil {

    private static final Logger log = LoggerFactory.getLogger(TimeUtil.class);

    /**
     * 获取当天指定时间（时分秒）的时间戳
     * @param day 指定天，格式：yyyy-MM-dd
     * @param time 指定时间，格式：HH:mm:ss
     * @return 时间戳
     */
    public static Long getTimestamp(String day, String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curDate = dateFormat.parse(day + " " + time);
            return curDate.getTime();
        } catch(Exception e) {
            log.error("getTimeMillis error", e);
        }
        return null;
    }

    /**
     * 时间戳转化为date格式的时间
     * @param timestamp 时间戳
     * @return string格式的时间，格式为 yyyy-MM-dd HH:mm:ss
     */
    public static Date covertTimestamp2Date(String timestamp) {
        long time = Long.parseLong(timestamp);
        return new Date(time);
    }

    /**
     * String格式的时间转化为时间戳
     * @param date String格式的时间
     * @return 时间戳
     */
    public static long covertDate2Timestamp(String date) {
        //时间转为时间戳
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            Date date2 = simpleDateFormat.parse(date);
            return date2.getTime();
        }catch (ParseException e){
            e.printStackTrace();
        }
        return 0;
    }
}
