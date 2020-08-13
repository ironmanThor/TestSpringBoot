package com.example.springboot.time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * 日期转换规则，默认时间戳转Date
 * 也可以支持大部分时间格式
 * @author szz
 */
@Component
public class DateConverterConfig {

    @Value("${date.converter.enableAll:true}")
    private boolean enableAll;


    private static ThreadLocal<SimpleDateFormat[]> formats = new ThreadLocal<SimpleDateFormat[]>() {
        @Override
        protected SimpleDateFormat[] initialValue() {
            return new SimpleDateFormat[]{
                    new SimpleDateFormat("yyyy-MM"),
                    new SimpleDateFormat("yyyy-MM-dd"),
                    new SimpleDateFormat("yyyy-MM-dd HH"),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm"),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            };
        }
    };

    /**
     * string 转 Date 针对 C
     * @return
     */
    @Bean
    public Converter<String, Date> stringDateConverter(){
        return new Converter<String,Date>(){
            @Override
            public Date convert(String source) {
                if (StringUtils.isBlank(source)) {return null;}
                try {
                    return new Date(Long.parseLong(source));
                } catch (Exception e) {
                    if (enableAll){
                        return AllParseDate(source);
                    }
                }
                finally {
                    formats.remove();
                }
                return null;
            }
        };
    }


    /**
     * Long 转 Date 针对 B
     * @return
     */
    @Bean
    public Converter<Long, Date> LongDateConverter(){
        return new Converter<Long,Date>(){
            @Override
            public Date convert(Long source) {
                if (null == source) {return null;}
                try {
                    return new Date(source);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    formats.remove();
                }
                return null;
            }
        };
    }

    private Date AllParseDate(String source){
        if (source.matches("^\\d{4}-\\d{1,2}$")) {
            return parseDate(source, formats.get()[0]);
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
            return parseDate(source, formats.get()[1]);
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}$")) {
            return parseDate(source, formats.get()[2]);
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
            return parseDate(source, formats.get()[3]);
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            return parseDate(source, formats.get()[4]);
        }
        return null;
    }


    /**
     * 格式化日期
     *
     * @param dateStr    String 字符型日期
     * @param dateFormat 日期格式化器
     * @return Date 日期
     */
    public Date parseDate(String dateStr, DateFormat dateFormat) {
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     *
     */
}
