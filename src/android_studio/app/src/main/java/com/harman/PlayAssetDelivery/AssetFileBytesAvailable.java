package com.harman.PlayAssetDelivery;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

import java.io.InputStream;

public class AssetFileBytesAvailable implements FREFunction {
    public static final String KEY = "AssetFileBytesAvailable";

    @Override
    public FREObject call(FREContext freContext, FREObject[] args) {
        PlayAssetExtensionContext ctx = (PlayAssetExtensionContext) freContext;
        FREObject returnValue = null;
        try {
            // inputstream index (1-based) is in arg 0
            FREObject input = args[0];
            int isIndex = input.getAsInt() - 1;
            PlayAssetLogger.i(KEY, "Requesting input stream index " + isIndex);
            InputStream is = ctx.getPlayAssetDeliveryManagerObj().mStreams.get(isIndex);
            int bytesAvailable = is.available();
            PlayAssetLogger.i(KEY, "Bytes available = " + bytesAvailable);
            returnValue = FREObject.newObject(bytesAvailable);
        } catch (Exception e) {
            PlayAssetLogger.d(KEY,"Exception: " + e.toString());
        }
        return returnValue;
    }
}
