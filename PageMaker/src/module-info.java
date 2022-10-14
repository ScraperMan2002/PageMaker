module PageMaker {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.web;
	requires javafx.base;
	requires java.xml;
	requires java.net.http;
	requires jdk.compiler;
	requires javafx.graphics;
	requires java.datatransfer;
	requires java.desktop;

	opens application to javafx.graphics, javafx.fxml;
}
