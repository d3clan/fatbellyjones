package com.viviquity.readmy.bean;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class PlaylistBean {

    private static final Logger logger = Logger.getLogger(PlaylistBean.class);

    private Long eventId;
    private Long tuneId;
    private Integer position;
    private String serializedOrder;
    private String className;

    /**
     * @return the eventId
     */
    public Long getEventId() {
	return eventId;
    }

    /**
     * @param eventId
     *            the eventId to set
     */
    public void setEventId(Long eventId) {
	this.eventId = eventId;
    }

    /**
     * @return the tuneId
     */
    public Long getTuneId() {
	return tuneId;
    }

    /**
     * @param tuneId
     *            the tuneId to set
     */
    public void setTuneId(Long tuneId) {
	this.tuneId = tuneId;
    }

    /**
     * @return the serializedOrder
     */
    public String getSerializedOrder() {
	return serializedOrder;
    }

    /**
     * @param serializedOrder
     *            the serializedOrder to set
     */
    public void setSerializedOrder(String serializedOrder) {
	this.serializedOrder = serializedOrder;
    }

    /**
     * @return the position
     */
    public Integer getPosition() {
	return position;
    }

    /**
     * @param position
     *            the position to set
     */
    public void setPosition(Integer position) {
	this.position = position;
    }

    /**
     * @return the tuneIds
     */
    public Map<Integer, Long> getTuneIds() {
	Map<Integer, Long> map = new HashMap<Integer, Long>();
	try {
	    if (serializedOrder != null) {
		String[] bits = serializedOrder.split("&");
		for (int i = 0; i < bits.length; i++) {
		    String id = bits[i].split("=")[1];
		    Long lo = new Long(id);
		    map.put(i + 1, lo);
		}
	    }
	} catch (Exception e) {
	    logger.warn("Cannot reorder items");
	}
	return map;
    }

    /**
     * @return the className
     */
    public String getClassName() {
	return className;
    }

    /**
     * @param className
     *            the className to set
     */
    public void setClassName(String className) {
	this.className = className;
    }

}
