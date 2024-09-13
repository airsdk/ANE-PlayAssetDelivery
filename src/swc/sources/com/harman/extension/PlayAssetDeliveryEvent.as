package com.harman.extension{
import flash.events.Event;

//
// PlayAssetDeliveryEvent
//

/**
 * The PlayAssetDelivery AIR Native Extension dispatches a PlayAssetDeliveryEvent object when there is an update available from the Google Play service regarding an Asset Pack. There is only one type of Play Asset devliery event: <code>PlayAssetDeliveryEvent.PLAY_ASSET_UPDATE</code>.
 *
 * @playerversion AIR 33.1
 * @langversion 3.0
 *
 * @see com.harman.extension.PlayAssetDelivery
 */
public class PlayAssetDeliveryEvent extends Event
{

        // Constants for event types
        /**
         * Defines the value of the <code>type</code> property of a <code>PlayAssetDeliveryEvent</code> object.
         * <p>This event has the following properties:</p>
         * <table class="innertable" width="100%">
         *     <tr><th>Property</th><th>Value</th></tr>
         *     <tr><td><code>bubbles</code></td><td><code>false</code></td></tr>
         *     <tr><td><code>cancelable</code></td><td><code>false</code>; there is no default behavior to cancel.</td></tr>
         *     <tr><td><code>status</code></td><td>An enumeration value describing the Asset Pack's status.</td></tr>
         *     <tr><td><code>assetPackName</code></td><td>The name or ID of the Asset Pack to which this status relates.</td></tr>
         *     <tr><td><code>target</code></td><td>The object reporting its status (the <code>PlayAssetDelivery</code> extension object).</td></tr>
         * </table>
         *
         * @playerversion AIR 33.1
         * @langversion 3.0
         * @eventType playAssetUpdate
         */
    public static const PLAY_ASSET_UPDATE : String = "playAssetUpdate";

	// State
	private var m_status:uint;
	private var m_assetPackName:String;

	// Constructor
	/**
	 * Creates an Event object that contains information about Play Asset delivery events.
	 * Event objects are passed as parameters to event listeners.
	 *
	 * @playerversion AIR 33.1
	 * @langversion 3.0
	 *
	 * @param status An enumeration value describing the Asset Pack's status, one of the values from <code>PlayAssetStatus</code>. Event listeners can access this information through the <code>status</code> property.
	 * @param assetPackName The name or ID of the Asset PAck to which this status relates. Event listeners can access this information through the <code>assetPackName</code> property.
	 * @param type  The type of the event. Event listeners can access this information through the inherited <code>type</code> property. There is only one type of Play Asset delivery event: <code>PlayAssetDeliveryEvent.PLAY_ASSET_UPDATE</code>.
	 * @param bubbles Determines whether the Event object participates in the bubbling stage of the event flow. Event listeners can access this information through the inherited <code>bubbles</code> property.
	 * @param cancelable Determines whether the Event object can be canceled. Event listeners can access this information through the inherited <code>cancelable</code> property.
	 *
	 * @tiptext Constructor for PlayAssetDeliveryEvent objects.
	 * @see com.harman.extension.PlayAssetStatus
	 * @see com.harman.extension.PlayAssetDeliveryEvent#PLAY_ASSET_UPDATE
	 */
	public function PlayAssetDeliveryEvent(status:uint, assetPackName:String, type:String=PLAY_ASSET_UPDATE, bubbles:Boolean=false, cancelable:Boolean=false)
	{
		super(type, bubbles, cancelable);
		m_status = status;
		m_assetPackName = assetPackName;
	}

	// copy utility
	/**
	 * Creates a copy of the PlayAssetDeliveryEvent object and sets the value of each property to match that of the original.
	 *
	 * @playerversion AIR 33.1
	 * @langversion 3.0
	 *
	 * @return A new PlayAssetDeliveryEvent object with property values that match those of the original.
	 */
	public override function clone():Event
	{
		return new PlayAssetDeliveryEvent(m_status, m_assetPackName, type, bubbles, cancelable);
	}


	// toString override
	/**
	 * Returns a string that contains all the properties of the PlayAssetDeliveryEvent object. The string is in the following format:
	 * <p><code>[PlayAssetDeliveryEvent type=<i>value</i> bubbles=<i>value</i> cancelable=<i>value</i> status=<i>value</i> assetPackName=<i>value</i>]</code></p>
	 *
	 * @playerversion AIR 33.1
	 * @langversion 3.0
	 *
	 * @return A string that contains all the properties of the PlayAssetDeliveryEvent object.
	 */
	public override function toString():String
	{
		return formatToString("PlayAssetDeliveryEvent", "type", "bubbles", "cancelable", "status", "assetPackName");
	}


	// State accessors
	/**
	 * A description of the object's status (one of the values from <code>PlayAssetStatus</code>).
	 *
	 * @playerversion AIR 33.1
	 * @langversion 3.0
	 *
	 * @see com.harman.extension.PlayAssetStatus
	 */
	public function get status():uint{ return m_status; }

	/**
	 * The name or ID of the Asset Pack to which this status relates.
	 *
	 * @playerversion AIR 33.1
	 * @langversion 3.0
	 *
	 * @see com.harman.extension.PlayAssetDelivery
	 */
	public function get assetPackName():String { return m_assetPackName; }
}
}