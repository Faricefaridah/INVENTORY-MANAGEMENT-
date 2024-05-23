package com.ims.app.reports;

import com.ims.app.model.Product;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ReportService {

    public void createBarChart(List<Product> products, String filePath) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Product product : products) {
            dataset.addValue(product.getMaximumProducts(), "Maximum Products", product.getProductName());
            dataset.addValue(product.getMinimumProducts(), "Minimum Products", product.getProductName());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Product Quantity Comparison",
                "Product",
                "Quantity",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartUtils.saveChartAsJPEG(new File(filePath), barChart, 800, 600);
    }

    public void createLineChart(List<Product> products, String filePath) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Product product : products) {
            dataset.addValue(product.getMaximumProducts(), "Maximum Products", product.getProductName());
            dataset.addValue(product.getMinimumProducts(), "Minimum Products", product.getProductName());
        }

        JFreeChart lineChart = ChartFactory.createLineChart(
                "Product Quantity Over Time",
                "Product",
                "Quantity",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartUtils.saveChartAsJPEG(new File(filePath), lineChart, 800, 600);
    }

    public void createPieChart(List<Product> products, String filePath) throws IOException {
        DefaultPieDataset dataset = new DefaultPieDataset();

        for (Product product : products) {
            dataset.setValue(product.getProductName(), product.getMaximumProducts());
        }

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Product Distribution",
                dataset,
                true, true, false);

        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setSectionOutlinesVisible(true);

        ChartUtils.saveChartAsJPEG(new File(filePath), pieChart, 800, 600);
    }
}

