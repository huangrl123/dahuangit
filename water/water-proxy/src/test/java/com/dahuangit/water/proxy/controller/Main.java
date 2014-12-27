package com.dahuangit.water.proxy.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
//		List list = new ArrayList();
       List list = new LinkedList();
       list.add(1);
       list.add(2);
       list.add(3);
       
       for (int i = 0; i < list.size(); i ++) {
    	   System.out.println(list.get(i));
       }
	}

}