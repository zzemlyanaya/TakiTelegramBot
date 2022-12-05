package ru.zzemlyanaya.takibot.domain.utils;

/* created by zzemlyanaya on 05/12/2022 */

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import ru.zzemlyanaya.takibot.domain.model.ChartEntryModel;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ChartGenerator {

    static StandardChartTheme theme = (StandardChartTheme) StandardChartTheme.createJFreeTheme();

    static {
        theme.setRangeGridlinePaint(Color.decode("#DADADA"));
        theme.setPlotBackgroundPaint(Color.white);
        theme.setChartBackgroundPaint(Color.white);

    }

    public static void generate(List<ChartEntryModel> data, Long userId) throws IOException {
        JFreeChart chart = createChart(createDataset(data));
        theme.apply(chart);

        ((BarRenderer) chart.getCategoryPlot().getRenderer()).setBarPainter(new StandardBarPainter());
        chart.getCategoryPlot().setOutlineVisible(false);
        chart.getCategoryPlot().getRangeAxis().setAxisLineVisible(false);
        chart.getCategoryPlot().getRangeAxis().setTickMarksVisible(false);

        BarRenderer r = (BarRenderer) chart.getCategoryPlot().getRenderer();
        r.setSeriesPaint(0, Color.decode("#7b68ee"));

        ChartUtils.saveChartAsPNG(new File("generated/%s.png".formatted(userId)), chart, 750, 600);

    }

    private static CategoryDataset createDataset(List<ChartEntryModel> data) {
        var dataset = new DefaultCategoryDataset();
        data.forEach(entry -> dataset.addValue(entry.progress(), "Progress", entry.date()));

        return dataset;
    }

    private static JFreeChart createChart(CategoryDataset dataset) {
        return ChartFactory.createBarChart(
            "Статистика!",
            "",
            "Прогресс, %",
            dataset,
            PlotOrientation.VERTICAL,
            false, true, false
        );
    }
}