/* Generated by Together */

package com.neocoders.vglviewer.vglDocument.utils;

import java.io.Reader;
import java.io.IOException;
import net.n3.nanoxml.IXMLBuilder;

import java.util.StringTokenizer;

class VGLXMLBuilder implements IXMLBuilder {
    public VGLXMLBuilder(VGLDocumentBuilder docBuilder, ErrorHandler errorHandler) {
        this.docBuilder = docBuilder;
        this.errorHandler = errorHandler;
        lineNr = 0;
    }

    public void startBuilding(String systemID, int lineNr) {
        docBuilder.newDocument();
    }

    public void newProcessingInstruction(String target, Reader reader) throws IOException {
    }

    public void startElement(String name, String nsPrefix, String nsSystemID, String systemID, int lineNr) {
        //    System.out.println("VGLXMLBuilder: starting element '" + name + "' at line " + lineNr);
        this.lineNr = lineNr;
        try {
            if (name.equals("vgl")) {
                //   docBuilder.newDocument();
            }
            if (name.equals("styleDef")) {
                docBuilder.openStyle();
            }
            else if (name.equals("polygon")) {
                docBuilder.openPolygon();
            }
            else if (name.equals("path")) {
                docBuilder.openPath();
            }
            else if (name.equals("circle")) {
                docBuilder.openCircle();
            }
            else if (name.equals("text")) {
                docBuilder.openText();
            }
        } catch (Exception e) {
            errorHandler.handleError(e.getMessage() + ", Line=" + lineNr);
        }
    }

    public void endElement(String name, String nsPrefix, String nsSystemID) {
        //  System.out.println("VGLXMLBuilder: ending element '" + name + "'");
        if (!name.equals("vgl")) {
            docBuilder.close();
        }
    }

    public void addAttribute(String key, String nsPrefix, String nsSystemID, String value, String type) {
        //  System.out.println("VGLXMLBuilder: adding attribute '" + key + "'");
        try {
            if (key.equals("name")) {
                docBuilder.setName(value);
            }
            if (key.equals("style")) {
                parseStyle(value);
            }
            if (key.equals("text")) {
                docBuilder.setText(value);
            }
            else if (key.equals("points")) {
                parsePoints(value);
            }
            else if (key.equals("origin")) {
                parseOrigin(value);
            }
            else if (key.equals("centre")) {
                parseCentre(value);
            }
            else if (key.equals("styleRef")) {
                docBuilder.applyStyle(value);
            }
        } catch (Exception e) {
            errorHandler.handleError(e.getMessage() + ", Line=" + lineNr);
        }
    }

    private void parseStyle(String styleStr) throws Exception {
        /* Some default font values; these will be modified if any vales are parsed for them.
        */

        String fontFamily = "helvetica";
        int fontStyle = 0;
        int fontSize = 12;
        StringTokenizer strTok = new StringTokenizer(styleStr, ";");
        while (strTok.hasMoreTokens()) {
            StringTokenizer strTok2 = new StringTokenizer(strTok.nextToken(), ":");
            String key = strTok2.nextToken().trim();
            String value = strTok2.nextToken().trim();
            if (key.equals("fill")) {
                if (!value.equals("none")) {
                    parseFillColor(value);
                }
            }
            else if (key.equals("stroke")) {
                if (!value.equals("none")) {
                    parseStrokeColor(value);
                }
            }
            else if (key.equals("font-family")) {
                if (!value.equals("none")) {
                    fontFamily = value;
                }
            }
            else if (key.equals("font-style")) {
                try {
                    fontStyle = Integer.parseInt(value); // just using this to validate integer format
                }
                catch (NumberFormatException nfe) {
                    throw new Exception("font-style not a valid integer: " + value);
                }
            }
            else if (key.equals("font-size")) {
                try {
                    fontSize = Integer.parseInt(value); // just using this to validate integer format
                }
                catch (NumberFormatException nfe) {
                    throw new Exception("font-size not a valid integer: " + value);
                }
            }
            else {
                throw new Exception("unrecognised style element: " + key);
            }
        }
        docBuilder.setFont(fontFamily, fontStyle, fontSize);
    }

    public void parseFillColor(String value) throws Exception {
        if (value.length() != 7) {
            throw new Exception("malformed fill color value: expected 7 characters in '" + value + "'");
        }
        if (value.charAt(0) != '#') {
            throw new Exception("malformed fill color value: '#' expected: '" + value + "'");
        }
        try {
            docBuilder.setFillColor(Integer.parseInt(value.substring(1, 3), 16), Integer.parseInt(value.substring(3, 5), 16),
                Integer.parseInt(value.substring(5, 7), 16));
        } catch (NumberFormatException e) {
            throw new Exception("fill color not valid hex value: '" + value + "'");
        }
    }

    public void parseStrokeColor(String value) throws Exception {
        if (value.length() != 7) {
            throw new Exception("malformed stroke color value: expected 7 characters in '" + value + "'");
        }
        if (value.charAt(0) != '#') {
            throw new Exception("malformed stroke color value: '#' expected: '" + value + "'");
        }
        try {
            docBuilder.setStrokeColor(Integer.parseInt(value.substring(1, 3), 16), Integer.parseInt(value.substring(3, 5), 16),
                Integer.parseInt(value.substring(5, 7), 16));
        } catch (NumberFormatException e) {
            throw new Exception("stroke color not valid hex value: '" + value + "'");
        }
    }

    public void parsePoints(String value) throws Exception {
        StringTokenizer strTok = new StringTokenizer(value, " ");
        int nPoints = strTok.countTokens();
        if (nPoints == 0) {
            throw new Exception("points empty");
        }
        String point;
        while (strTok.hasMoreTokens()) {
            point = (String)strTok.nextToken();
            StringTokenizer strTok2 = new StringTokenizer(point, ",");
            try {
                docBuilder.addVertex((long)Double.parseDouble(strTok2.nextToken()),
                    (long)Double.parseDouble(strTok2.nextToken()));
            }
            catch (NumberFormatException nfe) {
                throw new Exception("point(s) not valid double-precision value");
            }
        }
    }

    public void parseOrigin(String value) throws Exception {
        StringTokenizer strTok2 = new StringTokenizer(value, ",");
        try {
            docBuilder.setOrigin((long)Double.parseDouble(strTok2.nextToken()), (long)Double.parseDouble(strTok2.nextToken()));
        }
        catch (NumberFormatException nfe) {
            throw new Exception("origin element(s) not valid double-precision value");
        }
    }

    public void parseCentre(String value) throws Exception {
        StringTokenizer strTok2 = new StringTokenizer(value, ",");
        try {
            docBuilder.setCentre((long)Double.parseDouble(strTok2.nextToken()), (long)Double.parseDouble(strTok2.nextToken()));
        }
        catch (NumberFormatException nfe) {
            throw new Exception("centre element(s) not valid double-precision value");
        }
    }

    public void elementAttributesProcessed(String name, String nsPrefix, String nsSystemID) {
    }

    public void addPCData(Reader reader, String systemID, int lineNr) throws IOException {
    }

    public Object getResult() {
        return null;
    }

    private VGLDocumentBuilder docBuilder;

    /**
     * @supplierCardinality 1
     * @clientCardinality 1 
     */
    private ErrorHandler errorHandler;
    private int lineNr;
}
