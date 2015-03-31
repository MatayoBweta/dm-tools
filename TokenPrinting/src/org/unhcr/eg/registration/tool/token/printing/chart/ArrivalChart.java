/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.token.printing.chart;

import org.unhcr.eg.registration.tool.token.printing.models.ChartPoint;
import com.google.gson.Gson;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javafx.scene.Scene;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.unhcr.eg.registration.security.date.ClockManager;
import org.unhcr.eg.registration.tool.token.printing.models.AccessTimeReport;
import org.unhcr.eg.registration.tool.token.printing.models.DrillDownDetails;
import org.unhcr.eg.registration.tool.token.printing.models.DrilldownCategoryChartPoint;
import org.unhcr.eg.registration.tool.token.printing.models.VisitSummary;
import org.unhcr.eg.registration.tool.token.printing.service.TokenManagerService;
import org.w3c.dom.Document;

public class ArrivalChart extends JFXPanel {

    private Timeline animation;
    private Date startingDate;
    private Date endDate;
    private Date lastLoadingDate = new Date(0);
    private WebView webview;
    private TreeMap<java.sql.Timestamp, List<AccessTimeReport>> accessTimeReports;
    private TreeMap<String, List<VisitSummary>> visitTrendReport;
    private boolean ready;
    private String[] colors = {"#F85D2C", "#23709E", "#1FAE66", "#F89C2C", "#939BA2", "#DFF100", "#99FF66", "#66FF99", "#6666FF", "#9966FF", "#FF9966", "#FFCC66"};
    private final ProgressBar progress = new ProgressBar(); // or you can use ImageView with animated gif instead

    public ArrivalChart() throws Exception {
        startingDate = TokenManagerService.getMinReceptionDate();
        endDate = TokenManagerService.getMaxReceptionDate();
    }

    public void init() {
        StackPane root = new StackPane();

        webview = new WebView();
        webview.setContextMenuEnabled(false);

        webview.getEngine().load(
                ArrivalChart.class.getResource("/org/unhcr/eg/registration/tool/token/printing/chart/ArrivalChart.html").toExternalForm()
        );
        root.getChildren().addAll(webview, progress);
        final WebEngine engine = webview.getEngine();

        //hide progress bar then page is ready
      
        progress.setVisible(false);
        
        Platform.setImplicitExit(false);
        setScene(new Scene(root));
    }

    public void getFreshData() throws SQLException {

        startingDate = TokenManagerService.getMinReceptionDate();
        endDate = TokenManagerService.getMaxReceptionDate();
        getFreshData(startingDate, endDate);
    }

    public void getFreshData(Date startingDate, Date endDate) throws SQLException {
        this.startingDate = startingDate;
        this.endDate = endDate;
        if (startingDate != null && endDate != null) {
            Date visitTrendStartDate;
            Calendar c = Calendar.getInstance();
            c.setTime(endDate);
            c.add(Calendar.DATE, -30);
            visitTrendStartDate = c.getTime();
            accessTimeReports = TokenManagerService.getAccessTimeReport(ClockManager.getSQLDate(visitTrendStartDate), ClockManager.getSQLDate(endDate), ClockManager.getSQLDate(lastLoadingDate));
            visitTrendReport = TokenManagerService.getVisitTrendReport(ClockManager.getSQLDate(startingDate), ClockManager.getSQLDate(endDate));
        }
        getOfflineData(0, 0);
    }

    public void getOfflineData(int i, int j) {
        if (!ready) {
            webview.getEngine().documentProperty().addListener(new ChangeListener<Document>() {
                @Override
                public void changed(ObservableValue<? extends Document> observableValue, Document document, Document newDoc) {
                    if (newDoc != null) {
                        webview.getEngine().documentProperty().removeListener(this);
                        ready = true;
                        pushData(i, j);
                    }
                }
            });
        } else {
            pushData(i, j);
        }
    }

    protected void pushData(int i, int j) {
        Platform.runLater(() -> {
            webview.getEngine().executeScript("removeData()");
            parseData(accessTimeReports, i);
            if (j == 0) {
                webview.getEngine().executeScript("removeData_()");
                parseVisitTrend(visitTrendReport);
            }
        });
    }

    private void parseVisitTrend(TreeMap<String, List<VisitSummary>> visitTrendReport) {
        progress.setVisible(true);
        String cases = "'Cases'";
        String individuals = "'Individuals'";
        List<DrilldownCategoryChartPoint> ddms = new ArrayList<>();
        List<DrilldownCategoryChartPoint> ddms_i = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        Map<String, DrillDownDetails> drillDown = new HashMap<>();

        Gson gson = new Gson();
        int i = 0;
        if (visitTrendReport != null) {
            for (Map.Entry<String, List<VisitSummary>> entrySet : visitTrendReport.entrySet()) {
                String key = entrySet.getKey();
                categories.add(key);
                DrilldownCategoryChartPoint ddm = new DrilldownCategoryChartPoint(key, 0);
                DrillDownDetails downDetails = new DrillDownDetails(key);
                ddm.setDrilldown(true);
                ddm.setColor(colors[0]);
                DrilldownCategoryChartPoint ddm_i = new DrilldownCategoryChartPoint(key, 0);
                DrillDownDetails downDetails_i = new DrillDownDetails(key);
                ddm_i.setDrilldown(true);
                ddm_i.setColor(colors[1]);
                List<VisitSummary> value = entrySet.getValue();
                for (VisitSummary value1 : value) {
                    //                   CategoryChartPoint ccp = new CategoryChartPoint(value1.getReason(), value1.getCount(), colors[0]);
                    ArrayList ccp = new ArrayList();
                    ccp.add(value1.getReason());
                    ccp.add(value1.getCount());
                    ddm.setY(value1.getCount() + ddm.getY());
                    downDetails.getData().add(ccp);
                    ArrayList ccp_i = new ArrayList();
                    ccp_i.add(value1.getReason());
                    ccp_i.add(value1.getIndividuals());
                    downDetails_i.getData().add(ccp_i);
                    ddm_i.setY(value1.getIndividuals() + ddm_i.getY());
                }
                ddms.add(ddm);
                ddms_i.add(ddm_i);
                drillDown.put(downDetails.getName(), downDetails);
                drillDown.put(downDetails_i.getName(), downDetails_i);
            }
        }

        webview.getEngine().executeScript("setChartIntenral(" + cases + "," + gson.toJson(ddms) + ",'" + colors[0] + "',0)");
        webview.getEngine().executeScript("setChartIntenral(" + individuals + "," + gson.toJson(ddms_i) + ",'" + colors[1] + "',1)");
        webview.getEngine().executeScript("setDrilldown(" + gson.toJson(drillDown) + ")");

    }

    protected void parseData(TreeMap<java.sql.Timestamp, List<AccessTimeReport>> accessTimeReport, int i) {
        progress.setVisible(true);
        List<ChartPoint> individuals = new ArrayList<>();
        List<ChartPoint> cases = new ArrayList<>();
        List<ChartPoint> cumulateIndividuals = new ArrayList<>();
        List<ChartPoint> cumulateCases = new ArrayList<>();

        if (accessTimeReport != null) {
            accessTimeReport.entrySet().stream().forEach((entrySet) -> {
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
        }
        Gson gson = new Gson();
        if (i == 0) {
            webview.getEngine().executeScript("addData('Attendance of Persons of concern'," + gson.toJson(individuals) + ",0" + ",'" + colors[0] + "','" + new SimpleDateFormat("dd/MMMM/yyyy").format(endDate) + "')");
            webview.getEngine().executeScript("addData('Attendance of Persons of concern'," + gson.toJson(cases) + ",1" + ",'" + colors[1] + "','" + new SimpleDateFormat("dd/MMMM/yyyy").format(endDate) + "')");
        } else if (i == 1) {
            webview.getEngine().executeScript("addData('Cumulative Attendance of Persons of concern'," + gson.toJson(cumulateIndividuals) + ",2" + ",'" + colors[2] + "','" + new SimpleDateFormat("dd/MMMM/yyyy").format(endDate) + "')");
            webview.getEngine().executeScript("addData('Cumulative Attendance of Persons of concern'," + gson.toJson(cumulateCases) + ",3" + ",'" + colors[3] + "','" + new SimpleDateFormat("dd/MMMM/yyyy").format(endDate) + "')");
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
