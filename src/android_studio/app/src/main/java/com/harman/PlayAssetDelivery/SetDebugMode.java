package com.harman.PlayAssetDelivery;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

import java.util.Collections;
import java.util.List;

public class SetDebugMode implements FREFunction {
    public static final String KEY = "SetDebugMode";
    private static final String TAG = "SetDebugMode";

    @Override
    public FREObject call(FREContext freContext, FREObject[] args) {
        FREObject returnValue = null;
        PlayAssetLogger.i(TAG, "SetDebugMode");
        try {
            FREObject input = args[0];
	    boolean newVal = input.getAsBool();
	    PlayAssetLogger.g_enableReleaseLogging = newVal;
            returnValue = FREObject.newObject( PlayAssetLogger.g_enableReleaseLogging );
        } catch (Exception e) {
            PlayAssetLogger.d(TAG,"Exception"+e.toString());
        }
        PlayAssetLogger.i(TAG, "SetDebugMode end");
        return returnValue;
    }
}
