package Controller;

import java.security.InvalidParameterException;

import Controller.Controller;
import Controller.Server.JavaHTTPServerLauncher;

public class Main {
	public static void main(String[] args) {
		Controller controller = Controller.getInstance();
		JavaHTTPServerLauncher.start();
	}
}
