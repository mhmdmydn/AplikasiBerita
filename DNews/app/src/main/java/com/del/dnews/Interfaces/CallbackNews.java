package com.del.dnews.Interfaces;

public interface CallbackNews {
    void onReceiveJsonApi(String list);
    void onError(String error);
}
