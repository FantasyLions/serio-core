package com.serio.core.annotation;

import com.serio.core.annotation.media.ArgName;

/**
 * @author zl.shi
 */
public class ArgNameEntry {
	
	@ArgName("-v")
	private String video;

	@ArgName("-a")
	private String audio;
	
	@ArgName("-c")
	private String ccio;
	
	
	@ArgName("-b")
	private String basis;
}
