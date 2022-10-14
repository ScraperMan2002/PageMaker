package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.Scanner;

public class MainController {
	@FXML
	private TextField txtBook;
	@FXML
	private TextField txtAuthor;
	@FXML
	private TextField txtYear;
	@FXML
	private TextField txtSections;
	@FXML
	private TextField txtPrefix;
	@FXML
	private TextField txtStart;
	@FXML
	private TextField txtEnd;

	@FXML
	private TextArea txaResult;

	private Scanner scnr = new Scanner("");

	private boolean isDone = true;

	private String strPrev = null;
	private String strCurrent = null;
	private String strNext = null;

	public void titleExecute() {
		String strOutput = "{{Header|title=" + txtBook.getText() + "|section=|times=";
		if (txtAuthor.getText() != null) {
			strOutput += "|author=" + txtAuthor.getText();
		}
		if (txtYear.getText() != null) {
			strOutput += "|y=" + txtYear.getText();
		}
		strOutput += "|m=|d=}}\n";
		scnr = new Scanner(txtSections.getText());
		while (scnr.hasNext()) {
			String strToken = scnr.next();
			strOutput += "*[[/" + strToken + "|" + strToken + "]]\n";
		}
		txaResult.setText(strOutput);
	}

	public void sectionExecute() {
		if (isDone) {
			isDone = false;
			scnr = new Scanner(txtSections.getText());
			strPrev = null;
			strCurrent = scnr.next();
			strNext = scnr.next();
		} else {
			strPrev = strCurrent;
			strCurrent = strNext;
			if (scnr.hasNext()) {
				strNext = scnr.next();
			} else {
				strNext = null;
			}
		}
		String strOutput = "{{Header|title=" + txtBook.getText() + "|section=" + strCurrent + "|times=";
		if (txtAuthor.getText() != null) {
			strOutput += "|author=" + txtAuthor.getText();
		}
		if (txtYear.getText() != null) {
			strOutput += "|y=" + txtYear.getText();
		}
		strOutput += "|m=|d=";
		if (strPrev != null) {
			strOutput += "|previous=[[../" + strPrev + "|" + strPrev + "]]";
		}
		if (strNext != null) {
			strOutput += "|next=[[../" + strNext + "|" + strNext + "]]";
		}
		strOutput += "}}";
		txaResult.setText(strOutput);
		if (strNext == null) {
			isDone = true;
			Alert altFinish = new Alert(AlertType.CONFIRMATION);
			altFinish.setTitle("Execution Completed");
			altFinish.setHeaderText("Contents within TextArea are copied onto clipboard.");
			altFinish.showAndWait();
		}
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(txaResult.getText()), null);
	}

	public void iteration() {
		String strOutput = " " + txtSections.getText() + " ";
		try {
			int intStart = Integer.parseInt(txtStart.getText());
			int intEnd = Integer.parseInt(txtEnd.getText());
			String strPrefix = txtPrefix.getText();
			for (int i = intStart; i <= intEnd; i++) {
				strOutput += strPrefix + strIntToChinese(i) + " ";
			}
			txtSections.setText(strOutput);
		} catch (Exception e) {
			Alert altFinish = new Alert(AlertType.ERROR);
			altFinish.setTitle("Invalid Inputs");
			altFinish.setHeaderText("Contents within Iteration Start or End are not numerals.");
			altFinish.showAndWait();
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(txaResult.getText()),
					null);
		}
	}

	private String strIntToChinese(int intValue) {
		int intIntegerPlace = 0;
		int intStrInt = intValue;
		int intStrIntTemp = intValue;
		String strReturn = "";
		while (intStrIntTemp > 0) {
			switch (intIntegerPlace) {
			case 1:
				strReturn = "十" + strReturn;
				break;
			case 2:
				strReturn = "百" + strReturn;
			}
			int intPlaceValue = intStrIntTemp % 10;
			switch (intPlaceValue) {
			case 0:
				strReturn = "零" + strReturn;
				break;
			case 1:
				strReturn = "一" + strReturn;
				break;
			case 2:
				strReturn = "二" + strReturn;
				break;
			case 3:
				strReturn = "三" + strReturn;
				break;
			case 4:
				strReturn = "四" + strReturn;
				break;
			case 5:
				strReturn = "五" + strReturn;
				break;
			case 6:
				strReturn = "六" + strReturn;
				break;
			case 7:
				strReturn = "七" + strReturn;
				break;
			case 8:
				strReturn = "八" + strReturn;
				break;
			case 9:
				strReturn = "九" + strReturn;
				break;
			}
			intIntegerPlace++;
			intStrIntTemp /= 10;
		}
		strReturn = strReturn.replace("一十", "十");
		strReturn = strReturn.replace("十零", "十");
		if ((intStrInt / 10) % 10 != 0) {
			strReturn = strReturn.replace("百零", "百");
		}
		return strReturn;
	}
}
