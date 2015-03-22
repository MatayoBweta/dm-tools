/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.token.printing.temp;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import org.controlsfx.dialog.Dialog;
import org.jsoup.Jsoup;

/**
 *
 * @author UNHCRuser
 */
public class CommentPopOver {

    private Dialog popOver;

    public Dialog getPopOver() {
        return popOver;
    }

    public class Comment {

        private String comment;
        private String numberOfIndividual;
        private String htmlComment;

        public Comment(String commen, String numberOfIndividual, String htmlComment) {
            this.comment = commen;
            this.numberOfIndividual = numberOfIndividual;
            this.htmlComment = htmlComment;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getNumberOfIndividual() {
            return numberOfIndividual;
        }

        public void setNumberOfIndividual(String numberOfIndividual) {
            this.numberOfIndividual = numberOfIndividual;
        }

        public String getHtmlComment() {
            return htmlComment;
        }

        public void setHtmlComment(String htmlComment) {
            this.htmlComment = htmlComment;
        }
    }

    private TextField numberOfIndividual;
    private HTMLEditor comments;
    private Button ok;
    private Button cancel;
    private Comment comment;
    private String title;

    public Comment getComment() {
        return comment;

    }

    private String getComments() {
        return Jsoup.parse(comments.getHtmlText()).text();
    }

    public CommentPopOver(String title) {
        this.title = title;
        popOver = new Dialog(null, title);
        popOver.setMasthead(title);

//        popOver.setDetachedTitle(title);
//        popOver.setDetached(true);

    }

    public void init() {
        Platform.setImplicitExit(false);

        ok = new Button("Add Details");
        ok.setPrefSize(100, 20);

        ok.setOnAction((javafx.event.ActionEvent event) -> {
            comment = new Comment(getComments(), numberOfIndividual.getText(), comments.getHtmlText());
            popOver.hide();
        });

        cancel = new Button("Cancel");
        cancel.setPrefSize(100, 20);
        cancel.setOnAction((javafx.event.ActionEvent event) -> {
            popOver.hide();
        });

        VBox root = new VBox();

        GridPane grid = new GridPane();
        root.getChildren().add(grid);
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
        numberOfIndividual.textProperty().addListener((observable, oldValue, newValue) -> {
            ok.disableProperty().set(!isValidData());
        });
        grid.add(numberOfIndividual, 0, 2, 2, 1);

        Label commentsLabel = new Label("Comment:");
        grid.add(commentsLabel, 0, 3, 2, 1);

        comments = new HTMLEditor();
        comments.setMaxHeight(200);
        comments.setMaxWidth(500);

        grid.add(comments, 0, 4, 2, 1);

        Region emptySpace = new Region();
        VBox.setVgrow(emptySpace, Priority.ALWAYS);
        root.getChildren().add(emptySpace);

        HBox buttons = new HBox(10, ok, cancel);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.setPadding(new Insets(15, 12, 15, 12));
        buttons.setSpacing(10);

        root.getChildren().add(buttons);
        popOver.setContent(root);
    }

    public boolean isValidData() {
        return numberOfIndividual.getText().matches("\\d+") && getComments() != null && getComments().replace(" ", "").length() > 0;
    }
}
