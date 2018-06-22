package com.example.sxm.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class MyGLSurfaceView extends GLSurfaceView {
    private MyGLRender mGLRender ;
    public MyGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(3);
        mGLRender = new MyGLRender();
        setRenderer(mGLRender);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }


}
