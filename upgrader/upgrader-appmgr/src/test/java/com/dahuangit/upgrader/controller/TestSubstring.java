package com.dahuangit.upgrader.controller;

public class TestSubstring {

	public static void main(String[] args) {
		String fileName = "test-1.22.1.apk";
		String version = fileName.substring(fileName.indexOf(".") - 1,fileName.lastIndexOf("."));
		System.out.println(version);
	}

}
