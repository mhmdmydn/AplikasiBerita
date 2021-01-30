package com.del.dnews.Interfaces;

public interface CallBackApi {
    void onReceiveJsonApi(String result);
    void onError(String error);
}
