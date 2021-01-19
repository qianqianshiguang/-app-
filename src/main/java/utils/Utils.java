package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


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
        BufferedReader bufferedReader = null;
        Process proc = null;
        try {
            proc = runtime.exec(command);
            if (proc.waitFor() != 0) {
                System.err.println("exit value = " + proc.exitValue());
                InputStream error = proc.getErrorStream();
                byte[] array = new byte[1024];
                int read = error.read(array);
                System.out.println(new String(array,0,read));

            }
            //获取执行shell命令后的信息
            bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            //含有指定的关键字时，将此数据添加到list数组中
            while (((line = bufferedReader.readLine()) != null)) {
                if (line.contains(contain)){
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
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                proc.destroy();
            } catch (Exception e2) {
            }
        }

        return list;
    }

    //字符串中提取数字
//    public static String getDigit(String lineInfo) {
//        String regEx = "[^0-9]";
//        Pattern p = Pattern.compile(regEx);
//        Matcher m = p.matcher(lineInfo);
//        return m.replaceAll("").trim();
//    }
    //打印内存数据
    public static String getDigit(String meminfo) {
        String result = "";
        int num = 0;
        if (meminfo == null) {
            return null;
        }
        char[] c = meminfo.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 'k' || c[i] == 'K') {
                num++;
                break;
            } else if (Character.isLetter(c[i]) || Character.isSpaceChar(c[i])) {
                result = result;
            } else if (Character.isDigit(c[i])) {
                result = result + c[i];
            }
        }
        return result;

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
