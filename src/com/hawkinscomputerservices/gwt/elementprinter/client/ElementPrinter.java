package com.hawkinscomputerservices.gwt.elementprinter.client;

import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

public class ElementPrinter {
	public ElementPrinter(Element e) {
		// fix form elements
		updateDOM(e);
		
		String doc = new String("<!DOCTYPE html>\n<html>\n<head>\n");
		com.google.gwt.dom.client.Element head = e.getOwnerDocument().getDocumentElement().getElementsByTagName("head").getItem(0);
		NodeList<com.google.gwt.dom.client.Element> elements = head.getElementsByTagName("link");
		if (elements != null) {
			for (int i = 0; i < elements.getLength(); i++) {
				doc = doc + elements.getItem(i).getString() + "\n";
			}
		}
				
		elements = head.getElementsByTagName("style");
		if (elements != null) {
			for (int i = 0; i < elements.getLength(); i++) {
				doc = doc + elements.getItem(i).getString() + "\n";
			}
		}
				
		doc = doc +"</head><body>"
				+DOM.toString(e)
				+"</body></html>";

		_initPrintFrame(doc);
	}
	
	public void ReplaceInputWithText() {
		NodeList<com.google.gwt.dom.client.Element> elements = GetPrintElement().getElementsByTagName("textarea");
		if (elements != null) {
			while (elements.getLength() > 0) {
				TextAreaElement ta = TextAreaElement.as(elements.getItem(0));
				Label l = new HTML(ta.getValue().replaceAll("(\r\n|\r|\n)", "<br />"));
				ta.getParentElement().replaceChild(l.getElement(), ta);
			}
		}
		elements = GetPrintElement().getElementsByTagName("input");
		int skip = 0;
		if (elements != null) {
			while (elements.getLength() > skip) {
				InputElement ie = InputElement.as(elements.getItem(0));
				if (!ie.getType().equals("checkbox") && !ie.getType().equals("radio")) {
					Label l = new HTML(ie.getValue());
					ie.getParentElement().replaceChild(l.getElement(), ie);
				}
				else {
					skip++;
				}
			}
		}
		elements = GetPrintElement().getElementsByTagName("select");
		if (elements != null) {
			while (elements.getLength() > 0) {
				SelectElement se = SelectElement.as(elements.getItem(0));
				Label l = new HTML(se.getOptions().getItem(se.getSelectedIndex()).getInnerText());
				se.getParentElement().replaceChild(l.getElement(), se);
			}
		}
	}
	
	private void updateDOM(Element e) {
		NodeList<com.google.gwt.dom.client.Element> elements = e.getElementsByTagName("textarea");
		if (elements != null) {
			for(int i = 0; i < elements.getLength(); i++) {
				TextAreaElement ta = TextAreaElement.as(elements.getItem(i));
				ta.setDefaultValue(ta.getValue());
				ta.setInnerText(ta.getValue());
			}
		}
		
		elements = e.getElementsByTagName("input");
		if (elements != null) {
			for(int i = 0; i < elements.getLength(); i++) {
				InputElement ie = InputElement.as(elements.getItem(i));
				try {
		            ie.setDefaultValue(ie.getValue());
		        } finally {}
		        try {
		            ie.setDefaultChecked(ie.isChecked());
		        } finally {}
			}
		}
		
		elements = e.getElementsByTagName("option");
		if (elements != null) {
			for(int i = 0; i < elements.getLength(); i++) {
				OptionElement oe = OptionElement.as(elements.getItem(i));
				oe.setDefaultSelected(oe.isSelected());
			}
		}
		
	}

	public com.google.gwt.dom.client.Element GetPrintElement() {
		return IFrameElement.as(DOM.getElementById("__printerFrame")).getContentDocument().getDocumentElement();
	}

	private native void _initPrintFrame(String html)/*-{
    var frame = $doc.getElementById('__printerFrame');
    if (!frame) {
        $wnd.alert("Error: Can't find printing frame.");
        return;
    }
    var doc = frame.contentWindow.document;
    doc.open();
    doc.write(html);
    doc.close();
	}-*/;
	
	public native void Print() /*-{
    	var frame = $doc.getElementById('__printerFrame');
    	frame = frame.contentWindow;
    	frame.focus();
    	frame.print();
	}-*/;
}
