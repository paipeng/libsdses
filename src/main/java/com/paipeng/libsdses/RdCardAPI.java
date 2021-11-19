package com.paipeng.libsdses;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class RdCardAPI {

    public static Logger logger = LoggerFactory.getLogger(RdCardAPI.class);
    private static final String LIB_NAME = "RdCard";

    private static void setupNativeLibrary() {
        logger.trace("setupNativeLibrary");
        NativeLibrary.addSearchPath(LIB_NAME, "./libs");
        //System.setProperty("jna.library.path", "");
        //NativeLibrary.addSearchPath(LIB_NAME, "");
    }

    //Load DLL Library
    public interface RdCardLib extends StdCallLibrary {
        RdCardLib INSTANCE = Native.load(LIB_NAME, RdCardLib.class);



        int UCommand1(byte[] pCmd, int[] parg0, int[] parg1, int[] parg2);
        int UCommand1(String pCmd, IntByReference prag0, IntByReference prag1,
                      byte[] prag2);
        int GetName(byte[] buf);

        int GetSexGB(byte[] buf);

        int GetFolkGB(byte[] buf);

        int GetAddr(byte[] buf);

        int GetIDNum(byte[] buf);

        int GetDep(byte[] buf);

        int GetBirth(byte[] buf);

        int GetBegin(byte[] buf);

        int GetEnd(byte[] buf);

        int GetBmpPath(byte[] buf);




        int FID_GetEnName (byte[] buf); //璇诲彇鑻辨枃濮撳悕
        int FID_GetChName (byte[] buf); //璇诲彇涓枃濮撳悕
        int FID_GetNationality (byte[] buf); //璇诲彇鍥界睄
        int FID_GetSex (byte[] buf); //璇诲彇鎬у埆缂栫爜
        int FID_GetSexGB (byte[] buf); //璇诲彇鎬у埆
        int FID_GetBirth (byte[] buf); //璇诲彇鍑虹敓
        int FID_GetIDNum (byte[] buf); //璇诲彇鍏皯韬唤鍙风爜
        int FID_GetDep (byte[] buf); //璇诲彇绛惧彂鏈哄叧
        int FID_GetBegin (byte[] buf); //璇诲彇鏈夋晥鏈熻捣
        int FID_GetEnd (byte[] buf); //璇诲彇鏈夋晥鏈熸
        int FID_GetVersion (byte[] buf); //璇诲彇璇佷欢鐗堟湰鍙�

    }

    public void open() throws UnsupportedEncodingException {
        String aa;
        aa = String.format("%c", 0x41);

        IntByReference bb = new IntByReference();
        bb.setValue(0);
        IntByReference cc = new IntByReference();
        cc.setValue(8811);


        byte[] dd = { 0x02, 0x27, 0x00, 0x00 };    //9986

        System.out.println(RdCardLib.INSTANCE.UCommand1(aa, bb, cc, dd)); // 锟斤拷始锟斤拷

        aa = String.format("%c", 0x43);
        System.out.println(RdCardLib.INSTANCE.UCommand1(aa, bb, cc, dd));

        aa = String.format("%c", 0x49);

        //modified by jiadl 20170623
        //字符串后面必须加“0”
        String strPara2 = "D:\\测试\\"+"\0";
        System.out.println(System.getProperty("file.encoding"));
        byte[] bytePara2 = strPara2.getBytes("GBK");

        int flag = RdCardLib.INSTANCE.UCommand1(aa, bb, cc, bytePara2);

        System.out.println(flag);
        if((flag==62171) || (flag==62172)) //韬唤璇�
        {

            byte[] name1 = new byte[20];
            RdCardLib.INSTANCE.GetName(name1);
            String name = new String(name1, "GBK");
            System.out.println(name);
            name = reEncoding(name, "UTF-8");
            System.out.println(name);

            byte[] sex1 = new byte[20];
            RdCardLib.INSTANCE.GetSexGB(sex1);
            String sex = new String(sex1, "GBK");
            sex = reEncoding(sex, "UTF-8");
            System.out.println(sex);

            byte[] min1 = new byte[20];
            RdCardLib.INSTANCE.GetFolkGB(min1);
            String min = new String(min1, "GBK");
            min = reEncoding(min, "UTF-8");
            System.out.println(min);

            byte[] addr = new byte[50];
            RdCardLib.INSTANCE.GetAddr(addr);
            String add = new String(addr, "GBK");
            add = reEncoding(add, "UTF-8");
            System.out.println(add);

            byte[] idNum = new byte[50];
            RdCardLib.INSTANCE.GetIDNum(idNum);
            String id = new String(idNum, "GBK");
            id = reEncoding(id, "UTF-8");
            System.out.println("id:" + id);

            byte[] dep1 = new byte[50];
            RdCardLib.INSTANCE.GetDep(dep1);
            String dep = new String(dep1, "GBK");
            dep = reEncoding(dep, "UTF-8");
            System.out.println(dep);

            byte[] bir1 = new byte[20];
            RdCardLib.INSTANCE.GetBirth(bir1);
            String bir = new String(bir1, "GBK");
            bir = reEncoding(bir, "UTF-8");
            System.out.println(bir);

            byte[] begin = new byte[20];
            RdCardLib.INSTANCE.GetBegin(begin);
            String begi = new String(begin, "GBK");
            begi = reEncoding(begi, "UTF-8");
            System.out.println(begi);

            byte[] end1 = new byte[20];
            RdCardLib.INSTANCE.GetEnd(end1);
            String end = new String(end1, "GBK");
            end = reEncoding(end, "UTF-8");
            System.out.println(end);

            byte[] path1 = new byte[100];
            RdCardLib.INSTANCE.GetBmpPath(path1);
            String path = new String(path1, "GBK");
            path = reEncoding(path,"UTF-8");
            System.out.println("path*****" + path + "*******");

            FileWriter writer;
            try {
                writer = new FileWriter("D://obj.txt");
                writer.write(name + "\r\n");
                writer.write(id + "\r\n");
                writer.write(dep + "\r\n");
                writer.write(add + "\r\n");
                writer.write(sex + "\r\n");
                writer.write(min + "\r\n");
                writer.write(bir + "\r\n");
                writer.write(begi + "\r\n");
                writer.write(end + "\r\n");
                writer.flush();
                writer.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if(flag==62173) //澶栧浗浜烘案涔呭眳鐣欒韩浠借瘉
        {

            byte[] name1 = new byte[120];
            RdCardLib.INSTANCE.FID_GetEnName(name1);
            String name = new String(name1, "GBK");
            name = reEncoding(name, "UTF-8");
            System.out.println(name);

            byte[] sex1 = new byte[20];
            RdCardLib.INSTANCE.FID_GetSexGB(sex1);
            String sex = new String(sex1, "GBK");
            sex = reEncoding(sex, "UTF-8");
            System.out.println(sex);


            byte[] addr = new byte[50];
            RdCardLib.INSTANCE.FID_GetChName(addr);
            String add = new String(addr, "GBK");
            add = reEncoding(add, "UTF-8");
            System.out.println(add);

            byte[] idNum = new byte[50];
            RdCardLib.INSTANCE.FID_GetIDNum(idNum);
            String id = new String(idNum, "GBK");
            id = reEncoding(id, "UTF-8");
            System.out.println("id:" + id);

            byte[] dep1 = new byte[20];
            RdCardLib.INSTANCE.FID_GetNationality(dep1);
            String dep = new String(dep1, "GBK");
            dep = reEncoding(dep, "UTF-8");
            System.out.println(dep);

            byte[] bir1 = new byte[20];
            RdCardLib.INSTANCE.FID_GetBirth(bir1);
            String bir = new String(bir1, "GBK");
            bir = reEncoding(bir, "UTF-8");
            System.out.println(bir);

            byte[] begin = new byte[20];
            RdCardLib.INSTANCE.FID_GetBegin(begin);
            String begi = new String(begin, "GBK");
            begi = reEncoding(begi, "UTF-8");
            System.out.println(begi);

            byte[] end1 = new byte[20];
            RdCardLib.INSTANCE.FID_GetEnd(end1);
            String end = new String(end1, "GBK");
            end = reEncoding(end, "UTF-8");
            System.out.println(end);

            byte[] path1 = new byte[50];
            RdCardLib.INSTANCE.GetBmpPath(path1);
            String path = new String(path1, "GBK");
            path = reEncoding(path,"UTF-8");
            System.out.println("path*****" + path + "*******");

            FileWriter writer;
            try {
                writer = new FileWriter("D://obj.txt");
                writer.write(name + "\r\n");
                writer.write(id + "\r\n");
                writer.write(dep + "\r\n");
                writer.write(add + "\r\n");
                writer.write(sex + "\r\n");
                writer.write(bir + "\r\n");
                writer.write(begi + "\r\n");
                writer.write(end + "\r\n");
                writer.flush();
                writer.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public String reEncoding(String text, String newEncoding) {
        String rs = null;
        try {
            rs = new String(text.getBytes(), newEncoding);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rs;
    }


}
