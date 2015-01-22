/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.token.printing.chart;

import com.google.gson.Gson;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import javafx.scene.Scene;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.scene.web.WebView;

import org.unhcr.eg.registration.security.date.ClockManager;
import org.unhcr.eg.registration.tool.token.printing.models.AccessTimeReport;
import org.unhcr.eg.registration.tool.token.printing.service.TokenManagerService;
import org.w3c.dom.Document;

/**
 * A simulated stock line chart.
 *
 * @see javafx.scene.chart.Chart
 * @see javafx.scene.chart.LineChart
 * @see javafx.scene.chart.NumberAxis
 * @see javafx.scene.chart.XYChart
 */
public class ArrivalChart extends JFXPanel {

    private Timeline animation;
    private Date startingDate;
    private Date endDate;
    private Date lastLoadingDate = new Date(0);
    private WebView webview;
    private TreeMap<java.sql.Timestamp, List<AccessTimeReport>> accessTimeReports;
    private boolean ready;

    public ArrivalChart() throws Exception {
        startingDate = TokenManagerService.getMinReceptionDate();
        endDate = TokenManagerService.getMaxReceptionDate();
        webview = new WebView();
        webview.setContextMenuEnabled(false);
        webview.getEngine().load(
                ArrivalChart.class.getResource("/org/unhcr/eg/registration/tool/token/printing/chart/ArrivalChart.html").toExternalForm()
        );
        setScene(new Scene(webview));

    }

    public void getFreshData() throws SQLException {
        accessTimeReports = TokenManagerService.getAccessTimeReport(ClockManager.getSQLDate(startingDate), ClockManager.getSQLDate(endDate), ClockManager.getSQLDate(lastLoadingDate));
        getOfflineData(0);
    }

    public void getOfflineData(int i) {
        if (!ready) {
            webview.getEngine().documentProperty().addListener(new ChangeListener<Document>() {
                @Override
                public void changed(ObservableValue<? extends Document> observableValue, Document document, Document newDoc) {
                    if (newDoc != null) {
                        webview.getEngine().documentProperty().removeListener(this);
                        ready = true;
                        pushData(i);
                    }
                }
            });
        } else {
            pushData(i);
        }
    }

    protected void pushData(int i) {
        Platform.runLater(() -> {
            webview.getEngine().executeScript("removeData()");
            parseData(accessTimeReports, i);
        });

    }

    protected void parseData(TreeMap<java.sql.Timestamp, List<AccessTimeReport>> caseAccessTimeReport, int i) {
        List<ChartPoint> individuals = new ArrayList<>();
        List<ChartPoint> cases = new ArrayList<>();
        List<ChartPoint> cumulateIndividuals = new ArrayList<>();
        List<ChartPoint> cumulateCases = new ArrayList<>();

        caseAccessTimeReport.entrySet().stream().forEach((entrySet) -> {
            java.sql.Timestamp key = entrySet.getKey();
            Date label = new Date(key.getTime());
            List<AccessTimeReport> values = entrySet.getValue();

            for (AccessTimeReport value : values) {
                switch (value.getTypeOfNumber()) {
                    case "I":
                        individuals.add(new ChartPoint(label.getTime(), value.getCumulativeNumber()));
                        break;
                    case "C":
                        cases.add(new ChartPoint(label.getTime(), value.getCumulativeNumber()));
                        break;
                    case "AI":
                        cumulateIndividuals.add(new ChartPoint(label.getTime(), value.getCumulativeNumber()));
                        break;
                    case "CI":
                        cumulateCases.add(new ChartPoint(label.getTime(), value.getCumulativeNumber()));
                        break;
                }
            }
        });
        Gson gson = new Gson();
        if (i == 0) {
            System.out.println("addIndividuals addCases");
            webview.getEngine().executeScript("addIndividuals(" + gson.toJson(individuals) + ")");
            webview.getEngine().executeScript("addCases(" + gson.toJson(cases) + ")");
        } else if (i == 1) {
            System.out.println("addCumulateIndividuals addCumulateCases");
            webview.getEngine().executeScript("addCumulateIndividuals(" + gson.toJson(cumulateIndividuals) + ")");
            webview.getEngine().executeScript("addCumulateCases(" + gson.toJson(cumulateCases) + ")");

        }
    }

    protected void changeLastLoadingDate(Date label) {
        if (lastLoadingDate == null) {
            lastLoadingDate = label;
        }
        lastLoadingDate = ClockManager.max(label, lastLoadingDate);
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) throws SQLException {
        this.startingDate = ClockManager.getDateOnly(startingDate);
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) throws SQLException {
        this.endDate = ClockManager.getDateOnly(endDate);
    }

    protected String getDateLabel(Date date) {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return df.format(date);
    }

    public void play() {
        animation.play();
    }

    public void stop() {
        animation.pause();
    }

    private void clearData() {
        webview.getEngine().executeScript("removeData()");
    }

}
