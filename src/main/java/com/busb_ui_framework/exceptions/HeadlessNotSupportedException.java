package com.busb_ui_framework.exceptions;

public class HeadlessNotSupportedException extends IllegalStateException {

    public HeadlessNotSupportedException() {
        super("Headless not supported for this browser");
    }
}
