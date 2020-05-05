package Utils;

import Controller.Server.JSONArray;
import Controller.Server.JSONException;
import Controller.Server.JSONObject;

public class Utils {
	public static boolean[] toBooleanArray(JSONArray ja) throws JSONException {
		boolean[] b = new boolean[ja.length()];
		for(int i = 0; i < ja.length(); i++) {
			b[i] = ja.getBoolean(i);
		}
		return b;
	}
}
