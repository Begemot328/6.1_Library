package by.module6.library;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Locale;

import by.module6.library.XMLDAO.XMLDAO;
import by.module6.library.XMLDAO.XMLToLibraryConverter;
import by.module6.library.entity.Book;
import by.module6.library.entity.Librarian;
import by.module6.library.entity.Library;
import by.module6.library.entity.Reader;
import by.module6.library.menu.Controller;


/*  @author Yury Zmushko

 * Задание 1: создать консольное приложение “Учет книг в домашней библиотеке”.  
 * Общие требования к заданию: 
 * •  Система учитывает книги как в электронном, так и в бумажном варианте. 
 * •  Существующие роли: пользователь, администратор. 
 * •  Пользователь может просматривать книги в каталоге книг, осуществлять поиск
 *  книг в каталоге.  
 * •  Администратор может модифицировать каталог. 
 * •  *При добавлении описания книги в каталог оповещение о ней рассылается на 
 * e-mail всем пользователям 
 * •  **При просмотре каталога желательно реализовать постраничный просмотр 
 * •  ***Пользователь может предложить добавить книгу в библиотеку, переслав её 
 * администратору на e-mail. 
 * •  Каталог книг хранится в текстовом файле. 
 * •  Данные аутентификации пользователей хранятся в текстовом файле. Пароль 
 * не хранится в открытом виде 
 
 */

public class Runner {
	
	public static void main(String[] args) {
		Controller controller = Controller.getInstance();
		controller.run();
	}	
	
	public static String hashPassword(String password) {
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
}


