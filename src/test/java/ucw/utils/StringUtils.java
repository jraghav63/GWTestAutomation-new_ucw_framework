package ucw.utils;

public class StringUtils {
    public static void main(String[] args) {
        String str="Quote (0001021359)";
        String numberOnly= str.replaceAll("\\D", "");
        System.out.println(numberOnly);
    }
}
