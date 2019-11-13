package com.casic.web.controller.bank;

public class GlobalVar {
    public static final int CLIENT_TYPE_BSDReader = 1;
    public static final int CLIENT_TYPE_HT706RFIDReader = 2;
    public static int EnableXGReader = 0;
    public static int EnableBSDPrinter = 1;
    public static int EnableHT706RFIDReader = 0;

    // XGReader
    public static String XGReaderServerAddr = "192.168.1.59";
    public static String XGReaderServerInterface = "/BoxSync/XGBarCodeReader";
    public static int XGReaderServerPort = 8000;
    public static String XGReaderDevicePort = "/dev/ttyACM0";

    // BSD Printer
    public static int BSDListenPort = 8000;
    public static String BSDFontPath = "/tmp/hei.ttf";
    public static int BSDLabelWidthMM = 60;
    /*public static int BSDLabelHeightMM = 40;*/
    public static int BSDLabelHeightMM = 48;
    public static int BSDLabelGapMM = 4;
    public static int BSDLabelLeftGap = 30;
    public static int BSDRFIDEnable = 1;

    // HT706 RFID Reader
    public static final int TYPE_SOCKET = 0;
    public static final int TYPE_SERIAL = 1;
    public static int HT706ReaderPortType = TYPE_SERIAL;
    public static String HT706RFIDDeviceAddr = "127.0.0.1";
    public static int HT706RFIDDevicePort = 4001;
    public static String HT706RFIDSerialAddr = "/dev/ttyACM0";
    public static int HT706RFIDPower = 2100;
}
