package by.module6.library.logic;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import by.module6.library.exception.InputException;
import by.module6.library.entity.Book;
import by.module6.library.entity.Library;
import by.module6.library.entity.User;

public class LibraryLogic {
	public static final String WRONG_PASSWORD = "Wrong password!";
	public static final String USER_NOT_FOUND = "User not found!";
	public static final String WRONG_DATA = "Enter correct data!";
	private Library library;
	
	public LibraryLogic(Library library) {
		this.library = library;
	}
	
	public User getUser(String login, String password) throws InputException {
		for (User user : library.getUserList()) {
			if (user.getLogin().equals(login)) {
				if (user.getPassword().equals(hashPassword(password))) {
					return user;
				} else {
					throw new InputException(WRONG_PASSWORD);
				}
			} 
		}
		throw new InputException(USER_NOT_FOUND);
	}
	
	public User getUser(int id) {
		for (User user : library.getUserList()) {
			if(user.getId() == id) {
				return user;
			}
		}
		return null;
	}
	
	public ArrayList<User> searchUser(String name) {
		ArrayList<User> result = new ArrayList<>();
		for (User user : library.getUserList()) {
			if(user.getFirstName().toLowerCase().contains(name.toLowerCase()) 
					|| name.toLowerCase().contains(user.getFirstName().toLowerCase())) {
				result.add(user);
			}
		}
		return result;
	}
	
	public void addUser(User user) {
		String password;
		String hashedPassword;
		password = user.getPassword();
		hashedPassword = hashPassword(password);
		user.setPassword(hashedPassword);
		library.getUserList().add(user);
	}

	private static String hashPassword(String password) {
		String result;
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
			md.update(password.getBytes());
			
			byte[] mdbytes = md.digest();
            StringBuffer sb = new StringBuffer();
            // convert the byte to hex format
            for (int j = 0; j < mdbytes.length; j++) {
                String s = Integer.toHexString(0xff & mdbytes[j]);
                s = (s.length() == 1) ? "0" + s : s;
                sb.append(s);
            }
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
	
	
	public class BookSearch {
		private ArrayList<Book> list;
		
		public BookSearch searchByName(String name) {
			BookSearch result = new BookSearch();
			result.list = new ArrayList<Book>();
			if (list.isEmpty()) {
				return result;
			}
			for (Book book: this.list) {
				if(book.getTitle().contains(name) 
					|| name.contains(book.getTitle()) ) {
					result.list.add(book);
				}
			}
			return result;
		}
		
		public BookSearch searchByAuthor(String name) {
			BookSearch result = new BookSearch();
			result.list = new ArrayList<Book>();
			if (list.isEmpty()) {
				return result;
			}
			for (Book book: this.list) {
				if(book.getAuthor().contains(name) 
					|| name.contains(book.getAuthor()) ) {
					result.list.add(book);
				}
			}
			return result;
		}
		
		public ArrayList<Book> getResult() {
			return list;
		}
	}
	
	public BookSearch searchAll() {
		BookSearch result = new BookSearch();
		result.list = LibraryLogic.this.library.getBookList();
		return result;
	}

	public void addBook(String title, String author) throws InputException {
		int id = 0;
		if (title == null || author == null || author.isEmpty() || title.isEmpty()) {
			throw new InputException(WRONG_DATA);
		}		
		for (Book book: library.getBookList()) {
			if (book.getId() > id) {
				id = book.getId();
			}
		}
		library.getBookList().add(new Book(id, title, author));
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}
	
	
}
