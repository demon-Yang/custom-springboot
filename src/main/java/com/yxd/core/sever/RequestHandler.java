package com.yxd.core.sever;

import com.yxd.core.constant.SystemContants;
import com.yxd.core.factory.BeanFactory;
import com.yxd.core.factory.RouteFactory;
import com.yxd.core.util.KryoUtil;
import com.yxd.core.util.ParameterNameUtil;
import com.yxd.core.util.PropertyEditorUtil;
import com.yxd.core.util.ReflectionUtil;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @Description：GET POST请求处理
 * @Date 2020/12/06 16:10
 * @Author YXD
 * @Version 1.0
 */
public class RequestHandler {
    private static final AsciiString CONTENT_TYPE = AsciiString.cached("Content-Type");
    private static final AsciiString CONTENT_LENGTH = AsciiString.cached("Content-Length");

    public static FullHttpResponse handle(FullHttpRequest fullHttpRequest) {
        String requestUri = fullHttpRequest.uri();
        //请求链接，不带参数
        String requestPath = getRequestPath(requestUri);
        //请求参数map
        Map<String, String> queryParameterMap = getQueryParams(requestUri);
        //url请求映射的目标方法
        Method targetMethod = null;
        if (SystemContants.GET.equals(fullHttpRequest.method().name())) {
            targetMethod = RouteFactory.GET_MAP.get(requestPath);
        } else if (SystemContants.POST.equals(fullHttpRequest.method().name())) {
            targetMethod = RouteFactory.POST_MAP.get(requestPath);
        }
        if (!Objects.isNull(targetMethod)) {
            //映射的目标类
            Object targetObject = BeanFactory.BEANS.get(targetMethod.getDeclaringClass().getName());
            //映射的目标方法参数
            Parameter[] parameters = targetMethod.getParameters();
            String[] parameterNames = ParameterNameUtil.getMethodParameterNamesByAsm7(targetMethod);
            List<Object> targetArgs = new ArrayList<>();
            //只有基本类型参数处理
            for (int i = 0; i < parameters.length; i++) {
                String name = parameterNames[i];
                String args = queryParameterMap.get(name);
                if (args != null) {
                    targetArgs.add(PropertyEditorUtil.convert(parameters[i].getType(), args));
                } else {
                    targetArgs.add(null);
                }
            }
            if (targetMethod.getReturnType() == void.class) {
                ReflectionUtil.executeTargetMethodNotResult(targetObject, targetMethod, targetArgs.toArray());
                return buildSuccessResponse(null);
            }
            return buildSuccessResponse(ReflectionUtil.executeTargetMethod(targetObject, targetMethod, targetArgs.toArray()));
        }
        return buildErrorResponse();
    }

    /**
     * 获取请求的url
     */
    public static String getRequestPath(String uri) {
        QueryStringDecoder queryDecoder = new QueryStringDecoder(uri);
        return queryDecoder.path();
    }

    /**
     * 获取url的参数
     */
    public static Map<String, String> getQueryParams(String uri) {
        QueryStringDecoder queryDecoder = new QueryStringDecoder(uri);
        Map<String, List<String>> parameters = queryDecoder.parameters();
        Map<String, String> queryParams = new HashMap<>();
        for (Map.Entry<String, List<String>> attr : parameters.entrySet()) {
            for (String attrVal : attr.getValue()) {
                queryParams.put(attr.getKey(), attrVal);
            }
        }
        return queryParams;
    }

    /**
     * http成功返回消息体
     *
     * @param o
     * @return
     */
    private static FullHttpResponse buildSuccessResponse(Object o) {
        byte[] content = KryoUtil.serialize(o);
        FullHttpResponse response;
        if (Objects.isNull(o)) {
            response = new DefaultFullHttpResponse(HTTP_1_1, OK);
        } else {
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(content));
        }
        response.headers().set(CONTENT_TYPE, "application/json");
        response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
        return response;
    }

    /**
     * http失败返回消息体
     *
     * @return
     */
    private static FullHttpResponse buildErrorResponse() {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.NOT_FOUND);
        response.headers().set(CONTENT_TYPE, "application/json");
        response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
        return response;
    }
}
