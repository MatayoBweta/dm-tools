/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.token.printing;

import com.ezware.oxbow.swingbits.table.filter.TableRowFilterSupport;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.application.Platform;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumnModel;
import net.sf.jasperreports.engine.JRException;
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
import org.unhcr.eg.registration.tool.token.printing.models.Gate;
import org.unhcr.eg.registration.tool.token.printing.models.VisitCategory;
import org.unhcr.eg.registration.tool.token.printing.models.VisitReason;
import org.unhcr.eg.registration.tool.token.printing.service.PrinterManager;
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

    protected PrinterManager manager;
    protected final String reportLocation = "org/unhcr/eg/registration/tool/token/printing/reporttemplate/Token_Reception.jasper";
    protected final String reportViewLocation = "org/unhcr/eg/registration/tool/token/printing/reporttemplate/Token_Reception_view.jasper";
    private final TokenTableModel tokenTableModel;
    private final TokenSummaryTableModel tokenSummaryTableModel;
    private ArrivalChart arrivalChart;

    public TokenPrintingTopComponent() {
        initComponents();

        Platform.runLater(() -> {
            try {
                arrivalChart = new ArrivalChart();
                reportPanel.add(arrivalChart, BorderLayout.CENTER);
                arrivalChart.getFreshData();
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }
        });

        setName(Bundle.CTL_TokenPrintingTopComponent());
        setToolTipText(Bundle.HINT_TokenPrintingTopComponent());
        tokenTableModel = new TokenTableModel();
        tokenSummaryTableModel = new TokenSummaryTableModel();
        caseNumberTextField.requestFocusInWindow();
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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        caseNumberTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        categoryComboBox = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        reasonComboBox = new javax.swing.JComboBox();
        printTokenButton = new javax.swing.JButton();
        printTokenButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        gateComboBox = new javax.swing.JComboBox();
        reportPanel = new javax.swing.JPanel();
        jToolBar3 = new javax.swing.JToolBar();
        reloadChartButton = new javax.swing.JButton();
        dailyToggleButton = new javax.swing.JToggleButton();
        cumulativeToggleButton = new javax.swing.JToggleButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
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

        jPanel11.setLayout(new javax.swing.BoxLayout(jPanel11, javax.swing.BoxLayout.LINE_AXIS));
        jPanel9.add(jPanel11, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(TokenPrintingTopComponent.class, "TokenPrintingTopComponent.jLabel1.text")); // NOI18N

        caseNumberTextField.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        caseNumberTextField.setText(org.openide.util.NbBundle.getMessage(TokenPrintingTopComponent.class, "TokenPrintingTopComponent.caseNumberTextField.text")); // NOI18N

        jLabel2.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(TokenPrintingTopComponent.class, "TokenPrintingTopComponent.jLabel2.text")); // NOI18N

        categoryComboBox.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, visitCategories, categoryComboBox);
        bindingGroup.addBinding(jComboBoxBinding);

        categoryComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryComboBoxActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(TokenPrintingTopComponent.class, "TokenPrintingTopComponent.jLabel3.text")); // NOI18N

        reasonComboBox.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, visitReasons, reasonComboBox);
        bindingGroup.addBinding(jComboBoxBinding);

        printTokenButton.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        printTokenButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/unhcr/eg/registration/tool/token/printing/printer-16.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(printTokenButton, org.openide.util.NbBundle.getMessage(TokenPrintingTopComponent.class, "TokenPrintingTopComponent.printTokenButton.text")); // NOI18N
        printTokenButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printTokenButtonActionPerformed(evt);
            }
        });

        printTokenButton1.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        printTokenButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/unhcr/eg/registration/tool/token/printing/printer-16.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(printTokenButton1, org.openide.util.NbBundle.getMessage(TokenPrintingTopComponent.class, "TokenPrintingTopComponent.printTokenButton1.text")); // NOI18N
        printTokenButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printTokenButton1ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(TokenPrintingTopComponent.class, "TokenPrintingTopComponent.jLabel5.text")); // NOI18N

        gateComboBox.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, gates, gateComboBox);
        bindingGroup.addBinding(jComboBoxBinding);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(printTokenButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                    .addComponent(gateComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1)
                            .addComponent(caseNumberTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(categoryComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(reasonComboBox, 0, 246, Short.MAX_VALUE)))
                    .addComponent(printTokenButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(caseNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(4, 4, 4)
                .addComponent(categoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reasonComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(printTokenButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(printTokenButton)
                .addContainerGap())
        );

        jPanel3.add(jPanel1, java.awt.BorderLayout.WEST);

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

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel9.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel10.setBackground(new java.awt.Color(204, 204, 204));
        jPanel10.setLayout(new java.awt.BorderLayout());

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/unhcr/eg/registration/tool/token/printing/Token_System.jpg"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(TokenPrintingTopComponent.class, "TokenPrintingTopComponent.jLabel4.text")); // NOI18N
        jPanel10.add(jLabel4, java.awt.BorderLayout.CENTER);

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

    private void categoryComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryComboBoxActionPerformed
        VisitCategory category = (VisitCategory) categoryComboBox.getSelectedItem();
        if (category != null) {
            visitReasons.clear();
            visitReasons.addAll(VisitCategoryController.getReasonList(category.getSectionCode()));
        }

    }//GEN-LAST:event_categoryComboBoxActionPerformed

    private void printTokenButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printTokenButtonActionPerformed
        LongTaskBackgroundAction action = new LongTaskBackgroundAction("Print Token for " + caseNumberTextField.getText()) {

            @Override
            protected void mainAction() {
                final String caseNumber = caseNumberTextField.getText();
                final VisitReason reason = (VisitReason) reasonComboBox.getSelectedItem();
                final Gate gate = (Gate) gateComboBox.getSelectedItem();
                if (VisitCategoryController.checkDuplicateToken(reason.getReasonCode(), caseNumber, gate.getGateName())) {
                    int option = JOptionPane.showConfirmDialog(printTokenButton, "Token Already Printed\nDo you want to reprint it?", "Duplicate Token", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        effectivePrintToken(caseNumber, reason.getReasonCode(), gate.getGateName());
                    }
                } else {
                    effectivePrintToken(caseNumber, reason.getReasonCode(), gate.getGateName());
                }
            }

        };
        action.actionPerformed(evt);

    }//GEN-LAST:event_printTokenButtonActionPerformed

    protected void effectivePrintToken(final String caseNumber, final String reason, String gate) throws HeadlessException {

        if (VisitCategoryController.checkCaseNumber(caseNumber)) {
            try {
                InputStream is = this.getClass().getClassLoader().getResourceAsStream(reportLocation);
                manager = new PrinterManager(is, new HashMap<>());
                manager.setParameter("Case_Number", caseNumber);
                manager.setParameter("Visit_Reason", reason);
                manager.setParameter("Gate_Name", gate);
                manager.setParameter("New_Individuals", 0);
                manager.print();
            } catch (JRException | FileNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
        } else {
            JOptionPane.showMessageDialog(caseNumberTextField, "Case Number not present in proGres\nPlease check again", "Wrong Case Number", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void effectivePrintToken(final String caseNumber, final String reason, String gate, int numberOfIndividuals) throws HeadlessException {

        if (caseNumber.equals("NR")) {
            try {
                InputStream is = this.getClass().getClassLoader().getResourceAsStream(reportLocation);
                manager = new PrinterManager(is, new HashMap<>());
                manager.setParameter("Case_Number", caseNumber);
                manager.setParameter("Visit_Reason", reason);
                manager.setParameter("Gate_Name", gate);
                manager.setParameter("New_Individuals", numberOfIndividuals);
                manager.print();
            } catch (JRException | FileNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
        } else {
            JOptionPane.showMessageDialog(caseNumberTextField, "Case Number not present in proGres\nPlease check again", "Wrong Case Number", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void effectivePreviewToken(final String tokenGUID, String caseNumber) throws HeadlessException {

        InputStream is = this.getClass().getClassLoader().getResourceAsStream(reportViewLocation);
        manager = new PrinterManager(is, new HashMap<>());
        manager.setParameter("TokenDistributedGUID", tokenGUID);
        manager.previewPrinter("Preview the Token for Case " + caseNumber, "Token " + tokenGUID + " for Case " + caseNumber);
    }

    private void printTokenButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printTokenButton1ActionPerformed
        String txt = "Number Of Individuals: ";
        String title = "New Registration";

        NotifyDescriptor.InputLine input = new NotifyDescriptor.InputLine(txt, title);
        input.setInputText("1"); // specify a default name
        Object result = DialogDisplayer.getDefault().notify(input);
        if (result != NotifyDescriptor.OK_OPTION) {
            return;
        }
        String userInput = input.getInputText();
        if (!userInput.matches("\\d+")) {
            return;
        }
        LongTaskBackgroundAction action;
        caseNumberTextField.setEnabled(false);
        final Gate gate = (Gate) gateComboBox.getSelectedItem();
        action = new LongTaskBackgroundAction("Print New Token") {

            @Override
            protected void mainAction() {
                int option = JOptionPane.showConfirmDialog(printTokenButton, "Do you want to print Token for new family?", "New Registration", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    effectivePrintToken("NR", "VIS0001", gate.getGateName(), Integer.getInteger(userInput));
                }
                caseNumberTextField.setEnabled(true);
            }
        };

        action.actionPerformed(evt);


    }//GEN-LAST:event_printTokenButton1ActionPerformed

    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadButtonActionPerformed
        try {
            tokenTableModel.getAllToken(EntityManagerSingleton.getDefault().getConnectionForproGres());
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
            tokenTableModel.getTodayToken(EntityManagerSingleton.getDefault().getConnectionForproGres());
            adaptTable();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_todayButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        try {
            Connection con = EntityManagerSingleton.getDefault().getConnectionForproGres();
            int selectedRow = tokenTable.getSelectedRow();
            String updateSQL = "UPDATE    tmp_TokenReport\n"
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
        LongTaskBackgroundAction action = new LongTaskBackgroundAction("Print Token for " + caseNumberTextField.getText()) {

            @Override
            protected void mainAction() {
                int selectedRow = tokenTable.getSelectedRow();
                final String caseNumber = (String) tokenTableModel.getValueAt(tokenTable.convertRowIndexToModel(selectedRow), 5);
                final String reason = (String) tokenTableModel.getValueAt(tokenTable.convertRowIndexToModel(selectedRow), 11);
                final String gate = (String) tokenTableModel.getValueAt(tokenTable.convertRowIndexToModel(selectedRow), 13);
                if (VisitCategoryController.checkDuplicateToken(reason, caseNumber, gate, null)) {
                    int option = JOptionPane.showConfirmDialog(printTokenButton, "Token Already Printed\nDo you want to reprint it?", "Duplicate Token", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        effectivePrintToken(caseNumber, reason, gate);
                    }
                } else {
                    effectivePrintToken(caseNumber, reason, gate);
                }
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
            tokenSummaryTableModel.getAllToken(EntityManagerSingleton.getDefault().getConnectionForproGres());
            adaptTable();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            tokenSummaryTableModel.getTodayToken(EntityManagerSingleton.getDefault().getConnectionForproGres());
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
        LongTaskBackgroundAction action = new LongTaskBackgroundAction("Preview Token for " + caseNumberTextField.getText()) {

            @Override
            protected void mainAction() {
                int selectedRow = tokenTable.getSelectedRow();
                System.out.println("tokenTableModel.getValueAt(tokenTable.convertRowIndexToModel(selectedRow), 5) " + tokenTableModel.getValueAt(tokenTable.convertRowIndexToModel(selectedRow), 5));
                System.out.println("tokenTableModel.getValueAt(tokenTable.convertRowIndexToModel(selectedRow), 0) " + tokenTableModel.getValueAt(tokenTable.convertRowIndexToModel(selectedRow), 0));

                final String caseNumber = (String) tokenTableModel.getValueAt(tokenTable.convertRowIndexToModel(selectedRow), 5);
                final String tokenGUID = (String) tokenTableModel.getValueAt(tokenTable.convertRowIndexToModel(selectedRow), 0);
                effectivePreviewToken(tokenGUID, caseNumber);
            }

        };
        action.actionPerformed(evt);
    }//GEN-LAST:event_previewButtonActionPerformed

    private void reloadChartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reloadChartButtonActionPerformed
        try {
            arrivalChart.getFreshData();
            categoryButtonGroup.clearSelection();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_reloadChartButtonActionPerformed

    private void dailyToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dailyToggleButtonActionPerformed
        System.out.println("dailyToggleButtonActionPerformed ");
        arrivalChart.getOfflineData(0);

    }//GEN-LAST:event_dailyToggleButtonActionPerformed

    private void cumulativeToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cumulativeToggleButtonActionPerformed
        System.out.println("cumulativeToggleButtonActionPerformed ");
        arrivalChart.getOfflineData(1);

    }//GEN-LAST:event_cumulativeToggleButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField caseNumberTextField;
    private javax.swing.ButtonGroup categoryButtonGroup;
    private javax.swing.JComboBox categoryComboBox;
    private org.jdesktop.swingx.JXHeader child2Header;
    private org.jdesktop.swingx.JXHeader childHeader;
    private javax.swing.JToggleButton cumulativeToggleButton;
    private javax.swing.JToggleButton dailyToggleButton;
    private javax.swing.JButton exportToExcelButton;
    private javax.swing.JComboBox gateComboBox;
    private java.util.List<org.unhcr.eg.registration.tool.token.printing.models.Gate> gates;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
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
    private javax.swing.JButton previewButton;
    private javax.swing.JButton printAgainButton;
    private javax.swing.JButton printTokenButton;
    private javax.swing.JButton printTokenButton1;
    private javax.swing.JComboBox reasonComboBox;
    private javax.swing.JButton reloadChartButton;
    private javax.swing.JPanel reportPanel;
    private org.jdesktop.swingx.JXTable summaryXTable;
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
