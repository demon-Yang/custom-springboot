package com.yxd.core.sever;

import com.yxd.core.constant.SystemContants;
import com.yxd.core.factory.BeanFactory;
import com.yxd.core.factory.RouteFactory;
import com.yxd.core.util.KryoUtil;
import com.yxd.core.util.ReflectionUtil;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;

import java.lang.reflect.Method;
import java.util.*;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @Description：
 * @Date 2020/12/06 16:10
 * @Author YXD
 * @Version 1.0
 */
public class RequestHandler {
    private static final AsciiString CONTENT_TYPE = AsciiString.cached("Content-Type");
    private static final AsciiString CONTENT_LENGTH = AsciiString.cached("Content-Length");

    public static FullHttpResponse handle(FullHttpRequest fullHttpRequest) {
        String requestUri = fullHttpRequest.uri();
        String requestPath = getRequestPath(requestUri);
        Map<String, String> queryParameterMap = getQueryParams(requestUri);
        List<Object> targetMethodParams = new ArrayList<>();
        Object targetObject;
        Method targetMethod = null;
        if (SystemContants.GET.equals(fullHttpRequest.method().name())) {
            targetMethod = RouteFactory.GET_MAP.get(requestPath);
        } else if (SystemContants.POST.equals(fullHttpRequest.method().name())) {
            targetMethod = RouteFactory.POST_MAP.get(requestPath);
        }
        if (!Objects.isNull(targetMethod)) {
            Class<?> declaringClass = targetMethod.getDeclaringClass();
            targetObject = BeanFactory.BEANS.get(declaringClass.getName());
            if (targetMethod.getReturnType() == void.class) {
                ReflectionUtil.executeTargetMethodNotResult(targetObject, targetMethod, targetMethodParams.toArray());
                return buildSuccessResponse(null);
            }
            return buildSuccessResponse(ReflectionUtil.executeTargetMethod(targetObject, targetMethod, targetMethodParams.toArray()));
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
