package com.the.hugging.team.utils;

public abstract class WindowHandler {

    private Window currentWindow;

    public Window getWindow()
    {
        return this.currentWindow;
    }

    public void setWindow(Window win)
    {
        this.currentWindow = win;
    }
}
