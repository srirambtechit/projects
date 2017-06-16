package com.msrm.sqlrunner.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class TomcatUtil {

	public static boolean restartTomcat(String catalinaHome) {
		String startCommand = catalinaHome + File.separator + "bin" + File.separator;
		String stopCommand = catalinaHome + File.separator + "bin" + File.separator;

		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			startCommand += "catalina.bat start";
			stopCommand += "catalina.bat stop";
		} else {
			startCommand += "catalina.sh start";
			stopCommand += "catalina.sh stop";
		}

		Process stopProcess, startProcess;
		try {
			stopProcess = Runtime.getRuntime().exec(stopCommand);
			stopProcess.waitFor(180, TimeUnit.SECONDS);

			InputStream errStream = stopProcess.getErrorStream();
			int bytes = errStream.available();

			// no error occurred while stopping Tomcat Server
			if (bytes == 0 && stopProcess.exitValue() == 0) {
				startProcess = Runtime.getRuntime().exec(startCommand);
				startProcess.waitFor(180, TimeUnit.SECONDS);

				int startError = startProcess.getErrorStream().available();
				return startError == 0;
			}
			return false;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}

}
