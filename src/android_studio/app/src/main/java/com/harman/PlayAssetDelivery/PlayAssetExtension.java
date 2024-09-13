package com.harman.PlayAssetDelivery;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

public class PlayAssetExtension implements FREExtension {
    private static final String EXT_NAME = "PlayAssetAneExtension";
    private PlayAssetExtensionContext context;
    private String tag = EXT_NAME + "PlayAssetAneExtensionClass";
    public android.app.Activity activity;

    public FREContext createContext(String arg0) {
        PlayAssetLogger.i(tag, "Creating context");
        context = new PlayAssetExtensionContext(EXT_NAME);
        return context;
    }

    public void dispose() {
        PlayAssetLogger.i(tag, "Disposing extension");
        activity=null;
        // nothing to dispose for this example
    }

    public void initialize() {
        PlayAssetLogger.i(tag, "Initialize");
        // nothing to initialize for this example
    }
}
