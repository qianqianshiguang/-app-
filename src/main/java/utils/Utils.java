package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author: gq
 * @createtime: 2020/12/9 16:38
 * @description: 执行shell命令后将需要的数据存入list
 */
public class Utils {
    //获取内存，将内存数据转化为数组
    public static List<String> readLineInfoToArrList(String command, String contain) {
        List<String> list = new ArrayList<String>();
        Runtime runtime = Runtime.getRuntime();
        Process proc = null;
        try {
            proc = runtime.exec(command);
            if (proc.waitFor() != 0) {
                System.err.println("exit value = " + proc.exitValue());
            }
            //获取执行shell命令后的信息
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            //含有指定的关键字时，将此数据添加到list数组中
            while (((line = bufferedReader.readLine()) != null)) {
                if (containInfo(line, contain)) {
                    list.add(line);
                    break;
                }
            }

        } catch (InterruptedException e) {
            System.err.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                proc.destroy();
            } catch (Exception e2) {
            }
        }

        return list;
    }

    //字符串中提取数字
    public static String getDigit(String lineInfo) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(lineInfo);
        return m.replaceAll("").trim();
    }

    //判断是否含有指定的关键字
    public static Boolean containInfo(String info, String contain) {
        return info.contains(contain);
    }

    //获取指定的内存数据输出为map类型
    public static List<String> getContainData(List listData, String command, List<String> containList) throws IOException {
        //将获取的3个关键数据存储为List
        List list = new ArrayList();

        //获取关键字对应的数据
        for (int i = 0; i < containList.size(); i++) {

            String data = readLineInfoToArrList(command, containList.get(i)).toString();
            list.add(getDigit(data));
        }
        //打印数值
        System.out.println(list.toString());
        //将数据添加到listMeminfo中
        listData.add(list);
        return listData;
    }

}
