package com.music.gateway.predicate;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName NameRoutePredicateConfig
 * @Description TODO
 * @Author 86183
 * @Date2022-06-179:13
 * @Version 1.0
 **/
@Data
public class NameRoutePredicateConfig implements Serializable {
    private static final long serialVersionUID = -3289515863427972825L;
    private String name;
}
