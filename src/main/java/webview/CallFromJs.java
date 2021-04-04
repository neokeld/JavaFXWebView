package webview;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CallFromJs {

    private static final Logger logger = LoggerFactory.getLogger(CallFromJs.class);
	
    public void doIt() {
    	logger.debug("CallFromJs::doIt() called");
    }
	
}
