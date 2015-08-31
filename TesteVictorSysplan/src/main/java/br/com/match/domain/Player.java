package br.com.match.domain;

import java.util.ArrayList;
import java.util.List;

public class Player {
	
	
	private String name;
	
	private int killNumber;
	
	private int deadNumber;
	
	private List<Award> awards;
	
	private List<Event> events;
	
	private int bestSequence; //best kill´s sequence without die;
	
	private int currentSequence; //current sequence without die;
	
	public Player() {
		this("");
	}
	
	public Player(String name) {
		super();
		this.name = name;
		this.killNumber = 0;
		this.deadNumber = 0;
		this.awards = new ArrayList<Award>();
		this.events = new ArrayList<Event>();
		bestSequence = 0;
		currentSequence = 0;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public void increaseKillNumber() {
		killNumber++;
		
	}
	

	public void increaseDeadNumber() {
		deadNumber++;
	}

	/**
	 * @return the killNumber
	 */
	public int getKillNumber() {
		return killNumber;
	}

	/**
	 * @return the deadNumber
	 */
	public int getDeadNumber() {
		return deadNumber;
	}

	/**
	 * @return the awards
	 */
	public List<Award> getAwards() {
		return awards;
	}

	public void addAward(Award award) {
		this.awards.add(award);
	}
	
	public void addEvent(Event event) {
		this.events.add(event);
		if(EventType.KILL.equals(event.getEventType())) {
			if (event.getPlayerOne().equals(this)) {
				currentSequence++;
			} else if (event.getPlayerTwo().equals(this)) { 
				currentSequence = 0;
			}
			if (currentSequence > bestSequence) {
				bestSequence = currentSequence;
			}
			checkFiveInOneAward();
		}
	}

	private void checkFiveInOneAward() {
		int qtdKills = 0;
		List<Event> last5Kills = new ArrayList<Event>();
		if (events.size() >=5) {
			for (int i = events.size()-1; i >= 0 && qtdKills <5; i-- ) {
				if (events.get(i).getEventType().equals(EventType.KILL) && events.get(i).getPlayerOne() == this) {
					qtdKills++;
					last5Kills.add(events.get(i));
				}
			}
		}
		if (last5Kills.size() == 5) {
			long timeLast = last5Kills.get(0).getEventDate().getTime();
			long timeFirst = last5Kills.get(4).getEventDate().getTime();
			long diff = timeLast - timeFirst;
			if(diff <= (60000)) { // do primeiro ao ultimo menor ou igual a 60s
				this.addAward(Award.FIVE_IN_ONE);
			}
		}
		
	}

	public List<Event> getEvents() {
		return events;
	}

	public int getBestSequence() {
		return bestSequence;
	}

}
