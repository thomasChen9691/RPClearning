package com.thomas.RpcVersion3.client;

import com.thomas.RpcVersion3.common.RpcRequest;
import com.thomas.RpcVersion3.common.RpcResponse;

public interface RpcClient {
    RpcResponse sendRequest(RpcRequest response);
}
