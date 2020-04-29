package com.organization.api.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

//import com.deloitte.api.soap.Scripts.TestCaseProp;
import com.predic8.wsdl.AbstractSOAPBinding;
import com.predic8.wsdl.Definitions;
import com.predic8.wsdl.WSDLParser;
import com.predic8.wstool.creator.RequestTemplateCreator;
import com.predic8.wstool.creator.SOARequestCreator;

import groovy.xml.MarkupBuilder;

public class SoapUtils extends SoapConstants {

	public static List<String> soapAction = new ArrayList<String>();
	public static String nameSpace = null;
	public static String serviceEndPoint = null;
	public static String portType = null;
	public static String ops = null;
	public static String binding = null;
	public static WSDLParser parser = new WSDLParser();
	public static Definitions defs;

	public static Map<String, String> requestAttributeVariables = null;
	public static Map<String, String> environmentVariables = null;
	public static Map<String, String> responseAttributeValues = null;

	public static Map<String, String> requestsBindingOps = new HashMap<>();

	// String endpoint = "http://www.freewebservicesx.com/GetGoldPrice.asmx";
	static DocumentBuilderFactory docFactory1 = DocumentBuilderFactory.newInstance();
	static DocumentBuilder docBuilder1;
	static Document doc1;

	/* print console and testng reporter logs */

	public static void out(String str) {
		System.out.println(str);
		Reporter.log("<p>" + str + "<p>");
	}

	/*
	 * Create soap request templates , by reading wsdl and endpoint from soap
	 * constants
	 */

	public static void createRequestXMLTemplatefromWsdl(String soapWsdl) {

		defs = parser.parse(soapWsdl);

		out("-------------- WSDL Details --------------");
		out("TargenNamespace: \t" + defs.getTargetNamespace());
		nameSpace = defs.getTargetNamespace();
		// out("Style: \t\t\t" + defs.getStyle());
		if (defs.getDocumentation() != null) {
			out("Documentation: \t\t" + defs.getDocumentation());
		}
		out("\n");

		// For detailed schema information see the FullSchemaParser.java sample.
		out("Schemas: ");
		for (com.predic8.schema.Schema schema : defs.getSchemas()) {
			out("  TargetNamespace: \t" + schema.getTargetNamespace());
		}
		out("\n");

		out("Messages: ");
		for (com.predic8.wsdl.Message msg : defs.getMessages()) {
			out("  Message Name: " + msg.getName());
			out("  Message Parts: ");
			for (com.predic8.wsdl.Part part : msg.getParts()) {
				out("    Part Name: " + part.getName());
				out("    Part Element: " + ((part.getElement() != null) ? part.getElement() : "not available!"));
				out("    Part Type: " + ((part.getType() != null) ? part.getType() : "not available!"));
				out("");
			}
		}
		out("");

		out("PortTypes: ");
		for (com.predic8.wsdl.PortType pt : defs.getPortTypes()) {
			out("  PortType Name: " + pt.getName());
			portType = pt.getName(); //// Get Port Type to construct Request
										//// Template
			out("  PortType Operations: ");
			for (com.predic8.wsdl.Operation op : pt.getOperations()) {
				out("    Operation Name: " + op.getName());
				out("    Operation Input Name: "
						+ ((op.getInput().getName() != null) ? op.getInput().getName() : "not available!"));
				out("    Operation Input Message: " + op.getInput().getMessage().getQname());
				out("    Operation Output Name: "
						+ ((op.getOutput().getName() != null) ? op.getOutput().getName() : "not available!"));
				out("    Operation Output Message: " + op.getOutput().getMessage().getQname());
				out("    Operation Faults: ");
				if (op.getFaults().size() > 0) {
					for (com.predic8.wsdl.Fault fault : op.getFaults()) {
						out("      Fault Name: " + fault.getName());
						out("      Fault Message: " + fault.getMessage().getQname());
					}
				} else
					out("      There are no faults available!");

			}
			out("");
		}
		out("");

		out("Bindings: ");
		for (com.predic8.wsdl.Binding bnd : defs.getBindings()) {
			out("  Binding Name: " + bnd.getName());
			binding = bnd.getName(); // Get Binding Name to construct Request
										// Template
			out("  Binding Type: " + bnd.getPortType().getName());
			out("  Binding Protocol: " + bnd.getBinding().getProtocol());

			if (bnd.getBinding() instanceof AbstractSOAPBinding)
				out("  Style: " + (((AbstractSOAPBinding) bnd.getBinding()).getStyle()));
			out("  Binding Operations: ");
			for (com.predic8.wsdl.BindingOperation bop : bnd.getOperations()) {
				out("    Operation Name: " + bop.getName());
				ops = bop.getName(); // Get Operation Name to construct Request
										// Template
				if (bnd.getBinding() instanceof AbstractSOAPBinding) {
					out("    Operation SoapAction: " + bop.getOperation().getSoapAction());
					soapAction.add(bop.getOperation().getSoapAction());
					out("    SOAP Body Use: " + bop.getInput().getBindingElements().get(0).getUse());
				}

				try {
					createRequestXmlTemplates(portType, ops, binding);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			out("");
		}
		out("");
		out("Services: ");
		for (com.predic8.wsdl.Service service : defs.getServices()) {
			out("  Service Name: " + service.getName());
			out("  Service Potrs: ");
			for (com.predic8.wsdl.Port port : service.getPorts()) {
				out("    Port Name: " + port.getName());
				out("    Port Binding: " + port.getBinding().getName());
				out("    Port Address Location: " + port.getAddress().getLocation() + "\n");
				serviceEndPoint = port.getAddress().getLocation(); //// Get
																	//// Service
																	//// End
																	//// Point
																	//// from
																	//// Wsdl
			}
		}
		out("");
	}

	/* Create Requests by Reading Test Case data from Excel sheet */

	public static void createRequestXmlTemplates(String portType, String ops, String binding) {

		try {

			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();

			StringWriter writer = new StringWriter();

			// SOAPRequestCreator constructor: SOARequestCreator(Definitions,
			// Creator, MarkupBuilder)
			SOARequestCreator creator = new SOARequestCreator(defs, new RequestTemplateCreator(),
					new MarkupBuilder(writer));
			// creator.createRequest(PortType name, Operation name, Binding
			// name);
			creator.createRequest(portType, ops, binding);

			out(writer.toString());

			// deloitte.usdc.soap.utilities.SoapRequestTemplates
			PrintWriter pw = new PrintWriter(SOAP_REQUEST_TEMPLATE_PATH + binding + "_" + ops + ".xml");
			pw.write(writer.toString());
			pw.close();
			pw.flush();
			requestsBindingOps.put(binding, ops);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			out("createRequestXmlTemplates NOT CREATED .." + portType + ops + binding);
		}

	}

	/* Execute the requests created above and Save the Response */

	public static void getSoapResponse(String requestXmlName, String testCaseName, String serviceEndPoint)
			throws ClientProtocolException, IOException, ParserConfigurationException, SAXException,
			TransformerException {
		String reqFile = SOAP_REQUEST_XML_PATH + requestXmlName + "_" + testCaseName + ".xml";

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc1);
		StreamResult result = new StreamResult(new File(reqFile));
		transformer.transform(source, result);
		out("Done");

		try {
			HttpClient cilent = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(serviceEndPoint);
			post.setEntity(new InputStreamEntity(new FileInputStream(new File(reqFile))));
			post.setHeader("Content-type", "text/xml");
			HttpResponse response = cilent.execute(post);
			System.out.println(response.getStatusLine().getStatusCode());
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			PrintWriter pw = new PrintWriter(SOAP_RESPONSE_XML_PATH + requestXmlName + "_" + testCaseName + ".xml");
			pw.write(sb.toString());
			pw.close();
			pw.flush();
		} catch (Exception e) {
			System.out.println("Did Not create Response");
		}

	}

	/*
	 * Used to create Soap Request above by updating values read from Excel
	 * Request Sheet
	 */

	/*public static void updateEleVal(TestCaseProp testCaseProp, String requestTemplateFilePath)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {

		try {
			File requestFile1 = new File(requestTemplateFilePath);
			out(requestFile1.getAbsolutePath());
			docBuilder1 = docFactory1.newDocumentBuilder();
			doc1 = docBuilder1.parse(requestFile1);
			NodeList allnodes = doc1.getElementsByTagName(testCaseProp.getComponent());
			for (int i = 0; i < allnodes.getLength(); i++) {
				NodeList alltags = doc1.getElementsByTagName(testCaseProp.getTagName());
				for (int j = 0; j < alltags.getLength(); j++) {
					Node n = alltags.item(j);
					out(n.getNodeName());
					if (n.getParentNode().getNodeName().equals(testCaseProp.getParentTagName())) {
						n.setTextContent(testCaseProp.getTagValue());
						out(n.getTextContent());
					}
				}
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc1);
			StreamResult result = new StreamResult(requestFile1);
			transformer.transform(source, result);

			out(testCaseProp.getTagName() + " Tag Value Updated :" + testCaseProp.getTagValue());
		} catch (Exception e) {
			System.out.println(e);
		}
	}*/

	/* Move files from Request Template to Request folders */

	public static void moveFiles(String requestXmlTemplate, String testCaseName) {

		String requestXmlUpdated = SoapConstants.SOAP_REQUEST_TEMPLATE_PATH + requestXmlTemplate + ".xml";

		String reqFileNew = SOAP_REQUEST_XML_PATH + requestXmlTemplate + "_" + testCaseName + ".xml";
		// System.out.println(requestFile1.getAbsolutePath());

		try {
			File afile = new File(requestXmlUpdated);
			File bfile = new File(reqFileNew);

			FileInputStream inStream = new FileInputStream(afile);
			FileOutputStream outStream = new FileOutputStream(bfile);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = inStream.read(buffer)) > 0) {

				outStream.write(buffer, 0, length);

			}

			inStream.close();
			outStream.close();

			System.out.println("File is copied successful!");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * Create Response Values by Reading Tags and Values from Excel Response
	 * sheer
	 */

	/*public static void verifyTagValue(TestCaseProp testCaseProp, File responseFilePath)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {

		SoftAssert softAssertion = new SoftAssert();

		File responseFile = responseFilePath;

		DocumentBuilderFactory docFactory1 = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder1 = docFactory1.newDocumentBuilder();
		Document doc1 = docBuilder1.parse(responseFile);

		NodeList allnodes = doc1.getElementsByTagName(testCaseProp.getComponent());

		for (int i = 0; i < allnodes.getLength(); i++) {

			NodeList alltags = doc1.getElementsByTagName(testCaseProp.getTagName());

			for (int j = 0; j < alltags.getLength(); j++) {

				Node n = alltags.item(j);
				out(n.getNodeName());
				if (n.getParentNode().getNodeName().equals(testCaseProp.getParentTagName())) {
					out(n.getTextContent());
					softAssertion.assertEquals(n.getTextContent().toString(), testCaseProp.getTagValue());
				} else {

					out(n.getParentNode().getNodeName().equals(testCaseProp.getParentTagName()) + "Tag Not Found ...");
				}

			}

		}
		softAssertion.assertAll();
		out("Done");
	}*/

	/*
	 * Execute the request for the Request Xml Files and Excel Request Sheet
	 * names
	 */

	/*public static void executeSoapRequest(String requestXmlName, String wsdl, String soapEndPoint, String testCaseName,
			HashMap<String, List<TestCaseProp>> testCaseProp, String requestTemplateFilePath) {

		List<TestCaseProp> testCasePropObj = testCaseProp.get(testCaseName);

		try {

			SoapUtils.createRequestXMLTemplatefromWsdl(wsdl);

			testCasePropObj.forEach(TestCaseProp -> {
				try {
					SoapUtils.updateEleVal(TestCaseProp, requestTemplateFilePath);
				} catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
					System.out.println(e.getMessage());
				}
			});

			SoapUtils.moveFiles(requestXmlName, testCaseName);
			SoapUtils.getSoapResponse(requestXmlName, testCaseName, soapEndPoint);
			SoapUtils.createRequestXMLTemplatefromWsdl(wsdl);

		} catch (Exception e) {
			System.out.println("HERE..." + e);
		}

	}*/

	/* Validate the Response Xml Files and Excel Response Sheet names */

	/*public static void validateSoapResponse(String responseFile, String testCaseName,
		HashMap<String, List<TestCaseProp>> testCaseProp) {

		List<TestCaseProp> testCasePropObj = testCaseProp.get(testCaseName);
		File responseFilePath = new File(responseFile.replace("?!$&", testCaseName));
		try {

			testCasePropObj.forEach(TestCaseProp -> {
				try {
					SoapUtils.verifyTagValue(TestCaseProp, responseFilePath);
				} catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
					System.out.println(e.getMessage());
				}
			});
			
		} catch (Exception e) {
			System.out.println("HERE..." + e);
		}

	}*/

}
