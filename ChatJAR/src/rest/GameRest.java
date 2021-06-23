package rest;

import java.util.List;

import javax.ejb.Remote;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import game.Game;
import models.AID;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface GameRest {
	
	

	@GET
	@Path("/result/{team1}/{team2}")
	public void getResult(@PathParam("team1") String team1, @PathParam("team2") String team2);
	
	@POST
	@Path("/start/spider")
	public List<Game> startSpider();
	
	@GET
	@Path("/save")
	public void saveSearch(List<Game> games);
	
	@GET
	@Path("/prediction")
	public void startPrediction();
	
	@POST
	@Path("/getData")
	public List<Game> getData(AID aid);
	
}
