package com.deloitte.testng_hybrid_framework.captureObjects;

import static org.joox.JOOX.$;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.cyberneko.html.parsers.DOMParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class RepositoryGenerator {
	// Constants
	static final private String[] filteredTags = { "SCRIPT", "HTML", "HEAD", "BODY",
			"META", "OBJECT", "VAR", "BR", "STYLE" };
	static final private String[] wantedTags = { "A", "INPUT", "BUTTON", "TEXTAREA",
			"DIALOG", "FORM", "IMG", "MAP", "MENU", "MENUITEM", "SELECT",
			"VIDEO", "AUDIO" };
	static final private List<String> textTags = Arrays.asList("DIV", "SPAN", "B",
			"H1", "H2", "H3", "H4", "H6", "I", "LI", "OL", "P", "SMALL",
			"STRONG", "TABLE", "TD", "TFOOT", "TH", "THEAD", "TR", "U", "UL");
	
	// Utility variables
	private int count = 0;
	private Map<String, Integer> duplicateNames = new HashMap<String, Integer>();
	private Map<String, String> repositoryEntries = new HashMap<String, String>();
			 
	// Attributes
	private String repositoryName = "map.repository";
	private String url;
	private static String path = "./maps/";
	
	
	public static String getPath() {
		return path;
	}


	public static void setPath(String newPath) {
		path = newPath;
	}
	


	// Constructors
	public RepositoryGenerator() {}
	
	public RepositoryGenerator(String url, String repositoryName) {
		this.url = url;
		this.repositoryName = repositoryName;
	}

	
	// Setters and getters
	public String getRepositoryName() {
		return repositoryName;
	}


	public void setRepositoryName(String repositoryName) {
		this.repositoryName = repositoryName;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	/**
	 * Build a new repository for a page
	 * @param driver
	 */
	public void buildNewRepository() {
		buildNewRepository(url, repositoryName);
	}
	
	private void buildNewRepository(String url, String repositoryName) {
		try {		
			//ProfilesIni profile = new ProfilesIni();
			//FirefoxProfile ffprofile = profile.getProfile("default");
			System.setProperty("webdriver.gecko.driver", "C:\\Softwares\\Selenium\\geckodriver.exe");
			WebDriver driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			driver.get(url);
			
			DOMParser parser = new DOMParser();
			InputSource in = new InputSource(new StringReader(driver.getPageSource()));

			File dir = new File(path);
			dir.mkdirs();
			FileWriter writer = new FileWriter(path+repositoryName, false);
			
			
			duplicateNames = new HashMap<String, Integer>();
			repositoryEntries = new HashMap<String, String>();
			count = 0;
			
			writer.write("RepositoryName="+repositoryName+System.getProperty("line.separator"));
			writer.write("URL="+url+System.getProperty("line.separator"));
			
			parser.parse(in);
			buildRepository(parser.getDocument(), writer);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void buildNewRepository(WebDriver driver, String repositoryName) {
		try {
			DOMParser parser = new DOMParser();
			InputSource in = new InputSource(new StringReader(driver.getPageSource()));
			
			File dir = new File(path);
			dir.mkdirs();
			FileWriter writer = new FileWriter(path+repositoryName, false);
			
			duplicateNames = new HashMap<String, Integer>();
			repositoryEntries = new HashMap<String, String>();
			count = 0;
			writer.write("RepositoryName="+repositoryName+System.getProperty("line.separator"));
			writer.write("URL="+url+System.getProperty("line.separator"));
			parser.parse(in);
			buildRepository(parser.getDocument(), writer);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Update an exist repository
	 * @param driver
	 */
	public void updateRepository() {
		updateRepository(url, repositoryName);
	}
	
	private void updateRepository(String url, String repositoryName) {
		try {
			ProfilesIni profile = new ProfilesIni();
			FirefoxProfile ffprofile = profile.getProfile("default");
			WebDriver driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			driver.get(url);
			
			DOMParser parser = new DOMParser();
			InputSource in = new InputSource(new StringReader(driver.getPageSource()));
			FileWriter writer = new FileWriter(path+repositoryName, true);
			parser.parse(in);
			buildRepository(parser.getDocument(), writer);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void updateRepository(WebDriver driver, String repositoryName) {
		try {
			DOMParser parser = new DOMParser();
			InputSource in = new InputSource(new StringReader(driver.getPageSource()));
			FileWriter writer = new FileWriter(path+repositoryName, true);
			parser.parse(in);
			buildRepository(parser.getDocument(), writer);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Build repository starting from given node, recursively.
	 * @param node
	 * @param writer
	 */
	private void buildRepository(Node node, FileWriter writer) {
		if (!nodeIsFiltered(node)) {

			String result = buildRepositoryEntry(node);
			//System.out.println($((Element) node).tag());
			if(result != null)
				try {
					writer.write(result);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}// else System.out.println(node.getClass()());

		Node child = node.getFirstChild();
		while (child != null) {
			buildRepository(child, writer);
			child = child.getNextSibling();
		}
	}

	/**
	 * Construct an repository entry for a given node on DOM tree. 
	 * @param node
	 * @return repository entry, null if already exist in repository
	 */
	private String buildRepositoryEntry(Node node) {
		String result = null;
		String name = buildName((Element) node)+"_id";
		String entry = buildPropertyEntry(node);
		if(!repositoryEntries.containsKey(entry) || !repositoryEntries.get(entry).equals(name)) {
			repositoryEntries.put(entry, name);
			result = checkDuplicateName(name) +'='+ entry + System.getProperty("line.separator");
		}
		
		return result;
	}
	
	/**
	 * Build the actual property used to find that node
	 * @param node
	 * @return
	 */	
	private String buildPropertyEntry(Node node) {
		String result;
		if (!((Element) node).getAttribute("id").isEmpty())
			result = "Id:" + ((Element) node).getAttribute("id");
		else {
			result = "XPath:" + $((Element) node).xpath();
		}
		return result;
	}

	/**
	 * Check if a node has a text child node, which indicates this node has text.
	 * @param node
	 * @return
	 */
	private String nodeHasText(Node node) {
		NodeList childList = node.getChildNodes();
		for (int i = 0; i < childList.getLength(); i++) {
			Node child = childList.item(i);
			if (child.getNodeType() == Node.TEXT_NODE) {
				return child.getTextContent();
			}
		}
		return null;
	}

	/**
	 * Check if the given node is in filtered:
	 * If it is in filteredTags, it is filtered;
	 * If it is in wantedTags, it is not filtered;
	 * If none of the above applies, it is not filtered if has text. 
	 * @param node
	 * @return
	 */
	private boolean nodeIsFiltered(Node node) {
		if (!(node instanceof Element)) {
			return true;
		}

		for (String tag : filteredTags) {
			if ($((Element) node).tag().compareToIgnoreCase(tag) == 0)
				return true;
		}
		for (String tag : wantedTags) {
			if ($((Element) node).tag().compareToIgnoreCase(tag) == 0)
				return false;
		}

		String text = nodeHasText(node);

		if (text != null && !trimWhiteSpace(text).isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * Determine an alias name for the given element:
	 * Starts with the tag(LINK for A, TEXT for any text);
	 * If it has id, name, title, alt, value, src, use that as name;
	 * Otherwise, using the text or hyperlink if A(text and link are trimmed).
	 * @param element
	 * @return
	 */
	private String buildName(Element element) {
		String name = "";
		String tagname = $(element).tag();

		if (tagname.equals("A")) {
			name += "LINK";
		} else if (textTags.contains(tagname) && nodeHasText(element) != null) {
			if(tagname.equals("TD") || tagname.equals("TR")) {
				name += "TABLETEXT";
			}else {	
				name += "TEXT";
			}
		} else {
			name += $(element).tag();
		}

		if (!(element).getAttribute("id").isEmpty()) {
			name = name + "_" + (element).getAttribute("id");
		} else if (!(element).getAttribute("name").isEmpty()) {
			name = name + "_" + (element).getAttribute("name");
		} else if (!(element).getAttribute("title").isEmpty()) {
			name = name + "_" + trimWhiteSpace((element).getAttribute("title"));
		} else if (!(element).getAttribute("alt").isEmpty()) {
			name = name + "_" + trimWhiteSpace((element).getAttribute("alt"));
		} else if (!(element).getAttribute("value").isEmpty()) {
			name = name + "_" + trimWhiteSpace((element).getAttribute("value"));
		} else if ($(element).tag().compareToIgnoreCase("IMG") == 0) {
			String text = element.getAttribute("src");
			String[] textL = text.split("/");
			name = name + "_" + textL[textL.length - 1];
		} else {
			String text = nodeHasText((Node) element);
			if (text != null && !trimWhiteSpace(text).isEmpty()) {
				text = trimWhiteSpace(text);
				name = name
						+ "_"
						+ text.substring(0, 21 > text.length() ? text.length()
								: 21);
			} else if ($(element).tag().compareToIgnoreCase("A") == 0) {
				NodeList childList = element.getChildNodes();
				boolean hasText = false;
				name = name + "_";
				for (int i = 0; i < childList.getLength(); i++) {
					Node child = childList.item(i);
					String linkText = nodeHasText(child);
					if (linkText != null) {
						hasText = true;
						name = name + trimWhiteSpace(linkText);
					}
				}
				if (!hasText) {
					String link = element.getAttribute("href");
					link = link.replace("http://", "");
					link = link.replace("https://", "");
					link = link.replace("www.", "");
					link = link.replaceAll("=", "");

					name = name + link;
					/*
					 * + link.substring(0, 11 > link.length() ? link.length() :
					 * 10);
					 */
				}
			}
		}
		
		++count;
		return name;
	}
	
	/**
	 * If name is duplicated, add a suffix
	 * @param name
	 * @return
	 */
	private String checkDuplicateName(String name) {
		if (duplicateNames.containsKey(name)) {
			int tail = duplicateNames.get(name) + 1;
			duplicateNames.put(name, tail);
			name = name + "_" + tail;
		} else {
			duplicateNames.put(name, 0);
		}
		return name;
	}

	/**
	 * Trim a string in text node.
	 * @param s
	 * @return
	 */
	private String trimWhiteSpace(String s) {
		s = s.replaceAll("\\s+", "");
		// Dealing special case with &nsp;
		s = s.replaceAll("\u00a0", "");
		// Delete "="
		s = s.replaceAll("=", "");
		return s;
	}
}

