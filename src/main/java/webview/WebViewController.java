package webview;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

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
        //engine.load("http://www.example.org"); // Load a Web Page
        engine.load(fullLink); // Load Local Content
    }
}
