package com.example.sxm.utils;

import okhttp3.Request;
import okhttp3.Response;

public abstract class ResultCallback {
    public abstract void onError(Request request, Exception e);
    public abstract void onResponse(Response response);
}
