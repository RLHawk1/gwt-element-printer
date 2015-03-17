#Setting up your GWT project to use gwt-element-printer

# Getting Started #
  1. Add the following module import to your GWT module:
```
   <inherits name="com.hawkinscomputerservices.gwt.elementprinter.ElementPrinter" />
```

  1. Add gwt-element-printer-x.x.jar to your project build path
  1. Add the following iframe to your host page:
```
   <iframe id="__printerFrame" style="width:0;height:0;border:0"></iframe>
```

  1. Use the following to print an element.

```
   // Setup the element printer with the element you want to print.
   // This automatically copies any stylesheets and style elements
   // in the header.
   ElementPrinter ep = new ElementPrinter(RootPanel.getBodyElement());

   // Optionally replace text areas, text inputs and select boxes with text.
   // Useful if you have textareas with scrollbars that you'd like expanded.
   // Also fixes problems with input fields sometimes getting split across
   // multiple pages in the middle of a line of text.
   ep.ReplaceInputWithText();

   // Optionally make further changes before printing.
   ep.GetPrintElement().getLastChild().removeFromParent();

   // Print the modified element.
   ep.Print();
```