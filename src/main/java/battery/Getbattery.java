package battery;

import utils.HttpclientToDingWebhook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static utils.Utils.getContainData;

/**
 * @author: gq
 * @createtime: 2020/12/10
 * @description: 通过dumpsys battery获取电量和温度
 */
public class Getbattery {
    private static String devices = "621QEBPQ2CDTH";
    public static void main(String[] args) throws IOException, InterruptedException {
//        for (int i = 0; i < 100; i++) {
//            System.out.print(battery());
//            Thread.sleep(3000);
//        }
        int num = 0;
        List listinfo = new ArrayList();
        getBattery(listinfo, devices,num);
    }

    public static void getBattery(List batteryList,String devices,int num) {
        //定义钉钉机器人url
        String webhook = "https://oapi.dingtalk.com/robot/send?" +
                "access_token=b324c84816371842cfe590d243898eca1eee7a78d18212c2175db0c6fd914052";

        //执行的命令
        String command = "adb -s " + devices + " shell dumpsys battery";

        //定义需要的关键字
        List<String> containList = new ArrayList<String>();
        containList.add("temperature");
        containList.add("level");

        //获取电量和温度
        try {
            getContainData(batteryList, command, containList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //发送数据到钉钉，绘制折线图
        try {
            String text = "{\n" +
                    "     \"msgtype\": \"text\",\n" +
                    "     \"text\": {\n" +
                    "         \"content\": \"temperature：level" + batteryList.get(num) + "\"\n" +
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
            LineChart.getLine(batteryList);

            System.out.println("计数num：" + num);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static double battery() throws IOException {
        double batt = 0;
        Runtime runtime = Runtime.getRuntime();
        Process proc = runtime.exec("adb -s " + devices + " shell dumpsys battery");
        String str3 = "0";
        try {
            if (proc.waitFor() != 0) {
                System.err.println("exit value = " + proc.exitValue());
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String line = null;
            while ((line = in.readLine()) != null) {
                stringBuffer.append(line + " ");
                if (line.contains("temperature")) {
                    String str = line.substring(line.indexOf(":") + 2, line.length());
                }
                if (line.contains("level")) {
                    str3 = line.substring(line.indexOf(":") + 2, line.length());
                }
            }
            batt = Double.parseDouble(str3.trim());
        } catch (InterruptedException e) {
            batt = -0.1;
            System.err.println(e);
        } finally {
            try {
                proc.destroy();
            } catch (Exception e2) {
            }
        }
        return (batt);
    }


}


