/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.token.printing.chart;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.Duration;
import org.unhcr.eg.registration.security.date.ClockManager;
import org.unhcr.eg.registration.tool.token.printing.models.AccessTimeReport;
import org.unhcr.eg.registration.tool.token.printing.service.TokenManagerService;

/**
 * A simulated stock line chart.
 *
 * @see javafx.scene.chart.Chart
 * @see javafx.scene.chart.LineChart
 * @see javafx.scene.chart.NumberAxis
 * @see javafx.scene.chart.XYChart
 */
public class ArrivalChart {

    private XYChart.Series<String, Number> caseDataSeries;
    private XYChart.Series<String, Number> individualDataSeries;
    private CategoryAxis xAxis;
    private Timeline animation;
    private Date startingDate;
    private Date endDate;
    private String startingDateLabel;
    private String endDateLabel;

    private void init(Stage primaryStage) throws SQLException {

        endDate = TokenManagerService.getMaxReceptionDate();
        startingDate = TokenManagerService.getMinReceptionDate();
        Group root = new Group();
        primaryStage.setScene(new Scene(root));
        root.getChildren().add(createChart());
        // create timeline to add new data every 60th of second
        animation = new Timeline();
        animation.getKeyFrames().add(new KeyFrame(Duration.millis(1000 / 60), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                geNextData();
            }

        }));
        animation.setCycleCount(Animation.INDEFINITE);
    }

    protected void geNextData(Date startingDate, Date endDate) throws SQLException {
        clearData();
        TreeMap<java.sql.Date, AccessTimeReport> caseAccessTimeReport = TokenManagerService.getCaseAccessTimeReport(ClockManager.getSQLDate(startingDate), ClockManager.getSQLDate(endDate));
        parseData(caseAccessTimeReport, caseDataSeries);

        TreeMap<java.sql.Date, AccessTimeReport> individualAccessTimeReport = TokenManagerService.getIndividualAccessTimeReport(ClockManager.getSQLDate(startingDate), ClockManager.getSQLDate(endDate));
        parseData(individualAccessTimeReport, individualDataSeries);

    }

    protected void parseData(TreeMap<java.sql.Date, AccessTimeReport> caseAccessTimeReport, XYChart.Series<String, Number> dataSeries) {
        TreeMap<Date, Integer> values = new TreeMap<>();
        caseAccessTimeReport.entrySet().stream().forEach((entrySet) -> {
            java.sql.Date key = entrySet.getKey();
            Date label = ClockManager.getDateAndHourOnly(ClockManager.getUtilDate(key));
            AccessTimeReport value = entrySet.getValue();
            values.put(label, value.getCumulativeNumber());
        });
        values.entrySet().stream().forEach((entrySet) -> {
            Date key = entrySet.getKey();
            Integer value = entrySet.getValue();
            dataSeries.getData().add(new XYChart.Data<>(getDateLabel(key), value));
        });
    }

    protected void clearData() {
        caseDataSeries.getData().clear();
        individualDataSeries.getData().clear();
    }

    protected void geNextData() {
        clearData();

    }

    protected String getDateLabel(Date date) {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return df.format(date);
    }

    protected void plotTime(Date startingDate, Date endDate) throws SQLException {
        this.endDate = TokenManagerService.getMaxReceptionDate(ClockManager.getSQLDate(startingDate), ClockManager.getSQLDate(endDate));
        this.startingDate = TokenManagerService.getMinReceptionDate(ClockManager.getSQLDate(startingDate), ClockManager.getSQLDate(endDate));
        this.startingDateLabel = getDateLabel(this.startingDate);
        this.endDateLabel = getDateLabel(this.endDate);
    }

    protected LineChart<String, Number> createChart() {
        xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis(0, 1000, 10);
        final LineChart<String, Number> lc = new LineChart<>(xAxis, yAxis);
        // setup chart
        lc.setId("lineStockDemo");
        lc.setCreateSymbols(false);
        lc.setAnimated(false);
        lc.setLegendVisible(false);
        lc.setTitle("Arrival Chart Per hour");
        xAxis.setLabel("Time");

        yAxis.setLabel("Number of Individual");
        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis, "", null));
        // add starting data
        caseDataSeries = new XYChart.Series<>();
        caseDataSeries.setName("Case Data");
        individualDataSeries = new XYChart.Series<>();
        individualDataSeries.setName("Individual Data");

        lc.getData().add(individualDataSeries);
        lc.getData().add(caseDataSeries);
        return lc;
    }

    public void play() {
        animation.play();
    }

    public void stop() {
        animation.pause();
    }

    public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();
        play();
    }

}
