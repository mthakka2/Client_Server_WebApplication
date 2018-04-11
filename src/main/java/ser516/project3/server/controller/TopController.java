package ser516.project3.server.controller;

import org.apache.log4j.Logger;
import ser516.project3.interfaces.ControllerInterface;
import ser516.project3.interfaces.ViewInterface;
import ser516.project3.model.TopModel;
import ser516.project3.server.service.ServerConnectionServiceInterface;
import ser516.project3.server.view.TopView;
import ser516.project3.utilities.ServerCommonData;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class that helps communicate between TopView and TopModel.
 * The controller can receive and update data from the TopView, 
 * and use this data to update the TopModel.
 * 
 * @author Ser516-Team09
 */
public class TopController implements ControllerInterface{
  final static Logger logger = Logger.getLogger(TopController.class);

  private TopModel topModel;
  private TopView topView;
  private ServerConnectionServiceInterface serverConnectionService;

  private static final String START = "Start";
  private static final String STOP = "Stop";
  private static final String SEND = "Send";

  /**
   * Constructor to set the top view and model object
   * @param topModel - TopModel object
   * @param topView - TopView object 
   */
  public TopController(TopModel topModel, TopView topView) {
    this.topModel = topModel;
    this.topView = topView;
  }
  
  /**
   * Method to initialize the top view and to add listeners 
   * to all the components in the panel
   */
  @Override
  public void initializeView() {
    topView.initializeView(null);
    topView.addIntervalInputTextFieldListener(new IntervalDocumentListener());
    topView.addAutoRepeatCheckBoxListener(new AutoRepeatCheckBoxListener());
    topView.addServerStartStopButtonListener(new ServerStartStopButtonListener());
    topView.addSendButtonListener(new SendButtonListener());
    ServerCommonData.getInstance().getMessage().setInterval(topModel.getInterval());
  }

  /**
   * Method to get the TopView object
   * @return TopView object
   */
  @Override
  public TopView getView() {
    return topView;
  }

  @Override
  public ControllerInterface[] getSubControllers() {
    return null;
  }

  /**
   * Inner class to add document listener to timer interval 
   * component in the top panel 
   */
  class IntervalDocumentListener implements DocumentListener {
   
	  /**
	   * Method to remove update of time interval 
       */
	@Override
    public void removeUpdate(DocumentEvent e) {
      try {
        if(e.getDocument().getLength() == 0) {
          topModel.setInterval(1);
        } else {
          topModel.setInterval(Double.parseDouble(e.getDocument().getText(0, 
        		  									e.getDocument().getLength())));
        }
        topModel.setIntervalError(false);
        ServerCommonData.getInstance().getMessage().setInterval(topModel.getInterval());
        logger.info("Removed value of interval");
      } catch(NumberFormatException ex) {
        topModel.setIntervalError(true);
        JOptionPane.showMessageDialog(null, "Invalid interval!");
      } catch(BadLocationException ex) {
        logger.info("Some problem in the interval input.");
      }
    }
	/**
	 * Method to update the time interval 
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
      try {
        topModel.setInterval(Double.parseDouble(e.getDocument().getText(0, 
        											e.getDocument().getLength())));
        ServerCommonData.getInstance().getMessage().setInterval(topModel.getInterval());
        topModel.setIntervalError(false);
        logger.info("Inserted value of interval");
      } catch(NumberFormatException ex) {
        topModel.setIntervalError(true);
        JOptionPane.showMessageDialog(null, "Invalid interval!");
      } catch(BadLocationException ex) {
        logger.info("Some problem in the interval input.");
      }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
  }
  
  /**
   * Inner class to add action listener to auto repeat check box
   * component in the top panel 
   */
  class AutoRepeatCheckBoxListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      boolean isChecked = !topModel.isAutoRepeatCheckBoxChecked();
      topModel.setAutoRepeatCheckBoxChecked(isChecked);
      if(isChecked) {
        topModel.setSendButtonText(START);
      } else {
        topModel.setSendButtonText(SEND);
      }
      topView.updateSendButtonText();
      logger.info("Value of auto Repeat toggle changed: " + isChecked);
    }
  }
  
  /**
   * Inner class to add action listener to server start/stop button
   * in the top panel 
   */
  class ServerStartStopButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      logger.info("Start/Stop button pressed");
      boolean isStarted = topModel.isServerStarted();
      if(isStarted) {
        serverConnectionService.stopServerEndpoint();
        topModel.setServerStarted(false);
        topModel.setSendButtonEnabled(false);
        topModel.setServerStartStopButtonText("Start Server");
        setBlinking(false);
      } else {
        serverConnectionService.initServerEndpoint();
        topModel.setServerStarted(true);
        topModel.setSendButtonEnabled(true);
        topModel.setServerStartStopButtonText("Stop Server");
        setBlinking(true);
      }
      topView.enableDisableSendButton();
      topView.updateServerStartStopButtonText();
    }
  }
  
  /**
   * Inner class to add action listener to server data send button
   * in the top panel 
   */
  class SendButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      logger.info("Send button pressed");
      if(!topModel.isIntervalError()) {
        ServerController.getInstance().getConsoleController().getConsoleModel().
      											logMessage("Sending Data to Client.");
        if(topModel.isAutoRepeatCheckBoxChecked()) {
          if(topModel.getSendButtonText().equals(START))
            topModel.setSendButtonText(STOP);
          else
            topModel.setSendButtonText(START);
          topModel.setAutoRepeatEnabled(!topModel.isAutoRepeatEnabled());
          topModel.setIntervalEditable(!topModel.isIntervalEditable());
          topView.updateSendButtonText();
          topView.enableDisableAutoRepeatCheckBox();
          topView.enableDisableEditableIntervalInputTextField();
        }
        if (topModel.isShouldSendData()) {
          topModel.setShouldSendData(false);
        } else {
          topModel.setShouldSendData(true);
        }
      } else {
        JOptionPane.showMessageDialog(null, "Invalid interval! Please correct it.");
      }
    }
  }
  
  /**
   * Method to set the server connection service interface 
   * @param serverConnectionService - ServerConnectionServiceInterface object
   */
  public void setServerConnectionService(ServerConnectionServiceInterface serverConnectionService){
    this.serverConnectionService = serverConnectionService;
  }

  /**
   * Method to set the server status indicator
   * @param status - Status of the server
   */
  public void setBlinking(boolean status) {
    topView.setBlinking(status);
  }
  
  /**
   * Method to update the text in start/stop button
   */
  public void updateServerStartStopButtonText() {
    topView.updateServerStartStopButtonText();
  }

  /**
   * Method to enable/disable the send button
   */
  public void updateEnableDisableSendButton() {
    topView.enableDisableSendButton();
  }
  
  /**
   * Method to get the TopModel object
   * @return TopModel object 
   */
  public TopModel getTopModel() {
    return topModel;
  }
}