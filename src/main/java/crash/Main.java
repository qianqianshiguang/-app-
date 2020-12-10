package crash;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {

        int num = 0;
        String ip = "30.40.45.60:5555";
        String command = "adb -s " + ip + " shell logcat -v time";
        DumpCrash.readLineCrashToArrList(command);

    }
}


