//package com.music.gateway.filter;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author Aki
// */
//public class BaseContextHandler {
//
//    private static final ThreadLocal<Map<String, Object>> threadContext = ThreadLocal.withInitial(() -> new HashMap<>());
//
//    private BaseContextHandler() {
//
//    }
//
//    /**
//     * 取得thread context Map的实例。
//     *
//     * @return thread context Map的实例
//     */
//    public static Map<String, Object> getContextMap() {
//        return threadContext.get();
//    }
//
//    public static void remove() {
//        getContextMap().clear();
//    }
//
//}
//
