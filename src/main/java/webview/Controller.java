package webview;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
	
    @FXML
    private AnchorPane webView;
    
    @FXML
    private WebViewController webViewController;
    
    @FXML
    private Button btnOverview;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnMenus;

    @FXML
    private Button btnPackages;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignout;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlOrders;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlMenus;
    
    @FXML
    private Pane pnlPackages;
    
    @FXML
    private Pane pnlSettings;
    
    @FXML
    private Pane pnlSignout;
    
    @FXML
    private Button btnCloseWindow;

    @FXML
    public void initialize() {
    	logger.debug("Initialize Controller");
    }

    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnCustomers) {
            pnlCustomer.toFront();
        } else if (actionEvent.getSource() == btnMenus) {
            pnlMenus.toFront();
        } else if (actionEvent.getSource() == btnOrders) {
            pnlOrders.toFront();
        } else if (actionEvent.getSource() == btnOverview) {
            pnlOverview.toFront();
        } else if (actionEvent.getSource() == btnPackages) {
        	pnlPackages.toFront();
        } else if (actionEvent.getSource() == btnSettings) {
        	pnlSettings.toFront();
        } else if (actionEvent.getSource() == btnSignout) {
        	pnlSignout.toFront();
        }
    }
    
    public void handleCloseWindow() {
    	Platform.exit();
    }
}
