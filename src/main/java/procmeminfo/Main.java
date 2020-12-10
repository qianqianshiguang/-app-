package procmeminfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static utils.HttpclientToDingWebhook.sendResponseToDingWebhook;
import static utils.Utils.getContainData;

/**
 * @author: gq
 * @createtime: 2020/9/9 16:45
 * @description: TODO
 */
public class Main {
    public static void main(String[] args) throws IOException {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            //保存所有的信息
            private List listinfo = new ArrayList();
            private int num = 0;
            private String ip = "30.40.44.71:5555";
            //定义钉钉机器人url
            String webhook = "https://oapi.dingtalk.com/robot/send?" +
                    "access_token=b324c84816371842cfe590d243898eca1eee7a78d18212c2175db0c6fd914052";

            @Override
            public void run() {
                //执行的命令
                String command = "adb -s " + ip + " shell cat proc/meminfo";
                //定义需要的关键字
                List<String> containList = new ArrayList<String>();
                containList.add("MemFree");
                containList.add("MemAvailable");
                containList.add("Cached");
                try {
                    listinfo = getContainData(listinfo, command, containList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {

                    String text = "{\n" +
                            "     \"msgtype\": \"text\",\n" +
                            "     \"text\": {\n" +
                            "         \"content\": \"MemFree：MemAvailable：Cached" + listinfo + "\"\n" +
                            "     },\n" +
                            "     \"at\": {\n" +
                            "         \"atMobiles\": [\n" +
                            "             \"  \"\n" +
                            "         ], \n" +
                            "         \"isAtAll\": false\n" +
                            "     }\n" +
                            " }";
                    sendResponseToDingWebhook(webhook, text);
                    num = num + 1;
                    LineChart.getLine(listinfo);

                    System.out.println("计数num：" + num);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, 0, 10000);
    }
}
