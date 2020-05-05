import java.security.InvalidParameterException;
import Controller.Server.JavaHTTPServerLauncher;
import Model.GlobalBoard;

public class Azul {
	public static void main(String[] args) {
		GlobalBoard gb = GlobalBoard.getInstance();
		JavaHTTPServerLauncher.start();
	}
}
