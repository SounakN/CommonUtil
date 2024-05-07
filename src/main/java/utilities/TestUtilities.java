package utilities;


import com.google.common.primitives.Chars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class TestUtilities {
    private TestUtilities(){

    }
    public static List<String> getOtpInListForMobile(){
        List<String> otpList = new ArrayList<>();
        PropertyUtil.getProperties().get("MobileOtp").toString().chars().forEach(val ->{
            otpList.add(Character.toString((char)val));
        });
        return otpList;
    }
}
