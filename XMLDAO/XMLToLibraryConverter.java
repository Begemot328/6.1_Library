package by.module6.library.XMLDAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import by.module6.library.entity.Book;
import by.module6.library.entity.Librarian;
import by.module6.library.entity.Library;
import by.module6.library.entity.Reader;
import by.module6.library.entity.User;

/**
 * @author YZmushko
 *
 */
public class XMLToLibraryConverter implements IXMLWritable {
	public static final String AUTHOR = "author";
	public static final String TITLE = "title";
	public static final String ID = "id";
	public static final String BOOK = "book";
	public static final String LIBRARY = "library";
	private static final String READER = "reader";
	private static final String LIBRARIAN = "librarian";
	private static final String BOOK_LIST = "booklist";
	private static final String USER_LIST = "userlist";	
	private static final String ADRESS = "adress";
	private static final String LOGIN = "login";
	private static final String PASSWORD = "password";
	private static final String REGISTRATION_DATE = "date";
	private static final String FIRST_NAME = "firstname";
	private static final String LAST_NAME = "lastname";
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	public static final String DEFAULT_FILENAME = "default";
	public static final String EXTENSION = ".xml";
	
	private XMLDAO dao;
	private Library library;
	
	
	
	public XMLToLibraryConverter(XMLDAO dao, Library library) {
		this.dao = dao;
		this.library = library;
	}
	
	public XMLToLibraryConverter(Library library) throws FileNotFoundException {
		try {
			this.dao = new XMLDAO(new File(DEFAULT_FILENAME + EXTENSION));
		} catch (FileNotFoundException e) {
			throw e;
		}

		this.library = library;
		if (library == null) {
			this.library = readXMLFromFile(new File(DEFAULT_FILENAME + EXTENSION));
		}
	}
	
	public XMLToLibraryConverter( ) throws FileNotFoundException {
		try {
			this.dao = new XMLDAO(new File(DEFAULT_FILENAME + EXTENSION));
		} catch (FileNotFoundException e) {
			throw e;
		}
		this.library = readXMLFromFile();
	}
	/**
	 * @param s - String of a XML info about library
	 * @return Library object
	 * 
	 * Parsing XML text to Library
	 */
	public Library parseLibrary(String s) {
		
		String text;

		String body;
		String title;
		String author;
		int id;
		Library library;
		
		if (s == null || s.isEmpty()) {
			return null;
		}
		dao.setTag(0, s, LIBRARY);
		
		if (dao.hasNext(0)) {
			body = dao.next(0);
			library = new Library();
			
			dao.setTag(1, body, BOOK);
			while (dao.hasNext(1)) {
				text = dao.next(1);
				library.getBookList().add(parseBook(text));
			} 
			dao.setTag(1, body, READER);
			while (dao.hasNext(1)) {
				text = dao.next(1);
				library.getUserList().add(parseReader(text));
			} 
			dao.setTag(1, body, LIBRARIAN);
			while (dao.hasNext(1)) {
				text = dao.next(1);
				library.getUserList().add(parseLibrarian(text));
			} 			
		} else {
			return null;
		}
		return library;
	}
	
	/**
	 * @param text - String of a XML info about book
	 * @return Book object
	 * 
	 * Parsing XML text to book. Using to Library parsing
	 */
	private Book parseBook(String text) {
		String title;
		String author;
		int id;
		
		if (text == null || text.isEmpty()) {
			return null;
		}
	
		id = Integer.parseInt(singleParse(2, text, ID));
		title = singleParse(2, text, TITLE);
		author = singleParse(2, text, AUTHOR);
		
		if (title == null || author == null || id == 0) {
			return null;
		}
		return new Book(id, title, author);
	}
	
	private Reader parseReader(String text) {
	    String firstName;
	    String lastName;
	    String adress;
	    String login;
	    String password;
	    int id;
	    Date registrationDate;
		
		if (text == null || text.isEmpty()) {
			return null;
		}
	
		id = Integer.parseInt(singleParse(2, text, ID));
		firstName = singleParse(2, text, FIRST_NAME);
		lastName = singleParse(2, text, LAST_NAME);
		adress = singleParse(2, text, ADRESS);
		login = singleParse(2, text, LOGIN);
		password = singleParse(2, text, PASSWORD);
		try {
			registrationDate = new SimpleDateFormat(DATE_FORMAT).parse(singleParse(
					2, text, REGISTRATION_DATE));
		} catch (ParseException e) {
			System.err.println(e);
			return null;
		} 
		
		if (firstName == null || lastName == null || adress == null || login == null 
			|| password == null ||  registrationDate == null || id == 0) {
			return null;
		}
		return new Reader(id, firstName, lastName, login, password, registrationDate, adress);
	}
	
	private Librarian parseLibrarian(String text) {
	    String firstName;
	    String lastName;
	    String adress;
	    String login;
	    String password;
	    int id;
		
		if (text == null || text.isEmpty()) {
			return null;
		}
	
		id = Integer.parseInt(singleParse(2, text, ID));
		firstName = singleParse(2, text, FIRST_NAME);
		lastName = singleParse(2, text, LAST_NAME);
		login = singleParse(2, text, LOGIN);
		password = singleParse(2, text, PASSWORD);
		
		if (firstName == null || lastName == null || login == null 
			|| password == null ||  id == 0) {
			return null;
		}
		return new Librarian(id, firstName, lastName, login, password);
	}
	
	/**
	 * @param text - String of a XML info about element
	 * @return String - element containing
	 * 
	 * Extracting containings from XML element. Using to Library and book parsing
	 */	
	private String singleParse(int index, String text, String tag) {
		dao.setTag(index, text, tag);
		if (dao.hasNext(index)) {
			return dao.next(index);
		} else {
			return null;
		}
	}
	
	/**
	 * @return String - element containing library element description
 	 * 
	 * Converting this class Library object to XML text.
	 */	
	public String convertToXML() {
		return writeToXML(library);
	}
	
	private String writeToXML(Object object) {
		if (getParameters(object) == null) {
			return null;
		}
		if (object instanceof Book) {
			return dao.writeToXML(getParameters(object), TagType.PARAMETER);
		}
		if (object instanceof User) {
			return dao.writeToXML(getParameters(object), TagType.PARAMETER);
		}
		if (object instanceof Library) {
			return dao.writeToXML(LIBRARY, getParameters(object), TagType.OBJECT); 
			// Type may be removed due to it is always object
		}
		if (object instanceof Library) {
			return dao.writeToXML(LIBRARY, getParameters(object), TagType.OBJECT); 
			// Type may be removed due to it is always object
		}
		return null;
	}
	
	
	/**
	 * @param object - object to convert. 
	 * @return Book object
	 * 
	 * Parsing XML text to book. Using to Library parsing
	 */
	public ArrayList<XMLNode> getParameters(Object object) {
		ArrayList<XMLNode> result = new ArrayList<XMLNode>();
		ArrayList<XMLNode> list = new ArrayList<XMLNode>();
		Iterator<Book> iterator;
		if (object instanceof Book) {
			Book book = (Book) object;
			result.add(new XMLNode(AUTHOR, book.getAuthor()));
			result.add(new XMLNode(TITLE, book.getTitle()));
			result.add(new XMLNode(ID, Integer.toString(book.getId())));
			return result;
		}
		if (object instanceof Library) {
			Library library = (Library) object;
			iterator = library.getBookList().iterator();
			while (iterator.hasNext()) {
				list.add(new XMLNode(BOOK, dao.writeToXML(
						getParameters(iterator.next()), TagType.PARAMETER)));
			}
			result.add(new XMLNode(BOOK_LIST, dao.writeToXML(list, TagType.OBJECT)));
			list = new ArrayList<XMLNode>();
			for (User user:library.getUserList()) {
				if (user instanceof Reader) {
					list.add(new XMLNode(READER, dao.writeToXML(
							getParameters(user), TagType.PARAMETER)));
				}
				if (user instanceof Librarian) {
					list.add(new XMLNode(LIBRARIAN, dao.writeToXML(
							getParameters(user), TagType.PARAMETER)));
				}
			}
			result.add(new XMLNode(USER_LIST, dao.writeToXML(list, TagType.OBJECT)));
			
			return result;
		}
		if (object instanceof Reader) {
			Reader reader = (Reader) object;
			result.add(new XMLNode(ADRESS, reader.getAdress()));
			result.add(new XMLNode(REGISTRATION_DATE, new SimpleDateFormat(
					DATE_FORMAT).format(reader.getRegistrationDate())));
			result.add(new XMLNode(LAST_NAME, reader.getLastName()));
			result.add(new XMLNode(FIRST_NAME, reader.getFirstName()));
			result.add(new XMLNode(PASSWORD, reader.getPassword()));
			result.add(new XMLNode(LOGIN, reader.getLogin()));
			result.add(new XMLNode(ID, Integer.toString(reader.getId())));
			return result;
		}
		if (object instanceof Librarian) {
			Librarian librarian = (Librarian) object;
			result.add(new XMLNode(LAST_NAME, librarian.getLastName()));
			result.add(new XMLNode(FIRST_NAME, librarian.getFirstName()));
			result.add(new XMLNode(PASSWORD, librarian.getPassword()));
			result.add(new XMLNode(LOGIN, librarian.getLogin()));
			result.add(new XMLNode(ID, Integer.toString(librarian.getId())));
			return result;
		}
		return null;
	}

	
	public void writeToXMLFile() {
		dao.writeToFile(writeToXML(library), new File(DEFAULT_FILENAME + EXTENSION));
	}
	public void writeToXMLFile(String name) {
		dao.writeToFile(writeToXML(library), new File(name + EXTENSION));
	}
	
	public void writeToXMLFile(File file) {
		dao.writeToFile(writeToXML(library), file);
	}
	
	public Library readXMLFromFile(File file) throws FileNotFoundException {
		return parseLibrary(dao.readXMLFromFile(file));
	}
	
	public Library readXMLFromFile() throws FileNotFoundException {
		return parseLibrary(dao.readXMLFromFile(new File(DEFAULT_FILENAME + EXTENSION)));
	}
	
	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	public Library readXMLFromFile(String filename) throws FileNotFoundException {
		return parseLibrary(dao.readXMLFromFile(new File(filename + EXTENSION)));
		
	}
}
