package org.hibernate.tutorial.hbm;

/**
 * User: wenzhihong
 * Date: 10/23/13
 * Time: 1:53 PM
 */
public class Util {
    public static void startFlag() {
        System.out.println("###########################################################################################");
    }

    public static void endFlag() {
        System.out.println("###########################################################################################");
    }

    public static void separateFlag() {
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    }

    public static void log(String msg) {
        System.out.println("--" + msg);
    }
}
