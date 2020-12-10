package flow;

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
 * @createtime: 2020/9/9 16:28
 * @description: 绘制折线图
 */
public class LineChart {
    public static void getLine(List listMeminfo) throws IOException {
        DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();

        for (int i = 0; i < listMeminfo.size(); i++) {
            List list = (List) listMeminfo.get(i);
            Integer recevice = Integer.valueOf(String.valueOf(list.get(0)));
            Integer send = Integer.valueOf(String.valueOf(list.get(1)));

            line_chart_dataset.addValue(recevice, "recevice", i + "");
            line_chart_dataset.addValue(send, "send", i + "");


        }

        JFreeChart lineChartObject = ChartFactory.createLineChart(
                "dumpsys battery", "number",
                "",
                line_chart_dataset, PlotOrientation.VERTICAL,
                true, true, false);

        int width = 640; /* Width of the image */
        int height = 480; /* Height of the image */
        File lineChart = new File("meminfo.jpeg");
        ChartUtilities.saveChartAsJPEG(lineChart, lineChartObject, width, height);
    }

}

