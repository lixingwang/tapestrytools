package com.example.components;

import org.apache.tapestry5.annotations.Parameter;

public class Menu {
	@Parameter(name = "desc")
	private String name;
	
	@Parameter
	private String value;
	
	private String other;
}