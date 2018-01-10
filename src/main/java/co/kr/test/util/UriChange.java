package co.kr.test.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UriChange {
	
	
	public static final Map<String, Integer> CHANEGEPARAM (List<String> paramList) {
		Map<String, Integer> resultMap = new HashMap<>();

		for (String param : paramList) {
			String[] paramArr = param.split(":");
			String paramType = paramArr[0];
			String paramValue = paramArr[1];
			if ("size".equals(paramType)) {
				try {
					resultMap.put("size", (" ".equals(paramValue)) ? Integer.parseInt(Constants.DEFAULT_SIZE) : Integer.parseInt(paramValue));
				} catch (NumberFormatException e) {
					resultMap.put("size", Integer.parseInt(Constants.DEFAULT_SIZE));
				}
				
			}else if ("page".equals(paramType)){
				try {
					resultMap.put("page", (" ".equals(paramValue)) ? Integer.parseInt(Constants.DEFAULT_PAGE) : Integer.parseInt(paramValue));
				} catch (NumberFormatException e) {
					resultMap.put("page", Integer.parseInt(Constants.DEFAULT_PAGE));
				}
			}
		}
		return resultMap;
	}
}
