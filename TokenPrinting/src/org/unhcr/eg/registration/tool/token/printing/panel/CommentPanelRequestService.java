/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.token.printing.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javax.swing.JButton;
import org.jsoup.Jsoup;

/**
 *
 * @author UNHCRuser
 */
public class CommentPanelRequestService extends JFXPanel implements ActionListener {

    private TextField numberOfIndividual;
    private HTMLEditor comments;
    private JButton ok;
    private JButton cancel;

    public JButton getOk() {
        return ok;
    }

    public JButton getCancel() {
        return cancel;
    }

    public TextField getNumberOfIndividualText() {
        return numberOfIndividual;
    }

    public HTMLEditor getHTMLEditor() {
        return comments;
    }

    public String getNumberOfIndividuals() {
        return numberOfIndividual.getText();
    }

    public String getComments() {
        return Jsoup.parse(comments.getHtmlText()).text();
    }

    public String getHTMLComments() {
        return comments.getHtmlText();
    }

    public CommentPanelRequestService() {
        ok = new JButton("Add Details");
        ok.setActionCommand("Ok");
        cancel = new JButton("Cancel");
        cancel.setActionCommand("Cancel");
        setSize(400, 300);
    }

    public void init() {
        Platform.setImplicitExit(false);
        GridPane grid = new GridPane();
        
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Please specify service request details");
        grid.add(scenetitle, 0, 0, 2, 1);

        Label numberOfIndividualLabel = new Label("Number of indivduals:");
        grid.add(numberOfIndividualLabel, 0, 1, 2, 1);
        numberOfIndividual = new TextField();
        numberOfIndividual.setText("1");
        grid.add(numberOfIndividual, 0, 2, 2, 1);

        Label commentsLabel = new Label("Comment:");
        grid.add(commentsLabel, 0, 3, 2, 1);

        comments = new HTMLEditor();
        grid.add(comments, 0, 4, 2, 1);

        setScene(new Scene(grid, 300, 300));
       
    }

    public boolean isValidData() {
        return getNumberOfIndividualText().getText().matches("\\d+") && getComments() != null && getComments().replace(" ", "").length() > 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "Ok":
                if (!getNumberOfIndividualText().getText().matches("\\d+")) {
                    throw new IllegalArgumentException("Number of individuals should be an integer");
                }
                if (comments.getHtmlText() == null || getComments().replace(" ", "").length() == 0) {
                    throw new IllegalArgumentException("Please comment the Request");
                }
                break;
            case "Cancel":
                break;
        }
    }

}
