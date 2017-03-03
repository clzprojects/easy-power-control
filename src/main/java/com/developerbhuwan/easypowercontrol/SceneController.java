/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.developerbhuwan.easypowercontrol;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tooltip;

/**
 * FXML Controller class
 *
 * @author Bhuwan Pd. Upadhyay
 */
public class SceneController implements Initializable {

    @FXML
    private Button shutdownButton;
    @FXML
    private Button hibernateButton;
    @FXML
    private Button restartButton;
    @FXML
    private ComboBox hourComboBox;
    @FXML
    private ComboBox minuteComboBox;
    @FXML
    private ComboBox timerOperation;
    @FXML
    private Button setTimerButton;
    @FXML
    private Button cancelTimerButton;
    @FXML
    private Label copyrightLinkLabel;

    private final String SHUTDOWN_COMMAND = "Shutdown";
    private final String RESTART_COMMAND = "Restart";
    private final String HIBERNATE_COMMAND = "Hibernate";
    private final ObservableList<String> POWER_OPTIONS
            = FXCollections.observableArrayList(
                    SHUTDOWN_COMMAND,
                    RESTART_COMMAND,
                    HIBERNATE_COMMAND
            );

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.initVars();
        this.initActions();
    }

    private void initActions() {
        shutdownButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                setWindowPowerAction(SHUTDOWN_COMMAND, "01");
            }
        });
        restartButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                setWindowPowerAction(RESTART_COMMAND, "01");
            }
        });
        hibernateButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                setWindowPowerAction(HIBERNATE_COMMAND, "01");
            }

        });

        setTimerButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                int hours = Integer.valueOf(hourComboBox.getSelectionModel().getSelectedItem().toString());
                int minute = Integer.valueOf(minuteComboBox.getSelectionModel().getSelectedItem().toString());
                long seconds = hours * 60 * 60 + minute * 60;
                String operation = timerOperation.getSelectionModel().getSelectedItem().toString();
                setWindowPowerAction(operation, String.valueOf(seconds));
            }
        });
        cancelTimerButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                abortWindowPowerAction();
            }
        });
    }

    private void abortWindowPowerAction() {
        try {
            String command = "shutdown -t 00 -a -f";
            Runtime.getRuntime().exec(command);
            setDisableAll(true);
        } catch (IOException ex) {
            Logger.getLogger(SceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setWindowPowerAction(String COMMAND, String duration) {
        try {
            String command;
            switch (COMMAND) {
                case SHUTDOWN_COMMAND:
                    command = "shutdown -t " + duration + " -s -f";
                    break;
                case RESTART_COMMAND:
                    command = "shutdown -t " + duration + " -r -f";
                    break;
                case HIBERNATE_COMMAND:
                    command = "shutdown -t " + duration + " -h -f";
                    break;
                default:
                    command = "shutdown -t 00 -a -f";
                    break;
            }
            Runtime.getRuntime().exec(command);        
            setDisableAll(true);
        } catch (IOException ex) {
            Logger.getLogger(SceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setDisableAll(boolean state) {
        shutdownButton.setDisable(state);
        restartButton.setDisable(state);
        hibernateButton.setDisable(state);
        hourComboBox.setDisable(state);
        minuteComboBox.setDisable(state);
        timerOperation.setDisable(state);
        setTimerButton.setDisable(state);
    }

    private void initVars() {

        for (int i = 0; i <= 60; i++) {
            if (i <= 12) {
                hourComboBox.getItems().add(i);
            }
            minuteComboBox.getItems().add(i);
        }
        timerOperation.getItems().addAll(POWER_OPTIONS);
        shutdownButton.setTooltip(new Tooltip("Quick Shutdown"));
        restartButton.setTooltip(new Tooltip("Quick Restart"));
        hibernateButton.setTooltip(new Tooltip("Quick Hibernate"));
        hourComboBox.setTooltip(new Tooltip("Set Hours"));
        minuteComboBox.setTooltip(new Tooltip("Set Minute"));
        timerOperation.setTooltip(new Tooltip("Set Operation"));
        hourComboBox.getSelectionModel().select(0);
        minuteComboBox.getSelectionModel().select(0);
        timerOperation.getSelectionModel().select(0);
    }
    
}
