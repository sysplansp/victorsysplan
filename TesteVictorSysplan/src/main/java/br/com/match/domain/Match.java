package br.com.match.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Match {
	
	private Long id;
	
	private Set<Player> allPlayers;
	
	private Set<Gun> allGuns;
	
	private List<Event> matchEvents;
	
	private Gun bestGun;
	
	
	public Match() {
		super();
		allPlayers = new HashSet<Player>();
		allGuns = new HashSet<Gun>();
		matchEvents =  new ArrayList<Event>(); 
	}


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * @return the allPlayers
	 */
	public Set<Player> getAllPlayers() {
		return allPlayers;
	}


	/**
	 * @param allPlayers the allPlayers to set
	 */
	public void setAllPlayers(Set<Player> allPlayers) {
		this.allPlayers = allPlayers;
	}

	/**
	 * @return the matchEvents
	 */
	public List<Event> getMatchEvents() {
		return matchEvents;
	}


	/**
	 * @param matchEvents the matchEvents to set
	 */
	public void setMatchEvents(List<Event> matchEvents) {
		this.matchEvents = matchEvents;
	}


	public void addEvent(Event event) {
		matchEvents.add(event);
		computeScores(event);
		addPlayersAndGuns(event);
	}


	private void addPlayersAndGuns(Event event) {
		if (event.getPlayerOne() != null) {
			if(!event.getPlayerOne().getName().equals("<WORLD>")) {
				this.allPlayers.add(event.getPlayerOne());				
			}
		}
		if (event.getPlayerTwo() != null) {
			this.allPlayers.add(event.getPlayerTwo());
		}
		if(event.getGun() != null) {
			this.allGuns.add(event.getGun());
		}
	}


	private void computeScores(Event event) {
		if (EventType.KILL.equals(event.getEventType())) {
			event.getPlayerOne().increaseKillNumber();
			event.getPlayerTwo().increaseDeadNumber();
			if (event.getGun() != null) {
				event.getGun().increaseGunNumber();
			}
		} else if (EventType.END.equals(event.getEventType())) {
			setBestGun();
			setInvictAward();
		}
		
	}

	private void setInvictAward() {
		for (Player player : allPlayers) {
			if (player.getDeadNumber() == 0) {
				player.addAward(Award.INVICT_WIN);
			}
			
		}
		
	}


	private void setBestGun() {
		for (Gun gun : allGuns) {
			if (bestGun != null) {
				this.bestGun = bestGun.getGunNumber() >= gun.getGunNumber() ? bestGun : gun;
			} else {
				bestGun = gun;
			}
			
		}
	}

	public void printResult() {
		for (Player player : allPlayers) {
			System.out.print(" Player name: " + player.getName());
			System.out.print(", kills number: " + player.getKillNumber());
			System.out.print(", Dead number: " + player.getDeadNumber());
			System.out.print(", Awards: ");
			for (Award award : player.getAwards()) {
				System.out.print(award + ", ");
			}
			System.out.println();
		}
		System.out.println(" Best Gun is:  " + bestGun.getName());
		Player bestSequencePlayer = getPlayerWithBestSequence();
		System.out.println(" Best Sequence Player is:  " + bestSequencePlayer.getName() + " with: " + bestSequencePlayer.getBestSequence()+ " kills");
	}
	
	public Player getPlayerWithBestSequence() {
		Player bestSequencePlayer = null;
		for (Player player : allPlayers) {
			if (bestSequencePlayer != null) {
				bestSequencePlayer = bestSequencePlayer.getBestSequence() >= player.getBestSequence() ? bestSequencePlayer : player;
			} else {
				bestSequencePlayer = player;
			}
		}
		return bestSequencePlayer;
	}


	/**
	 * @return the allGuns
	 */
	public Set<Gun> getAllGuns() {
		return allGuns;
	}


	/**
	 * @param allGuns the allGuns to set
	 */
	public void setAllGuns(Set<Gun> allGuns) {
		this.allGuns = allGuns;
	}


	/**
	 * @return the bestGun
	 */
	public Gun getBestGun() {
		return bestGun;
	}

}
