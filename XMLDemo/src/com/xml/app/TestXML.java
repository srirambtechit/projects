package com.xml.app;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TestXML {

    public static void main(String[] args) {
	String filename = "D:\\Sriram\\demo.xml";
	try {
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    Document doc = builder.newDocument();
	    Element element = doc.createElement("lines");

	    Element testTag = doc.createElement("test");
	    testTag.setTextContent("Testing");
	    element.appendChild(testTag);

	    TransformerFactory tFactory = TransformerFactory.newInstance();
	    Transformer t = tFactory.newTransformer();
	    DOMSource src = new DOMSource(element);
	    StreamResult sr = new StreamResult(new File(filename));
	    t.transform(src, new StreamResult(System.out));
	} catch (ParserConfigurationException | TransformerException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
