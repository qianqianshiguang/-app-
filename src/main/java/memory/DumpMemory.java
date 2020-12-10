package memory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: gq
 * @createtime: 2020/9/9 16:19
 * @description: TODO
 */
public class DumpMemory {
    //获取内存，将内存数据转化为数组
    public static List<String> readLineMemoryToArrList(String command, String contain) throws IOException {
        String lineMemory;
        List<String> list = new ArrayList<String>();
        BufferedInputStream bs = null;
        try {
            Process process = Runtime.getRuntime().exec(command);
            bs = new BufferedInputStream(process.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bs));
            while ((lineMemory = bufferedReader.readLine()) != null) {
                if (isName(lineMemory, contain)) {
                    list.add(lineMemory);
                    break;
                }

            }
//            process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bs.close();
            return list;
        }


    }

    //获取指定的内存数据输出为map类型
    public static List<String> getContainMemory(List listMeminfo, String command) throws IOException {
        String memory = null;
        List list = new ArrayList();
        List<String> contain = new ArrayList<String>();
        contain.add("pari");
        contain.add("system");
        contain.add("free");
        for (int i = 0; i < contain.size(); i++) {
            memory = DumpMemory.readLineMemoryToArrList(command, contain.get(i)).toString();
            list.add(printMemory(memory));
        }
        System.out.println(list.toString());
        listMeminfo.add(list);
        return listMeminfo;
    }

    //打印内存数据
    public static String printMemory(String meminfo) {
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

    public static Boolean isName(String memory, String contain) {
        return memory.contains(contain);
    }

}
