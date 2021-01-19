package memory;

import utils.HttpclientToDingWebhook;
import utils.LineChart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static utils.Utils.getContainData;

/**
 * @author: gq
 * @createtime: 2021/1/19 14:35
 * @description: TODO
 */
public class GetDumpMemory {
    public static void getDumpMemory(String ip, List listinfo, int num) throws IOException {
        //定义钉钉机器人url
        String webhook = "https://oapi.dingtalk.com/robot/send?" +
                "access_token=b324c84816371842cfe590d243898eca1eee7a78d18212c2175db0c6fd914052";

        String cmd = "adb connect " + ip;
        Process process = Runtime.getRuntime().exec(cmd);

        //执行的命令
        String command = "adb -s " + ip + " shell dumpsys meminfo";

        //定义需要的关键字
        List<String> containList = new ArrayList<String>();
        containList.add("midi");
        containList.add("system");
        containList.add("Free RAM");

        try {
            listinfo = getContainData(listinfo, command, containList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String text = "{\n" +
                    "     \"msgtype\": \"text\",\n" +
                    "     \"text\": {\n" +
                    "         \"content\": \"midi：system：free" + listinfo.get(num) + "\"\n" +
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

            //绘制折线图
            LineChart.getLine(listinfo, containList);

            System.out.println("计数num：" + num);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
