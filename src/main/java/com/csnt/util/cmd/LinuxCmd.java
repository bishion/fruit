package com.csnt.util.cmd;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class LinuxCmd {

	public static String executeCmd(String cmd) throws IOException{
		Process process = Runtime.getRuntime().exec(cmd);
		 
        InputStreamReader ir = new InputStreamReader(process.getInputStream());
        LineNumberReader input = new LineNumberReader(ir);

        StringBuilder result = new StringBuilder();
        while ((input.readLine()) != null) {
        	result.append(input.readLine());
        }
        return result.toString();
	}
}
