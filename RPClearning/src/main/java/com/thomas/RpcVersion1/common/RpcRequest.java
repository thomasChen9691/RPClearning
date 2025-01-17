package com.thomas.RpcVersion1.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 在上个例子中，我们的Request仅仅只发送了一个id参数过去，这显然是不合理的，
 * 因为服务端不会只有一个服务一个方法，因此只传递参数服务端不会知道调用那个方法
 * 因此一个RPC请求中，client发送应该是需要调用的Service接口名，方法名，参数，参数类型
 * 这样服务端就能根据这些信息根据反射调用相应的方法
 * 使用java自带的序列化方式（实现接口）
 */
@Data
@Builder
public class RpcRequest implements Serializable {
    // 服务类名，客户端只知道接口名，在服务端中用接口名指向实现类//service name
    private String interfaceName;
    // 方法名//class names
    private String methodName;
    // 参数列表// args list
    private Object[] params;
    // 参数类型//args type
    private Class<?>[] paramsTypes;
}