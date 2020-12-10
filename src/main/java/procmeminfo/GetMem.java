package procmeminfo;

import utils.HttpclientToDingWebhook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static utils.Utils.getContainData;

/**
 * @author: gq
 * @createtime: 2020/12/9 17:57
 * @description: 获取mem
 */
public class GetMem {
    public static void getMem(String ip,List listinfo,int num) {
        //定义钉钉机器人url
        String webhook = "https://oapi.dingtalk.com/robot/send?" +
                "access_token=b324c84816371842cfe590d243898eca1eee7a78d18212c2175db0c6fd914052";

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
                    "         \"content\": \"MemFree：MemAvailable：Cached" + listinfo.get(num) + "\"\n" +
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
            LineChart.getLine(listinfo);

            System.out.println("计数num：" + num);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
