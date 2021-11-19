package com.paipeng.libsdses;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.win32.StdCallLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FingerPrintValidateAPI {

    public static Logger logger = LoggerFactory.getLogger(FingerPrintValidateAPI.class);
    private static final String LIB_NAME = "xxxxx";

    private static void setupNativeLibrary() {
        logger.trace("setupNativeLibrary");
        NativeLibrary.addSearchPath(LIB_NAME, "./libs");
        //System.setProperty("jna.library.path", "");
        //NativeLibrary.addSearchPath(LIB_NAME, "");
    }

    //Load DLL Library
    public interface FingerPrintValidateLib extends StdCallLibrary {
        FingerPrintValidateLib INSTANCE = Native.load(LIB_NAME, FingerPrintValidateLib.class);

        /**
         * 获得比对算法版本
         * @param pcVersionInfo 厂家及算法版本标识，其长度由外部调用者分配，长度为6字节。输出参数。
         * @return 调用成功返回1。<0 返回错误代码。
         */
        long LIVESCAN_Get_Version(byte[] pcVersionInfo);

        /**
         * 提取特征模板
         * @param pcImageData 给定待提取特征的（包括文件头在内的BMP格式的）指纹图像数据。输入参数。
         * @param lDataLen 待提取特征的指纹图像数据长度，上限为640×640+1078字节。输入参数。
         * @param pcTemplateData 指向存放指纹特征模板的内存数据块，其大小为1024字节，由调用者分配。输出参数。
         * @return 若特征模板提取成功（同时应生成特征模板数据），该返回值代表算法对输入图像的品质评价系数，取值为1～100 的整数，
         * 该系数值愈小表示图像质量愈差，该系数值愈大则表示图像质量愈好；若输入像图像的质量不满足算法提取特征的返回0。
         * 说明:
         * 从指纹图像数据中抽取特征模板，若发生其他不可预测错误，如给定的数据格式不可识别等情况，该函数返回-1，识别算法负责捕获异常。
         */
        long LIVESCAN_FeatureExtractMem(byte[] pcImageData, long lDataLen, byte[] pcTemplateData);

        /**
         * 特征模板比对
         * @param pcTemplateDataA 给定参与比对的第1个特征模板数据，其长度为1024字节。输入参数。
         * @param pcTemplateDataB 给定参与比对的第2个特征模板数据，其长度为1024字节。输入参数。
         * @param pfSim 参与比对的两个特征模板数据匹配的相似度，大小为0.00～4.00，按第5位4舍5入方式取小数点后4位。输出参数。
         * @return 若成功完成比对返回1，如果发生其他不可预测错误，如给定的特征模板数据格式不可识别等情况，该函数返回-1。
         * 两个文件比对的相似度通过pfSim 参数返回，相似度为0.00～4.00 的浮点数，相似度越大，表示两个模板匹配程度越高。
         *
         * 说明:
         * 比较两个输入的特征模板文件，返回相似度。
         */
        long LIVESCAN_FeatureMatchMem (byte[] pcTemplateDataA, byte[] pcTemplateDataB, float[] pfSim);
    }
}
