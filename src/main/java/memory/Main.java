package memory;

import utils.HttpclientToDingWebhook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author: gq
 * @createtime: 2020/9/9 16:45
 * @description: TODO
 */
public class Main {
    public static void main(String[] args) throws IOException {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            private List listMemoinfo = new ArrayList();
            private int num = 0;
            private String ip = "30.40.45.151:5555";
            String webhook = "https://oapi.dingtalk.com/robot/send?access_token=b324c84816371842cfe590d243898eca1eee7a78d18212c2175db0c6fd914052";


            @Override
            public void run() {
                String command = "adb -s " + ip + " shell dumpsys meminfo";
                try {
                    listMemoinfo = DumpMemory.getContainMemory(listMemoinfo, command);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    String text = "{\n" +
                            "     \"msgtype\": \"text\",\n" +
                            "     \"text\": {\n" +
                            "         \"content\": \"pair：system：free" + listMemoinfo + "\"\n" +
                            "     },\n" +
                            "     \"at\": {\n" +
                            "         \"atMobiles\": [\n" +
                            "             \"  \"\n" +
                            "         ], \n" +
                            "         \"isAtAll\": false\n" +
                            "     }\n" +
                            " }";
                    HttpclientToDingWebhook.sendResponseToDingWebhook(webhook, text);
                    num = num + 1;
                    LineChartMem.getLine(listMemoinfo);
                    System.out.println("计数num：" + num);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, 0, 60000);
    }
}
