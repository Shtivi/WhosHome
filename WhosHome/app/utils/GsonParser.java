package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonParser {
	private static final GsonBuilder builder = new GsonBuilder();
    private static Gson instance;
	
    static {
    	instance = builder.create();
    }
    
	public static Gson instance() {
		return (instance);
	}
}
