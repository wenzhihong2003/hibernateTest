package cfg;

/**
 * User: wenzhihong
 * Date: 10/23/13
 * Time: 1:53 PM
 */
public class Util {
    public static void startFlag(String msg) {
        System.out.print("##########################################");
        System.out.print(msg);
        System.out.println("#################################################");
    }

    public static void startFlag() {
        System.out.println("###########################################################################################");
    }

    public static void endFlag() {
        System.out.println("###########################################################################################");
    }

    public static void endFlag(String msg) {
        System.out.print("##########################################");
        System.out.print(msg);
        System.out.println("#################################################");
    }

    public static void separateFlag() {
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    }

    public static void log(String msg) {
        System.out.println("--" + msg);
    }
}
