package org.hni.passwordvalidater;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hni.user.om.User;

public class CheckPassword {

	public static boolean passwordCheck(User u) {
		boolean result = false;
		String pattern = "^(?=.*\\d)(?=.*[a-zA-Z]).{6,}$";
		if(isRegExpMatched(pattern,u) && u.getPassword().length()>=6 ){
			pattern = "[~!@#$%^&*()_+{}|:<>?/.,';\\=`]";
			if(isRegExpMatched(pattern,u))
					result = true;
		}
		return result;
	}

	private static boolean isRegExpMatched(String regEx, User user){
		boolean result;
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(user.getPassword());
		result = matcher.find();
		return result;
	}
}
