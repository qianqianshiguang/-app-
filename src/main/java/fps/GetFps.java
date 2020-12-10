//package fps;
//
//import Common.Commons;
//import performance.Main;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//
//public class GetFps {
//
//    private static int WAITTIME = 1600;
//    //采样频率，单位ms
//    private static float countTime = 0;
//    //清空之前采样的数据，防止统计重复的时间
//    private static String clearCommand = "adb shell dumpsys SurfaceFlinger --latency-clear";
//    //跳帧率
//    public static long jumpingFrames = 0;
//    public static long totalFrames = 0;
//    public static float lostFrameRate = 0;
//    public static float fps;
//
//    public static void main(String[] args) throws InterruptedException, ArrayIndexOutOfBoundsException {
//        System.out.println(fps()[0]);
//        System.out.println(fps()[1]);
//
//    }
//
//    public static double[] fps(){
//        String gfxCMD = "adb -s " + Main.devices + " shell dumpsys gfxinfo " + Main.packageName;
//        double[] result = new double[2];
//        try {
//            Runtime.getRuntime().exec(clearCommand);
//            result = getFps(gfxCMD);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    public static double[] getFps(String gfxCMD) {
//        double[] result = new double[2];
//        BufferedReader br = null, br1 = null;
//        try {
//            Process pro = Runtime.getRuntime().exec(gfxCMD);
//            br1 = new BufferedReader(new InputStreamReader(pro.getInputStream()));
//            String line = null;
//            boolean flag = false;
//            int framesCount = 0, jankCount = 0, vsync_overtime_sum = 0;
//            while ((line = br1.readLine()) != null) {
//                if (line.length() > 0) {
//                    if (line.contains("Execute")) {
//                        flag = true;
//                        continue;
//                    }
//                    if (line.contains("View hierarchy:")) {
//                        flag = false;
//                        continue;
//                    }
//                    if (flag) {
//                        if (!line.contains(":") && !line.contains("@")) {
//                            String[] timeArray = line.trim().split("\t");
//                            float render_time = Float.parseFloat(timeArray[0]) + Float.parseFloat(timeArray[1])
//                                + Float.parseFloat(timeArray[2]) + Float.parseFloat(timeArray[3]);
//                            //总帧数
//                            framesCount += 1;
//                            countTime = countTime + render_time;
//                            if (render_time > 16.67) {
//                                jankCount += 1;
//                                if (render_time % 16.67 == 0) {
//                                    vsync_overtime_sum += render_time / 16.67 - 1;
//                                } else {
//                                    vsync_overtime_sum += Math.floor(render_time / 16.67);
//                                }
//                            }
//                        }
//                    }
//
//                }
//            }
//            if ((framesCount + vsync_overtime_sum) > 0) {
//                fps = framesCount * 60 / (framesCount + vsync_overtime_sum);
//                jumpingFrames += jankCount;
//                totalFrames += framesCount;
//                lostFrameRate = (float) jumpingFrames / (float) totalFrames;
////                result[0] = fps;
////                result[1] = (float) Commons.streamDouble(lostFrameRate * 100);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (br != null) {
//                try {
//                    Runtime.getRuntime().exec(clearCommand);
//                    br.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        result[0] = fps;
//        result[1] = Commons.streamDouble(lostFrameRate * 100);
//        return result;
//    }
//
//    /**
//     * 求平均值
//     *
//     * @param list
//     * @return
//     */
//    public static int getAvarage(ArrayList<Float> list) {
//        Float fps = 0f;
//        for (int i = 0; i < list.size(); i++) {
//            fps += list.get(i);
//        }
//        return (int)(fps / list.size());
//    }
//
//}
//
