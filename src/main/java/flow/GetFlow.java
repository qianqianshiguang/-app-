package flow;

import utils.HttpclientToDingWebhook;
import utils.LineChart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.Utils.readLineInfoToArrList;

public class GetFlow {
    private static List flowList = new ArrayList();
//    private static String devices = "621QEBPQ2CDTH";
//    private static String packageName = "com.alibaba.android.rimet";
//    private static int num = 0;

    public static void main(String[] args) throws IOException, InterruptedException {
//        for (int i = 0; i < 10; i++) {
//            System.out.println(getWifiFlow());
//            Thread.sleep(3000);
//        }

//        PID(devices,packageName);

//        getFlow(flowList,devices,packageName, num);
    }

    //获取PID
    public static String PID(String devices, String packageName) {
        //shell命令
        String command = "adb -s " + devices + " shell ps | grep " + packageName;

        String pid = null;
        //获取pid整行信息
        String line = readLineInfoToArrList(command, packageName).toString();
        //获取pid
        pid = line.split("\\s+")[1];
        return pid;
    }


    public static void getFlow(List flowList,String devices,String packageName, int num) {
        List list = new ArrayList();
        //定义钉钉机器人url
        String webhook = "https://oapi.dingtalk.com/robot/send?" +
                "access_token=b324c84816371842cfe590d243898eca1eee7a78d18212c2175db0c6fd914052";
//        long str3 = 0;
        //获取pid
        String pid = PID(devices, packageName);
        //定义shell命令
        String command = "adb -s " + devices + " shell cat /proc/" + pid + "/net/dev";

        //获取流量数据
        String line = readLineInfoToArrList(command, "wlan0:").toString();
        List<String> result = Arrays.asList(line.split("\\s+"));
        String a = result.get(2).trim();
        String b = result.get(10).trim();

        //上行流量
        long receive = Long.parseLong(a);
        //下行流量
        long send = Long.parseLong(b);
        list.add(receive/1024/1024);
        list.add(send/1024/1024);
        System.out.println(list);
        flowList.add(list);
//        str3 = ((receive + send) / 1024) / 1024;
//        System.out.println(str3);

        //发送数据到钉钉，绘制折线图
        try {
            String text = "{\n" +
                    "     \"msgtype\": \"text\",\n" +
                    "     \"text\": {\n" +
                    "         \"content\": \"recevice：send" + flowList.get(num) + "\"\n" +
                    "     },\n" +
                    "     \"at\": {\n" +
                    "         \"atMobiles\": [\n" +
                    "             \"  \"\n" +
                    "         ], \n" +
                    "         \"isAtAll\": false\n" +
                    "     }\n" +
                    " }";
            //发送数据到钉钉
            HttpclientToDingWebhook.sendResponseToDingWebhook(webhook, text);
            num = num + 1;
            //绘制折线图
            List<String> config = new ArrayList<String>();
            config.add("recevice");
            config.add("send");
            LineChart.getLine(flowList, config);

            System.out.println("计数num：" + num);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取流量
     */
    public static long getWifiFlow(String devices,String packageName) throws IOException {

        String Pid = PID(devices, packageName);
        long str3 = 0;
        String cmd = "adb -s " + devices + " shell cat /proc/" + Pid + "/net/dev";
        System.out.println(cmd);
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec(cmd);
            try {
                if (proc.waitFor() != 0) {
                    System.err.println("exit value = " + proc.exitValue());
                }
                String line = null;
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        proc.getInputStream()));
                StringBuffer stringBuffer = new StringBuffer();
                while ((line = in.readLine()) != null) {
                    stringBuffer.append(line + " ");
                    if (line.contains("wlan0:")) {
                        List<String> list = Arrays.asList(line.split("\\s+"));
                        String rcv = list.get(2).trim();
                        String send = list.get(10).trim();
                        long a = Long.parseLong(rcv);
                        long b = Long.parseLong(send);
                        System.out.println("【流量数据统计】：下行：" + ((a / 1024) / 1024) + "MB" + "上行：" + ((b / 1024) / 1024) + "MB");
                        str3 = ((a + b) / 1024) / 1024;
                        break;
                    }
                }

            } catch (InterruptedException e) {
                System.err.println(e);
            } finally {
                try {
                    proc.destroy();
                } catch (Exception e2) {
                }
            }
        } catch (Exception StringIndexOutOfBoundsException) {
        }
        return str3;
    }

}


