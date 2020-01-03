package com.sleepfuriously.digitalturbinechallenge.model.dtXmlData;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Top-level root data.  Everything is this essentially (the God class
 * of the xml data, hehe).
 */
@Root(name = "ads", strict = true)         // root is denoted by this
public class DTXmlDataRoot {

    @ElementList(inline = true, name = "ad")
    public List<DTXmlDataAd> adList;

    @Element
    public String responseTime;

    @Element
    public Integer totalCampaignsRequested;

    @Element
    public String version;

}
