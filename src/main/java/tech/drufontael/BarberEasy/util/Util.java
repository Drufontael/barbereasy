package tech.drufontael.BarberEasy.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

    public static Boolean emailValidator(String email){
        Matcher matcher= pattern.matcher(email);
        return matcher.matches();
    }

    public static void copyNonNullProperties(Object source,Object target){
        BeanUtils.copyProperties(source,target,getNullPropertyName(source));
    }
    public static String[] getNullPropertyName(Object source){
        final BeanWrapper src=new BeanWrapperImpl(source);
        PropertyDescriptor[] pds=src.getPropertyDescriptors();
        Set<String> empty=new HashSet<>();
        for(PropertyDescriptor pd:pds){
            Object srcValue=src.getPropertyValue(pd.getName());
            if(srcValue==null){
                empty.add(pd.getName());
            }
        }
        String[] result=new String[empty.size()];
        return empty.toArray(result);
    }
}
