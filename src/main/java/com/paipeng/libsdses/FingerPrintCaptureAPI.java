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
    public interface FingerPrintCaptureLib extends StdCallLibrary {
        FingerPrintCaptureLib INSTANCE = Native.load(LIB_NAME, FingerPrintCaptureLib.class);

        // 遵循GA/T 626.1—2010中3.1规定

        /**
         * 初始化采集设备
         * @return 调用成功，返回 1；< 0 返回错误代码
         */
        int LIVESCAN_Init();

        /**
         * 释放采集设备
         * @return 调用成功，返回 1；< 0 返回错误代码
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
         * @return 调用成功，返回 1；< 0 返回错误代码
         */
        int LIVESCAN_SetBright(int nChannel, int nBright);

        /**
         * 设置采集设备当前的对比度
         * @param nChannel 通道号
         * @param nContrast
         * @return 调用成功，返回 1；< 0 返回错误代码
         */
        int LIVESCAN_SetContrast(int nChannel, int nContrast);

        /**
         * 获得采集设备当前的亮度
         * @param nChannel 通道号
         * @param pnBright
         * @return 调用成功，返回 1；< 0 返回错误代码
         */
        int LIVESCAN_GetBright(int nChannel, int[] pnBright);

        /**
         * 获得采集设备当前的对比度
         * @param nChannel 通道号
         * @param pnContrast
         * @return 调用成功，返回 1；< 0 返回错误代码
         */
        int LIVESCAN_GetContrast(int nChannel, int[] pnContrast);

        /**
         * 获得采集设备图像的宽度，高度最大值
         * @param nChannel 通道号
         * @param pnWidth 存放图像宽度的整数型指针
         * @param pnHeight 存放图像高度的整数型指针
         * @return 调用成功，返回 1；< 0 返回错误代码
         */
        int LIVESCAN_GetMaxImageSize(int nChannel, int[] pnWidth, int[] pnHeight);

        /**
         * 调用采集设备的属性设置对话框
         * @return 调用成功，返回 1；< 0 返回错误代码
         */
        int LIVESCAN_Setup();

        // int  LIVESCAN_ SetVideoWindow (HWND hWnd);

        /**
         * 获得当前图像的采集位置，宽度和高度
         * @param nChannel 通道号
         * @param pnOriginX 存放图像采集窗口的采集原点坐标X值的整数型指针
         * @param pnOriginY 存放图像采集窗口的采集原点坐标Y值的整数型指针
         * @param pnWidth 存放图像采宽度的整数型指针
         * @param pnHeight 存放图像采高度的整数型指针
         * @return 调用成功，返回 1；< 0 返回错误代码
         */
        int LIVESCAN_GetCaptWindow(int nChannel, int[] pnOriginX, int[] pnOriginY, int[] pnWidth, int[] pnHeight);

        /**
         * 设置当前图像的采集位置，宽度和高度
         * @param nChannel 通道号
         * @param nOriginX 图像采集窗口的采集原点坐标X值。
         * @param nOriginY 图像采集窗口的采集原点坐标Y值。
         * @param nWidth 图像宽度。对于单指指纹采集器，值应该为640。对于平面右手四连指
         * @param nHeight
         * @return 调用成功，返回 1；< 0 返回错误代码
         */
        int LIVESCAN_SetCaptWindow(int nChannel, int nOriginX, int nOriginY, int nWidth, int nHeight);


        /**
         * 准备采集一帧图像
         * @param nChannel 通道号
         * @return 调用成功，返回 1；< 0 返回错误代码
         */
        int LIVESCAN_BeginCaptutre(int nChannel);

        /**
         * 采集一帧图像
         * @param nChannel 通道号
         * @param pRawData 内存块大小为单指指纹采集图像大小。
         * @return 调用成功，返回 1；< 0 返回错误代码
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
         * @return 调用成功，返回 1；< 0 返回错误代码
         */
        int LIVESCAN_EndCapture(int nChannel);

        /**
         * 判断采集设备是否支持采集窗口设置
         * @param nChannel 通道号
         * @return 若采集接口只支持单指指纹采集窗口的设置，则返回 1；
         * 若采集接口只支持平面右手四连指，平面左手四连指或者平面左右手拇指指纹采集窗口的设置，则返回 2；
         * 若采集接口支持单指，平面右手四连指，平面左手四连指或者平面左右受拇指指纹采集窗口的设置，则返回 3；
         * 若采集接口不支持设置采集窗口，则返回 0。
         * < 0 返回错误代码
         */
        int LIVESCAN_IsSupportCaptWindow(int nChannel);


        /**
         * 采集设备是否支持设置对话框
         * @return 若采集接口支持LIVESCAN_Setup，则返回 1，否则返回 0
         */
        int LIVESCAN_IsSupportSetup();

        /**
         * 获取预览图像大小
         * @param nChannel 通道号
         * @param pnWidth 存放预览图像宽度的整形指针
         * @param pnHeight 存放预览图像高度的整形指针
         * @return 调用成功，返回 1；< 0 返回错误代码
         */
        int LIVESCAN_GetPreviewImageSize(int nChannel, int[] pnWidth, int[] pnHeight);

        /**
         * 采集一帧预览图像
         * @param nChannel 通道号
         * @param pRawData 指向存放采集的预览图像数据的内存块，调用者分配。
         * @return 调用成功，返回 1；< 0 返回错误代码
         */
        int LIVESCAN_GetPreviewData(int nChannel, byte[] pRawData);

        /**
         * 判断采集设备是否支持采集预览图像
         * @return 支持，返回 1；否则 返回 0
         */
        int LIVESCAN_IsSupportPreview();



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
