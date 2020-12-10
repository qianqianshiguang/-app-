package cpu;

import utils.Commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class GetTop {
    private static double Cpu = 0;

    public static void main(String[] args) throws IOException {
        String devices = "VBJGL18A11000026";
        String packageName = "com.ss.android.ugc.aweme";
        for (int i = 0; i < 10; i++) {
            System.out.println(" Cpu：" + topCpu("com.ss.android", devices));
        }
    }

    public static double topCpu(String packageName, String devices) {

        String cpuStr = "0";
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("adb -s " + devices + " shell top -n 1| grep " + packageName);
            try {
                if (proc.waitFor() != 0) {
                    System.err.println("exit value = " + proc.exitValue());
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                String line = in.readLine();
                List<String> strList = Arrays.asList(line.split("\\s+"));
//                cpuStr = strList.get(4).trim().replace("%", "");
//                if (strList.get(5).trim().contains("%")) {
//                    cpuStr = strList.get(5).trim().replace("%", "");
//                } else {
                cpuStr = strList.get(9);
//                }
                Cpu = Double.parseDouble(cpuStr);
            } catch (InterruptedException e) {
                System.err.println(e);
            } finally {
                try {
                    proc.destroy();
                } catch (Exception e2) {
                }
            }
        } catch (Exception StringIndexOutOfBoundsException) {
            System.out.print("请检查设备是否连接TopCpu");
            return -0.1;
        }
        return Commons.streamDouble(Cpu);
    }


}


