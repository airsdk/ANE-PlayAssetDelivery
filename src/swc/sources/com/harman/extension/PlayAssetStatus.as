package com.harman.extension{

public class PlayAssetStatus {
    public static const UNKNOWN:int = 0;

    public static const PENDING:int = 1;

    public static const DOWNLOADING:int = 2;

    public static const TRANSFERRING:int = 3;

    public static const COMPLETED:int = 4;

    public static const FAILED:int = 5;

    public static const CANCELED:int = 6;

    public static const WAITING_FOR_WIFI:int = 7;

    public static const NOT_INSTALLED:int = 8;

   // public static const SUCCESS:int = 9;

    public function PlayAssetStatus() {
    }

    public static function getStatus(assetPackStatus:int):String {
        var status:String;
        switch (assetPackStatus) {
            case 0:
                status = "UNKNOWN";
                break;
            case 1:
                status = "PENDING";
                break;
            case 2:
                status = "DOWNLOADING";
                break;
            case 3:
                status = "TRANSFERRING";
                break;
            case 4:
                status = "COMPLETED";
                break;
            case 5:
                status = "FAILED";
                break;
            case 6:
                status = "CANCELED";
                break;
            case 7:
                status = "WAITING_FOR_WIFI";
                break;
            case 8:
                status = "NOT_INSTALLED";
                break;
 /*           case 9:
                status = "SUCCESS";
                break;*/
            default:
        }
        return  status;
    }
}
}