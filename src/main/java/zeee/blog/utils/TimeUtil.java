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
 */
public class TimeUtil {

    private static final Logger log = LoggerFactory.getLogger(TimeUtil.class);

    /**
     * 获取当天指定时间（时分秒）的时间戳
     * @param time 指定时间，格式：HH:mm:ss
     * @return 时间戳
     */
    public static long getTimestamp(String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
            return curDate.getTime();
        } catch(Exception e) {
            log.error("getTimeMillis error", e);
        }
        return 0;
    }

    /**
     * 时间戳转化为date格式的时间
     * @param timestamp 时间戳
     * @return string格式的时间，格式为 yyyy-MM-dd HH:mm:ss
     */
    public static String covertTimestamp2Date(String timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = Long.parseLong(timestamp);
        Date date = new Date(time);
        return simpleDateFormat.format(date);
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
