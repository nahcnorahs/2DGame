package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Pokemon extends SuperObject {

	public OBJ_Pokemon() {

		name = "Pokemon";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/snorlax.png"));
		}

		catch (IOException e) {
			e.printStackTrace();
		}

		collision = true;
	}
}
