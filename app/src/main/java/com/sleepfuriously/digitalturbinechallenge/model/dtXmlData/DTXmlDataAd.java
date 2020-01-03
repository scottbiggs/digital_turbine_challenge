package com.sleepfuriously.digitalturbinechallenge.model.dtXmlData;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Describes the primary data element in the Digital Turbine
 * data.
 *
 * An "ad" seems to appear on the 2nd level, just below the root.
 * There may be lots and lots of 'em.
 */
@Root(name = "ad", strict = true)
public class DTXmlDataAd {

    @Element(name="appId")
    public String appId;

    @Element(name = "appPrivacyPolicyUrl", required = false)
    public String appPrivacyPolicyUrl;

    @Element(name="averageRatingImageURL")
    public String averageRatingImageURL;

    @Element(name="bidRate")
    public Float bidRate;

    @Element(name="billingTypeId")
    public Integer billingTypeId;

    @Element(name="callToAction")
    public String callToAction;

    @Element(name="campaignDisplayOrder")
    public Integer campaignDisplayOrder;

    @Element(name="campaignId")
    public Integer campaignId;

    @Element(name="campaignTypeId")
    public Integer campaignTypeId;

    @Element(name="categoryName")
    public String categoryName;

    @Element(name="clickProxyURL")      // may cause probs
    public String clickProxyURL;

    @Element(name="creativeId")
    public Integer creativeId;

    @Element(name="externalMetadata")   // this may cause errors as it has a boolean tag as well as a URL
    public DTXmlDataExternalMetaData externalMetadata;

    @Element(name="homeScreen")
    public Boolean homeScreen;

    @Element(name="impressionTrackingURL")  // also may cause probs
    public String impressionTrackingURL;

    @Element(name="isRandomPick")
    public Boolean isRandomPick;

    @Element(name="numberOfRatings")
    public String numberOfRatings;     // looks like an Integer, but can have plus signs too

    @Element(name="productDescription")
    public String productDescription;

    @Element(name = "minOSVersion", required = false)
    public String minOSVersion;

    @Element(name="productId")
    public Integer productId;

    @Element(name="productName")
    public String productName;

    @Element(name="productThumbnail")
    public String productThumbnail;    // url to image

    @Element(name="rating")
    public Float rating;

    @Element(name="numberOfDownloads")
    public String numberOfDownloads;   // another integer with plus sign

    @Element(name="tstiEligible")
    public Boolean tstiEligible;

    @Element(name="stiEnabled")
    public Boolean stiEnabled;

    @Element(name="postInstallActions")
    public DTXmlDataPostInstallActions postInstallActions;  // contains no data


}
