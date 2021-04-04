package webview;

import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class WebViewController
{
    @FXML
    private WebView webView;

    @FXML
    private void initialize()
    {
    	String fileLocInClasspath = "index.html";
    	String fullLink = getClass().getResource(fileLocInClasspath).toExternalForm();
        WebEngine engine = webView.getEngine();
        // expose Java object to Javascript in the JavaFX WebView
        engine.getLoadWorker().stateProperty().addListener(
    	    (ObservableValue<? extends Worker.State> observable, Worker.State oldState, Worker.State newState) -> {
	            if (newState != Worker.State.SUCCEEDED) { return; }

	            JSObject window = (JSObject) engine.executeScript("window");
	            window.setMember("callFromJs", new CallFromJs());
    	    }
        );
        //engine.load("http://www.example.org"); // Load a Web Page
        engine.load(fullLink); // Load Local Content
    }
}
