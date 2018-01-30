package com.healthmall.sail.cat_doctor.serialport;

import com.healthmall.sail.cat_doctor.Constant;
import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.bean.BloodOxygenReport;
import com.healthmall.sail.cat_doctor.bean.BodyReport;
import com.mai.xmai_fast_lib.utils.MLog;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android_serialport_api.SerialPort;

/**
 * Created by mai on 2017/12/1.
 */
public class SerialPortEngine {


    private List<Integer> heightTemps = new ArrayList<>(); //用于判断数据是否稳定
    private List<Integer> weightTemps = new ArrayList<>(); //用于判断数据是否稳定
    private List<Integer> bloodoTemps = new ArrayList<>(); //用于判断数据是否稳定
    private List<Integer> prTemps = new ArrayList<>(); //用于判断数据是否稳定

    static SerialPortEngine instance;

    public static SerialPortEngine getInstance() {
        if (instance == null) {
            instance = new SerialPortEngine();
        }
        return instance;
    }

    protected SerialPort mSerialPort;
    protected OutputStream mOutputStream;
    private InputStream mInputStream;
    private ReadThread mReadThread;

    private final String SUFFIX = "\n";

    private StringBuilder sbMsg = new StringBuilder();

    private class ReadThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                int size;
                try {
                    byte[] buffer = new byte[8];
                    if (mInputStream == null) return;
                    size = mInputStream.read(buffer);
                    if (size > 0) {
//                        onDataReceived(buffer, size);

                        String result = new String(buffer, 0, size);
                        sbMsg.append(result);
//                        MLog.log("收到回调,大小" + size + "--->" + result + "   结束没：" + result.endsWith(SUFFIX));
                        if (result.endsWith(SUFFIX)) {
                            String finalResult = sbMsg.toString().trim();
                            MLog.log("最后结果--->" + finalResult.length() + "--->" + finalResult);
                            sbMsg.delete(0, sbMsg.length());

                            if (finalResult.startsWith(SerialPortCmd.OK_HEIGHT)) { //身高
                                String height = SerialPortCmd.parseHeight2(finalResult);
                                int realHeight = getRealHeight(height);
                                if (realHeight != -1) {
                                    MyApplication.get().serialPortCallBack(SerialPortCmd.OK_HEIGHT + "=" + realHeight);
                                    heightTemps.clear();
                                    SerialPortCmd.stopHeight();
                                } else if (Integer.parseInt(height) > 100) {
                                    MyApplication.get().serialPortIng(finalResult);
                                }
                            } else if (finalResult.startsWith(SerialPortCmd.OK_WEIGHT)) { //体重
                                String weight = SerialPortCmd.parseWeight2(finalResult);
                                int realWeight = getRealWeight(weight);
                                if (realWeight != -1) {
                                    MyApplication.get().serialPortCallBack(SerialPortCmd.OK_WEIGHT + "=" + realWeight);
                                    weightTemps.clear();
                                    SerialPortCmd.stopWeight();
                                } else if (Integer.parseInt(weight) > 100) {
                                    MyApplication.get().serialPortIng(finalResult);
                                }
                            } else if (finalResult.startsWith(SerialPortCmd.OK_BODYTEMP)) { //体温
                                int realTemp = getRealTemp(SerialPortCmd.parseTemp2(finalResult));
                                if (realTemp != -1) {
                                    MyApplication.get().serialPortCallBack(finalResult);
                                    SerialPortCmd.stopTemp();
                                }
                            } else if(finalResult.startsWith(SerialPortCmd.OK_BODYFAT)){ //人体成分

                                BodyReport bodyReport = new BodyReport("", "");
                                SerialPortCmd.parseBodyFat(finalResult, bodyReport);
                                if(Float.parseFloat(bodyReport.getBm_bf_bf()) > 0f){
                                    MyApplication.get().serialPortCallBack(finalResult);
                                }

                            } else if (finalResult.startsWith(SerialPortCmd.OK_BLOODOX)) {//血氧
                                String[] datas = SerialPortCmd.parseBloodOX2(finalResult);
                                MLog.log("血氧值：" + datas.toString());
                                int realSpo2 = getRealNo0(datas[0], bloodoTemps);
                                int realPr = getRealNo0(datas[1], prTemps);

                                if (realSpo2 != -1 && realPr != -1) {
                                    MyApplication.get().serialPortCallBack(finalResult);
                                    bloodoTemps.clear();
                                    prTemps.clear();
                                    SerialPortCmd.stopBloodOX();
                                } else if (Integer.parseInt(datas[0]) > 0) {
                                    MyApplication.get().serialPortIng(finalResult);
                                }
                            }  else {
                                MyApplication.get().serialPortCallBack(finalResult);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    SerialPortEngine() {
        try {
            mSerialPort = new SerialPort(new File(Constant.SERIAL_PATH), Constant.BAUDRATE, 0);
            mOutputStream = mSerialPort.getOutputStream();
            mInputStream = mSerialPort.getInputStream();

			/* Create a receiving thread */
            mReadThread = new ReadThread();
            mReadThread.start();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {

        if (mOutputStream == null)
            return;
        try {
            MLog.log("发送信息--->" + msg);
            mOutputStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getRealNo0(String data, List<Integer> dataTemp) {
        int dataInt = Integer.parseInt(data);
        if (dataInt == 0)
            return -1;

        return getReal2(dataInt, dataTemp);
    }

    public int getRealTemp(String data) {
        int temp = Integer.parseInt(data);
        if (temp < 100) {
            return -1;
        }
        return temp;
    }

    public int getRealHeight(String data) {
        int heightInt = Integer.parseInt(data);
        if (heightInt < 100)
            return -1;
        return getReal(heightInt, heightTemps, 1);
    }

    public int getRealWeight(String data) {
        int weightInt = Integer.parseInt(data);
        if (weightInt < 100)
            return -1;
        return getReal(weightInt, weightTemps, 1);
    }

    public int getReal(int data, List<Integer> cacheTemps, int subError) {
        cacheTemps.add(data);
        if (cacheTemps.size() < 3) {
            return -1;
        }

        MLog.log("比较三个值-->" + cacheTemps.get(0) + "  " + cacheTemps.get(1) + "  " + cacheTemps.get(2));
        if (Math.abs(cacheTemps.get(0) - cacheTemps.get(1)) <= subError && Math.abs(cacheTemps.get(1) - cacheTemps.get(2)) <= subError) {
            return (cacheTemps.get(0) + cacheTemps.get(1) + cacheTemps.get(2)) / 3;
        } else {
            cacheTemps.remove(0);
            cacheTemps.add(data);
            return -1;
        }
    }


    public int getReal2(int data, List<Integer> cacheTemps) {
        cacheTemps.add(data);
        if (cacheTemps.size() < 5) {
            return -1;
        }

        MLog.log("比较五个值-->" + cacheTemps.get(0) + "  " + cacheTemps.get(1) + "  " + cacheTemps.get(2) + "  " + cacheTemps.get(3) + "  " + cacheTemps.get(4));
        if ((int) cacheTemps.get(0) == cacheTemps.get(1) && (int) cacheTemps.get(1) == cacheTemps.get(2)
                && (int) cacheTemps.get(2) == cacheTemps.get(3) && (int) cacheTemps.get(3) == cacheTemps.get(4)) {
            return (cacheTemps.get(0) + cacheTemps.get(1) + cacheTemps.get(2)) / 3;
        } else {
            cacheTemps.remove(0);
            cacheTemps.add(data);
            return -1;
        }
    }
}
