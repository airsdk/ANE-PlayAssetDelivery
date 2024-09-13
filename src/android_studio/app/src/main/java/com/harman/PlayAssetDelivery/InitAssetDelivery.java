package com.harman.PlayAssetDelivery;

import android.app.Activity;
import android.content.Context;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class InitAssetDelivery implements FREFunction {
    public static final String KEY = "InitAssetDelivery";
    private static final String TAG = "InitAssetDelivery";

    @Override
    public FREObject call(FREContext freContext, FREObject[] freObjects) {
        FREObject returnValue = null;
        try {
        PlayAssetExtensionContext ctx = (PlayAssetExtensionContext) freContext;
            PlayAssetLogger.i(TAG, "InitAssetDelivery");
        Activity activity = ctx.getActivity();
        Context applicationContext = activity.getApplicationContext();
        PlayAssetDeliveryManager instance = new PlayAssetDeliveryManager();
            boolean ret = instance.setContext(applicationContext, activity, freContext);
            ctx.setPlayAssetDeliveryManagerObj(instance);
            returnValue = FREObject.newObject(ret);
        } catch (Exception e) {
            PlayAssetLogger.d(TAG,"exception"+e.toString());
        }
        PlayAssetLogger.i(TAG, "InitAssetDelivery end");
        return returnValue;
    }
}
