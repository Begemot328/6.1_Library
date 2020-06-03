package by.module6.library.menu;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import by.module6.library.XMLDAO.XMLDAO;
import by.module6.library.XMLDAO.XMLToLibraryConverter;
import by.module6.library.entity.Book;
import by.module6.library.entity.Librarian;
import by.module6.library.entity.Library;
import by.module6.library.entity.Reader;
import by.module6.library.entity.User;
import by.module6.library.exception.InputException;
import by.module6.library.logic.LibraryLogic;
import by.module6.library.logic.LibraryLogic.BookSearch;

public class Controller {
	private final static String ANYKEY = "Press any key to continue...";
	private final static String EMPTY = "is empty";
	private final static String ENTER_CORRECT_DATA = "Enter valid data!";
	private final static String COMMAND_DESCRIPTION = "Enter COMMAND to DO_SOMETHING";
	private final static String COMMAND = "COMMAND";
	private final static String DO_SOMETHING = "DO_SOMETHING";
	private final static String NO_SUCH_COMMAND = "No such comand";
	private final static String DATA_DESCRIPTION = "Enter DATA";
	private final static String DATA = "DATA";
	private final static String NO_RESULTS = "No treasures found";
	private final static String FILENAME = "filename";
	private final static String WRONG_DATA_TYPE = "Wrong data type!";
	private final static String WRONG_COMMAND = "Wrong command!";
	private final static String FILE_NOT_FOUND = "File not found!";
	private final static String DEFAULT_FILENAME = "default";
	private final static String LOGIN = "login";
	private final static String PASSWORD = "password";
	private final static String WELCOME = "Welcome, USER FIRSTNAME LASTNAME!";
	private final static String FIRSTNAME = "FIRSTNAME";
	private final static String LASTNAME = "LASTNAME";

	private final static String TITLE = "title";
	private final static String AUTHOR = "author";
	private final static String AMOUNT = "amount";
	private final static String PRICE = "price";
	private final static String NAME = "name";
	private final static String BOOKLIST = "List of books:";
	private final static String USER = "USER";
	private final static String READER = "reader";
	private final static String LIBRARIAN = "librarian";
	
	private static Controller controller;
	
	
	private Scanner scanner;
	private Menu currentMenu;
	private User currentUser;
	private LibraryLogic logic;
	private XMLToLibraryConverter converter;
	private XMLDAO dao;
	private BookSearch result;
	
	private Controller() {
		currentMenu = Menu.LOGIN_MENU;
		scanner = new Scanner(System.in);
		try {
			converter = new XMLToLibraryConverter();
		} catch (FileNotFoundException e) {
			System.out.println(FILE_NOT_FOUND);
		}
		logic = new LibraryLogic(converter.getLibrary());
	}	
	
	public static Controller getInstance() {
		if (controller == null)  {
			controller = new Controller();
		}
		return controller;
	}
	
	public void run() {
		while (true) {
			readCommand();	
		}
	}
	
	public void readCommand() {
		String command = new String();
		
		while(true) {
			switch (currentMenu) {
			case LOGIN_MENU:
				for (LoginMenuCommand menuCommand: LoginMenuCommand.values()) {
					System.out.println(COMMAND_DESCRIPTION.replace(
										COMMAND, menuCommand.value).replace(
										DO_SOMETHING, menuCommand.name));
					
				}  
				break;
			case READER_MENU:
				for (ReaderMenuCommand menuCommand: ReaderMenuCommand.values()) {
					System.out.println(COMMAND_DESCRIPTION.replace(
										COMMAND, menuCommand.value).replace(
										DO_SOMETHING, menuCommand.name));
					
				}
				break;
			
			case SEARCH_BOOK_MENU:
				for (SearchBookMenuCommand menuCommand: SearchBookMenuCommand.values()) {
					System.out.println(COMMAND_DESCRIPTION.replace(
										COMMAND, menuCommand.value).replace(
										DO_SOMETHING, menuCommand.name));
				}
				break;
			case LIBRARIAN_MENU:
				for (LibrarianMenuCommand menuCommand: LibrarianMenuCommand.values()) {
					System.out.println(COMMAND_DESCRIPTION.replace(
										COMMAND, menuCommand.value).replace(
										DO_SOMETHING, menuCommand.name));
					
				}
				break;
				/*
			case ADD_BOOK_MENU:
				for (AddBookMenuCommand menuCommand: AddBookMenuCommand.values()) {
					System.out.println(COMMAND_DESCRIPTION.replace(
										COMMAND, menuCommand.value).replace(
										DO_SOMETHING, menuCommand.name));
					
				}
				break;
			case SEARCH_READER_MENU:
				for (SearchReaderMenuCommand menuCommand: SearchReaderMenuCommand.values()) {
					System.out.println(COMMAND_DESCRIPTION.replace(
										COMMAND, menuCommand.value).replace(
										DO_SOMETHING, menuCommand.name));
					
				}
				break;*/
			}
			
			if(scanner.hasNext()) {
				command = scanner.next();
			}
			
			switch (currentMenu) {
			case LOGIN_MENU:
				if (LoginMenuCommand.getCommand(command) == null) {
					System.out.println(WRONG_COMMAND);
					break;
				}
				execute(LoginMenuCommand.getCommand(command));
				break;
			case READER_MENU:
				if (ReaderMenuCommand.getCommand(command) == null) {
					System.out.println(WRONG_COMMAND);
					break;
				}
				execute(ReaderMenuCommand.getCommand(command));
				break;
			case LIBRARIAN_MENU:
				if (LibrarianMenuCommand.getCommand(command) == null) {
					System.out.println(WRONG_COMMAND);
					break;
				}
				execute(LibrarianMenuCommand.getCommand(command));
				break;
			case SEARCH_BOOK_MENU:
				if (SearchBookMenuCommand.getCommand(command) == null) {
					System.out.println(WRONG_COMMAND);
					break;
				}
				execute(SearchBookMenuCommand.getCommand(command));
				break; 
				/*
			case SEARCH_READER_MENU:
				if (SearchReaderMenuCommand.getCommand(command) == null) {
					System.out.println(WRONG_COMMAND);
					break;
				}
				execute(LoginMenuCommand.getCommand(command));
				break;
			*/
			}
		}
	}
	
	private void execute(ReaderMenuCommand command) {
		String login;
		String password;
		switch (command) {
		case FIND_BOOK:
			currentMenu = Menu.SEARCH_BOOK_MENU;
			result = logic.searchAll();
			break;
		case VIEW_BOOKS:
			printBooks(logic.searchAll().getResult());
			break;
		case EXIT:
			exit();
			break;
		}	
	}
	
	
	
	private void execute(LibrarianMenuCommand command) {
		String login;
		String password;
		String title;
		String author;
		String filename;
		
		switch (command) {
		case FIND_BOOK:
			currentMenu = Menu.SEARCH_BOOK_MENU;
			result = logic.searchAll();
			break;
		case VIEW_BOOKS:
			printBooks(logic.searchAll().getResult());
			break;
		case ADD_BOOK:
			while (true) {
				author = (String) readParameter(Type.STRING, AUTHOR);
				title = (String) readParameter(Type.STRING, TITLE);
				try {
					logic.addBook(title, author);
					break;
				} catch (InputException e) {
					System.out.println(e.getMessage());
				}
			}
			printBooks(logic.searchAll().getResult());
			break;
		case OPEN_LIBRARY:
			try {
				converter.readXMLFromFile();
			} catch (FileNotFoundException e) {
				System.out.println(FILE_NOT_FOUND);
			}
			break;	
		case SAVE_LIBRARY:
				converter.writeToXMLFile();
			break;
		case OPEN_LIBRARY_FROM_FILE:
			filename = (String) readParameter(Type.STRING, FILENAME);
			Library library = null;
			try {
				library = converter.readXMLFromFile(filename);
				
			} catch (FileNotFoundException e) {
				System.out.println(FILE_NOT_FOUND);
			}
			if (library == null) {
				System.out.println(ENTER_CORRECT_DATA);
			}
			logic.setLibrary(library);
			break;		
		case SAVE_LIBRARY_TO_FILE:
			filename = (String) readParameter(Type.STRING, FILENAME);
			converter.writeToXMLFile();
		case EXIT:
			exit();
			break;
		}	
	}
	
	
	private void execute(SearchBookMenuCommand command) {
		String author;
		String title;
		switch (command) {
			case SEARCH_BY_TITLE:
				title = (String) readParameter(Type.STRING, TITLE);
				result = result.searchByName(title);
				printBooks(result.getResult());
				break;
			case SEARCH_BY_AUTHOR:
				author = (String) readParameter(Type.STRING, AUTHOR);
				result = result.searchByName(author);
				printBooks(result.getResult());
				break;
			case RESET_SEARCH:
				result = logic.searchAll();
				printBooks(result.getResult());
				break;
			case MAIN_MENU:
				if (currentUser instanceof Librarian) {
					currentMenu = Menu.LIBRARIAN_MENU;
					break;
				}
				if (currentUser instanceof Reader) {
					currentMenu = Menu.READER_MENU;
					break;
				}
				break;
			case EXIT:
				exit();
				break;
	
		}
	}
	
	private void printBooks(ArrayList<Book> list) {
		int i = 0;
		int j = 0;
		System.out.println(BOOKLIST);
		
		if(list.isEmpty()) {
			System.out.println(EMPTY);
			return;
		}
		for (Book book: list) {
			i++;
			j++;
			if(i > 10) {
				i = 0;
				pause();
			}
			System.out.println(j + " " + book);
		}
		System.out.println();
	}
	


	private void execute(LoginMenuCommand command) {
		String login;
		String password;
		switch (command) {
		case LOGIN:
			while (true) {
				login = (String) readParameter(Type.STRING, LOGIN);
				password = (String) readParameter(Type.STRING, PASSWORD);
				if (login.isEmpty() || password.isEmpty()) {
					System.out.println(ENTER_CORRECT_DATA);
					continue;
				}
				try {
					currentUser = logic.getUser(login, password);
					if (currentUser == null) {
						continue;
					}
					if (currentUser instanceof Librarian) {
						currentMenu = Menu.LIBRARIAN_MENU;
						System.out.println(WELCOME.replace(USER, LIBRARIAN).replace(
								FIRSTNAME, currentUser.getFirstName()).replaceAll(
										LASTNAME, currentUser.getLastName()));
						break;
					}
					if (currentUser instanceof Reader) {
						currentMenu = Menu.READER_MENU;
						System.out.println(WELCOME.replace(USER, READER).replace(
								FIRSTNAME, currentUser.getFirstName()).replaceAll(
										LASTNAME, currentUser.getLastName()));
						break;
					}
				} catch (InputException e) {
					System.out.println(e.getMessage());
				}
			}
			break;
		case REGISTER:
			registerUser();
			break;
		case EXIT:
			exit();
			break;
		}	
	}
	
	private void registerUser() {
		// TODO Auto-generated method stub
		
	}

	private Object readParameter(Type type, String data) {
		while (true) {
		System.out.println(DATA_DESCRIPTION.replace(DATA, data));	
		switch (type) {
			case STRING:
				if(scanner.hasNext()) {
					return scanner.next();
				}
			case DOUBLE:
				try {
					if(scanner.hasNext()) {
						return Double.parseDouble(scanner.next());
					}
					
				} catch (NumberFormatException e) {
					System.out.println(WRONG_DATA_TYPE);
				}
				
				if(scanner.hasNextDouble()) {
					return scanner.nextDouble();
				} else {
					System.out.println(WRONG_DATA_TYPE);
					scanner.next();
				}
			}
		}
	}
	
	private void pause() {
		System.out.println(ANYKEY);
		if (scanner.hasNext()) {
			return;
		}
		
	}
	
	private void exit() {
		System.exit(0);
	}
}
