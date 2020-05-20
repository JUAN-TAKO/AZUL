import java.awt.*;
import java.io.File;
import java.io.IOException;

import Controller.Controller;
import Controller.Server.JavaHTTPServerLauncher;

public class Azul {
	public static void main(String[] args)  {
		Controller controller = Controller.getInstance();
		JavaHTTPServerLauncher.start();

		File htmlFile = new File("dist/index.html");

		try {
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
