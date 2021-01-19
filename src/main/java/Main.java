import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static memory.GetDumpMemory.getDumpMemory;

public class Main {
    private static String devices = "621QEBPQ2CDTH";
    private static String packageName = "com.alibaba.android.rimet";
    private static String ip = "30.40.47.161:5555";
    private static List memList = new ArrayList();
    private static List batteryList = new ArrayList();
    private static List topList = new ArrayList();
    private static List flowList = new ArrayList();
    private static List fpsList = new ArrayList();

    public static void main(String[] args)  {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int num = 0;
            @Override
            public void run() {
                //通过cat proc/meminfo获取内存
//                getMem(ip, memList,num);

                //通过dumpsys battery获取温度和电量
//                getBattery(batteryList, devices, num);

                //通过cat /proc/pid/net/dev获取流量使用情况
//                getFlow(flowList,devices,packageName, num);

                try {
                    getDumpMemory(ip, memList, num);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                num = num + 1;
            }

        }, 0, 60000);


    }
}
