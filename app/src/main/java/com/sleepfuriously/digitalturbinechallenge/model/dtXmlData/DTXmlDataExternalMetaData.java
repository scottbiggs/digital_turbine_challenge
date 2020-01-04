package com.sleepfuriously.digitalturbinechallenge.model.dtXmlData;

import androidx.annotation.NonNull;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Holds the contents of the "externalMetadata" element
 */
@Root(name = "externalMetadata", strict = false)
public class DTXmlDataExternalMetaData implements Serializable {

    // todo
//    @Attribute(name = "xmlns:xsi")
    String xsiUrl;

    // todo
    /** TRUE means xsi is Nil */
//    @Attribute(name = "xsi:nil")
    Boolean xsiNil;

    @NonNull
    @Override
    public String toString() {
        String str = "xsi: ";
        if (xsiNil == null) {
//            str += "null";    todo: correct this when SimpleXml bug is fixed.
            str += "true";
        }
        else {
            str += xsiNil;
        }

        str += ", xmlns:xsi: ";
        if (xsiUrl == null) {
//            str += "null";    todo: ibid
            str += "http://www.w3.org/2001/XMLSchema-instance";
        }
        else {
            str += xsiUrl;
        }
        return str;
    }
}
