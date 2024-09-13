package com.harman.PlayAssetDelivery;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

import java.io.InputStream;

public class AssetFileClose implements FREFunction {
    public static final String KEY = "AssetFileClose";

    @Override
    public FREObject call(FREContext freContext, FREObject[] args) {
        PlayAssetExtensionContext ctx = (PlayAssetExtensionContext) freContext;
        try {
            // inputstream index (1-based) is in arg 0
            FREObject input = args[0];
            int isIndex = input.getAsInt() - 1;
            PlayAssetLogger.i(KEY, "Requesting input stream index " + isIndex);
            InputStream is = ctx.getPlayAssetDeliveryManagerObj().mStreams.get(isIndex);
            is.close();
            ctx.getPlayAssetDeliveryManagerObj().mStreams.set(isIndex, null);
        } catch (Exception e) {
            PlayAssetLogger.d(KEY,"Exception: " + e.toString());
        }
        return null;
    }
}
