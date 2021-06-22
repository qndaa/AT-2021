package game;

import java.io.Serializable;

public class Game implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Team team;
	public int points;
	
	public Game() {
		// TODO Auto-generated constructor stub
	}
	
	public Game(Team team, int points) {
		this.team = team;
		this.points = points;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + points;
		result = prime * result + ((team == null) ? 0 : team.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		if (points != other.points)
			return false;
		if (team != other.team)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Game [team=" + team + ", points=" + points + "]";
	}
	
	

}
