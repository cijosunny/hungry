package org.hni.common;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;

public class HNIUtils {
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "").trim();
	}

	private static Map<String, Object> getFieldDefMap(String fieldName, String displayName, Boolean resizable,
			Boolean enableCellEdit, Boolean hasWidth) {
		Map<String, Object> fieldDef = new HashMap<>();
		fieldDef.put("field", fieldName);
		fieldDef.put("displayName", displayName);
		fieldDef.put("resizable", resizable);
		fieldDef.put("enableCellEdit", enableCellEdit);
		fieldDef.put("cellTooltip", true);
		if(hasWidth)
		fieldDef.put("width", getColumnWidth(displayName, fieldDef));
		// fieldDef.put("field", fieldName);

		return fieldDef;
	}
	
	private static int getColumnWidth(String displayName, Map<String, Object> fieldDef){
		if(displayName.toLowerCase().contains("name") || displayName.toLowerCase().contains("address") || displayName.toLowerCase().contains("email") || displayName.toLowerCase().contains("website")){
			return 150;
		}
		return (displayName.length()*10);
	}

	public static List<Map<String, Object>> getReportHeaders(Integer reportId, Boolean canEdit) {

		List<Map<String, Object>> headers = new ArrayList<>();
		if (Constants.REPORT_ALL_NGO.equals(reportId)) {

			headers.add(getFieldDefMap("name", "NGO Name", true, canEdit, false));
			headers.add(getFieldDefMap("phone", "NGO Phone", true, canEdit, false));
			headers.add(getFieldDefMap("address", "NGO Address", true, canEdit, false));
			headers.add(getFieldDefMap("createdUsers", "Total number of Clients", true, canEdit, false));
		} else if (Constants.REPORT_ALL_CUSTOMER.equals(reportId)) {

			headers.add(getFieldDefMap("firstName", "First Name", true, canEdit,true));
			headers.add(getFieldDefMap("lastName", "Last Name", true, canEdit,true));
			headers.add(getFieldDefMap("mobilePhone", "Phone Number", true, canEdit,true));
			headers.add(getFieldDefMap("address", "Address", true, canEdit,true));
			headers.add(getFieldDefMap("mealsPerDay", "Meals Allowed (Daily)", true, canEdit,true));
			headers.add(getFieldDefMap("orders", "No of Orders", true, canEdit,true));
			headers.add(getFieldDefMap("active", "Status", true, canEdit,true));
			headers.add(getFieldDefMap("sheltered", "Sheltered", true, canEdit,true));
			headers.add(getFieldDefMap("contactName", "NGO Name", true, canEdit,true));
			headers.add(getFieldDefMap("noOfDependents", "No of Dependents", true, canEdit,true));

		} else if (Constants.REPORT_ALL_CUSTOMER_NGO.equals(reportId)) {

			headers.add(getFieldDefMap("firstName", "First Name", true, canEdit, false));
			headers.add(getFieldDefMap("lastName", "Last Name", true, canEdit, false));
			headers.add(getFieldDefMap("mobilePhone", "Phone Number", true, canEdit, false));
			headers.add(getFieldDefMap("address", "Address", true, canEdit, false));
			headers.add(getFieldDefMap("mealsPerDay", "Meals Allowed (Daily)", true, canEdit, false));
			headers.add(getFieldDefMap("orders", "No of Orders", true, canEdit, false));
			headers.add(getFieldDefMap("active", "Status", true, canEdit, false));
			headers.add(getFieldDefMap("sheltered", "Sheltered", true, canEdit, false));

		} else if (Constants.REPORT_ALL_VOLUNTEER.equals(reportId)) {
			headers.add(getFieldDefMap("firstName", "First Name", true, canEdit, false));
			headers.add(getFieldDefMap("lastName", "Last Name", true, canEdit, false));
			headers.add(getFieldDefMap("address", "Address", true, canEdit, false));
			headers.add(getFieldDefMap("phone", "Phone Number", true, canEdit, false));
			headers.add(getFieldDefMap("email", "Email", true, canEdit, false));
		} else if (Constants.REPORT_ALL_ORDER.equals(reportId)) {
			headers.add(getFieldDefMap("orderDate", "Order date", true, canEdit, false));
			headers.add(getFieldDefMap("readyDate", "Ready date", true, canEdit, false));
			headers.add(getFieldDefMap("name", "Orderd By", true, canEdit, false));
			headers.add(getFieldDefMap("orderstatus", "Order status", true, canEdit, false));
			headers.add(getFieldDefMap("total", "Total", true, canEdit, false));
			// headers.add(addField("orderItems", "Ordered Items"));
		} else if (Constants.REPORT_ALL_PROVIDER.equals(reportId)) {

			headers.add(getFieldDefMap("name", "Provider Name", true, canEdit, false));
			headers.add(getFieldDefMap("address", "Address", true, canEdit, true));
			headers.add(getFieldDefMap("website", "Website", true, canEdit, true));
			headers.add(getFieldDefMap("active", "Status", true, false, true));
			headers.add(getFieldDefMap("createdOn", "Created On", true, canEdit, false));
			headers.add(getFieldDefMap("createdBy", "Created By", true, canEdit, false));
		} else if (Constants.REPORT_ALL_PROVIDER_LOCATIONS.equals(reportId)) {

			headers.add(getFieldDefMap("name", "Name", true, canEdit, true));
			headers.add(getFieldDefMap("menu.name", "Menu Name", true, canEdit, true));
			headers.add(getFieldDefMap("providerLocationHours[0].openHour", "Open Hour", true, canEdit, true));
			headers.add(getFieldDefMap("providerLocationHours[0].closeHour", "Close Hour", true, canEdit, true));
			headers.add(getFieldDefMap("isActive", "Active", true, canEdit, true));
			headers.add(getFieldDefMap("address.address1", "Address Line 1", true, canEdit, true));
			headers.add(getFieldDefMap("address.address2", "Address Line 2", true, canEdit, true));
			headers.add(getFieldDefMap("address.city", "City", true, canEdit, true));
			headers.add(getFieldDefMap("address.state", "State", true, canEdit, true));
			headers.add(getFieldDefMap("address.latitude", "Latitude", true, canEdit, true));
			headers.add(getFieldDefMap("address.longitude", "Longitude", true, canEdit, true));
		} else if (Constants.MENU_ITEM_BY_MENU.equals(reportId)) {

			headers.add(getFieldDefMap("name", "Name", true, canEdit, false));
			headers.add(getFieldDefMap("description", "Description", true, canEdit, false));
			headers.add(getFieldDefMap("price", "Price", true, canEdit, false));
			headers.add(getFieldDefMap("expires", "Expires", true, canEdit, false));
			headers.add(getFieldDefMap("calories", "Calories", true, canEdit, false));
			headers.add(getFieldDefMap("protien", "Protien", true, canEdit, false));
			headers.add(getFieldDefMap("fat", "Fat", true, canEdit, false));
			headers.add(getFieldDefMap("carbs", "Carbs", true, canEdit, false));
		} else if(Constants.GIFT_CARDS.equals(reportId)){
			
			headers.add(getFieldDefMap("cardSerialId", "Serial Number", true, canEdit, false));
			headers.add(getFieldDefMap("cardNumber", "Card Number", true, canEdit, false));
			headers.add(getFieldDefMap("status", "Status", true, false, false));
			headers.add(getFieldDefMap("originalBalance", "Original Balance", true, canEdit, false));
			headers.add(getFieldDefMap("balance", "Balance", true, canEdit, false));
		}
		return headers;
	}

	private static Map<String, String> addField(String field, String label) {
		Map<String, String> header = new HashMap<String, String>();
		header.put("field", field);
		header.put("label", label);
		return header;
	}

	public static String getHash(String authCode, Object salt) {
		ByteSource slt = new SimpleByteSource(org.apache.commons.codec.binary.Base64.decodeBase64((String) salt));
		Hash h = new SimpleHash("SHA-256", authCode, slt, 1024);
		Base64.Encoder enc = Base64.getEncoder();
		return new String(enc.encode(h.getBytes()));
	}

	public static String getSalt() {
		byte[] salt = new byte[16];
		SecureRandom random = new SecureRandom();
		random.nextBytes(salt);
		Base64.Encoder enc = Base64.getEncoder();
		return enc.encodeToString(salt);
	}

	public static boolean isPositiveNumeric(String text) {
		boolean isValid = false;
		try {
			Integer val = Integer.parseInt(text);
			if (Integer.signum(val.intValue()) != -1) {
				isValid = true;
			}
		} catch (Exception e) {
			// Nothing to do with exception
		}

		return isValid;
	}

	public static boolean isPositiveNumeric(Integer num) {
		return Integer.signum(num) != -1;
	}

	public static Integer getNumber(String text) {
		try {
			return Integer.parseInt(text);
		} catch (Exception e) {
			// Nothing to do with exception
			return null;
		}
	}

}