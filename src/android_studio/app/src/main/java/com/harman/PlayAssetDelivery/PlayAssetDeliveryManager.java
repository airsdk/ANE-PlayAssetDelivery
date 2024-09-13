package com.harman.PlayAssetDelivery;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.adobe.fre.FREContext;
import com.google.android.play.core.assetpacks.AssetLocation;
import com.google.android.play.core.assetpacks.AssetPackLocation;
import com.google.android.play.core.assetpacks.AssetPackManager;
import com.google.android.play.core.assetpacks.AssetPackManagerFactory;
import com.google.android.play.core.assetpacks.AssetPackState;
import com.google.android.play.core.assetpacks.AssetPackStateUpdateListener;
import com.google.android.play.core.assetpacks.AssetPackStates;
import com.google.android.play.core.assetpacks.model.AssetPackStatus;
import com.google.android.play.core.assetpacks.model.AssetPackStorageMethod;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayAssetDeliveryManager implements AssetPackStateUpdateListener {

    private static  PlayAssetDeliveryManager playAssertInstance =null;
    AssetPackManager assetPackManager;
    Task<AssetPackStates> assetPackStatesTask =null;
    private Context mContext;
    private FREContext mExtCtx;
    private static final String TAG = "PlayAssetDeliveryManager";
    ArrayList<InputStream> mStreams;
    Map<String, AssetPackState> mAssetPackStateMap = null;

    // private constructor restricted to this class itself
    public PlayAssetDeliveryManager()
    {
        mStreams = new ArrayList<>();
        mAssetPackStateMap = new HashMap<String, AssetPackState>();
    }

    // static method to create instance of Singleton class
    public static PlayAssetDeliveryManager getInstance()
    {
        PlayAssetLogger.d(TAG,"PlayAssetDeliveryManager getInstance()\n");
        if (playAssertInstance == null) {
            playAssertInstance = new PlayAssetDeliveryManager();
        }
        PlayAssetLogger.d(TAG,"getInstance() end");
        return playAssertInstance;
    }

    public boolean setContext(Context context, Activity mActivity, FREContext freContext){
        PlayAssetLogger.d(TAG,"setContext()");
        mContext = context;
        mExtCtx = freContext;
        // guaranteed to be non-null
        assetPackManager=AssetPackManagerFactory
                .getInstance(mActivity.getApplicationContext());
        assetPackManager.registerListener(this);
        PlayAssetLogger.d(TAG,"setContext() end");
        return true;
    }

    public int openInstallTimeDeliveryAsset(String assetName) throws Exception{

        PlayAssetLogger.d(TAG,"openInstallTimeDeliveryAsset");
        AssetManager assetManager = mContext.getAssets();
        int nStreamID = 0;
        InputStream is = assetManager.open(assetName);
        if (is != null)
        {
            mStreams.add(is);
            nStreamID = mStreams.size(); // so 1 more than its index..
            if (nStreamID > 0x7FFFFF0) Log.w("AdobeAIR", "Play Asset Delivery: too many asset files opened");
        }
        PlayAssetLogger.d(TAG,"openInstallTimeDeliveryAssert end -> stream ID " + nStreamID);

        return nStreamID;
    }

    public void getPackStates(String assetPackName) {
        PlayAssetLogger.d(TAG, "getPackStates()");
        // below code is directly from https://developer.android.com/guide/playcore/asset-delivery/integrate-java#java
        assetPackManager
                .getPackStates(Collections.singletonList(assetPackName))
                .addOnCompleteListener(new OnCompleteListener<AssetPackStates>() {
                    @Override
                    public void onComplete(Task<AssetPackStates> task) {
                        AssetPackStates assetPackStates;
                        try {
                            assetPackStates = task.getResult();
                            if (assetPackStates == null)
                                PlayAssetLogger.w(TAG, "AssetPackStates result is not available");
                            AssetPackState assetPackState =
                                    assetPackStates.packStates().get(assetPackName);
                            PlayAssetLogger.d(TAG, "status: " + assetPackState.status() +
                                    ", name: " + assetPackState.name() +
                                    ", errorCode: " + assetPackState.errorCode() +
                                    ", bytesDownloaded: " + assetPackState.bytesDownloaded() +
                                    ", totalBytesToDownload: " + assetPackState.totalBytesToDownload() +
                                    ", transferProgressPercentage: " + assetPackState.transferProgressPercentage());
                            mAssetPackStateMap.put(assetPackName, assetPackState);
                            mExtCtx.dispatchStatusEventAsync(assetPackState.name(), ""+assetPackState.status());
                        } catch (RuntimeExecutionException err) {
                            PlayAssetLogger.w(TAG, "Exception in AssetPackStates completion handler: " + err.getMessage());
                            return;
                        }
                    }
                });
    }

    public int fetch(List<String> packNameList) {
        PlayAssetLogger.d(TAG, "fetch()");
        int status = AssetPackStatus.UNKNOWN;
        if (!packNameList.isEmpty()) {
            Task<AssetPackStates> assetpackStates = assetPackManager.fetch(packNameList);
            assetpackStates.addOnCompleteListener(new OnCompleteListener<AssetPackStates>() {
                @Override
                public void onComplete(Task<AssetPackStates> task) {
                    AssetPackStates assetPackStates;
                    try {
                        assetPackStates = task.getResult();
                        if (assetPackStates == null)
                            PlayAssetLogger.w(TAG, "Fetch asset pack result is not available");
                        AssetPackState assetPackState =
                                assetPackStates.packStates().get( packNameList.get(0) );
                        PlayAssetLogger.i(TAG, "status: " + assetPackState.status() +
                                ", name: " + assetPackState.name() +
                                ", errorCode: " + assetPackState.errorCode() +
                                ", bytesDownloaded: " + assetPackState.bytesDownloaded() +
                                ", totalBytesToDownload: " + assetPackState.totalBytesToDownload() +
                                ", transferProgressPercentage: " + assetPackState.transferProgressPercentage());
                        mAssetPackStateMap.put(assetPackState.name(), assetPackState);
                    } catch (RuntimeExecutionException err) {
                        PlayAssetLogger.w(TAG, "Exception in fetch asset pack completion handler: " + err.getMessage());
                        return;
                    }
                }
            });
        }

        PlayAssetLogger.d(TAG, "fetch() end: status " + status);
        return status;
    }


    public String getAbsoluteAssetPath(String assetPack,String relativeAssetPath) {
		if (relativeAssetPath.startsWith("/") == false) relativeAssetPath = "/" + relativeAssetPath;
        PlayAssetLogger.d(TAG, "getAbsoluteAssetPath(\"" + assetPack + "\", \"" + relativeAssetPath + "\")");
        String assetsFolderPath =null;
        AssetPackState state = mAssetPackStateMap.get(assetPack);
        if (state == null) {
            PlayAssetLogger.w(TAG, "Attempting to get path for asset pack with unknown status");
            return null;
        }
        if (state.status() != AssetPackStatus.COMPLETED)
        {
            PlayAssetLogger.w(TAG, "Attempting to get path for asset pack that is not yet completed");
            return null;
        }
        //assetPackPath = assetPackManager.getPackLocation(assetPack);
        AssetLocation loc = assetPackManager.getAssetLocation(assetPack, relativeAssetPath);
        if (loc == null) {
            PlayAssetLogger.w(TAG, "Asset location not found");
        }
        assetsFolderPath = loc.path();
        PlayAssetLogger.d(TAG, "Asset location is " + assetsFolderPath);
        return assetsFolderPath;
    }

    public String getAssetPackLocation(String assetPack) {
        String assetPackPath = null;
        PlayAssetLogger.d(TAG, "getPackLocation");
        // just get the location, ignore the status first...
        AssetPackLocation packLocation = assetPackManager.getPackLocation(assetPack);
        if (packLocation == null)
        {
            PlayAssetLogger.i(TAG, "Asset pack [" + assetPack + "] has not been downloaded");
        }
        else
        {
            PlayAssetLogger.i(TAG, "Asset pack [" + assetPack + "] is already available");
            int storageMethod = packLocation.packStorageMethod();
            switch(storageMethod)
            {
                case AssetPackStorageMethod.APK_ASSETS:
                    PlayAssetLogger.d(TAG, "Storage method = APK_ASSETS");
                    break;
                case AssetPackStorageMethod.STORAGE_FILES:
                    PlayAssetLogger.d(TAG, "Storage method = STORAGE_FILES");
                    break;
                default:
                    PlayAssetLogger.d(TAG, "Storage method = " + storageMethod);
            }
            assetPackPath = packLocation.assetsPath();
            if (assetPackPath != null) PlayAssetLogger.d(TAG, "Asset pack files at " + assetPackPath);
            else PlayAssetLogger.w(TAG, "Asset pack is not using file storage");
        }
        PlayAssetLogger.d(TAG, "getPackLocation end " + assetPackPath);
        return assetPackPath;
    }

    public int getStatus(String assetPackName) {
        PlayAssetLogger.d(TAG, "getStatus()");
        AssetPackStates assetPackStates;
        int status = AssetPackStatus.UNKNOWN;
        assetPackStatesTask = null;
        getPackStates(assetPackName);
        return status;
    }

    public int cancel(String packNames){
        PlayAssetLogger.d(TAG, "cancel()");
        List<String> list = new ArrayList<>();
        list.add(packNames);
        AssetPackStates assetPackStates = assetPackManager.cancel(list);
        int status = AssetPackStatus.UNKNOWN;
        if (assetPackStates != null) {
            AssetPackState state = assetPackStates.packStates().get(packNames);
            if (state != null) {
                status = state.status();
            }
        }
        PlayAssetLogger.d(TAG, "cancel status = " + status);
        return status;
    }

    public int removePack(String packNames) {
        PlayAssetLogger.d(TAG, "removePack()" + "packnames" + " " + packNames);

        String assetPackLocation = getAssetPackLocation(packNames);

        if (assetPackLocation == null) {
            PlayAssetLogger.d(TAG, "removePack() AssetPackStatus.FAILED");
            return AssetPackStatus.FAILED;
        }
        Task<Void> removeTask = assetPackManager.removePack(packNames);
        removeTask.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                PlayAssetLogger.d(TAG, "removePack() onComplete event");
                AssetPackStates assetPackStates;
                if (task.isSuccessful()) {
                    String assetPackLocation = getAssetPackLocation(packNames);
                    if (assetPackLocation == null) {
                        PlayAssetLogger.d(TAG, "removePack() onComplete event remove pack success sending eVent to AS3");
                        mExtCtx.dispatchStatusEventAsync(packNames, "" + AssetPackStatus.NOT_INSTALLED);
                    } else {
                        PlayAssetLogger.d(TAG, "removePack() onComplete event remove pack failed");
                        mExtCtx.dispatchStatusEventAsync(packNames, "" + AssetPackStatus.FAILED);
                    }
                } else {
                    mExtCtx.dispatchStatusEventAsync(packNames, "" + AssetPackStatus.FAILED);
                }

            }
        });
        return AssetPackStatus.PENDING;
    }

    public int totalBytesToDownload(String assetPackName) {
        PlayAssetLogger.d(TAG, "totalBytesToDownload");
        AssetPackState state = mAssetPackStateMap.get(assetPackName);
        if (state == null) {
            PlayAssetLogger.w(TAG, "Attempting to get details for asset pack with unknown status");
            return 0;
        }
        PlayAssetLogger.d(TAG, "totalBytesToDownload=" + state.totalBytesToDownload());
        return (int)state.totalBytesToDownload();
    }

    public int byteDownloaded(String assetPackName) {
        PlayAssetLogger.d(TAG, "byteDownloaded");
        AssetPackState state = mAssetPackStateMap.get(assetPackName);
        if (state == null) {
            PlayAssetLogger.w(TAG, "Attempting to get details for asset pack with unknown status");
            return 0;
        }
        PlayAssetLogger.d(TAG, "byteDownloaded=" + state.bytesDownloaded());
        return (int)state.bytesDownloaded();
    }

    public int  transferProgressPercentage(String assetPackName) {
        PlayAssetLogger.d(TAG, "transferProgressPercentage");
        AssetPackState state = mAssetPackStateMap.get(assetPackName);
        if (state == null) {
            PlayAssetLogger.w(TAG, "Attempting to get details for asset pack with unknown status");
            return 0;
        }
        PlayAssetLogger.d(TAG, "transferProgressPercentage=" + state.transferProgressPercentage());
        return (int)state.transferProgressPercentage();
    }

    public boolean showCellularDataConfirmation(String assetPackName) {
        PlayAssetLogger.d(TAG, "requestCellularDataConfirmation for " + assetPackName);
        AssetPackState state = mAssetPackStateMap.get(assetPackName);
        if (state == null) {
            PlayAssetLogger.w(TAG, "Attempting to get details for asset pack with unknown status");
            return false;
        }
        if (state.status() != AssetPackStatus.WAITING_FOR_WIFI) {
            PlayAssetLogger.w(TAG, "Asset pack is not in the 'WAITING_FOR_WIFI' status: ignoring request");
            return false;
        }
        assetPackManager.showConfirmationDialog(mExtCtx.getActivity())
                .addOnSuccessListener(new OnSuccessListener<Integer>() {
                    @Override
                    public void onSuccess(Integer resultCode) {
                        if (resultCode == Activity.RESULT_OK) {
                            PlayAssetLogger.i(TAG, "Confirmation dialog has been accepted.");
                        } else if (resultCode == Activity.RESULT_CANCELED) {
                            PlayAssetLogger.i(TAG, "Confirmation dialog has been denied by the user.");
                        } else {
                            PlayAssetLogger.i(TAG, "Confirmation dialog unknown response = " + resultCode);
                        }
                    }
                });
        return true;
    }

    @Override
    public void onStateUpdate(AssetPackState assetPackState) {
        PlayAssetLogger.i(TAG, "Asset pack onStateUpdate -> " + assetPackState.name() + " -> " + assetPackState.status());
        PlayAssetLogger.i(TAG, "   Bytes downloaded = " + assetPackState.bytesDownloaded() + " out of " + assetPackState.totalBytesToDownload() + ", percentage = " + assetPackState.transferProgressPercentage());

        try {
            if(assetPackState.name() !=null) {
                mAssetPackStateMap.put(assetPackState.name(), assetPackState);
                mExtCtx.dispatchStatusEventAsync(assetPackState.name(), "" + Integer.toString(assetPackState.status()));
            }
        }catch (IllegalArgumentException ex){
            PlayAssetLogger.i(TAG, "Exception Asset pack onStateUpdate -> " +ex.toString());
        }
    }
}
