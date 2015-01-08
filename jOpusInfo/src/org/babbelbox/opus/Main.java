package org.babbelbox.opus;

import java.io.File;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		try {
			OpusInfoFactory.getInstance().parseOpusFile(new File("/home/jannis/Downloads/encoded.opus"));
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
