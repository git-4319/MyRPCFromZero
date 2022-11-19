package com.ganghuan.myRPCVersion0.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder//@Builder主要作用是用来生成对象，并能够进行链式赋值  //例如：Test t = Test.builder().A("a").B("b").C("c").build();
@Data
@NoArgsConstructor//lombok下的注解：作用于类，生成一个无参构造方法
@AllArgsConstructor//使用后添加一个构造函数，该构造函数含有所有已声明字段属性参数
public class User implements Serializable {
    // 客户端和服务端共有的
    private Integer id;
    private String userName;
    private Boolean sex;
}
