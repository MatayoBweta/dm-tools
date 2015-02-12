/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.token.printing;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.unhcr.eg.registration.tool.token.printing.models.Gate;
import org.unhcr.eg.registration.tool.token.printing.models.VisitCategory;
import org.unhcr.eg.registration.tool.token.printing.models.VisitReason;
import org.unhcr.eg.registration.tool.token.printing.service.VisitCategoryController;

/**
 *
 * @author UNHCRuser
 */
public class TokenUpdatePanel extends JFXPanel {

    public TokenUpdatePanel() {

    }

    private TextField phoneNumber;

    private TextField caseNumberText;

    private ComboBox<VisitCategory> groupeOfServiceCombobox;
    private ObservableList<VisitCategory> groupeOfServiceData = FXCollections.observableArrayList();

    private ComboBox<VisitReason> serviceRequestedCombobox;
    private ObservableList<VisitReason> serviceRequestedData = FXCollections.observableArrayList();

    private ComboBox<Gate> gateCombobox;
    private ObservableList<Gate> gateData = FXCollections.observableArrayList();

    public void init() {
        Platform.setImplicitExit(false);
        groupeOfServiceData.addAll(VisitCategoryController.getSectionList());

        // The root part
        VBox root = new VBox();
        // Panel containing fields
        GridPane panel = new GridPane();
        root.getChildren().add(panel);
        panel.setAlignment(Pos.CENTER);
        panel.setHgap(10);
        panel.setVgap(10);
        panel.setPadding(new Insets(25, 25, 25, 25));

        int row = 0;

        Label caseNumberLabel = new Label("Case Number");
        panel.add(caseNumberLabel, 0, row, 2, 1);
        caseNumberText = new TextField();
        panel.add(caseNumberText, 0, row++, 2, 1);
        row++;

        Label gateLabel = new Label("Gate");
        panel.add(gateLabel, 0, row, 2, 1);
        gateCombobox = new ComboBox(gateData);
        panel.add(gateCombobox, 0, row++, 2, 1);
        row++;

        Label categoryOfServiceRequestedLabel = new Label("Category Of Service Requested");
        panel.add(categoryOfServiceRequestedLabel, 0, row, 2, 1);
        groupeOfServiceCombobox = new ComboBox(groupeOfServiceData);

        // Handle ComboBox event.
        groupeOfServiceCombobox.setOnAction((event) -> {
            VisitCategory selectedCategory = groupeOfServiceCombobox.getSelectionModel().getSelectedItem();
            serviceRequestedData.clear();
            serviceRequestedData.addAll(VisitCategoryController.getReasonList(selectedCategory.getSectionCode()));
        });

        // Define rendering of the list of values in ComboBox drop down. 
        groupeOfServiceCombobox.setCellFactory((comboBox) -> {
            return new ListCell<VisitCategory>() {
                @Override
                protected void updateItem(VisitCategory item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getSectionCode() + " " + item.getSectionText());
                    }
                }
            };
        });

        // Define rendering of selected value shown in ComboBox.
        groupeOfServiceCombobox.setConverter(new StringConverter<VisitCategory>() {
            @Override
            public String toString(VisitCategory item) {
                if (item == null) {
                    return null;
                } else {
                    return item.getSectionCode() + " " + item.getSectionText();
                }
            }

            @Override
            public VisitCategory fromString(String personString) {
                return null; // No conversion fromString needed.
            }
        });

        panel.add(groupeOfServiceCombobox, 0, row++, 2, 1);
        row++;

        Label kindOfServiceRequestedLabel = new Label("Service Requested");
        panel.add(kindOfServiceRequestedLabel, 0, row, 2, 1);
        serviceRequestedCombobox = new ComboBox(serviceRequestedData);

        // Define rendering of the list of values in ComboBox drop down. 
        serviceRequestedCombobox.setCellFactory((comboBox) -> {
            return new ListCell<VisitReason>() {
                @Override
                protected void updateItem(VisitReason item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getReasonCode() + " " + item.getReasonText());
                    }
                }
            };
        });

        // Define rendering of selected value shown in ComboBox.
        serviceRequestedCombobox.setConverter(new StringConverter<VisitReason>() {
            @Override
            public String toString(VisitReason item) {
                if (item == null) {
                    return null;
                } else {
                    return item.getReasonCode() + " " + item.getReasonText();
                }
            }

            @Override
            public VisitReason fromString(String personString) {
                return null; // No conversion fromString needed.
            }
        });

        panel.add(serviceRequestedCombobox, 0, row++, 2, 1);
        row++;

        Label phoneNumberLabel = new Label("Phone Number");
        panel.add(phoneNumberLabel, 0, row, 2, 1);
        phoneNumber = new TextField();
        panel.add(phoneNumber, 0, row++, 2, 1);
        row++;

        Region emptySpace = new Region();
        VBox.setVgrow(emptySpace, Priority.ALWAYS);
        root.getChildren().add(emptySpace);

        VBox bottom = new VBox();
        root.getChildren().add(bottom);
        Button registerQuery = new Button("Register Service Request");
        registerQuery.setMaxWidth(Double.MAX_VALUE);
        registerQuery.setOnAction(this::registerQuery);
        bottom.getChildren().add(registerQuery);

        Button printNewRegistrationToken = new Button("New Registration");
        printNewRegistrationToken.setMaxWidth(Double.MAX_VALUE);
        printNewRegistrationToken.setOnAction(this::printNewRegistrationToken);
        bottom.getChildren().add(printNewRegistrationToken);

        Button printOtherToken = new Button("Print Token for Entrance");
        printOtherToken.setMaxWidth(Double.MAX_VALUE);
        printOtherToken.setOnAction(this::printOtherToken);
        bottom.getChildren().add(printOtherToken);
        setScene(new Scene(root, 300, 300));
    }

    private void registerQuery(ActionEvent event) {

    }

    private void printNewRegistrationToken(ActionEvent event) {

    }

    private void printOtherToken(ActionEvent event) {

    }

}
