package com.harman.extension
{
import flash.external.ExtensionContext;
import flash.events.EventDispatcher;
import flash.events.StatusEvent;
import flash.system.Capabilities;

/** PlayAssetDelivery */
public class PlayAssetDelivery extends EventDispatcher
{
    /** Extension Context */
    private var extContext:ExtensionContext=null;

    //
    // Public Methods
    //
    public function PlayAssetDelivery()
    {
        initContext();
    }

    private var _debugMode : Boolean;
    public function set debugMode(val : Boolean) : void
    {
        if (_debugMode != val)
	{
	    if (extContext) _debugMode = extContext.call("SetDebugMode", val) as Boolean;
	    if (val) trace("Set PlayAssetDelivery debug mode -> " + _debugMode);
	}
    }
    public function get debugMode() : Boolean
    {
        return _debugMode;
    }

    public function initAssetDelivery():Boolean
    {
        var status:Boolean;
        if (extContext) status =extContext.call("InitAssetDelivery","init") as Boolean;
        return status;
    }

    
    public function openInstallTimeAsset(assetFileName : String) : AssetFile
    {
        var assetFile : AssetFile = null;
        var javaRef : uint = 0;
        if (extContext) javaRef = extContext.call("OpenInstallTimeAsset", assetFileName) as uint;
        if (javaRef)
        {
            assetFile = new AssetFile();
            assetFile.setup(extContext, javaRef);
        }
        return assetFile;
    }

    public function getAssetAbsolutePath(assetPack:String,relativeAssetNamePath:String):String
    {
        var status:String = null;
        if (extContext) status = extContext.call("GetAssetAbsolutePath",assetPack,relativeAssetNamePath) as String;
        return status;
    }

    public function getTotalBytesToDownLoad(assetPack:String):int
    {
        var value:int;
        if (extContext) value =extContext.call("GetTotalBytesToDownLoad",assetPack) as int;
        return value;
    }

    public function getByteDownloaded(assetPack:String):int
    {
        var value:int;
        if (extContext) value=extContext.call("GetByteDownloaded",assetPack) as int;
        return value;
    }

    public function getAssetPackLocation(assetPack:String):String
    {
        var status:String;
        if (extContext) status=extContext.call("GetAssetPackLocation",assetPack) as String;
        return status;
    }

    public function getTransferProgressPercentage(assetPack:String):int
    {
        var value:int;
        if (extContext) value=extContext.call("GetTransferProgressPercentage",assetPack)as int;
        return value;
    }

    public function fetchAssetPack(assetPack:String):int
    {
        var value:int;
        if (extContext) value=extContext.call("FetchAsset",assetPack) as int;
        return value;
    }

    public function getAssetPackStatus(assetPack:String):int
    {
        var value:int;
        if (extContext) value=extContext.call("GetAssetStatus",assetPack) as int;
        return value;
    }

    public function removeAssetPack(assetPack:String):int
    {
        var value:int;
        if (extContext) value=extContext.call("RemoveAssetPack",assetPack) as int;
        return value;
    }


    public function cancelAssetPack(assetPack:String):int
    {
        var value:int;
        if (extContext) value=extContext.call("CancelAssetPack",assetPack) as int;
        return value;
    }

    public function showCellularDataConfirmation(assetPack:String):Boolean
    {
        var value:Boolean;
        if (extContext) value=extContext.call("ShowCellularDataConfirmation",assetPack) as Boolean;
        return value;
    }

    //
    // Implementation
    //

    /** Init Context */
    private function initContext():void
    {
        //if(!extContext && isSupported)
        if(isSupported)
        {
            extContext = ExtensionContext.createExtensionContext("com.harman.PlayAssetDelivery","");
            extContext.addEventListener(StatusEvent.STATUS, onStatusEvent);
        }
    }
	
    private function onStatusEvent(e : StatusEvent) : void
    {
        // forward to listeners
        if (hasEventListener(PlayAssetDeliveryEvent.PLAY_ASSET_UPDATE))
        {
            var newEvent : PlayAssetDeliveryEvent = new PlayAssetDeliveryEvent( new int(e.level), e.code );
            dispatchEvent(newEvent);
        }
    }

    public static function get isSupported():Boolean
    {
        var version:String = Capabilities.version;
        var subStr:String = version.substr(0,3);
        if(subStr=="AND"){
            return true;

        }else{
            return false;
        }

    }
}
}
