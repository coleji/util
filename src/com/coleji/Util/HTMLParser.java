package com.coleji.Util;

import java.util.HashMap;
import java.util.Stack;

public class HTMLParser {
	
	// These tags dont require a closing tag.  Everything else does.
	public static final HashMap<String,Boolean> SINGLETONS;
	static {
		SINGLETONS = new HashMap<String,Boolean>();
		SINGLETONS.put("area", Boolean.TRUE);
		SINGLETONS.put("base", Boolean.TRUE);
		SINGLETONS.put("br", Boolean.TRUE);
		SINGLETONS.put("col", Boolean.TRUE);
		SINGLETONS.put("command", Boolean.TRUE);
		SINGLETONS.put("embed", Boolean.TRUE);
		SINGLETONS.put("hr", Boolean.TRUE);
		SINGLETONS.put("img", Boolean.TRUE);
		SINGLETONS.put("input", Boolean.TRUE);
		SINGLETONS.put("keygen", Boolean.TRUE);
		SINGLETONS.put("link", Boolean.TRUE);
		SINGLETONS.put("meta", Boolean.TRUE);
		SINGLETONS.put("param", Boolean.TRUE);
		SINGLETONS.put("source", Boolean.TRUE);
		SINGLETONS.put("track", Boolean.TRUE);
		SINGLETONS.put("wbr", Boolean.TRUE);
	}
	
	private static final int STATE_SEARCHING = 1;
	private static final int STATE_IN_TAG = 2;
	private static final int STATE_IN_CLOSING_TAG = 3;
	private static final int STATE_IN_OPENING_TAG_NAME = 4;
	private static final int STATE_IN_CLOSING_TAG_NAME = 5;
	private static final int STATE_IN_TAG_ATTRIBUTES = 6;
	
	private Stack<String> tagStack;
	
	public boolean areAllTagsClosed(String html) {
		char[] chars = html.toCharArray();
		int state = STATE_SEARCHING;
		StringBuilder tagBuilder = null;
		tagStack = new Stack<String>();
		
		for (int i=0; i<chars.length; i++) {
			char c = chars[i];
			switch (state) {
			case STATE_SEARCHING:
				// We are outside of a tag.
				
				if (c == '<') {
					state = STATE_IN_TAG;
				} else if (c == '>') {
					return false;
				}
				break;
			case STATE_IN_TAG:
				// We passed a '<' and maybe some spaces but that's it.  Not sure yet if opening or closing tag.
				
				if (c == ' ') {
					// keep going...
				} else if (c == '/') {
					state = STATE_IN_CLOSING_TAG;
					tagBuilder = new StringBuilder();
				} else if (c == '<' || c == '>') {
					return false;
				} else {
					state = STATE_IN_OPENING_TAG_NAME;
					tagBuilder = new StringBuilder();
					tagBuilder.append(c);
				}
				break;
			case STATE_IN_CLOSING_TAG:
				// We've passed "</" so far, maybe "</  " but no letters yet.
				
				if (c == ' ') {
					// keep going....
				} else if (c == '<' || c == '/' || c == '>') {
					return false;
				} else {
					tagBuilder.append(c);
					state = STATE_IN_CLOSING_TAG_NAME;
				}
				break;
			case STATE_IN_OPENING_TAG_NAME:
				// We have seen at least one letter and no leading slash.
				
				if (c == ' ' || c == '/' || c == '>') {
					String tagName = tagBuilder.toString().toLowerCase();
					tagBuilder = null;
					if (!SINGLETONS.containsKey(tagName)) {
						tagStack.push(tagName);
					}
					if (c == '>') {
						state = STATE_SEARCHING;
					} else {
						state = STATE_IN_TAG_ATTRIBUTES;
					}
				} else if (c == '<') {
					return false;
				} else {
					tagBuilder.append(c);
				}
				break;
			case STATE_IN_CLOSING_TAG_NAME:
				// We have seen "</", maybe some whitespace, and then at least one letter.
				
				if (c == ' ' || c == '>') {
					String tagName = tagBuilder.toString().toLowerCase();
					tagBuilder = null;
					if (!tagStack.empty() && tagStack.peek().equals(tagName)) {
						tagStack.pop();
					} else {
						return false;
					}
					if (c == '>') {
						state = STATE_SEARCHING;
					} else {
						state = STATE_IN_TAG_ATTRIBUTES;
					}
				} else if (c == '<') {
					return false;
				} else {
					tagBuilder.append(c);
				}
				break;
			case STATE_IN_TAG_ATTRIBUTES:
				if (c == '>') {
					state = STATE_SEARCHING;
				} else if (c == '<') {
					return false;
				}
				break;
			}
		}
		return (state == STATE_SEARCHING && tagStack.empty());
	}
}
