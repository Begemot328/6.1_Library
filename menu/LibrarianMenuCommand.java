package by.module6.library.menu;

public enum LibrarianMenuCommand {
	FIND_BOOK ("1", "find a book"), 
	VIEW_BOOKS ("2", "view all books"), 
	ADD_BOOK ("3", "add new book"), 
	OPEN_LIBRARY ("4", "open library"), 
	SAVE_LIBRARY ("5", "save library"),
	OPEN_LIBRARY_FROM_FILE ("6", "open library from file"), 
	SAVE_LIBRARY_TO_FILE ("7", "save library to file"),
	MAIN_MENU ("8", "exit to main menu"),
	EXIT ("9", "exit to system");	
	
	String value;
	String name;
	
	LibrarianMenuCommand(String value, String name) {
		this.value = value;
		this.name = name;
	}	
	
	static LibrarianMenuCommand getCommand(String name) {
		for (LibrarianMenuCommand menuCommand: LibrarianMenuCommand.values()) {
			if(menuCommand.value.equals(name)) {
				return menuCommand;
			}
		}
		return null;
	}
}
