package Utils;

import Controller.Server.JSONException;
import Controller.Server.JSONObject;
public class Triplet<T, U, V> {

    private final T first;
    private final U second;
    private final V third;

    public Triplet(T first, U second, V third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public T getFirst() { return first; }
    public U getSecond() { return second; }
    public V getThird() { return third; }
    public JSONObject toJSON(String key1, String key2, String key3) throws JSONException {
		JSONObject jsonObject = new JSONObject("{}");
		jsonObject.put(key1, first.toString());
		jsonObject.put(key2, second.toString());
		jsonObject.put(key3, third.toString());
		return jsonObject;
    }
}