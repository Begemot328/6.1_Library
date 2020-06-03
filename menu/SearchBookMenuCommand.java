package by.module6.library.menu;

public enum SearchBookMenuCommand {
	SEARCH_BY_TITLE ("1", "search book by title"), 
	SEARCH_BY_AUTHOR ("2", "search book by author"), 
	RESET_SEARCH ("3", "reset search"), 
	MAIN_MENU ("4", "exit to main menu"),	
	EXIT ("5", "exit to system");	
	
	String value;
	String name;
	
	SearchBookMenuCommand(String value, String name) {
		this.value = value;
		this.name = name;
	}	
	
	static SearchBookMenuCommand getCommand(String name) {
		for (SearchBookMenuCommand menuCommand: SearchBookMenuCommand.values()) {
			if(menuCommand.value.equals(name)) {
				return menuCommand;
			}
		}
		return null;
	}
}
