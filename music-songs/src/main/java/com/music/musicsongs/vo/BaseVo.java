//package com.music.musicsongs.vo;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.BeanUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @ClassName BaseVo
// * @Description TODO
// * @Author 86183
// * @Date2022-06-089:59
// * @Version 1.0
// **/
//public class BaseVo<T,E> {
//    public List<T> ToVoList(List<E> list,Class source,Class target) throws IllegalAccessException, InstantiationException {
//        source.getClass().newInstance();
//        List<T> t = list.stream().map(item->{
//
//            T t1  = ;
//                    BeanUtils.copyProperties(list,t1);
//            return;
//        })
//        return t;
//    }
//}
