package com.incometaxcalculator.gui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RefineryUtilities;

import javax.swing.JFrame;
import java.awt.BorderLayout;

import static com.incometaxcalculator.data.io.LogOptions.BASIC;
import static com.incometaxcalculator.data.io.LogOptions.ENTERTAINMENT;
import static com.incometaxcalculator.data.io.LogOptions.HEALTH;
import static com.incometaxcalculator.data.io.LogOptions.OTHER;
import static com.incometaxcalculator.data.io.LogOptions.TRAVEL;

class ChartDisplay {

  static void createPieChart(
      double entertainment,
      double basic,
      double travel,
      double health,
      double other
  ) {
    JFrame pieChartFrame = new JFrame("Analysis of receipts");
    pieChartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    pieChartFrame.setLayout(new BorderLayout(0, 5));
    pieChartFrame.add(
        createPieChartPanel(entertainment, basic, travel, health, other),
        BorderLayout.CENTER
    );
    pieChartFrame.pack();
    RefineryUtilities.centerFrameOnScreen(pieChartFrame);
    pieChartFrame.setVisible(true);
  }

  private static ChartPanel createPieChartPanel(double entertainment, double basic, double travel,
      double health, double other) {
    JFreeChart pieChart = ChartFactory.createPieChart(
        "Percentage of the total amount of each kind of receipt.",
        createDefaultPieDataset(entertainment, basic, travel, health, other),
        true,
        true,
        false
    );
    ChartPanel barChartPanel = new ChartPanel(pieChart);
    barChartPanel.setPreferredSize(new java.awt.Dimension(450, 550));
    return barChartPanel;
  }

  private static DefaultPieDataset<String> createDefaultPieDataset(
      double entertainment,
      double basic,
      double travel,
      double health,
      double other
  ) {
    var pieChartDataset = new DefaultPieDataset<String>();
    pieChartDataset.setValue(ENTERTAINMENT.originalName(), entertainment);
    pieChartDataset.setValue(BASIC.originalName(), basic);
    pieChartDataset.setValue(TRAVEL.originalName(), travel);
    pieChartDataset.setValue(HEALTH.originalName(), health);
    pieChartDataset.setValue(OTHER.originalName(), other);
    return pieChartDataset;
  }

  static void createBarChart(double basicTax, double taxVariation, double totalTax) {
    JFrame barChartFrame = new JFrame("Bar Chart");
    barChartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    barChartFrame.setLayout(new BorderLayout(0, 5));
    barChartFrame.add(createBarChartPanel(basicTax, taxVariation, totalTax), BorderLayout.CENTER);
    barChartFrame.pack();
    RefineryUtilities.centerFrameOnScreen(barChartFrame);
    barChartFrame.setVisible(true);
  }

  private static ChartPanel createBarChartPanel(double basicTax, double taxVariation,
      double totalTax) {
    JFreeChart barChart = ChartFactory.createBarChart("", "", "Tax analysis in $",
        createDefaultCategoryDataset(basicTax, taxVariation, totalTax), PlotOrientation.VERTICAL,
        true, true, false);
    ChartPanel barChartPanel = new ChartPanel(barChart);
    barChartPanel.setPreferredSize(new java.awt.Dimension(450, 550));
    return barChartPanel;
  }

  private static DefaultCategoryDataset createDefaultCategoryDataset(double basicTax,
      double taxVariation, double totalTax) {
    DefaultCategoryDataset barChartDataset = new DefaultCategoryDataset();
    barChartDataset.addValue(basicTax, "Tax", BASIC.originalName());
    if (taxVariation > 0) {
      barChartDataset.addValue(taxVariation, "Tax", "Increase");
    } else {
      barChartDataset.addValue(-taxVariation, "Tax", "Decrease");
    }
    barChartDataset.addValue(totalTax, "Tax", "Total");
    return barChartDataset;
  }
}