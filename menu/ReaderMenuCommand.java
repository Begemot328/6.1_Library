package by.module6.library.menu;

public enum ReaderMenuCommand {
	FIND_BOOK ("1", "find a book"), 
	VIEW_BOOKS ("2", "view all books"), 
	EXIT ("3", "exit to system");	
	
	String value;
	String name;
	
	ReaderMenuCommand(String value, String name) {
		this.value = value;
		this.name = name;
	}	
	
	static ReaderMenuCommand getCommand(String name) {
		for (ReaderMenuCommand menuCommand: ReaderMenuCommand.values()) {
			if(menuCommand.value.equals(name)) {
				return menuCommand;
			}
		}
		return null;
	}
}
