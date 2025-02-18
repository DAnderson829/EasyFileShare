package com.EasyFileSwap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileFormats {
	private static final String[] SUPPORTED_INPUTS = {
			".JPG",".PNG", ".PDF", ".HEIC", ".GIF", ".CSV", ".JSON", ".XML", ".YAML"};
	
	
	private static final Map<String, List<String>> SUPPORTED_CONVERSIONS = new HashMap<String, List<String>>();
	
	static {
		SUPPORTED_CONVERSIONS.put(".JPG", Arrays.asList(".PNG", ".GIF",".HEIC", ".PDF"));
		SUPPORTED_CONVERSIONS.put(".CSV", Arrays.asList(".JSON", ".XML"));
		SUPPORTED_CONVERSIONS.put(".GIF", Arrays.asList(".JPG", ".PNG"));
		SUPPORTED_CONVERSIONS.put(".HEIC", Arrays.asList(".JPG", ".PDF", ".PNG"));
		SUPPORTED_CONVERSIONS.put(".JSON", Arrays.asList(".CSV", ".XML", ".YAML"));
		SUPPORTED_CONVERSIONS.put(".PDF", Arrays.asList(".HEIC", ".HTML", ".JPG", ".PNG", ".TXT"));
		SUPPORTED_CONVERSIONS.put(".PNG", Arrays.asList(".GIF", ".HEIC", ".JPG", ".PDF"));
		SUPPORTED_CONVERSIONS.put(".XML", Arrays.asList(".CSV", ".JSON"));
		SUPPORTED_CONVERSIONS.put(".YAML", Arrays.asList(".JSON"));




	}
}


