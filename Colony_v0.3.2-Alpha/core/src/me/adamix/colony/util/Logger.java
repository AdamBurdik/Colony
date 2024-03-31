package me.adamix.colony.util;

public class Logger {

	public static void info(String prefix, Object message) {
		System.out.println("[" + prefix.toUpperCase() + "] \u001B[34m" + message + "\u001B[0m");
	}

	public static void error(String prefix, Object message) {
		System.out.println("[" + prefix.toUpperCase() + "] \u001B[31m" + message + "\u001B[0m");

	}

	public static void warning(String prefix, Object message) {
		System.out.println("[" + prefix.toUpperCase() + "] \u001B[33m" + message + "\u001B[0m");

	}

	public static void success(String prefix, Object message) {
		System.out.println("[" + prefix.toUpperCase() + "] \u001B[32m" + message + "\u001B[0m");

	}



}
