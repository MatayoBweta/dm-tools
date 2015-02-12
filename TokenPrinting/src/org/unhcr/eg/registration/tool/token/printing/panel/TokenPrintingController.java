/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.token.printing.panel;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class TokenPrintingController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField caseNumberText;

    @FXML
    private ComboBox<?> groupeOfServiceCombobox;

    @FXML
    private ComboBox<?> serviceRequestCombobox;

    @FXML
    private ComboBox<?> gateComboboX;

    @FXML
   private void initialize() {
        assert phoneNumber != null : "fx:id=\"phoneNumber\" was not injected: check your FXML file 'TokenPrintingView.fxml'.";
        assert caseNumberText != null : "fx:id=\"caseNumberText\" was not injected: check your FXML file 'TokenPrintingView.fxml'.";
        assert groupeOfServiceCombobox != null : "fx:id=\"groupeOfServiceCombobox\" was not injected: check your FXML file 'TokenPrintingView.fxml'.";
        assert serviceRequestCombobox != null : "fx:id=\"serviceRequestCombobox\" was not injected: check your FXML file 'TokenPrintingView.fxml'.";
        assert gateComboboX != null : "fx:id=\"gateComboboX\" was not injected: check your FXML file 'TokenPrintingView.fxml'.";
    }

    @FXML
    private void registerQuery(ActionEvent event) {

    }

    @FXML
    private void printNewRegistrationToken(ActionEvent event) {

    }

    @FXML
    private void printOtherToken(ActionEvent event) {

    }
}
