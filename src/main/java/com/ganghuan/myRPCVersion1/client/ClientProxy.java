package com.ganghuan.myRPCVersion1.client;

import com.ganghuan.myRPCVersion1.common.RPCRequest;
import com.ganghuan.myRPCVersion1.common.RPCResponse;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@AllArgsConstructor
public class ClientProxy implements InvocationHandler {
    // 传入参数Service接口的class对象，反射封装成一个request
    private String host;
    private int port;


    // jdk 动态代理， 每一次代理对象调用方法，会经过此方法增强（反射获取request对象，socket发送至客户端）

    /**
     * subject.SayHello("jiankunking")这句话时，会自动调用InvocationHandlerImpl的invoke方法
     * Java 动态代理，具体有如下四步骤：
     * 1.通过实现 InvocationHandler 接口创建自己的调用处理器；
     * 2.通过为 Proxy 类指定 ClassLoader 对象和一组 interface 来创建动态代理类；
     * 3.通过反射机制获得动态代理类的构造函数，其唯一参数类型是调用处理器接口类型；
     * 4.通过构造函数创建动态代理类实例，构造时调用处理器对象作为参数被传入。
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // request的构建，使用了lombok中的builder，代码简洁
        RPCRequest request = RPCRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args)
                .paramsTypes(method.getParameterTypes()).build();
        //数据传输
        RPCResponse response = IOClient.sendRequest(host, port, request);
        //System.out.println(response);
        return response.getData();
    }
    <T>T getProxy(Class<T> clazz){
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T)o;
    }
}
