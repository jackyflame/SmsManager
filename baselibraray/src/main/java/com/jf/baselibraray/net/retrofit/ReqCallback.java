package com.jf.baselibraray.net.retrofit;

import com.jf.baselibraray.event.HttpEvent;

public interface ReqCallback<T> {

    void onReqStart();

    void onNetResp(T response);

    void onReqError(HttpEvent httpEvent);

}
