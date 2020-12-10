package crash;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DumpCrash {
    //获取内存，将内存数据转化为数组
    public static List<String> readLineCrashToArrList(String command) throws IOException {
        //1.命令获取
        BufferedInputStream bs = null;
        BufferedReader bufferedReader = null;
        List<String> listCrash = new ArrayList<String>();
        String crash = null;
        int num = 0;
        try {
            //            FileInputStream inputStream = new FileInputStream("C:\\Users\\Administrator\\Desktop\\log\\osf(1).log");
            //            //设置inputStreamReader的构造方法并创建对象设置编码方式为gbk
            //            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "gbk"));

            Process process = Runtime.getRuntime().exec(command);
            bs = new BufferedInputStream(process.getInputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(bs));
            while ((crash = bufferedReader.readLine()) != null) {
                if (crash.contains("Fatal") || crash.contains("FATAL") || crash.contains("fatal")) {
                    System.out.println(crash);
                    listCrash.add(crash);
                    HttpWebApi.getHttpWebApi(listCrash.get(num));
                    num++;
                    System.out.println("crash次数：" + num);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bs.close();
            bufferedReader.close();
            return listCrash;
        }

        //2.从文件获取
//        String crash = null;
//        List<String> listCrash = new ArrayList<String>();
//        FileInputStream inputStream = null;
//        BufferedReader bufferedReader = null;
//        int num = 0;
//        try {
//            inputStream = new FileInputStream("C:\\Users\\Administrator\\Desktop\\log\\osf(1).log");
//            //设置inputStreamReader的构造方法并创建对象设置编码方式为gbk
//            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "gbk"));
//            while ((crash = bufferedReader.readLine()) != null) {
//                if (crash.contains("Fatal") || crash.contains("FATAL") || crash.contains("fatal")) {
//                    System.out.println(crash);
//                    listCrash.add(crash);
//                    HttpWebApi.getHttpWebApi(listCrash.get(num));
//                    num ++;
//                    System.out.println("crash次数："+num);
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        finally {
//            inputStream.close();
//            bufferedReader.close();
//            return listCrash;
//        }
    }
}
