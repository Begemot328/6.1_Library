package by.module6.library.entity;

import java.util.ArrayList;
import java.util.HashSet;

public class Library {
	private ArrayList<Book> bookList;
	private HashSet<User> userList;
	
	public Library() {
		this.bookList = new ArrayList<Book>();
		this.userList = new HashSet<User>();
	}
	
	public ArrayList<Book> getBookList() {
		return bookList;
	}
	public void setBookList(ArrayList<Book> bookList) {
		this.bookList = bookList;
	}
	public HashSet<User> getUserList() {
		return userList;
	}
	public void setUserList(HashSet<User> userList) {
		this.userList = userList;
	}

	@Override
	public String toString() {
		String result = "Library \n";
		for (Book book:bookList) {
			result += book + "\n";
		}
		return result;
	}
	
	
}
