package com.dev.yarulin.smstotime.models;

/**
 * Created by Yarulin on 28.12.2017.
 */

public class DateFormatPattern {
    private final int id;
    private final String desc;
    private final String regexPattern;


   public DateFormatPattern(int id, String desc,String regexPattern){

        this.id = id;
        this.desc = desc;
        this.regexPattern = regexPattern;
    }

    public int getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public String getRegexPattern() {
        return regexPattern;
    }

    /*
    * sample regex:
    * (0*[1-9]|[12][0-9]|3[01])[- /.](0*[1-9]|1[012])[- /.]((19|20)\d\d|\d\d)
    *
    * dd = (0*[1-9]|[12][0-9]|3[01])
    * MM = (0*[1-9]|1[012])
    * yyyyy  = (19|20)\d\d
    * yy = \d\d
    * delimiter = [- /.]
    * */
}
