package com.ganghuan.myRPCVersion3.client;


import com.ganghuan.myRPCVersion3.common.RPCRequest;
import com.ganghuan.myRPCVersion3.common.RPCResponse;

/**
 * 不同的网络连接，网络传输方式的客户端分别实现这个接口
 */
public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}
