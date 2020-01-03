package com.sleepfuriously.digitalturbinechallenge.model.dtXmlData;

import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Holds the contents of the "externalMetadata" element
 */
@Root(name = "externalMetadata", strict = false)
public class DTXmlDataExternalMetaData implements Serializable {

    // todo
//    @Attribute(name = "xmlns:xsi")
//    String xsiUrl;

    // todo
    /** TRUE means xsi is Nil */
//    @Attribute(name = "xsi")
//    Boolean xsiNil;

}
