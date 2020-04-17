package org.prepare.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StandardStringUtility {

    public static final String REGEX_PHONE = "(09[0-9]|01[2|6|8|9]|03[2-9]|07[0|9|7|6|8]|08[3|4|5|1|2]|05[6|8|9])+([0-9]{7})|(09[0-9]|01[2|6|8|9]|03[2-9]|07[0|9|7|6|8]|08[3|4|5|1|2]|05[6|8|9])+([0-9]{4}\\*\\*\\*)";
    public static final String REGEX_EMAIL = "^(.+)@(.+)$|email";
    public static final String REGEX_LH = "lh|liên hệ|liên lạc";
    public static final String REGEX_REMOVE_NUMBER = "[0-9]";
    public static final String REGEX_UP_STAIR = "([1-9]*\\s*(tầng|tang|lầu|lau|trệt|tret))";
    public static final String REGEX_UP_PN_WC = "([1-9]+\\s*phòng\\s*ngủ)|([1-9]+\\s*pn)|([1-9]+\\s*phong\\s*ngu)|([1-9]+\\s*pn)|([1-9]+\\s*wc)|([1-9]+\\s*vệ sinh)|([1-9]+\\s*toilet)";
    public static final String REGEX_PRICE = "(([1-9]*)(\\s*)(tỷ|tr|triệu|ty|VNĐ))|(giá|giá chỉ)((\\s*)(:)*(\\s*)(([1-9]*)(\\s*)(tỷ|tr|triệu|ty|VNĐ)))*";
    public static final String REGEX_ADDRESS = "(địa chỉ)|(đc)|(dc)|(khu vực)|(kv)|(vị trí)|(vt)";
    public static final String REGEX_TIME = "([0-9]){2}(-|\\\\/)([0-9]){2}(-|\\\\/)([0-9])*";
    public static final String REGEX_SUFACE = "(dt)(\\s*)([0-9]*)|(diện tích)(\\s*)([0-9]*)|(dtsd)(\\s*)([0-9]*)|(diện tích sử dụng)(\\s*)([0-9]*)|([1-9]+\\s*m2)|([1-9]+\\s*(m|m2)*\\s*x\\s*[1-9]+(m2|t|m)*)|" +
                                                "(diện tích)(\\s*)(:)*(\\s*)([0-9]*)|(diện tích sử dụng)(\\s*)(:)*(\\s*)([0-9]*)|" +
                                                "(diện tích)(\\s*)(:)*(\\s*)(không xác định)";

    public static final String REGEX_REMOVE = "[\\{\\}!@_✓=;\\?“|–\",:.()+/\\*\\-♻️☀%]";
    public static final String REGEX_REMOVE_SPACE_AMONG_NUMBER_PHONE = "(?:\\s+)(?=[0-9])";
    public static final String IDOT = "...|. ";
    public static final String REGEX_SPACE = "\\s+";




    public static String getStringFeatures(String in) {
        if (in == null)
            return "";
        // normal strings to lower case
        String out = in.toLowerCase();
        // extract feature into string
        out = extractFeatures(out);
        // remove special characters
        out = out.replaceAll(REGEX_REMOVE, " ");
        out = out.replaceAll(REGEX_REMOVE_SPACE_AMONG_NUMBER_PHONE, "");
        // remove number
        out = out.replaceAll(REGEX_REMOVE_NUMBER, " ");
        // replace multi space close to one space
        out = out.replaceAll(REGEX_SPACE, " ");
        // trim space start and end
        out = out.trim();
        return out;
    }

    public static String extractFeatures(String in) {
        String out = in;
        Pattern pattern = Pattern.compile(REGEX_PHONE);
        Matcher matcher = pattern.matcher(in);
        if (matcher.find()) {
            out += " featurephone";
        }

        pattern = Pattern.compile(REGEX_EMAIL);
        matcher = pattern.matcher(in);
        if (matcher.find()) {
            out += " featureEmail";
        }

        pattern = Pattern.compile(REGEX_LH);
        matcher = pattern.matcher(in);
        if (matcher.find()) {
            out += " featureLH";
        }

        pattern = Pattern.compile(REGEX_UP_PN_WC);
        matcher = pattern.matcher(in);
        if (matcher.find()) {
            out += " featurePhong";
        }

        pattern = Pattern.compile(REGEX_PRICE);
        matcher = pattern.matcher(in);
        if (matcher.find()) {
            out += " featurePrice";
        }

        pattern = Pattern.compile(REGEX_ADDRESS);
        matcher = pattern.matcher(in);
        if (matcher.find()) {
            out += " featureAddress";
        }

        pattern = Pattern.compile(REGEX_TIME);
        matcher = pattern.matcher(in);
        if (matcher.find()) {
            out += " featureTime";
        }

        pattern = Pattern.compile(REGEX_SUFACE);
        matcher = pattern.matcher(in);
        if (matcher.find()) {
            out += " featuresuface";
        }

        pattern = Pattern.compile(REGEX_UP_STAIR);
        matcher = pattern.matcher(in);
        if (matcher.find()) {
            out += " featureupstair";
        }
        return out;
    }

    public static void main(String args[]) {
        String out = getStringFeatures("092.133.1235 dt 23m2");
        System.out.println(out);

    }

}
