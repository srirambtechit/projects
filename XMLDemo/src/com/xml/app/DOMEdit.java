package com.xml.app;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
/**
 * SAX Implementation
 * @author ltadmin
 *
 */
public class DOMEdit {
    static public void main(String[] arg) {
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	dbf.setValidating(true);
	dbf.setNamespaceAware(true);
	dbf.setIgnoringElementContentWhitespace(true);

	Document doc = null;
	try {
	    DocumentBuilder builder = dbf.newDocumentBuilder();
	    builder.setErrorHandler(new MyErrorHandler());
	    InputSource is = new InputSource("persionWithDTD.xml");
	    doc = builder.parse(is);

	    importName(doc, doc);

	    write(doc);
	} catch (Exception e) {
	    System.err.println(e);
	}
    }

    public static void importName(Document doc1, Document doc2) {
	Element root1 = doc1.getDocumentElement();
	Element personInDoc1 = (Element) root1.getFirstChild();

	Node importedPerson = doc2.importNode(personInDoc1, true);

	Element root2 = doc2.getDocumentElement();
	root2.appendChild(importedPerson);
    }

    private static final String TAB = "    ";

    private static void write(Document doc) throws IOException {
	outputHeading(doc);
	outputElement(doc.getDocumentElement(), "");
    }

    private static void outputHeading(Document doc) {
	System.out.print("<?xml version=\"1.0\"");
	DocumentType doctype = doc.getDoctype();
	if (doctype != null) {
	    if ((doctype.getPublicId() == null) && (doctype.getSystemId() == null)) {
		System.out.println(" standalone=\"yes\"?>");
	    } else {
		System.out.println(" standalone=\"no\"?>");
	    }
	} else {
	    System.out.println("?>");
	}
    }

    private static void outputElement(Element node, String indent) {
	System.out.print(indent + "<" + node.getTagName());
	NamedNodeMap nm = node.getAttributes();
	for (int i = 0; i < nm.getLength(); i++) {
	    Attr attr = (Attr) nm.item(i);
	    System.out.print(" " + attr.getName() + "=\"" + attr.getValue() + "\"");
	}
	System.out.println(">");
	NodeList list = node.getChildNodes();
	for (int i = 0; i < list.getLength(); i++)
	    outputloop(list.item(i), indent + TAB);
	System.out.println(indent + "</" + node.getTagName() + ">");
    }

    private static void outputText(Text node, String indent) {
	System.out.println(indent + node.getData());
    }

    private static void outputCDATASection(CDATASection node, String indent) {
	System.out.println(indent + node.getData());
    }

    private static void outputComment(Comment node, String indent) {
	System.out.println(indent + "<!-- " + node.getData() + " -->");
    }

    private static void outputProcessingInstructionNode(ProcessingInstruction node, String indent) {
	System.out.println(indent + "<?" + node.getTarget() + " " + node.getData() + "?>");
    }

    private static void outputloop(Node node, String indent) {
	switch (node.getNodeType()) {
	    case Node.ELEMENT_NODE:
		outputElement((Element) node, indent);
		break;
	    case Node.TEXT_NODE:
		outputText((Text) node, indent);
		break;
	    case Node.CDATA_SECTION_NODE:
		outputCDATASection((CDATASection) node, indent);
		break;
	    case Node.COMMENT_NODE:
		outputComment((Comment) node, indent);
		break;
	    case Node.PROCESSING_INSTRUCTION_NODE:
		outputProcessingInstructionNode((ProcessingInstruction) node, indent);
		break;
	    default:
		System.out.println("Unknown node type: " + node.getNodeType());
		break;
	}
    }
}

class MyErrorHandler implements ErrorHandler {
    public void warning(SAXParseException e) throws SAXException {
	show("Warning", e);
	throw (e);
    }

    public void error(SAXParseException e) throws SAXException {
	show("Error", e);
	throw (e);
    }

    public void fatalError(SAXParseException e) throws SAXException {
	show("Fatal Error", e);
	throw (e);
    }

    private void show(String type, SAXParseException e) {
	System.out.println(type + ": " + e.getMessage());
	System.out.println("Line " + e.getLineNumber() + " Column " + e.getColumnNumber());
	System.out.println("System ID: " + e.getSystemId());
    }
}
