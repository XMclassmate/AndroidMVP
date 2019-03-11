package lib.xm.mvp.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class GsonUtils {

    private volatile static GsonUtils jsonUtilInstance;
    private volatile static Gson gson;

    static {
        getInstance();
        init();
    }

    private GsonUtils() {
    }

    public synchronized static void init() {
        if (gson == null) {
            synchronized (GsonUtils.class) {
                if (gson == null) {
                    gson = new Gson();
                }
            }
        }
    }

    private synchronized static GsonUtils getInstance() {
        if (jsonUtilInstance == null) {
            synchronized (GsonUtils.class) {
                if (jsonUtilInstance == null) {
                    jsonUtilInstance = new GsonUtils();
                }
            }
        }
        return jsonUtilInstance;
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static <T> T fromJson(String json, Type type) {
        return gson.fromJson(json, type);
    }

    public static <T> String toJson(T data) {
        return gson.toJson(data);
    }


}
