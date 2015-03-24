/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.token.printing;

import org.unhcr.eg.registration.tool.token.printing.panel.TokenDateFilter;
import org.unhcr.eg.registration.tool.token.printing.panel.TokenUpdatePanel;
import com.ezware.oxbow.swingbits.table.filter.TableRowFilterSupport;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javafx.application.Platform;
import javax.swing.Timer;
import javax.swing.table.TableColumnModel;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.painter.GlossPainter;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.*;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Exceptions;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.unhcr.eg.registration.security.action.LongTaskBackgroundAction;
import org.unhcr.eg.registration.security.em.EntityManagerSingleton;
import org.unhcr.eg.registration.security.exchange.ExcelSaver;
import org.unhcr.eg.registration.tool.token.printing.chart.ArrivalChart;
import org.unhcr.eg.registration.tool.token.printing.service.TokenManagerService;
import org.unhcr.eg.registration.tool.token.printing.service.TokenSummaryTableModel;
import org.unhcr.eg.registration.tool.token.printing.service.TokenTableModel;
import org.unhcr.eg.registration.tool.token.printing.service.VisitCategoryController;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//org.unhcr.eg.registration.tool.token.printing//TokenPrinting//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "TokenPrintingTopComponent",
        iconBase = "org/unhcr/eg/registration/tool/token/printing/printer-16.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "org.unhcr.eg.registration.tool.token.printing.TokenPrintingTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_TokenPrintingAction",
        preferredID = "TokenPrintingTopComponent"
)
@Messages({
    "CTL_TokenPrintingAction=Token Printing",
    "CTL_TokenPrintingTopComponent=TokenPrinting Window",
    "HINT_TokenPrintingTopComponent=This is a Token Printing Window"
})
public final class TokenPrintingTopComponent extends TopComponent {

    protected final String reportLocation = "org/unhcr/eg/registration/tool/token/printing/reporttemplate/Token_Reception.jasper";
    protected final String reportViewLocation = "org/unhcr/eg/registration/tool/token/printing/reporttemplate/Token_Reception_view.jasper";
    private final TokenTableModel tokenTableModel;
    private final TokenSummaryTableModel tokenSummaryTableModel;
    private ArrivalChart arrivalChart;
    private TokenUpdatePanel tokenUpdatePanel;
    private final SimpleDateFormat sdf = new SimpleDateFormat(
            "EEE, d MMM yyyy HH:mm:ss");

    public TokenPrintingTopComponent() {
        initComponents();
        try {
            arrivalChart = new ArrivalChart();
            tokenUpdatePanel = new TokenUpdatePanel(reportLocation);
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        reportPanel.add(arrivalChart, BorderLayout.CENTER);
        dataCollectionPanel.add(tokenUpdatePanel, BorderLayout.CENTER);
        Platform.runLater(() -> {
            try {
                arrivalChart.init();
                tokenUpdatePanel.init();
                arrivalChart.getFreshData();
                reportPanel.repaint();
                dataCollectionPanel.repaint();
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }
        });
        ActionListener taskPerformer = (ActionEvent evt) -> {
            timeLabel.setText("<html>" + sdf.format(new Date()));
        };
        Timer timer = new Timer(1000, taskPerformer);
        timer.setRepeats(true);
        timer.start();
        setName(Bundle.CTL_TokenPrintingTopComponent());
        setToolTipText(Bundle.HINT_TokenPrintingTopComponent());
        tokenTableModel = new TokenTableModel();
        tokenSummaryTableModel = new TokenSummaryTableModel();
        tokenTable.addHighlighter(HighlighterFactory.createAlternateStriping(new Color(234, 234, 234), Color.WHITE));
        tokenTable.setModel(tokenTableModel);

        summaryXTable.addHighlighter(HighlighterFactory.createAlternateStriping(new Color(234, 234, 234), Color.WHITE));
        summaryXTable.setModel(tokenSummaryTableModel);
        mainHeader.setBackground(new Color(128, 128, 128, 100));
        childHeader.setBackground(new Color(192, 192, 192, 100));
        child2Header.setBackground(new Color(192, 192, 192, 100));

        mainHeader.setBackgroundPainter(new GlossPainter(GlossPainter.GlossPosition.TOP));
        childHeader.setBackgroundPainter(new GlossPainter(GlossPainter.GlossPosition.TOP));
        child2Header.setBackgroundPainter(new GlossPainter(GlossPainter.GlossPosition.TOP));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        visitCategories = ObservableCollections.observableList(VisitCategoryController.getSectionList())
        ;
        visitReasons = ObservableCollections.observableList(new ArrayList());
        gates = ObservableCollections.observableList(VisitCategoryController.getGateList())
        ;
        categoryButtonGroup = new javax.swing.ButtonGroup();
        mainHeader = new org.jdesktop.swingx.JXHeader();
        jPanel9 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        reportPanel = new javax.swing.JPanel();
        jToolBar3 = new javax.swing.JToolBar();
        reloadChartButton = new javax.swing.JButton();
        dailyToggleButton = new javax.swing.JToggleButton();
        cumulativeToggleButton = new javax.swing.JToggleButton();
        dataCollectionPanel = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        photoLabel = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        childHeader = new org.jdesktop.swingx.JXHeader();
        jToolBar1 = new javax.swing.JToolBar();
        loadButton = new javax.swing.JButton();
        todayButton = new javax.swing.JButton();
        exportToExcelButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        cancelButton = new javax.swing.JButton();
        printAgainButton = new javax.swing.JButton();
        previewButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tokenTable = new org.jdesktop.swingx.JXTable();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        child2Header = new org.jdesktop.swingx.JXHeader();
        jToolBar2 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        summaryXTable = new org.jdesktop.swingx.JXTable();

        setLayout(new java.awt.BorderLayout());

        mainHeader.setDescriptionFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        mainHeader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/unhcr/eg/registration/tool/token/printing/printer-64.png"))); // NOI18N
        mainHeader.setIconPosition(org.jdesktop.swingx.JXHeader.IconPosition.LEFT);
        mainHeader.setTitle(org.openide.util.NbBundle.getMessage(TokenPrintingTopComponent.class, "TokenPrintingTopComponent.mainHeader.title")); // NOI18N
        mainHeader.setTitleFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        add(mainHeader, java.awt.BorderLayout.PAGE_START);

        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel11.setLayout(new java.awt.BorderLayout());
        jPanel9.add(jPanel11, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.BorderLayout());

        reportPanel.setLayout(new java.awt.BorderLayout());

        jToolBar3.setRollover(true);

        reloadChartButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/unhcr/eg/registration/tool/token/printing/1421940027_reload_all_tabs.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(reloadChartButton, org.openide.util.NbBundle.getMessage(TokenPrintingTopComponent.class, "TokenPrintingTopComponent.reloadChartButton.text")); // NOI18N
        reloadChartButton.setFocusable(false);
        reloadChartButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        reloadChartButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        reloadChartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reloadChartButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(reloadChartButton);

        categoryButtonGroup.add(dailyToggleButton);
        dailyToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/unhcr/eg/registration/tool/token/printing/1421940493_Calendar.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(dailyToggleButton, org.openide.util.NbBundle.getMessage(TokenPrintingTopComponent.class, "TokenPrintingTopComponent.dailyToggleButton.text")); // NOI18N
        dailyToggleButton.setFocusable(false);
        dailyToggleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        dailyToggleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        dailyToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dailyToggleButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(dailyToggleButton);

        categoryButtonGroup.add(cumulativeToggleButton);
        cumulativeToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/unhcr/eg/registration/tool/token/printing/1421940545_piechart.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(cumulativeToggleButton, org.openide.util.NbBundle.getMessage(TokenPrintingTopComponent.class, "TokenPrintingTopComponent.cumulativeToggleButton.text")); // NOI18N
        cumulativeToggleButton.setFocusable(false);
        cumulativeToggleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cumulativeToggleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cumulativeToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cumulativeToggleButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(cumulativeToggleButton);

        reportPanel.add(jToolBar3, java.awt.BorderLayout.NORTH);

        jPanel3.add(reportPanel, java.awt.BorderLayout.CENTER);

        dataCollectionPanel.setLayout(new java.awt.BorderLayout());
        jPanel3.add(dataCollectionPanel, java.awt.BorderLayout.WEST);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel9.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel10.setBackground(new java.awt.Color(204, 204, 204));
        jPanel10.setLayout(new java.awt.BorderLayout());

        photoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/unhcr/eg/registration/tool/token/printing/Token_System.jpg"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(photoLabel, org.openide.util.NbBundle.getMessage(TokenPrintingTopComponent.class, "TokenPrintingTopComponent.photoLabel.text")); // NOI18N
        jPanel10.add(photoLabel, java.awt.BorderLayout.CENTER);

        timeLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        timeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/unhcr/eg/registration/tool/token/printing/1423434044_clock_48.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(timeLabel, org.openide.util.NbBundle.getMessage(TokenPrintingTopComponent.class, "TokenPrintingTopComponent.timeLabel.text")); // NOI18N
        jPanel10.add(timeLabel, java.awt.BorderLayout.NORTH);

        jPanel9.add(jPanel10, java.awt.BorderLayout.WEST);

        add(jPanel9, java.awt.BorderLayout.CENTER);

        jPanel6.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setLastDividerLocation(700);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel7.setLayout(new java.awt.BorderLayout());

        childHeader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/unhcr/eg/registration/tool/token/printing/1417627941_699325-icon-53-notebook-list-32.png"))); // NOI18N
        childHeader.setTitle(org.openide.util.NbBundle.getMessage(TokenPrintingTopComponent.class, "TokenPrintingTopComponent.childHeader.title")); // NOI18N
        childHeader.setTitleFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jPanel7.add(childHeader, java.awt.BorderLayout.NORTH);

        jToolBar1.setRollover(true);

        loadButton.setFont(new java.awt.Font("Calibri", 0, 13)); // NOI18N
        loadButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/unhcr/eg/registration/tool/token/printing/1415040031_698860-icon-129-cloud-download-16.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(loadButton, org.openide.util.NbBundle.getMessage(TokenPrintingTopComponent.class, "TokenPrintingTopComponent.loadButton.text")); // NOI18N
        loadButton.setFocusable(false);
        loadButton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        loadButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        loadButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        loadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(loadButton);

        todayButton.setFont(new java.awt.Font("Calibri", 0, 13)); // NOI18N
        todayButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/unhcr/eg/registration/tool/token/printing/1415039476_today.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(todayButton, org.openide.util.NbBundle.getMessage(TokenPrintingTopComponent.class, "TokenPrintingTopComponent.todayButton.text")); // NOI18N
        todayButton.setFocusable(false);
        todayButton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        todayButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        todayButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        todayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                todayButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(todayButton);

        exportToExcelButton.setFont(new java.awt.Font("Calibri", 0, 13)); // NOI18N
        exportToExcelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/unhcr/eg/registration/tool/token/printing/1415295482_receipt-excel-text.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(exportToExcelButton, org.openide.util.NbBundle.getMessage(TokenPrintingTopComponent.class, "TokenPrintingTopComponent.exportToExcelButton.text")); // NOI18N
        exportToExcelButton.setFocusable(false);
        exportToExcelButton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        exportToExcelButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        exportToExcelButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        exportToExcelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportToExcelButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(exportToExcelButton);
        jToolBar1.add(jSeparator1);

        cancelButton.setFont(new java.awt.Font("Calibri", 0, 13)); // NOI18N
        cancelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/unhcr/eg/registration/tool/token/printing/1415037030_698591-icon-79-document-cancel-16.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(cancelButton, org.openide.util.NbBundle.getMessage(TokenPrintingTopComponent.class, "TokenPrintingTopComponent.cancelButton.text")); // NOI18N
        cancelButton.setFocusable(false);
        cancelButton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        cancelButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        cancelButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tokenTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), cancelButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(cancelButton);

        printAgainButton.setFont(new java.awt.Font("Calibri", 0, 13)); // NOI18N
        printAgainButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/unhcr/eg/registration/tool/token/printing/printer-16.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(printAgainButton, org.openide.util.NbBundle.getMessage(TokenPrintingTopComponent.class, "TokenPrintingTopComponent.printAgainButton.text")); // NOI18N
        printAgainButton.setFocusable(false);
        printAgainButton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        printAgainButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        printAgainButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tokenTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), printAgainButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        printAgainButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printAgainButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(printAgainButton);

        previewButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/unhcr/eg/registration/tool/token/printing/1421961775_Preview.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(previewButton, org.openide.util.NbBundle.getMessage(TokenPrintingTopComponent.class, "TokenPrintingTopComponent.previewButton.text")); // NOI18N
        previewButton.setFocusable(false);
        previewButton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        previewButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        previewButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tokenTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), previewButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        previewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previewButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(previewButton);

        jPanel7.add(jToolBar1, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel7, java.awt.BorderLayout.NORTH);

        tokenTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tokenTable.setColumnControlVisible(true);
        tokenTable.setHorizontalScrollEnabled(true);
        tokenTable.setShowVerticalLines(false);
        tokenTable.setVisibleRowCount(15);
        jScrollPane1.setViewportView(tokenTable);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setLeftComponent(jPanel4);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel8.setLayout(new java.awt.BorderLayout());

        child2Header.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/unhcr/eg/registration/tool/token/printing/1417628058_table_select_all.png"))); // NOI18N
        child2Header.setTitle(org.openide.util.NbBundle.getMessage(TokenPrintingTopComponent.class, "TokenPrintingTopComponent.child2Header.title")); // NOI18N
        child2Header.setTitleFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jPanel8.add(child2Header, java.awt.BorderLayout.NORTH);

        jToolBar2.setRollover(true);

        jButton1.setFont(new java.awt.Font("Calibri", 0, 13)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/unhcr/eg/registration/tool/token/printing/1415040031_698860-icon-129-cloud-download-16.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(TokenPrintingTopComponent.class, "TokenPrintingTopComponent.jButton1.text")); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton1);

        jButton2.setFont(new java.awt.Font("Calibri", 0, 13)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/unhcr/eg/registration/tool/token/printing/1415039476_today.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(TokenPrintingTopComponent.class, "TokenPrintingTopComponent.jButton2.text")); // NOI18N
        jButton2.setFocusable(false);
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton2);

        jButton3.setFont(new java.awt.Font("Calibri", 0, 13)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/unhcr/eg/registration/tool/token/printing/1415295482_receipt-excel-text.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButton3, org.openide.util.NbBundle.getMessage(TokenPrintingTopComponent.class, "TokenPrintingTopComponent.jButton3.text")); // NOI18N
        jButton3.setFocusable(false);
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton3);

        jPanel8.add(jToolBar2, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel8, java.awt.BorderLayout.NORTH);

        summaryXTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        summaryXTable.setColumnControlVisible(true);
        summaryXTable.setHorizontalScrollEnabled(true);
        summaryXTable.setVisibleRowCount(5);
        jScrollPane2.setViewportView(summaryXTable);

        jPanel5.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(jPanel5);

        jPanel6.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        add(jPanel6, java.awt.BorderLayout.SOUTH);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents


    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadButtonActionPerformed
        try {
            tokenTableModel.getAllToken(EntityManagerSingleton.getDefault().getConnection());
            adaptTable();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

    }//GEN-LAST:event_loadButtonActionPerformed

    protected void adaptTable() {
        TableColumnModel columnModel = tokenTable.getColumnModel();
        if (columnModel.getColumnCount() > 0) {
            tokenTable.getColumn("TokenDistributedGUID").setResizable(false);
            tokenTable.getColumn("TokenDistributedGUID").setMaxWidth(0);
            tokenTable.getColumn("TokenDistributedGUID").setPreferredWidth(0);
            TableRowFilterSupport.forTable(tokenTable).searchable(true).useTableRenderers(true).apply();
        }
        columnModel = summaryXTable.getColumnModel();
        if (columnModel.getColumnCount() > 0) {
            TableRowFilterSupport.forTable(summaryXTable).searchable(true).useTableRenderers(true).apply();
        }
    }

    private void todayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_todayButtonActionPerformed
        try {
            tokenTableModel.getTodayToken(EntityManagerSingleton.getDefault().getConnection());
            adaptTable();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_todayButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        NotifyDescriptor nd = new NotifyDescriptor.Confirmation("Do you want to inactivate this token?", "Inactivation confirmation", NotifyDescriptor.YES_NO_OPTION);
        Object result = DialogDisplayer.getDefault().notify(nd);
        if (result != NotifyDescriptor.OK_OPTION) {
            return;
        }
        try {
            Connection con = EntityManagerSingleton.getDefault().getConnection();
            int selectedRow = tokenTable.getSelectedRow();
            String updateSQL = "UPDATE tmp_TokenReport\n"
                    + "SET      TokenStatus = N'X'\n"
                    + "WHERE     (TokenDistributedGUID = ?)";

            HashMap<Integer, Object> map = new HashMap<>();
            String tokenGUID = (String) tokenTableModel.getValueAt(tokenTable.convertRowIndexToModel(selectedRow), 0);
            map.put(1, tokenGUID);
            tokenTableModel.executePreparedQuery(con, updateSQL, map);
            tokenTableModel.getAllToken(EntityManagerSingleton.getDefault().getConnectionForproGres());
            adaptTable();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

    }//GEN-LAST:event_cancelButtonActionPerformed

    private void printAgainButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printAgainButtonActionPerformed

        int selectedRow = tokenTable.getSelectedRow();
        final String caseNumber = (String) tokenTableModel.getValueAt(tokenTable.convertRowIndexToModel(selectedRow), 5);
        final String reason = (String) tokenTableModel.getValueAt(tokenTable.convertRowIndexToModel(selectedRow), 11);
        final String gate = (String) tokenTableModel.getValueAt(tokenTable.convertRowIndexToModel(selectedRow), 13);
        LongTaskBackgroundAction action = new LongTaskBackgroundAction("Print Token for " + caseNumber) {

            @Override
            protected void mainAction() {

                TokenManagerService.printTokenAction(reason, caseNumber, gate, reportLocation);
            }
        };
        action.actionPerformed(evt);


    }//GEN-LAST:event_printAgainButtonActionPerformed

    private void exportToExcelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportToExcelButtonActionPerformed

        ExcelSaver excelSaver = new ExcelSaver(tokenTable, "Token_document.xls", "Tokens");
        excelSaver.saveTable();

    }//GEN-LAST:event_exportToExcelButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            tokenSummaryTableModel.getAllToken(EntityManagerSingleton.getDefault().getConnection());
            adaptTable();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            tokenSummaryTableModel.getTodayToken(EntityManagerSingleton.getDefault().getConnection());
            adaptTable();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        ExcelSaver excelSaver = new ExcelSaver(summaryXTable, "Token_Summary_document.xls", "Tokens Summary");
        excelSaver.saveTable();

    }//GEN-LAST:event_jButton3ActionPerformed

    private void previewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previewButtonActionPerformed
        int selectedRow = tokenTable.getSelectedRow();
        final String caseNumber = (String) tokenTableModel.getValueAt(tokenTable.convertRowIndexToModel(selectedRow), 5);
        final String tokenGUID = (String) tokenTableModel.getValueAt(tokenTable.convertRowIndexToModel(selectedRow), 0);
        LongTaskBackgroundAction action = new LongTaskBackgroundAction("Preview Token for " + caseNumber) {

            @Override
            protected void mainAction() {

                TokenManagerService.effectivePreviewToken(tokenGUID, caseNumber, reportLocation);
            }
        };
        action.actionPerformed(evt);
    }//GEN-LAST:event_previewButtonActionPerformed

    private void reloadChartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reloadChartButtonActionPerformed
        try {
            TokenDateFilter form = new TokenDateFilter();
            form.setStartDate(TokenManagerService.getMinReceptionDate());
            form.setEndDate(TokenManagerService.getMaxReceptionDate());
            String msg = "Date Filtration for Arrival Chart";
            DialogDescriptor dd = new DialogDescriptor(form, msg);
            Object result = DialogDisplayer.getDefault().notify(dd);
            if (result == NotifyDescriptor.OK_OPTION) {
                if (form.getStartDate() != null && form.getEndDate() != null) {
                    arrivalChart.getFreshData(form.getStartDate(), form.getEndDate());
                } else {
                    arrivalChart.getFreshData();
                }
            }
            categoryButtonGroup.clearSelection();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_reloadChartButtonActionPerformed

    private void dailyToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dailyToggleButtonActionPerformed
        arrivalChart.getOfflineData(0);

    }//GEN-LAST:event_dailyToggleButtonActionPerformed

    private void cumulativeToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cumulativeToggleButtonActionPerformed
        arrivalChart.getOfflineData(1);

    }//GEN-LAST:event_cumulativeToggleButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.ButtonGroup categoryButtonGroup;
    private org.jdesktop.swingx.JXHeader child2Header;
    private org.jdesktop.swingx.JXHeader childHeader;
    private javax.swing.JToggleButton cumulativeToggleButton;
    private javax.swing.JToggleButton dailyToggleButton;
    private javax.swing.JPanel dataCollectionPanel;
    private javax.swing.JButton exportToExcelButton;
    private java.util.List<org.unhcr.eg.registration.tool.token.printing.models.Gate> gates;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JButton loadButton;
    private org.jdesktop.swingx.JXHeader mainHeader;
    private javax.swing.JLabel photoLabel;
    private javax.swing.JButton previewButton;
    private javax.swing.JButton printAgainButton;
    private javax.swing.JButton reloadChartButton;
    private javax.swing.JPanel reportPanel;
    private org.jdesktop.swingx.JXTable summaryXTable;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JButton todayButton;
    private org.jdesktop.swingx.JXTable tokenTable;
    private java.util.List<org.unhcr.eg.registration.tool.token.printing.models.VisitCategory> visitCategories;
    private java.util.List<org.unhcr.eg.registration.tool.token.printing.models.VisitReason> visitReasons;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
}
