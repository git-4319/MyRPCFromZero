package com.ganghuan.myRPCVersion2.server;



import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 存放服务接口名与服务端对应的实现类
 * 服务启动时要暴露其相关的实现类
 * 根据request中的interface调用服务端中相关实现类
 */
public class ServiceProvider {
    /**
     * 一个实现类可能实现多个接口
     */
    private Map<String, Object> interfaceProvider;

    public ServiceProvider(){
        this.interfaceProvider = new HashMap<>();
    }

    public void provideServiceInterface(Object service){
        /**
         * 返回由该对象表示的类或接口直接实现的接口。
         * 如果此对象表示一个类，则返回值是一个数组，其中包含表示该类直接实现的所有接口的对象
         */
        Class<?>[] interfaces = service.getClass().getInterfaces();//getClass():返回此对象的运行时类

        for(Class clazz : interfaces){
            interfaceProvider.put(clazz.getName(),service);
        }

    }

    public Object getService(String interfaceName){
        return interfaceProvider.get(interfaceName);
    }
}
