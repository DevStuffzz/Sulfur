package com.sulfurengine.util;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Debug {
	public static boolean DEBUG = false;
	
	public static void Log(String s) {
		if(!DEBUG) return;
		System.out.println(s);
	}
	
	public static void Logf(String format, Object ...args) {
		if(!DEBUG) return;
		System.out.printf(format, args);
	}
	
	public static void LogError(String s) {
		if(!DEBUG) return;
		System.err.println(s);
	}
	
	public static void LogErrorf(String format, Object ...args) {
		if(!DEBUG) return;
		System.err.printf(format, args);
	}
	
	public static boolean fileExists(String path) {
		URL imageUrl = Debug.class.getResource(path);
		return imageUrl != null;
	}
}
