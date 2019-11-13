package com.casic.web.controller.bank;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


public interface LibraryBSDPrinterInterface extends Library {
    public static String soName =  "PostekFunctionLib";// 动态库名字

    LibraryBSDPrinterInterface INSTANCE = (LibraryBSDPrinterInterface) Native.loadLibrary(soName, LibraryBSDPrinterInterface.class);// 动�?�库实例

    // 动�?�链接库CDFPSK.dll中的方法.
    int OpenPort(int px); // 打开通讯端口

    void GetOnePrinterSn(byte[] buf);//获取打印机的序列�?

    int OpenPortWithSn(String Sn);//当有两台或�?�两台以上的打印机时用次函数打开端口

    int ClosePort(); // 关闭使用 OpenPort函数打开的�?�讯端口

    //int SetPCComPort(int BaudRate, boolean HandShake);

    int PTK_GetErrState();//是检测使�? libPostekFunctionLib.so 里的其它函数后是否有错误产生

    int PTK_GetInfo();//获取API 函数库版本的信息

    int PTK_ClearBuffer(); // 清除打印机缓冲内存的�?

    int PTK_SetDarkness(int id); // 设置打印头发热温�?

    int PTK_SetPrintSpeed(int px); // 设置打印速度

    int PTK_SetLabelHeight(int lheight, int gapH);// 设置标签的高度和定位间隙 \黑线 \穿孔的高度�??

    int PTK_SetLabelWidth(int lwidth); // 设置标签的宽度�??

    int PTK_SetDirection(char direct);

    int PTK_SetCoordinateOrigin(int px, int py);

    int PTK_PrintLabel(int number, int cpnumber); // 命令打印机执行工�?

    int PTK_DrawText(int px, int py, int pdirec, int pFont, int pHorizontal, int pVertical, char ptext, String string); // 打印�?行文本字

    int PTK_DrawTextEx(int px, int py, int pdirec, int pFont, int pHorizontal, int pVertical, char c, String pstr,
                       boolean i); // 打印�?行文本文字，内容可以是常量�?�序列号变或组合符串�?

    //int PTK_DrawTextTrueTypeW(int x, int y, int FHeight, int FWidth, String FType, int Fspin, int FWeight,int FItalic, int FUnline, int FStrikeOut, String id_name, String data); // 打印�?�?
    // TrueTypeFont文字，并且宽度和高可以微�?
    int PTK_DrawTextTrueTypeW(int x, int y, int FHeight, int FWidth, int FWeight, int FLean, int Fspin, int FUnline, int Fgap, String path, String ptext, int numOfTF);
    int PTK_DrawBarcode(int px, int py, int pdirec, String pCode, int pHorizontal, int pVertical, int pbright,
                        char ptext, String pstr); // 打印�?个条码�??

    int PTK_DrawBarcodeEx(int px, int py, int pdirec, String pCode, int NarrowWidth, int pHorizontal, int pVertical,
                          int pbright, char ptext, String pstr, int Varible); // 打印�?个条码�??

    int PTK_DrawBar2D_DATAMATRIX(int x, int y, int w, int v, int o, int m, String pstr); // 打印DataMatrix二维条码

    int PTK_DrawBar2D_QR(int x, int y, int w, int v, int o, int r, int m, int g, int s, String pstr); // 打印�?�? QR 条码

    int PTK_DrawBar2D_QREx(int x, int y, int o, int r, int g, int s, int v, String id_name, String pstr); // 打印�?�? QR

    int PTK_DrawBar2D_MaxiCode(int x, int y, int m, int u, String pstr);

    int PTK_DrawBar2D_Pdf417(int x, int y, int w, int v, int s, int c, int px, int py, int r, int l, int t, int o,
                             String pstr); // 打印�?�?

    int PTK_DrawBar2D_HANXIN(int x, int y, int w, int v, int o, int r, int m, int g, int s, String pstr); // 打印�?个汉信码二维条码

    int PTK_PcxGraphicsList();

    int PTK_PcxGraphicsDel(String pid); // 删除存储�? 打印�? RAM�? FLASH存储器里的一个或�?有图�?

    int PTK_PcxGraphicsDownload(String pcxname, String pcxpath); // 存储�?�? PCX格式的图形到打印�?

    int PTK_DrawPcxGraphics(int px, int py, String gname); // 打印指定的图�?

    int PTK_PrintPCX(int px, int py, String filename);// 函数是打印一�? 函数是打印一�? PCXPCXPCX格式的图形�??

    int PTK_BmpGraphicsDownload(String pcxname, String pcxpath, int iDire);

    int PTK_BinGraphicsList();

    int PTK_BinGraphicsDel(String pid);

    int PTK_BinGraphicsDownload(String name, int pbyte, int pH, byte[] Gdata);

    int PTK_RecallBinGraphics(int px, int py, String name);

    int PTK_DrawBinGraphics(int px, int py, int pbyte, int pH, byte[] Gdata);

    int PTK_DrawRectangle(int px, int py, int thickness, int pEx, int pEy); // 画矩�?

    int PTK_DrawLineXor(int px, int py, int pbyte, int pH);

    int PTK_DrawLineOr(int px, int py, int pLength, int pH); // 画直�? (两直线相交处作�?�或”处�? )�?

    int PTK_DrawDiagonal(int px, int py, int thickness, int pEx, int pEy);

    int PTK_DrawWhiteLine(int px, int py, int plength, int pH);

    int PTK_SoftFontList();

    int PTK_SoftFontDel(char pid);

    int PTK_FormList();

    int PTK_FormDel(String pid); // 删除存储在打印机里的�?个或�?有表�?

    int PTK_FormDownload(String pid); // 存储�?个表格到打印机； 此命令与 此命令与 PTK_FormEnd 函数配对使用�?

    int PTK_FormEnd(); // 结束存储表格 (Form)，此函数�? PTK_FormDownload配对使用

    int PTK_ExecForm(String pid); // 运行指定的表�?

    //int PTK_DefineCounter(int id, int maxNum, short ptext, String pstr, String pMsg); // 定义�?个序列号变量

    int PTK_DefineCounter(int id, int maxNum, char ptext, String pstr, String pMsg);

    int PTK_DefineVariable(int pid, int pmax, char porder, String pmsg);

    int PTK_Download(); // 下载变量或系列号变量

    int PTK_DownloadInitVar(String pstr); // 初始化变量或系列号变�?

    int PTK_PrintLabelAuto(int number, int cpnumber);

    int PTK_SendFile(String FilePath);

    int PTK_GetUSBID(String USBDeviceSerial);

    int PTK_DisableBackFeed();

    int PTK_EnableBackFeed(int distance);

    int PTK_PrintConfiguration();

    int PTK_SetPrinterState(char state);

    int PTK_DisableErrorReport();

    int PTK_EnableErrorReport();

    int PTK_EnableFLASH();

    int PTK_DisableFLASH();

    int PTK_FeedMedia();

    int PTK_MediaDetect();

    int PTK_CutPage(int page);

    int PTK_CutPageEx(int page);

    int PTK_FeedBack();

    int PTK_Reset();

    int PTK_ErrorReport(int wPort, int rPort, int BaudRate, int HandShake, int TimeOut);

    int PTK_SetRFIDLabelRetryCount(int nRetryCount);

    int PTK_SetRFLabelPWAndLockRFLabel(int nOperationMode, int OperationnArea, String pstr);// 设置RFID标签密码和锁定RFID标签

    int PTK_SetFontGap(int gap);

    int PTK_SetBarCodeFontName(char Name, int FontW, int FontH);

    int PTK_RenameDownloadFont(int StoreType, char Fontname, String DownloadFontName);

    int PTK_SetCharSets(int BitValue, char CharSets, String CountryCode);

    //设置高频RFID 标签�?
    int PTK_SetHFRFID(char pWForm, int nProtocolType, int nMaxErrNumd);
    int PTK_ReadRFID(int comm_mode, int start_addr, int block, int len, int atuo_foward, byte[] data);
    int PTK_WriteRFID(int start_addr, int block, int len, int data_moden, String data);
}
