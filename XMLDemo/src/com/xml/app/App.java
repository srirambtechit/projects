package com.xml.app;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class App {
    public static void main(String[] args) {
	String filename = "D:\\Sriram\\BUSI_1-LOG29.xml";
	try {
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    Document srcDoc = builder.parse(new File(filename));
	    String lineTag = "lines";
	    String logLineTag = "logLine";
	    String libraryNameTag = "libraryName";

	    NodeList logLineNodeList = srcDoc.getElementsByTagName(logLineTag);
	    System.out.println(logLineNodeList.getLength());

	    String startElement = null;
	    String endElement = null;
	    int counter = 0;

	    Document targetDoc = null;
	    Node parentNode = null;

	    for (int index = 0; index < logLineNodeList.getLength(); index++) {
		Node node = logLineNodeList.item(index);
		NodeList childNodes = node.getChildNodes();
		if (childNodes != null) {
		    Node innerNode = childNodes.item(0);
		    while (innerNode != null) {
			if (innerNode.getNodeType() == Node.ELEMENT_NODE) {
			    if (innerNode.getNodeName() != null && libraryNameTag.equals(innerNode.getNodeName())) {
				if (innerNode.getTextContent() != null && !innerNode.getTextContent().equals("") && startElement == null && endElement == null) {
				    startElement = endElement = innerNode.getTextContent();
				    targetDoc = builder.newDocument();
				    parentNode = targetDoc.createElement(lineTag);
				}
				if (startElement != null && endElement != null && startElement.equals(endElement)) {
				    counter++;

				    // // Create a duplicate node
				    // Node newNode = node.cloneNode(true);
				    // // Transfer ownership of the new node
				    // into the destination document
				    // outputDoc.adoptNode(newNode);
				    // // Make the new node an actual item in
				    // the target document
				    // outputNode.appendChild(newNode);

				    Node newNode = targetDoc.importNode(node, true);
				    parentNode.appendChild(newNode);
				}
				// System.out.println(innerNode.getTextContent());
				if (startElement != null && innerNode.getTextContent() != null && innerNode.getTextContent().equals("")) {
				    System.out.println(startElement + " : " + --counter);
				    createXMLFile(startElement + ".xml", parentNode);
				    startElement = endElement = null;
				    parentNode = null;
				    counter = 0;
				    break;
				}
			    }
			}
			innerNode = innerNode.getNextSibling();
		    }
		}
	    }
	} catch (ParserConfigurationException | SAXException | IOException e) {
	    e.printStackTrace();
	}
    }

    public static void createXMLFile(String filename, Node node) {
	filename = "D:\\Sriram\\" + filename;
	try {
	    TransformerFactory tFactory = TransformerFactory.newInstance();
	    Transformer t = tFactory.newTransformer();
	    DOMSource src = new DOMSource(node);
	    StreamResult stream = new StreamResult(new File(filename));
	    t.transform(src, stream);
	} catch (TransformerException e) {
	    e.printStackTrace();
	}
    }
}
