package driver;

public enum Mobiles {
    ANDROID_WEB,
    ANDROID_NATIVE;

    public static Mobiles get(String mobile) {
        return Mobiles.valueOf(mobile.trim().toUpperCase());
    }
}
