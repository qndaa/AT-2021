package game;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Stateful;


@Singleton
@LocalBean
@Remote(GameManager.class)
public class GameManagerBean implements GameManager {

	
	public List<Game> games = new ArrayList<Game>();
	
	public GameManagerBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void concanate(List<Game> g) {
		games.addAll(g);
	}

	@Override
	public void reset() {
		this.games = new ArrayList<Game>();		
	}

	@Override
	public List<Game> getGames() {
		return this.games;
	}
	
	

}
