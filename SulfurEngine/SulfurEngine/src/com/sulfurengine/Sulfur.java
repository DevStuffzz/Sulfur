package com.sulfurengine;

import com.sulfurengine.util.Debug;

public class Sulfur {
	public static final boolean IGNORE_WATERMARK_SCENE = false;
	public static final String VERSION = "1.0.1";

	public static void init() {
		if(!Debug.fileExists("/resources/branding/logo.png")) {
			throw new RuntimeException("Logo unavailable at \"resources/branding/logo.png\" and is one of the required assets", null);
		}

	}
}
