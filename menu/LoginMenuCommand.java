package by.module6.library.menu;

public enum LoginMenuCommand {
	LOGIN ("1", "login"), 
	REGISTER ("2", "register"), 
	EXIT ("3", "exit to system");	
	
	String value;
	String name;
	
	LoginMenuCommand(String value, String name) {
		this.value = value;
		this.name = name;
	}	
	
	public static LoginMenuCommand getCommand(String name) {
		for (LoginMenuCommand menuCommand: LoginMenuCommand.values()) {
			if(menuCommand.value.equals(name)) {
				return menuCommand;
			}
		}
		return null;
	}
}
