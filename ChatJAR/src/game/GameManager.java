package game;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface GameManager {

	
	public void concanate(List<Game> games);
	public void reset();
	public List<Game> getGames();
}
