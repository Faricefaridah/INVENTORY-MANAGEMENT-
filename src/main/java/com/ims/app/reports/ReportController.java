package com.ims.app.reports;

import com.ims.app.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/bar")
    public String generateBarChart(List<Product> products) {
        try {
            reportService.createBarChart(products, "barChart.jpeg");
            return "Bar chart generated successfully.";
        } catch (IOException e) {
            return "Error generating bar chart: " + e.getMessage();
        }
    }

    @GetMapping("/line")
    public String generateLineChart(List<Product> products) {
        try {
            reportService.createLineChart(products, "lineChart.jpeg");
            return "Line chart generated successfully.";
        } catch (IOException e) {
            return "Error generating line chart: " + e.getMessage();
        }
    }

    @GetMapping("/pie")
    public String generatePieChart(List<Product> products) {
        try {
            reportService.createPieChart(products, "pieChart.jpeg");
            return "Pie chart generated successfully.";
        } catch (IOException e) {
            return "Error generating pie chart: " + e.getMessage();
        }
    }

//    @GetMapping("/sample")
//    public List<Product> getSampleProducts() {
//        return List.of(
//                new Product(1L, "Electronics", "Laptop", 5L, 4L, 50L, 5L, "user1", "user1@example.com", 1234567890L),
//                new Product(2L, "Electronics", "Smartphone", 4L, 4L, 100L, 10L, "user2", "user2@example.com", 1234567891L),
//                new Product(3L, "Furniture", "Chair", 3L, 3L, 200L, 20L, "user3", "user3@example.com", 1234567892L)
//        );
    }

