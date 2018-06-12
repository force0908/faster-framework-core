package cn.faster.framework.core.utils;

import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.DigestUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by zhangbowen on 2015/12/4.
 */
public class Utils {
    public static final int MINUTE = 60;
    public static final int HOUR = MINUTE * 60;

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static int random(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 根据手机号生成用户名
     *
     * @param phone
     * @return
     */
    public static String generateNameByPhone(String phone) {
        return phone.substring(0, 3) + "****" + phone.substring(7);
    }

    /**
     * 生成短信验证码
     *
     * @return
     */
    public static String generateSmsCode() {
        return String.valueOf((int) (Math.random() * 9000) + 1000);
    }

    public static Class getSuperClassGenericType(final Class clazz, final int index) {
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        //返回表示此类型实际类型参数的 Type 对象的数组。
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }

    /**
     * 生成md5
     *
     * @param str 要加密的内容
     * @return
     */
    public static String md5(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    /**
     * bean转map
     *
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 排序签名
     *
     * @param o      要签名的对象
     * @param secret 秘钥
     */
    public static <T> String signWithSort(T o, String secret) {
        Map<String, Object> map = beanToMap(o);
        String signStr = map.keySet().stream().sorted()
                .map(key -> key.concat("=").concat(map.get(key) + ""))
                .collect(Collectors.joining("&"));
        return md5(signStr + secret);
    }
}
