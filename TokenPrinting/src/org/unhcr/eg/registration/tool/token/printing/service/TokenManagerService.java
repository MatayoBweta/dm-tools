/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.token.printing.service;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import javafx.application.Platform;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.sf.jasperreports.engine.JRException;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.Exceptions;
import org.unhcr.eg.registration.security.action.LongTaskBackgroundAction;
import org.unhcr.eg.registration.security.em.EntityManagerSingleton;
import org.unhcr.eg.registration.tool.token.printing.panel.CommentPanelRequestService;
import org.unhcr.eg.registration.tool.token.printing.panel.RequestDetailsPanel;
import org.unhcr.eg.registration.tool.token.printing.TokenPrintingTopComponent;
import org.unhcr.eg.registration.tool.token.printing.models.AccessTimeReport;
import org.unhcr.eg.registration.tool.token.printing.models.CommentInput;
import org.unhcr.eg.registration.tool.token.printing.models.Gate;
import org.unhcr.eg.registration.tool.token.printing.models.TokenDetails;
import org.unhcr.eg.registration.tool.token.printing.models.VisitReason;

/**
 *
 * @author UNHCRuser
 */
public class TokenManagerService {

    public static Date getMaxReceptionDate(Date startingDate, Date endDate) throws SQLException {
        Date date = null;
        String sqlQuery = "SELECT MAX(t1.AccesDateTime) AS MaxTime FROM vAttendanceOffice t1 \n"
                + "	WHERE \n"
                + "	DATEADD(d, DATEDIFF(d, 0, t1.AccesDateTime), 0) BETWEEN DATEADD(d, DATEDIFF(d, 0, ?), 0) AND DATEADD(d, DATEDIFF(d, 0, ?), 0)";
        Connection connection = EntityManagerSingleton.getDefault().getConnection();
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        statement.setDate(1, startingDate);
        statement.setDate(2, endDate);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            date = rs.getDate("MaxTime");
        }
        return date;
    }

    public static Date getMaxReceptionDate(Date currentDate) throws SQLException {
        Date date = null;
        String sqlQuery = "SELECT MAX(t1.AccesDateTime) AS MaxTime FROM vAttendanceOffice t1 \n"
                + "	WHERE \n"
                + "	DATEADD(d, DATEDIFF(d, 0, t1.AccesDateTime), 0) = DATEADD(d, DATEDIFF(d, 0, ?), 0)";
        Connection connection = EntityManagerSingleton.getDefault().getConnection();
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        statement.setDate(1, currentDate);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            date = rs.getDate("MaxTime");
        }
        return date;
    }

    public static Date getMaxReceptionDate() throws SQLException {
        Date date = null;
        String sqlQuery = "SELECT MAX(t1.AccesDateTime) AS MaxTime FROM vAttendanceOffice t1";
        Connection connection = EntityManagerSingleton.getDefault().getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sqlQuery);
        if (rs.next()) {
            date = rs.getDate("MaxTime");
        }
        return date;
    }

    public static Date getMinReceptionDate(Date startingDate, Date endDate) throws SQLException {
        Date date = null;
        String sqlQuery = "SELECT MIN(t1.AccesDateTime) AS MaxTime FROM vAttendanceOffice t1 \n"
                + "	WHERE \n"
                + "	DATEADD(d, DATEDIFF(d, 0, t1.AccesDateTime), 0) BETWEEN DATEADD(d, DATEDIFF(d, 0, ?), 0) AND DATEADD(d, DATEDIFF(d, 0, ?), 0)";
        Connection connection = EntityManagerSingleton.getDefault().getConnection();
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        statement.setDate(1, startingDate);
        statement.setDate(2, endDate);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            date = rs.getDate("MaxTime");
        }
        return date;
    }

    public static Date getMinReceptionDate(Date currentDate) throws SQLException {
        Date date = null;
        String sqlQuery = "SELECT MIN(t1.AccesDateTime) AS MaxTime FROM vAttendanceOffice t1 \n"
                + "	WHERE \n"
                + "	DATEADD(d, DATEDIFF(d, 0, t1.AccesDateTime), 0) = DATEADD(d, DATEDIFF(d, 0, ?), 0)";
        Connection connection = EntityManagerSingleton.getDefault().getConnection();
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        statement.setDate(1, currentDate);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            date = rs.getDate("MaxTime");
        }
        return date;
    }

    public static Date getMinReceptionDate() throws SQLException {
        Date date = null;
        String sqlQuery = "SELECT MIN(t1.AccesDateTime) AS MaxTime FROM vAttendanceOffice t1";
        Connection connection = EntityManagerSingleton.getDefault().getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sqlQuery);
        if (rs.next()) {
            date = rs.getDate("MaxTime");
        }
        return date;
    }

    private static final String RECEPTION_CASE_DATA = "{call GetDailyCumulativeVisit(?,?,?)}";
    private static final String GET_NEXT_TOKEN = "{call Get_Token_Details_New(?,?,?,?,?,?)}";

    public static TreeMap<Timestamp, List<AccessTimeReport>> getAccessTimeReport(Date startingDate, Date endDate, Date lastUploadDate) throws SQLException {
        TreeMap<Timestamp, List<AccessTimeReport>> accessTimeReports = new TreeMap<>();
        Connection connection = EntityManagerSingleton.getDefault().getConnection();
        String getCaseData = RECEPTION_CASE_DATA;
        CallableStatement statement = connection.prepareCall(getCaseData);
        statement.setDate(1, startingDate);
        statement.setDate(2, endDate);
        statement.setTimestamp(3, new java.sql.Timestamp(lastUploadDate.getTime()));
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Timestamp accesDateTime = rs.getTimestamp("AccesDateTime");
            Integer number = rs.getInt("TotalNumber");
            Integer cumulativeNumber = rs.getInt("CumulativeDailyTotalNumber");
            String gate = rs.getString("Gate");
            String typeOfNumber = rs.getString("TypeOfNumber");
            if (accessTimeReports.get(accesDateTime) == null) {
                accessTimeReports.put(accesDateTime, new ArrayList<>());
            }
            accessTimeReports.get(accesDateTime).add(new AccessTimeReport(accesDateTime, gate, typeOfNumber, number, cumulativeNumber));
        }
        return accessTimeReports;
    }

    public static TokenDetails addToken(String caseNumber, String reason, String gate, int numberOfIndividuals, String comments) throws SQLException {
        Connection connection = EntityManagerSingleton.getDefault().getConnection();
        TokenDetails details = new TokenDetails();
        String getNextToken = GET_NEXT_TOKEN;
        CallableStatement statement = connection.prepareCall(getNextToken);
        statement.setString(1, caseNumber);
        statement.setString(2, reason);
        statement.setString(3, gate);
        statement.setInt(4, numberOfIndividuals);
        statement.setString(5, comments);
        statement.setBoolean(6, Boolean.TRUE);

        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            details.setAccesDateTime(new java.util.Date(rs.getTimestamp("AccesDateTime").getTime()));
            details.setCaseNumber(rs.getString("CaseNumber").toUpperCase());
            details.setConditions(rs.getString("Conditions"));
            details.setFamilySize(rs.getInt("FamilySize"));
            details.setGateName(rs.getString("GateName"));
            details.setIndividualGUID(rs.getString("IndividualGUID"));

            details.setIssueToFix(rs.getString("IssueToFix"));
            details.setLocation(rs.getString("Location"));
            details.setTokenDistributedGUID(rs.getString("TokenDistributedGUID"));
            details.setTokenNumber(rs.getInt("TokenNumber"));

            details.setTokenStatus(rs.getString("TokenStatus"));
            details.setVisitNumber(rs.getInt("VisitNumber"));
            details.setVisitReason(rs.getString("VisitReason"));
            details.setReasonForVisit(rs.getString("ReasonForVisit"));

            details.setComments(rs.getString("Comments"));
            details.setRequestOfService(rs.getBoolean("RequestOfService"));
        }
        return details;
    }

    public static void effectivePreviewToken(final String tokenGUID, String caseNumber, String reportLocation) throws HeadlessException {
        InputStream is = TokenManagerService.class.getClassLoader().getResourceAsStream(reportLocation);
        PrinterManager manager = new PrinterManager(is, new HashMap<>());
        manager.setParameter("TokenDistributedGUID", tokenGUID);
        manager.previewPrinter("Preview the Token for Case " + caseNumber, "Token " + tokenGUID + " for Case " + caseNumber);
    }

    public static boolean printTokenAction(String reason, String caseNumber, String gate, String reportLocation) throws HeadlessException {
        if (VisitCategoryController.checkDuplicateToken(reason, caseNumber, gate)) {
            NotifyDescriptor.Confirmation confirmation = new NotifyDescriptor.Confirmation("Token Already Printed\nDo you want to reprint it?", "Duplicate Token", NotifyDescriptor.YES_NO_OPTION);
            Object option = DialogDisplayer.getDefault().notify(confirmation);
            if (option == NotifyDescriptor.OK_OPTION) {
                return TokenManagerService.effectivePrintToken(caseNumber, reason, gate, 0, reportLocation);
            }
        } else {
            return TokenManagerService.effectivePrintToken(caseNumber, reason, gate, 0, reportLocation);
        }
        return false;
    }

    public static boolean effectivePrintToken(final String caseNumber, final String reason, String gate, int numberOfIndividuals, String reportLocation) throws HeadlessException {
        if (caseNumber.equals("NR")) {
            try {
                fillAndPrintToken(reportLocation, caseNumber, reason, gate, numberOfIndividuals);
            } catch (JRException | FileNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
        } else if (VisitCategoryController.checkCaseNumber(caseNumber)) {
            try {
                fillAndPrintToken(reportLocation, caseNumber, reason, gate, 0);
            } catch (JRException | FileNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        return false;
    }

    protected static void fillAndPrintToken(String reportLocation, final String caseNumber, final String reason, String gate, int numberOfIndividuals) throws FileNotFoundException, JRException {
        InputStream is = TokenManagerService.class.getClassLoader().getResourceAsStream(reportLocation);
        PrinterManager manager = new PrinterManager(is, new HashMap<>());
        manager.setParameter("Case_Number", caseNumber);
        manager.setParameter("Visit_Reason", reason);
        manager.setParameter("Gate_Name", gate);
        manager.setParameter("New_Individuals", numberOfIndividuals);
        manager.print();
    }

    public static boolean printNewRegistrationTokenAction(String gate, String reportLocation) {
        String defaultData = "<html><body contentEditable=\"true\">New Registration</body></html>";
        final CommentInput input = getUserInput("Number of New Registration Requester", defaultData);
        LongTaskBackgroundAction action;
        NotifyDescriptor.Confirmation confirmPrinting = new NotifyDescriptor.Confirmation("Do you want to print Token for new family?", "New Registration", NotifyDescriptor.YES_NO_OPTION);
        Object resultConfirmation = DialogDisplayer.getDefault().notify(confirmPrinting);
        if (resultConfirmation == NotifyDescriptor.OK_OPTION) {
            action = new LongTaskBackgroundAction("Print New Token") {
                @Override
                protected void mainAction() {
                    effectivePrintToken("NR", "VIS0001", gate, input.getCount(), reportLocation);
                }
            };
            action.actionPerformed(null);
            return true;
        }
        return false;
    }

    public static CommentInput getUserInput(String inputTitle, String defaultData) {

        CommentPanelRequestService commentPanelRequestService = new CommentPanelRequestService();
        commentPanelRequestService.init();
        if (defaultData != null) {
            commentPanelRequestService.getHTMLEditor().setHtmlText(defaultData);
        }
//        Platform.runLater(() -> {
//            
//        });
//        JPanel customPanel = new JPanel();
//        customPanel.setPreferredSize(new Dimension(400, 300));
//        customPanel.setLayout(new BorderLayout());
//        customPanel.add(commentPanelRequestService, BorderLayout.CENTER);
//        customPanel.repaint();
        DialogDescriptor input = new DialogDescriptor(commentPanelRequestService, inputTitle, true, commentPanelRequestService);
        input.setOptions(new Object[]{commentPanelRequestService.getOk(), commentPanelRequestService.getCancel()});
        input.setClosingOptions(new Object[]{});
        input.addPropertyChangeListener((PropertyChangeEvent evt1) -> {
            if ("value".equalsIgnoreCase(evt1.getPropertyName())) {
                // Escape pressed or dialog closed...
                if (evt1.getNewValue() != null && ((commentPanelRequestService.isValidData() && evt1.getNewValue() == commentPanelRequestService.getOk()) || (evt1.getNewValue() == commentPanelRequestService.getCancel()))) {
                    input.setClosingOptions(null);
                } else {
                    System.out.println("Ok Not valid");
                }
            }
        });

        Object result = DialogDisplayer.getDefault().notify(input);
        if (result != commentPanelRequestService.getOk()) {
            return null;
        }
        String userInput = commentPanelRequestService.getNumberOfIndividuals();
        String comments = commentPanelRequestService.getHTMLComments();

        if (!userInput.matches("\\d+")) {
            return null;
        }
        CommentInput commentInput = new CommentInput(comments, Integer.parseInt(userInput));
        return commentInput;
    }

    public static void registerServiceRequestAction(String caseNumber, String reasonCode, String gateCode) {
        CommentInput input = getUserInput("Service Request Details", null);
        if (input != null) {
            if (VisitCategoryController.checkCaseNumber(caseNumber)) {
                try {
                    TokenDetails details = TokenManagerService.addToken(caseNumber, reasonCode, gateCode, input.getCount(), input.getComments());
                    RequestDetailsPanel form = new RequestDetailsPanel(details);
                    String msg = "Service Request Added Successfully";
                    DialogDescriptor dd = new DialogDescriptor(form, msg);
                    DialogDisplayer.getDefault().notify(dd);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            } else {
                NotifyDescriptor.Message message = new NotifyDescriptor.Message("Case Number not present in proGres\nPlease check again", NotifyDescriptor.ERROR_MESSAGE);
                DialogDisplayer.getDefault().notify(message);
            }
        }
    }

}
