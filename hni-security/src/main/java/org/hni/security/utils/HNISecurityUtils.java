package org.hni.security.utils;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.hni.common.HNIUtils;
import org.hni.user.om.User;



public class HNISecurityUtils extends HNIUtils{
	
	final List<String> OPERATORS = Arrays.asList(new String[] {"+", "-"});
	
	static Random rand = new Random(2);
	
	public static Map<String, String> getSecurityMathQuestion() {
		Map<String, String> question = new HashMap<>(3);
		int op1 = rand.nextInt(9);
		int op2 = rand.nextInt(9);
		int ans = op1 + op2;
		StringBuilder sb = new StringBuilder();
		
		sb.append(op1);
		sb.append(" ");
		sb.append(" + ");
		sb.append(" ");
		sb.append(op2);
		sb.append(" = ");
		
		question.put("question", sb.toString());
		question.put("answer", String.valueOf(ans));
		
		return question;
	}
	
	public static User setHashSecret(User user, String password) {
		user.setCreated(new Date());
		user.setSalt(HNISecurityUtils.getSalt());
		user.setHashedSecret(HNISecurityUtils.getHash(password, user.getSalt()));
		
		return user;
	}

}
