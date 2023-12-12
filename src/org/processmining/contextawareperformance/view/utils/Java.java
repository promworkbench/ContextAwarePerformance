package org.processmining.contextawareperformance.view.utils;

public class Java {

	public static double JAVA_VERSION = getVersion();

	static double getVersion() {
		String version = System.getProperty("java.version");

		int pos = version.indexOf('.');
		pos = version.indexOf('.', pos + 1);
		double dblVersion = Double.parseDouble(version.substring(0, pos));

		if (dblVersion < 1.8) {
			System.out.println("You're running java version " + version);
			System.out.println("Some features might not be available.");
		}

		return dblVersion;
	}

}
