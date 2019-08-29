package com.seatig.utils;

import com.quanaxy.state.GrantsState;
import com.seatig.domain.GrantState;
import net.corda.core.contracts.StateAndRef;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.fasterxml.jackson.databind.util.ISO8601Utils.format;

public class Utils {

    /**
     * 精确到毫秒的完整时间    如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";

    /**
     * 获取properties文件的信息
     */
    public static String get(String key, String configPath) throws FileNotFoundException {
        Properties properties = new Properties();
        InputStream in = new BufferedInputStream(new FileInputStream(configPath));
        try {
            properties.load(in);
            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @throws
     * @Title:把文件写入磁盘
     * @Description: ${todo}
     * @param: ${tags}
     * @return: ${return_type}
     */
    public static void fileToDisk(BufferedOutputStream bos, InputStream is) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(is);
        byte[] buff = new byte[1024 * 8];
        int len = 0;
        while ((len = bis.read(buff)) > 0) {
            bos.write(buff, 0, len);
        }
        if (bis != null) {
            bis.close();
        }
        if (bos != null) {
            bos.close();
        }

    }


    /**
     * @throws
     * @Title: 获取系统10位时间戳
     * @Description: ${todo}
     * @param: ${tags}
     * @return: ${return_type}
     */
    public static String getTime() {

        long time = System.currentTimeMillis() / 1000;//获取系统时间的10位的时间戳

        String str = String.valueOf(time);

        return str;

    }


    /**
     * 获取当前时间
     */
    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 获取日期年份
     *
     * @param date 日期
     * @return
     */
    public static int getYear(Date date, int amount) {

        return Integer.parseInt(format(date).substring(0, 4)) - amount;
    }

    /**
     * 功能描述：返回月
     *
     * @param date Date 日期
     * @return 返回月份
     */
    public static String getMonth(Date date, int amount) {

        //String[] month = {"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
        String[] month = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int w = 0;
        w = Calendar.MONTH + amount;
        if (w < 0) {
            w = month.length + w;
        }
        return month[w];
    }

    /**
     * 功能描述：返回日期
     *
     * @param date Date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 功能描述：返回小时
     *
     * @param date 日期
     * @return 返回小时
     */
    public static int getHour(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, amount);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }


    public static List<GrantState> toListGrantState(List<StateAndRef<GrantsState>>  states){
        List<GrantState> list=new ArrayList<>();
        for (StateAndRef state : states
        ) {
            GrantState s = new GrantState();
            GrantsState grantState = (GrantsState) state.getState().getData();
            if (grantState.getAgent()!=null){
                s.setAgent(grantState.getAgent().getName().toString());
            }
            if (grantState.getTradingCounterparty()!=null){
                s.setTradingCounterparty(grantState.getTradingCounterparty().getName().toString());
            }
            if (grantState.getFeePayer()!=null){
                s.setFeePayer(grantState.getFeePayer().getName().toString());
            }
            if (grantState.getTrasanctionFees()!=null){
                s.setFaceValue(grantState.getFaceValue().toDecimal());
            }
            s.setMonitor(grantState.getMonitor().getName().toString());
            s.setLinearId(grantState.getLinearId().toString());
            s.setOwner(grantState.getOwner().getName().toString());
            s.setTotalTokenizationAmount(grantState.getTotalTokenizationAmount());
            s.setTradingAmount(grantState.getTradingAmount());
            s.setTrasanctionFees(grantState.getTrasanctionFees());
            s.setStartTime(grantState.getStartTime().toString());
            list.add(s);
        }
        return list;
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param date
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date date, int amount) {
        String[] weekDays = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - amount;
        if (w < 0)
            //w==-1
            w = weekDays.length + w;
        return weekDays[w];
    }


    /**
     * 功能描述：返回分
     *
     * @param date 日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }


}
