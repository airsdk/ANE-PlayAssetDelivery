package com.harman.PlayAssetDelivery;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;

import java.util.HashMap;
import java.util.Map;

public class PlayAssetExtensionContext extends FREContext {
    private static final String CTX_NAME = "PlayAssetExtensionContext";
    private String tag;
    private PlayAssetDeliveryManager objPlayAssetDeliveyMgr;

    public PlayAssetExtensionContext(String extensionName ) {
        tag = extensionName + "." + CTX_NAME;
        PlayAssetLogger.i(tag, "Creating context");
    }

    @Override
    public void dispose() {
        PlayAssetLogger.i(tag, "Dispose context");
    }

    @Override
    public Map<String, FREFunction> getFunctions() {
        PlayAssetLogger.i(tag, "Creating function Map");
        Map<String, FREFunction> functionMap = new HashMap<String, FREFunction>();

        functionMap.put( OpenInstallTimeAsset.KEY, new OpenInstallTimeAsset() );
        functionMap.put( GetAssetStatus.KEY, new GetAssetStatus() );
        functionMap.put( InitAssetDelivery.KEY, new InitAssetDelivery() );
        functionMap.put( FetchAsset.KEY, new FetchAsset() );
        functionMap.put(GetAssetAbsolutePath.KEY,new GetAssetAbsolutePath());
        functionMap.put(RemoveAssetPack.KEY,new RemoveAssetPack());
        functionMap.put(CancelAssetPack.KEY,new CancelAssetPack());
        functionMap.put(GetTotalBytesToDownLoad.KEY,new GetTotalBytesToDownLoad());
        functionMap.put(GetByteDownloaded.KEY,new GetByteDownloaded());
        functionMap.put(GetTransferProgressPercentage.KEY,new GetTransferProgressPercentage());
        functionMap.put(GetAssetPackLocation.KEY,new GetAssetPackLocation());
        functionMap.put(AssetFileBytesAvailable.KEY, new AssetFileBytesAvailable());
        functionMap.put(AssetFileClose.KEY, new AssetFileClose());
        functionMap.put(AssetFileReadValue.KEY, new AssetFileReadValue());
        functionMap.put(AssetFileReadBytes.KEY, new AssetFileReadBytes());
        functionMap.put(AssetFileReadString.KEY, new AssetFileReadString());
        functionMap.put(ShowCellularDataConfirmation.KEY, new ShowCellularDataConfirmation());
        functionMap.put(SetDebugMode.KEY, new SetDebugMode());
        return functionMap;
    }

    public void setPlayAssetDeliveryManagerObj(PlayAssetDeliveryManager obj){

        PlayAssetLogger.i(tag, "setPlayAssetDeliveryManagerObj set");
        if(obj ==null){
            PlayAssetLogger.i(tag, "setPlayAssetDeliveryManagerObj set obj null");
        }
        objPlayAssetDeliveyMgr = obj;
    }

    public PlayAssetDeliveryManager getPlayAssetDeliveryManagerObj(){
        PlayAssetLogger.i(tag, "setPlayAssetDeliveryManagerObj get");
        return objPlayAssetDeliveyMgr;
    }

    public String getIdentifier() {
        return tag;
    }
}
