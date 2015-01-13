/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.dataQuality.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.prefs.Preferences;
import org.openide.util.NbPreferences;
import org.unhcr.eg.registration.security.jdbc.JDBCTableModel;

/**
 *
 * @author UNHCRUser
 */
public class DataMistakeController extends JDBCTableModel {

    public static int T_MISTAKE_BY_ID = 1;
    public static int T_MISTAKE_BY_CREATE_DATE = 2;
    public static int T_MISTAKE_BY_CREATE_DATE_AND_BY_ID = 3;
    private String id;
    private Date createDate;
    public static String queryMistakeByCreateDate = "SELECT [ProcessStatusCode]\n"
            + "                 ,[ProcessingGroupNumber]\n"
            + "                 ,[IndividualID]\n"
            + "                 ,[RegistrationDate]\n"
            + "                 ,[NationalityCode]\n"
            + "                 ,[Quality Check]\n"
            + "            	 ,[UserIDCreate]\n"
            + "                 ,[ICreateDate]\n"
            + "            	 ,[ErrorDetails]\n"
            + "            	 ,[ErrorValue]\n"
            + "                FROM  (\n"
            + "SELECT [ProcessStatusCode]\n"
            + "      ,[ProcessingGroupNumber]\n"
            + "      ,[IndividualID]\n"
            + "      ,[RegistrationDate]\n"
            + "      ,[NationalityCode]\n"
            + "      ,[Quality Check]\n"
            + "      ,[Empty Arabic Name]\n"
            + "      ,[Empty Arrival Date]\n"
            + "      ,[Empty Registration Date]\n"
            + "      ,[Empty Occupation]\n"
            + "      ,[Empty Marriage Status Code]\n"
            + "      ,[Empty Education Level]\n"
            + "      ,[Empty Fled Date]\n"
            + "      ,[Empty Phone Number]\n"
            + "      ,[Empty Arabic Address]\n"
            + "      ,[Empty Religion]\n"
            + "      ,[Empty Ethnicity]\n"
            + "      ,[Empty Level 1 COA]\n"
            + "      ,[Empty Level 2 COA]\n"
            + "      ,[Empty Level 3 COA]\n"
            + "      ,[Empty Level 1 COO]\n"
            + "      ,[Empty Level 6 COO]\n"
            + "      ,[Empty Citizenship]\n"
            + "      ,[No Photo]\n"
            + "      ,[Date Of Arrival After Registration Date]\n"
            + "      ,[Date Of Fled After Registration Date]\n"
            + "      ,[Date Of Fled After Arrival Date]\n"
            + "      ,[Young Head Of Family]\n"
            + "      ,[Wrong COA Country Not Equal Egypt]\n"
            + "      ,[Wrong COO Country Equal Egypt]\n"
            + "      ,[Duplicate Passport Number]\n"
            + "      ,[Duplicate MFA Number]\n"
            + "      ,[Date Of Birth After Registration Date]\n"
            + "      ,[Egyptian With Status Different To Not Of Concern]\n"
            + "      ,[Passport Start With P]\n"
            + "      ,[Child Head Of Household Without SN]\n"
            + "      ,[Child Married Without SN]\n"
            + "      ,[Phone Number With Less Than 9 Digit]\n"
            + "      ,[Under Eighteen Not Student]\n"
            + "      ,[Adult With No Occupation]\n"
            + "      ,[Adult With Empty Occupation]\n"
            + "      ,[Above Twelve Without ASC]\n"
            + "      ,[Active Non Egyptian Not Of Concern]\n"
            + "      ,[Active Syrian Not Of Concern]\n"
            + "      ,[Empty Origin Country]\n"
            + "      ,[Above 12 Empty Passport Number]\n"
            + "      ,[Ref Certificate # Different to Gov #]\n"
            + "      ,[Asylum Seeker # Different to Soft Field 1 #]\n"
            + "      ,[UserIDCreate]\n"
            + "      ,DATEADD(Day, DATEDIFF(Day, 0, CreateDate), 0) AS [ICreateDate]\n"
            + "  FROM [vGlobalDataQualityControl_v2]) QualityIndicator\n"
            + "UNPIVOT  ([ErrorValue] FOR [ErrorDetails] \n"
            + "  IN ( [Empty Arabic Name]\n"
            + "      ,[Empty Arrival Date]\n"
            + "      ,[Empty Registration Date]\n"
            + "      ,[Empty Occupation]\n"
            + "      ,[Empty Marriage Status Code]\n"
            + "      ,[Empty Education Level]\n"
            + "      ,[Empty Fled Date]\n"
            + "      ,[Empty Phone Number]\n"
            + "      ,[Empty Arabic Address]\n"
            + "      ,[Empty Religion]\n"
            + "      ,[Empty Ethnicity]\n"
            + "      ,[Empty Level 1 COA]\n"
            + "      ,[Empty Level 2 COA]\n"
            + "      ,[Empty Level 3 COA]\n"
            + "      ,[Empty Level 1 COO]\n"
            + "      ,[Empty Level 6 COO]\n"
            + "      ,[Empty Citizenship]\n"
            + "      ,[No Photo]\n"
            + "      ,[Date Of Arrival After Registration Date]\n"
            + "      ,[Date Of Fled After Registration Date]\n"
            + "      ,[Date Of Fled After Arrival Date]\n"
            + "      ,[Young Head Of Family]\n"
            + "      ,[Wrong COA Country Not Equal Egypt]\n"
            + "      ,[Wrong COO Country Equal Egypt]\n"
            + "      ,[Duplicate Passport Number]\n"
            + "      ,[Duplicate MFA Number]\n"
            + "      ,[Date Of Birth After Registration Date]\n"
            + "      ,[Egyptian With Status Different To Not Of Concern]\n"
            + "      ,[Passport Start With P]\n"
            + "      ,[Child Head Of Household Without SN]\n"
            + "      ,[Child Married Without SN]\n"
            + "      ,[Phone Number With Less Than 9 Digit]\n"
            + "      ,[Under Eighteen Not Student]\n"
            + "      ,[Adult With No Occupation]\n"
            + "      ,[Adult With Empty Occupation]\n"
            + "      ,[Above Twelve Without ASC]\n"
            + "      ,[Active Non Egyptian Not Of Concern]\n"
            + "      ,[Active Syrian Not Of Concern]\n"
            + "      ,[Empty Origin Country]\n"
            + "      ,[Above 12 Empty Passport Number]\n"
            + "      ,[Ref Certificate # Different to Gov #]\n"
            + "      ,[Asylum Seeker # Different to Soft Field 1 #]   \n"
            + ")) AS vDataQualityIndicator WHERE [ErrorValue] = 1 AND [ICreateDate] = ?";
    public static String queryMistake = "SELECT [ProcessStatusCode]\n"
            + "                 ,[ProcessingGroupNumber]\n"
            + "                 ,[IndividualID]\n"
            + "                 ,[RegistrationDate]\n"
            + "                 ,[NationalityCode]\n"
            + "                 ,[Quality Check]\n"
            + "            	  ,[UserIDCreate]\n"
            + "                 , [ICreateDate]\n"
            + "            	  ,[ErrorDetails]\n"
            + "            	  ,[ErrorValue]\n"
            + "                FROM  (\n"
            + "SELECT [ProcessStatusCode]\n"
            + "      ,[ProcessingGroupNumber]\n"
            + "      ,[IndividualID]\n"
            + "      ,[RegistrationDate]\n"
            + "      ,[NationalityCode]\n"
            + "      ,[Quality Check]\n"
            + "      ,[Empty Arabic Name]\n"
            + "      ,[Empty Arrival Date]\n"
            + "      ,[Empty Registration Date]\n"
            + "      ,[Empty Occupation]\n"
            + "      ,[Empty Marriage Status Code]\n"
            + "      ,[Empty Education Level]\n"
            + "      ,[Empty Fled Date]\n"
            + "      ,[Empty Phone Number]\n"
            + "      ,[Empty Arabic Address]\n"
            + "      ,[Empty Religion]\n"
            + "      ,[Empty Ethnicity]\n"
            + "      ,[Empty Level 1 COA]\n"
            + "      ,[Empty Level 2 COA]\n"
            + "      ,[Empty Level 3 COA]\n"
            + "      ,[Empty Level 1 COO]\n"
            + "      ,[Empty Level 6 COO]\n"
            + "      ,[Empty Citizenship]\n"
            + "      ,[No Photo]\n"
            + "      ,[Date Of Arrival After Registration Date]\n"
            + "      ,[Date Of Fled After Registration Date]\n"
            + "      ,[Date Of Fled After Arrival Date]\n"
            + "      ,[Young Head Of Family]\n"
            + "      ,[Wrong COA Country Not Equal Egypt]\n"
            + "      ,[Wrong COO Country Equal Egypt]\n"
            + "      ,[Duplicate Passport Number]\n"
            + "      ,[Duplicate MFA Number]\n"
            + "      ,[Date Of Birth After Registration Date]\n"
            + "      ,[Egyptian With Status Different To Not Of Concern]\n"
            + "      ,[Passport Start With P]\n"
            + "      ,[Child Head Of Household Without SN]\n"
            + "      ,[Child Married Without SN]\n"
            + "      ,[Phone Number With Less Than 9 Digit]\n"
            + "      ,[Under Eighteen Not Student]\n"
            + "      ,[Adult With No Occupation]\n"
            + "      ,[Adult With Empty Occupation]\n"
            + "      ,[Above Twelve Without ASC]\n"
            + "      ,[Active Non Egyptian Not Of Concern]\n"
            + "      ,[Active Syrian Not Of Concern]\n"
            + "      ,[Empty Origin Country]\n"
            + "      ,[Above 12 Empty Passport Number]\n"
            + "      ,[Ref Certificate # Different to Gov #]\n"
            + "      ,[Asylum Seeker # Different to Soft Field 1 #]\n"
            + "      ,[UserIDCreate]\n"
            + "      ,DATEADD(Day, DATEDIFF(Day, 0, CreateDate), 0) AS [ICreateDate]\n"
            + "  FROM [vGlobalDataQualityControl_v2]) QualityIndicator\n"
            + "UNPIVOT  ([ErrorValue] FOR [ErrorDetails] \n"
            + "  IN ( [Empty Arabic Name]\n"
            + "      ,[Empty Arrival Date]\n"
            + "      ,[Empty Registration Date]\n"
            + "      ,[Empty Occupation]\n"
            + "      ,[Empty Marriage Status Code]\n"
            + "      ,[Empty Education Level]\n"
            + "      ,[Empty Fled Date]\n"
            + "      ,[Empty Phone Number]\n"
            + "      ,[Empty Arabic Address]\n"
            + "      ,[Empty Religion]\n"
            + "      ,[Empty Ethnicity]\n"
            + "      ,[Empty Level 1 COA]\n"
            + "      ,[Empty Level 2 COA]\n"
            + "      ,[Empty Level 3 COA]\n"
            + "      ,[Empty Level 1 COO]\n"
            + "      ,[Empty Level 6 COO]\n"
            + "      ,[Empty Citizenship]\n"
            + "      ,[No Photo]\n"
            + "      ,[Date Of Arrival After Registration Date]\n"
            + "      ,[Date Of Fled After Registration Date]\n"
            + "      ,[Date Of Fled After Arrival Date]\n"
            + "      ,[Young Head Of Family]\n"
            + "      ,[Wrong COA Country Not Equal Egypt]\n"
            + "      ,[Wrong COO Country Equal Egypt]\n"
            + "      ,[Duplicate Passport Number]\n"
            + "      ,[Duplicate MFA Number]\n"
            + "      ,[Date Of Birth After Registration Date]\n"
            + "      ,[Egyptian With Status Different To Not Of Concern]\n"
            + "      ,[Passport Start With P]\n"
            + "      ,[Child Head Of Household Without SN]\n"
            + "      ,[Child Married Without SN]\n"
            + "      ,[Phone Number With Less Than 9 Digit]\n"
            + "      ,[Under Eighteen Not Student]\n"
            + "      ,[Adult With No Occupation]\n"
            + "      ,[Adult With Empty Occupation]\n"
            + "      ,[Above Twelve Without ASC]\n"
            + "      ,[Active Non Egyptian Not Of Concern]\n"
            + "      ,[Active Syrian Not Of Concern]\n"
            + "      ,[Empty Origin Country]\n"
            + "      ,[Above 12 Empty Passport Number]\n"
            + "      ,[Ref Certificate # Different to Gov #]\n"
            + "      ,[Asylum Seeker # Different to Soft Field 1 #]   \n"
            + ")) AS vDataQualityIndicator WHERE [ErrorValue] = 1\n";
    public static String queryMistakeByCreateDateAndID = "SELECT [ProcessStatusCode]\n"
            + "                 ,[ProcessingGroupNumber]\n"
            + "                 ,[IndividualID]\n"
            + "                 ,[RegistrationDate]\n"
            + "                 ,[NationalityCode]\n"
            + "                 ,[Quality Check]\n"
            + "            	 ,[UserIDCreate]\n"
            + "                 ,[ICreateDate]\n"
            + "            	 ,[ErrorDetails]\n"
            + "            	 ,[ErrorValue]\n"
            + "                FROM  (\n"
            + "SELECT [ProcessStatusCode]\n"
            + "      ,[ProcessingGroupNumber]\n"
            + "      ,[IndividualID]\n"
            + "      ,[RegistrationDate]\n"
            + "      ,[NationalityCode]\n"
            + "      ,[Quality Check]\n"
            + "      ,[Empty Arabic Name]\n"
            + "      ,[Empty Arrival Date]\n"
            + "      ,[Empty Registration Date]\n"
            + "      ,[Empty Occupation]\n"
            + "      ,[Empty Marriage Status Code]\n"
            + "      ,[Empty Education Level]\n"
            + "      ,[Empty Fled Date]\n"
            + "      ,[Empty Phone Number]\n"
            + "      ,[Empty Arabic Address]\n"
            + "      ,[Empty Religion]\n"
            + "      ,[Empty Ethnicity]\n"
            + "      ,[Empty Level 1 COA]\n"
            + "      ,[Empty Level 2 COA]\n"
            + "      ,[Empty Level 3 COA]\n"
            + "      ,[Empty Level 1 COO]\n"
            + "      ,[Empty Level 6 COO]\n"
            + "      ,[Empty Citizenship]\n"
            + "      ,[No Photo]\n"
            + "      ,[Date Of Arrival After Registration Date]\n"
            + "      ,[Date Of Fled After Registration Date]\n"
            + "      ,[Date Of Fled After Arrival Date]\n"
            + "      ,[Young Head Of Family]\n"
            + "      ,[Wrong COA Country Not Equal Egypt]\n"
            + "      ,[Wrong COO Country Equal Egypt]\n"
            + "      ,[Duplicate Passport Number]\n"
            + "      ,[Duplicate MFA Number]\n"
            + "      ,[Date Of Birth After Registration Date]\n"
            + "      ,[Egyptian With Status Different To Not Of Concern]\n"
            + "      ,[Passport Start With P]\n"
            + "      ,[Child Head Of Household Without SN]\n"
            + "      ,[Child Married Without SN]\n"
            + "      ,[Phone Number With Less Than 9 Digit]\n"
            + "      ,[Under Eighteen Not Student]\n"
            + "      ,[Adult With No Occupation]\n"
            + "      ,[Adult With Empty Occupation]\n"
            + "      ,[Above Twelve Without ASC]\n"
            + "      ,[Active Non Egyptian Not Of Concern]\n"
            + "      ,[Active Syrian Not Of Concern]\n"
            + "      ,[Empty Origin Country]\n"
            + "      ,[Above 12 Empty Passport Number]\n"
            + "      ,[Ref Certificate # Different to Gov #]\n"
            + "      ,[Asylum Seeker # Different to Soft Field 1 #]\n"
            + "      ,[UserIDCreate]\n"
            + "      ,DATEADD(Day, DATEDIFF(Day, 0, CreateDate), 0) AS [ICreateDate]\n"
            + "  FROM [vGlobalDataQualityControl_v2]) QualityIndicator\n"
            + "UNPIVOT  ([ErrorValue] FOR [ErrorDetails] \n"
            + "  IN ( [Empty Arabic Name]\n"
            + "      ,[Empty Arrival Date]\n"
            + "      ,[Empty Registration Date]\n"
            + "      ,[Empty Occupation]\n"
            + "      ,[Empty Marriage Status Code]\n"
            + "      ,[Empty Education Level]\n"
            + "      ,[Empty Fled Date]\n"
            + "      ,[Empty Phone Number]\n"
            + "      ,[Empty Arabic Address]\n"
            + "      ,[Empty Religion]\n"
            + "      ,[Empty Ethnicity]\n"
            + "      ,[Empty Level 1 COA]\n"
            + "      ,[Empty Level 2 COA]\n"
            + "      ,[Empty Level 3 COA]\n"
            + "      ,[Empty Level 1 COO]\n"
            + "      ,[Empty Level 6 COO]\n"
            + "      ,[Empty Citizenship]\n"
            + "      ,[No Photo]\n"
            + "      ,[Date Of Arrival After Registration Date]\n"
            + "      ,[Date Of Fled After Registration Date]\n"
            + "      ,[Date Of Fled After Arrival Date]\n"
            + "      ,[Young Head Of Family]\n"
            + "      ,[Wrong COA Country Not Equal Egypt]\n"
            + "      ,[Wrong COO Country Equal Egypt]\n"
            + "      ,[Duplicate Passport Number]\n"
            + "      ,[Duplicate MFA Number]\n"
            + "      ,[Date Of Birth After Registration Date]\n"
            + "      ,[Egyptian With Status Different To Not Of Concern]\n"
            + "      ,[Passport Start With P]\n"
            + "      ,[Child Head Of Household Without SN]\n"
            + "      ,[Child Married Without SN]\n"
            + "      ,[Phone Number With Less Than 9 Digit]\n"
            + "      ,[Under Eighteen Not Student]\n"
            + "      ,[Adult With No Occupation]\n"
            + "      ,[Adult With Empty Occupation]\n"
            + "      ,[Above Twelve Without ASC]\n"
            + "      ,[Active Non Egyptian Not Of Concern]\n"
            + "      ,[Active Syrian Not Of Concern]\n"
            + "      ,[Empty Origin Country]\n"
            + "      ,[Above 12 Empty Passport Number]\n"
            + "      ,[Ref Certificate # Different to Gov #]\n"
            + "      ,[Asylum Seeker # Different to Soft Field 1 #]   \n"
            + ")) AS vDataQualityIndicator WHERE [ErrorValue] = 1 AND ([ProcessingGroupNumber] LIKE ? OR [IndividualID] LIKE ? OR [ICreateDate] = ?)";
    public static String queryMistakeByID = "SELECT [ProcessStatusCode]\n"
            + "                 ,[ProcessingGroupNumber]\n"
            + "                 ,[IndividualID]\n"
            + "                 ,[RegistrationDate]\n"
            + "                 ,[NationalityCode]\n"
            + "                 ,[Quality Check]\n"
            + "            	 ,[UserIDCreate]\n"
            + "                 ,[ICreateDate]\n"
            + "            	 ,[ErrorDetails]\n"
            + "            	 ,[ErrorValue]\n"
            + "                FROM  (\n"
            + "SELECT [ProcessStatusCode]\n"
            + "      ,[ProcessingGroupNumber]\n"
            + "      ,[IndividualID]\n"
            + "      ,[RegistrationDate]\n"
            + "      ,[NationalityCode]\n"
            + "      ,[Quality Check]\n"
            + "      ,[Empty Arabic Name]\n"
            + "      ,[Empty Arrival Date]\n"
            + "      ,[Empty Registration Date]\n"
            + "      ,[Empty Occupation]\n"
            + "      ,[Empty Marriage Status Code]\n"
            + "      ,[Empty Education Level]\n"
            + "      ,[Empty Fled Date]\n"
            + "      ,[Empty Phone Number]\n"
            + "      ,[Empty Arabic Address]\n"
            + "      ,[Empty Religion]\n"
            + "      ,[Empty Ethnicity]\n"
            + "      ,[Empty Level 1 COA]\n"
            + "      ,[Empty Level 2 COA]\n"
            + "      ,[Empty Level 3 COA]\n"
            + "      ,[Empty Level 1 COO]\n"
            + "      ,[Empty Level 6 COO]\n"
            + "      ,[Empty Citizenship]\n"
            + "      ,[No Photo]\n"
            + "      ,[Date Of Arrival After Registration Date]\n"
            + "      ,[Date Of Fled After Registration Date]\n"
            + "      ,[Date Of Fled After Arrival Date]\n"
            + "      ,[Young Head Of Family]\n"
            + "      ,[Wrong COA Country Not Equal Egypt]\n"
            + "      ,[Wrong COO Country Equal Egypt]\n"
            + "      ,[Duplicate Passport Number]\n"
            + "      ,[Duplicate MFA Number]\n"
            + "      ,[Date Of Birth After Registration Date]\n"
            + "      ,[Egyptian With Status Different To Not Of Concern]\n"
            + "      ,[Passport Start With P]\n"
            + "      ,[Child Head Of Household Without SN]\n"
            + "      ,[Child Married Without SN]\n"
            + "      ,[Phone Number With Less Than 9 Digit]\n"
            + "      ,[Under Eighteen Not Student]\n"
            + "      ,[Adult With No Occupation]\n"
            + "      ,[Adult With Empty Occupation]\n"
            + "      ,[Above Twelve Without ASC]\n"
            + "      ,[Active Non Egyptian Not Of Concern]\n"
            + "      ,[Active Syrian Not Of Concern]\n"
            + "      ,[Empty Origin Country]\n"
            + "      ,[Above 12 Empty Passport Number]\n"
            + "      ,[Ref Certificate # Different to Gov #]\n"
            + "      ,[Asylum Seeker # Different to Soft Field 1 #]\n"
            + "      ,[UserIDCreate]\n"
            + "      ,DATEADD(Day, DATEDIFF(Day, 0, CreateDate), 0) AS [ICreateDate]\n"
            + "  FROM [vGlobalDataQualityControl_v2]) QualityIndicator\n"
            + "UNPIVOT  ([ErrorValue] FOR [ErrorDetails] \n"
            + "  IN ( [Empty Arabic Name]\n"
            + "      ,[Empty Arrival Date]\n"
            + "      ,[Empty Registration Date]\n"
            + "      ,[Empty Occupation]\n"
            + "      ,[Empty Marriage Status Code]\n"
            + "      ,[Empty Education Level]\n"
            + "      ,[Empty Fled Date]\n"
            + "      ,[Empty Phone Number]\n"
            + "      ,[Empty Arabic Address]\n"
            + "      ,[Empty Religion]\n"
            + "      ,[Empty Ethnicity]\n"
            + "      ,[Empty Level 1 COA]\n"
            + "      ,[Empty Level 2 COA]\n"
            + "      ,[Empty Level 3 COA]\n"
            + "      ,[Empty Level 1 COO]\n"
            + "      ,[Empty Level 6 COO]\n"
            + "      ,[Empty Citizenship]\n"
            + "      ,[No Photo]\n"
            + "      ,[Date Of Arrival After Registration Date]\n"
            + "      ,[Date Of Fled After Registration Date]\n"
            + "      ,[Date Of Fled After Arrival Date]\n"
            + "      ,[Young Head Of Family]\n"
            + "      ,[Wrong COA Country Not Equal Egypt]\n"
            + "      ,[Wrong COO Country Equal Egypt]\n"
            + "      ,[Duplicate Passport Number]\n"
            + "      ,[Duplicate MFA Number]\n"
            + "      ,[Date Of Birth After Registration Date]\n"
            + "      ,[Egyptian With Status Different To Not Of Concern]\n"
            + "      ,[Passport Start With P]\n"
            + "      ,[Child Head Of Household Without SN]\n"
            + "      ,[Child Married Without SN]\n"
            + "      ,[Phone Number With Less Than 9 Digit]\n"
            + "      ,[Under Eighteen Not Student]\n"
            + "      ,[Adult With No Occupation]\n"
            + "      ,[Adult With Empty Occupation]\n"
            + "      ,[Above Twelve Without ASC]\n"
            + "      ,[Active Non Egyptian Not Of Concern]\n"
            + "      ,[Active Syrian Not Of Concern]\n"
            + "      ,[Empty Origin Country]\n"
            + "      ,[Above 12 Empty Passport Number]\n"
            + "      ,[Ref Certificate # Different to Gov #]\n"
            + "      ,[Asylum Seeker # Different to Soft Field 1 #]   \n"
            + ")) AS vDataQualityIndicator WHERE [ErrorValue] = 1 AND ([ProcessingGroupNumber] LIKE ? OR [IndividualID] LIKE ?)";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setTableStructureContents(Connection conn,
            String id, Date date)
            throws SQLException {
        setId(id);
        setCreateDate(date);
        getContents(conn, mistakeType(id, date));
        fireTableStructureChanged();
        fireTableDataChanged();
    }

    public static int mistakeType(String id, Date createDate) {

        if (id == null || id.isEmpty()) {
            return T_MISTAKE_BY_CREATE_DATE;
        } else if (createDate == null || createDate.toString().isEmpty()) {
            return T_MISTAKE_BY_ID;
        } else if (createDate != null && id != null && !id.isEmpty()) {
            return T_MISTAKE_BY_CREATE_DATE_AND_BY_ID;
        }
        return -1;
    }

    public static String getStoredProcedureToUseForMistakeCheck(int mistakeType) {
        Preferences node = NbPreferences.root();
        if (mistakeType == T_MISTAKE_BY_CREATE_DATE) {
            return node.get("sp.mistake.createDate", queryMistakeByCreateDate);
        } else if (mistakeType == T_MISTAKE_BY_CREATE_DATE_AND_BY_ID) {
            return node.get("sp.mistake.createDateAndID", queryMistakeByCreateDateAndID);
        } else if (mistakeType == T_MISTAKE_BY_ID) {
            return node.get("sp.mistake.id", queryMistakeByID);
        }
        return node.get("sp.mistake", queryMistake);
    }

    @SuppressWarnings("empty-statement")
    protected void getContents(Connection conn, int mistakeType)
            throws SQLException {
        {
        //    DriverManager.setLogWriter(new PrintWriter(System.out));
            String query = getStoredProcedureToUseForMistakeCheck(mistakeType);
            System.out.println("query " + query);
            PreparedStatement statement = conn.prepareStatement(query);
            setParameters(statement, mistakeType);
            System.out.println("statement.toString() " + statement.toString());
            try (ResultSet results = statement.executeQuery()) {
                ResultSetMetaData metaData = results.getMetaData();
                System.out.println("got column results");
                ArrayList colNamesList = new ArrayList();
                ArrayList colClassesList = new ArrayList();
                int nbreOfColumn = metaData.getColumnCount();
                System.out.println("nbreOfColumn " + nbreOfColumn);
                for (int i = 1; i <= nbreOfColumn; i++) {
                    colNamesList.add(metaData.getColumnName(i));
                    System.out.println(i + ". " + metaData.getColumnName(i));
                    int dbType = metaData.getColumnType(i);
                    switch (dbType) {
                        case Types.INTEGER:
                            colClassesList.add(Integer.class);
                            break;
                        case Types.FLOAT:
                            colClassesList.add(Float.class);
                            break;
                        case Types.DOUBLE:
                        case Types.REAL:
                            colClassesList.add(Double.class);
                            break;
                        case Types.DATE:
                        case Types.TIME:
                        case Types.TIMESTAMP:
                            colClassesList.add(java.sql.Date.class);
                            break;
                        default:
                            colClassesList.add(String.class);
                            break;
                    }
                }
                columnNames = new String[colNamesList.size()];
                colNamesList.toArray(columnNames);
                columnClasses = new Class[colClassesList.size()];
                colClassesList.toArray(columnClasses);
                
                ArrayList rowList = new ArrayList();
                while (results.next()) {
                    System.out.println("result");
                    ArrayList cellList = new ArrayList();
                    for (int i = 0; i < columnClasses.length; i++) {
                        Object cellValue = null;
                        if (columnClasses[i] == String.class) {
                            cellValue = results.getString(columnNames[i]);
                        } else if (columnClasses[i] == Integer.class) {
                            cellValue = results.getInt(columnNames[i]);
                        } else if (columnClasses[i] == Float.class) {
                            cellValue = (float) results.getInt(columnNames[i]);
                        } else if (columnClasses[i] == Double.class) {
                            cellValue = results.getDouble(columnNames[i]);
                        } else if (columnClasses[i] == java.sql.Date.class) {
                            cellValue = results.getDate(columnNames[i]);
                        } else {
                            System.out.println("Can't assign "
                                    + columnNames[i]);
                        }
                        cellList.add(cellValue);
                    }// for
                    Object[] cells = cellList.toArray();
                    rowList.add(cells);
                    
                } // while
                // finally create contents two-dim array
                contents = new Object[rowList.size()][];
                for (int i = 0; i < contents.length; i++) {
                    contents[i] = (Object[]) rowList.get(i);
                }
                System.out.println("Created model with "
                        + contents.length + " rows");
            }
        }
    }

    protected Statement setParameters(PreparedStatement statement, int mistakeType) throws SQLException {
        if (mistakeType == T_MISTAKE_BY_CREATE_DATE) {
            System.out.println("1");
            statement.setDate(1, new java.sql.Date(createDate.getTime()));
        } else if (mistakeType == T_MISTAKE_BY_CREATE_DATE_AND_BY_ID) {
            System.out.println("2");
            statement.setString(1, "%" + id + "%");
            statement.setString(2, "%" + id + "%");
            createDate = getODate(createDate);
            statement.setDate(3, new java.sql.Date(createDate.getTime()));
        } else if (mistakeType == T_MISTAKE_BY_ID) {
            System.out.println("3");
            System.out.println("id "+id);
            statement.setString(1, "%" + id + "%");
            statement.setString(2, "%" + id + "%");
        }
        return statement;
    }

    public Date getODate(Date createDate) {
        // Get Calendar object set to the date and time of the given Date object  
        Calendar cal = Calendar.getInstance();
        cal.setTime(createDate);

        // Set time fields to zero  
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        createDate = cal.getTime();
        return createDate;
    }
}
