package com.sleepfuriously.digitalturbinechallenge.model;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

/**
 * Simple parser for xml.  It uses a String and returns elements built along it.
 */
public class MyXmlParser {

    //-------------------------
    //  constants
    //-------------------------

    private static final String TAG = MyXmlParser.class.getSimpleName();

    /** The different types of tags */
    public enum TagType {
        START, END, EMPTY_ELEMENT
    }

    //-------------------------
    //  data
    //-------------------------


    //-------------------------
    //  methods
    //-------------------------


    /**
     * Figures out the number of top-level components in this xml string.
     *
     * @param xmlString     A string that should be a proper xml format.
     *
     * @return  The number of components in the top level of this xml string.
     *          -1 if the string is not properly formatted.
     */
    public static int getNumComponents(String xmlString) {
        int numComponents = -1;

//        String startTag = getFirsTag(xmlString);

        // todo
        return 0;
    }


    /**
     * Finds the first markup section of the given string.
     * Returns null if none or the given string isn't well formed.
     *
     * A markup are everything within '<' and '>'.  Slashes are ignored.
     */
    public static String getFirsTag(String xmlString) {

        int start = xmlString.indexOf('<');
        if (start == -1) {
            Log.e(TAG, "cannot find a '<' in getFirsTag");
            return null;
        }
        start++;  // increment so it points to the next char, the


        int end = xmlString.indexOf('>');
        if ((end == -1) || (end <= start)) {
            Log.e(TAG, "problem with finding '>' in getFirsTag");
            return null;
        }

        String markup = xmlString.substring(start, end);

        if (markup.length() < 1) {
            Log.e(TAG, "markup too short in getFirsTag()");
            return null;
        }
        return markup;
    }

    /**
     * Figures out what kind of tag we're looking at.
     *
     * @param tag   The result from calling {@link #getFirsTag(String)} or some other
     *              similar method.
     *
     * @return  And enum defining what type of tag we have.
     */
    public static TagType getTypeOfTag(String tag) {

        if (tag.charAt(0) == '/') {
            return TagType.END;
        }
        if (tag.charAt(tag.length() - 1) == '/') {
            return TagType.EMPTY_ELEMENT;
        }
        return TagType.START;
    }

    /**
     * Returns the first Content section found in the given xmlString.
     * Note that the <i>content</i> is everything between the FIRST '>'
     * and the following '<' sign.
     *
     * Null on error.
     */
    public static String getFirstContent(String xmlString) {
        int start = xmlString.indexOf('>');
        if (start == -1) {
            Log.e(TAG, "can't find '>' to start in getFirstContent()");
            return null;
        }

        int end = xmlString.indexOf('<', start);
        if (end == -1) {
            Log.e(TAG, "can't find '<' to end in getFirstContent()");
            return null;
        }

        if (end == start + 1) {
            // empty string
            return "";
        }

        String firstContent = xmlString.substring(start + 1, end);
        if (firstContent.length() < 0) {
            Log.e(TAG, "illegal length in getFirstContent()");
            return null;
        }
        return firstContent;
    }

    /**
     * A start-tag begins with a '<' and ends with a '>' with NO SLASHES
     * to the right of either character.
     *
     * @param xmlString
     * @return
     */
    public static String getFirstStartTag(String xmlString) {
        // todo
        return null;
    }

}
