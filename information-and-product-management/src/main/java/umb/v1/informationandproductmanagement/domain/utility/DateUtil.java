package umb.v1.informationandproductmanagement.domain.utility;

import umb.v1.informationandproductmanagement.domain.exception.ApiException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static umb.v1.informationandproductmanagement.domain.utility.Constant.DATE_FORMAT;

public class DateUtil {

    private DateUtil() {
    }

    public static Date getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {
            Date currentDate = new Date();
            String currentDateFormatted = sdf.format(currentDate);
            return sdf.parse(currentDateFormatted);
        } catch (ParseException e) {
            throw new ApiException("No fue posible obtener la fecha actual...", 500);
        }
    }

}
