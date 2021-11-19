package com.paipeng.libsdses;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.win32.StdCallLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SdsesAPI {
    public static Logger logger = LoggerFactory.getLogger(SdsesAPI.class);
    private static final String LIB_NAME = "xxxxx";

    private static void setupNativeLibrary() {
        logger.trace("setupNativeLibrary");
        NativeLibrary.addSearchPath(LIB_NAME, "./libs");
        //System.setProperty("jna.library.path", "");
        //NativeLibrary.addSearchPath(LIB_NAME, "");
    }

    //Load DLL Library
    public interface SdsesLib extends StdCallLibrary {
        SdsesLib INSTANCE = Native.load(LIB_NAME, SdsesLib.class);
    }
}
