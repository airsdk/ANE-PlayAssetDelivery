package com.harman.PlayAssetDelivery;

import android.app.Activity;
import android.content.Context;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class OpenInstallTimeAsset implements FREFunction {
    public static final String KEY = "OpenInstallTimeAsset";

    @Override
    public FREObject call(FREContext context, FREObject[] args) {
        PlayAssetExtensionContext ctx = (PlayAssetExtensionContext) context;
        FREObject returnValue = null;
        PlayAssetLogger.i(KEY, "OpenInstallTimeAsset");

        try {
            FREObject input = args[0];
            String assetFileName = input.getAsString();
            PlayAssetLogger.i(KEY, "Input value is " + assetFileName);
            Activity activity = context.getActivity();
            if (activity != null) {
                Context applicationContext = activity.getApplicationContext();
                if (applicationContext == null) {
                    PlayAssetLogger.i(KEY, "applicationContext is null");
                } else {
                    PlayAssetDeliveryManager instance = ctx.getPlayAssetDeliveryManagerObj();
                    int assetRef = instance.openInstallTimeDeliveryAsset(assetFileName);
                    PlayAssetLogger.i(KEY, "return value "+ assetRef);
                    returnValue = FREObject.newObject(assetRef);
                }
            } else {
                PlayAssetLogger.i(KEY, "Activity is null");
            }

        } catch (Exception e) {
            PlayAssetLogger.d(KEY,"exception"+e.toString());
        }
        PlayAssetLogger.i(KEY, "OpenInstallTimeAsset end");
        return returnValue;
    }
}
