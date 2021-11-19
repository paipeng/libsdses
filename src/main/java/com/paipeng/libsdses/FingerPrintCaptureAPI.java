package com.paipeng.libsdses;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.win32.StdCallLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FingerPrintCaptureAPI {
    public static Logger logger = LoggerFactory.getLogger(FingerPrintCaptureAPI.class);
    private static final String LIB_NAME = "xxxxx";

    private static void setupNativeLibrary() {
        logger.trace("setupNativeLibrary");
        NativeLibrary.addSearchPath(LIB_NAME, "./libs");
        //System.setProperty("jna.library.path", "");
        //NativeLibrary.addSearchPath(LIB_NAME, "");
    }

    //Load DLL Library
    public interface FingerPrintLib extends StdCallLibrary {
        FingerPrintLib INSTANCE = Native.load(LIB_NAME, FingerPrintLib.class);

        // 遵循GA/T 626.1—2010中3.1规定

        /**
         * 初始化采集设备
         * @return 调用成功，返回1；< 0 返回错误代码
         */
        int LIVESCAN_Init();

        /**
         * 释放采集设备
         * @return 调用成功，返回1；< 0 返回错误代码
         */
        int LIVESCAN_Close();

        /**
         * 获得采集设备通道数量
         * @return 调用成功，返回通道数量（ > 0); < 0 返回错误代码
         */
        int LIVESCAN_GetChannelCount();

        /**
         * 设置采集设备当前的亮度
         * @param nChannel 通道号
         * @param nBright
         * @return
         */
        int LIVESCAN_SetBright(int nChannel, int nBright);

        /**
         * 设置采集设备当前的对比度
         * @param nChannel 通道号
         * @param nContrast
         * @return
         */
        int LIVESCAN_SetContrast(int nChannel, int nContrast);

        /**
         * 获得采集设备当前的亮度
         * @param nChannel 通道号
         * @param pnBright
         * @return
         */
        int LIVESCAN_GetBright(int nChannel, int[] pnBright);

        /**
         * 获得采集设备当前的对比度
         * @param nChannel 通道号
         * @param pnContrast
         * @return
         */
        int LIVESCAN_GetContrast(int nChannel, int[] pnContrast);

        /**
         * 调用采集设备的属性设置对话框
         * @return
         */
        int LIVESCAN_Setup();

        // int  LIVESCAN_ SetVideoWindow (HWND hWnd);

        /**
         * 准备采集一帧图像
         * @param nChannel 通道号
         * @return
         */
        int LIVESCAN_BeginCaptutre(int nChannel);

        /**
         * 采集一帧图像
         * @param nChannel 通道号
         * @param pRawData 内存块大小为单指指纹采集图像大小。
         * @return
         */
        int LIVESCAN_GetFPRawData(int nChannel, byte[] pRawData);

        /**
         * 采集一幅BMP格式图像数据 (指向存放采集BMP格式的指纹图像数据的内存块，调用者分配，大小应该为： 400字节×400字节+1078字节。输出参数。)
         * @param nChannel 通道号
         * @param pBmpData 返回的图像数据为包括文件头在内的8位灰度BMP格式的图像，采集的图像宽和高为400像素×400像素，图像无压缩、无缩放，数据存储顺序为从左到右，从下到上。
         * @return  1, 2, 3
         * 如果采集接口不具备纠偏功能，采集成功返回1。
         * 如果采集接口具备纠偏功能，指纹图像采集成功后纠偏失败，返回2，此时输出的图像数据为没有纠偏前的数据。
         * 如果采集接口具备纠偏功能，指纹图像采集成功并且纠偏成功，返回3，输出的图像数据为纠偏后的数据。
         */
        int LIVESCAN_GetFPBmpData(int nChannel, byte[] pBmpData);

        /**
         * 结束采集一帧图像
         * @param nChannel 通道号
         * @return
         */
        int LIVESCAN_EndCapture(int nChannel);

        /**
         * 取得接口的版本
         * @return
         */
        int LIVESCAN_GetVersion();

        /**
         * 获得接口的说明
         * @param pszDesc byte data [1024]
         * @return
         */
        int LIVESCAN_GetDesc(byte[] pszDesc);

        /**
         * 取得采集接口错误信息
         * @param nErrorNo byte data [256]
         * @return
         */
        int LIVESCAN_GetErrorInfo(int nErrorNo, byte[] pszErrorInfo);
    }

    public enum LIVESCAN_CAPTURE_ERROR {
        INVALID_PARAMETER(-1),
        MEMORY_INSUFFICIENT(-2),
        FUNCTION_ERROR(-3),
        DEVICE_UNAVAILABLE(-4),
        DEVICE_UNINITIALIZED(-5),
        ERROR_CODE_INVALID(-6),
        LICENSE_INVALID(-7),
        SYSTEM_ERRORS(-8),
        USER_DEFINE_ERRORS(-101);

        public int key;

        LIVESCAN_CAPTURE_ERROR(int key) {
            this.key = key;
        }

    }

}
