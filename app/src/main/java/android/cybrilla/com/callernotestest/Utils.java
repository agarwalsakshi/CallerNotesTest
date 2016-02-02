package android.cybrilla.com.callernotestest;

/**
 * Created by sakshiagarwal on 01/02/16.
 */

public class Utils {
    public static boolean isSet(int flags, int flag) {
        return (flags & flag) == flag;
    }
}