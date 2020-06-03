package by.module6.library.XMLDAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLDAO {
	public static final String INDENTION = System.getProperty("line.separator");
	public static final String NODE_REGEXP = "(<NODE>)(.+?)(</NODE>)";
	public static final String NODE = "NODE";
	public static final String PARENT_TAG = "<TAGNAME>" + INDENTION + "CONTAINER" 
											+ /*INDENTION +*/ "</TAGNAME>";
	public static final String PARAMETER_TAG = "<TAGNAME>CONTAINER</TAGNAME>";
	public static final String TAGNAME = "TAGNAME";
	public static final String TAG_CONTAINER = "CONTAINER";
	public static final String EXTENSION = ".xml";
	
	File file;
	String text;
	String objects;
	String parentTag;
	Pattern[] pattern;
	Matcher[] matcher;
	String[] next;
	
	public XMLDAO(int i, File file) throws FileNotFoundException {
		pattern = new Pattern[i];
		matcher = new Matcher[i];
		next = new String[i];
	}
	
	public XMLDAO(File file) throws FileNotFoundException {
		this(5, file);
	}
	
	public XMLDAO(String name) throws FileNotFoundException {
		this(5, name);
	}
	
	public XMLDAO(int i, String name) throws FileNotFoundException {
		pattern = new Pattern[i];
		matcher = new Matcher[i];
		next = new String[i];
		file = new File(name.toLowerCase() + EXTENSION);
	}
	

	
	public void setTag(int index, String text, String tag) {
		if (tag == null) {
			tag = TAGNAME;
		}
		pattern[index] = Pattern.compile(NODE_REGEXP.replace(NODE, tag), Pattern.DOTALL);
		matcher[index] = pattern[index].matcher(text);		
	}	
	
	public boolean hasNext(int index) {
		if (pattern[index] == null || matcher[index] == null) {
			return false;
		}
		if (matcher[index].find()) {
			next[index] = matcher[index].group(2);
			return true;
		} else {
			return false;
		}
	}	
	
	public String next(int index) {
		if (next[index] != null) {
			return next[index];
		} else {
			return null;
		}
	}	
	
	
	public String getTagType(TagType tagType) {
		switch (tagType) {
		case OBJECT:
			return PARENT_TAG;
		case PARAMETER:
			return PARAMETER_TAG;
		}
		return new String();
	}
	
	String readXMLFromFile(File file) throws FileNotFoundException {
		String text = new String();
		try {
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()) {
				text += scan.nextLine() + INDENTION;
			}
		} catch (FileNotFoundException e) {
			throw e;
		}
		return text;
	}
	
	String writeToXML(ArrayList<XMLNode> list, TagType tagType) {
		String result = new String();
		String node = getTagType(tagType);
		
		for (XMLNode XMLNode: list) {
			if (XMLNode == null) {
				return null;
			}
			result += node.replace(TAGNAME, XMLNode.getName()).replace(TAG_CONTAINER,
					XMLNode.getValue()) + INDENTION;
		}
		return result;
	}
	
	String writeToXML(String tag, ArrayList<XMLNode> list, TagType tagType) {
		String node = getTagType(tagType);
		return node.replace(TAGNAME, tag).replace(TAG_CONTAINER,
				writeToXML(list, tagType));
	}

	public void writeToFile(String text, File file) {
		FileWriter writer;
		
		try {
			writer = new FileWriter(file);
			writer.write(text);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeToFile(String text) {
		writeToFile(text, this.file);
	}
}