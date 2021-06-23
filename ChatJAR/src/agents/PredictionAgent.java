package agents;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateful;

import game.Game;
import models.ACLMessage;
import models.Performative;



@Stateful
@Remote(Agent.class)
@LocalBean
public class PredictionAgent extends XjafAgent {
	
	public String winner = "";

	@Override
	public void handleMessage(ACLMessage msg) {
		// TODO Auto-generated method stub
		if (msg.getPerformative().equals(Performative.PROPAGATE)) {
			List<Game> data = (List<Game>) msg.getContentObj();
			String content = msg.getContent();
			
			List<Game> dataTeam1 = new ArrayList<Game>();
			List<Game> dataTeam2 = new ArrayList<Game>();
			
			String tokens[] = content.split("-");
			String team1 = tokens[0];
			String team2 = tokens[1];
			System.out.println(team1);
			System.out.println(team2);
			
			int sum1 = 0;
			int sum2 = 0;
			double avg1 = 0.0;
			double avg2 = 0.0;
			
			for (Game g: data) {
				if (g.team.toString().toLowerCase().equals(team1.toLowerCase())) {
					dataTeam1.add(g);
					sum1 += g.points;
				} else if (g.team.toString().toLowerCase().equals(team2.toLowerCase())) {
					dataTeam2.add(g);
					sum2 += g.points;
				}
			}
			
			avg1 = (sum1 * 1.0) / dataTeam1.size();
			avg2 = (sum2 * 1.0) / dataTeam2.size();
			
			if (avg1 > avg2) {
				winner = team1;
			} else {
				winner = team2;
			}
			
			
			
			
			
			System.out.println(avg1);
			System.out.println(avg2);
		}
		
	}

	@Override
	public List<Game> getGames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWinner() {
		// TODO Auto-generated method stub
		return this.winner;
	}

}
