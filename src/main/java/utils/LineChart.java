package utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author: gq
 * @createtime: 2020/12/9 17:21
 * @description: 将获取的数据绘制成折线图
 */
public class LineChart {
    public static void getLine(Integer memFree, Integer memAvailable, Integer cached,List<String> containList) throws IOException {
        DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
        for (int i = 0; i < containList.size(); i++) {
            line_chart_dataset.addValue(memFree,containList.get(0),i+"");
            line_chart_dataset.addValue(memAvailable,containList.get(1),i+"");
            line_chart_dataset.addValue(cached,containList.get(2),i+"");
        }


//        for (int i = 0; i < listMeminfo.size(); i++) {
//            List list = (List) listMeminfo.get(i);
//            Integer focus = Integer.valueOf(String.valueOf(list.get(0)));
//            Integer system = Integer.valueOf(String.valueOf(list.get(1)));
//            Integer free = Integer.valueOf(String.valueOf(list.get(2)));
//            line_chart_dataset.addValue(focus, "MemFree", i + "");
//            line_chart_dataset.addValue(system, "MemAvailable", i + "");
//            line_chart_dataset.addValue(free, "Cached", i + "");
//
//        }

        JFreeChart lineChartObject = ChartFactory.createLineChart(
                "cat /proc/meminfo", "number",
                "kb",
                line_chart_dataset, PlotOrientation.VERTICAL,
                true, true, false);

        int width = 640; /* Width of the image */
        int height = 480; /* Height of the image */
        File lineChart = new File("meminfo.jpeg");
        ChartUtilities.saveChartAsJPEG(lineChart, lineChartObject, width, height);
    }

}

