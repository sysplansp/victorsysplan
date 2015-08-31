package br.com.match.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.match.domain.Event;
import br.com.match.domain.EventType;
import br.com.match.domain.Gun;
import br.com.match.domain.Match;
import br.com.match.domain.Player;

public class MatchLogParser {
	
	private static final int BUFFER_LENGTH = 1024;
	
	private Match match;
	
	private Map<String, Player> players = null;
	
	private Map<String, Gun> guns = null;
	
	
	public MatchLogParser() {
		players = new HashMap<String, Player>();
		guns = new HashMap<String, Gun>();
	}
	
	protected Player getPlayer(String name) {
		Player newPlayer = players.get(name);
		if(newPlayer == null) {
			newPlayer = new Player(name);
			players.put(name, newPlayer);
		}
		return newPlayer;
	}
	
	protected Gun getGun(String name) {
		Gun newGun = guns.get(name);
		if(newGun == null) {
			newGun = new Gun(name);
			guns.put(name, newGun);
		}
		return newGun;
	}
	
	public void parserFilePath(String pathFileLog) {
		String logString =readLogFile(pathFileLog);
		parserLog(logString);
	}
	
	public void parserLog(String logString) {
		String[] lines = logString.split("\\n");
		for (String line : lines) {
			Event event = null;
			if (line.trim().length() > 0) {
				event = parserLogLine(line);
				setEventInMatch(event);
			}
		}
	}

	private String readLogFile(String pathFileLog) {
		File file = new File(pathFileLog);
		StringBuilder sb = new StringBuilder();
		try {
			byte[] buffer = new byte[BUFFER_LENGTH];
			FileInputStream fis = new FileInputStream(file);
			int readed = 0 ;
			
			do {
				readed = fis.read(buffer);
				sb.append(new String(buffer,0,readed));
			} while(readed == BUFFER_LENGTH);
			fis.close();
			 
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File Not Found");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	protected Event parserLogLine(String line) {
		Event event = new Event();
		setEventDate(event, line);
		setEventType(event, line);
		setEventsAttributes(event, line);
		return event;
	}
	
	private void setEventsAttributes(Event event, String line) {
		String[] attributes = line.split(" ");
		if (EventType.KILL.equals(event.getEventType())) {
			for (int i = 0; i < attributes.length; i++) {				
				if (EventType.KILL.getText().equalsIgnoreCase(attributes[i])) {
					Player playerOne = getPlayer(attributes[i-1]);  
					Player playerTwo = getPlayer(attributes[i+1]);
					event.setPlayerOne(playerOne);
					event.setPlayerTwo(playerTwo);
					playerOne.addEvent(event);
					playerTwo.addEvent(event);
					i++;
				} else if ("using".equalsIgnoreCase(attributes[i])) {
					Gun gun = getGun(attributes[i+1]);
					event.setGun(gun);
					i++;
				}
			}
		}
	}

	private void setEventInMatch(Event event) {
		if (event.getEventType().equals(EventType.START)) {
			this.match = new Match();
			this.match.addEvent(event);
		} else if(match != null) {
			this.match.addEvent(event);
		} else {
			System.out.println("Ignoring events before START");
		}
		
	}

	private void setEventType(Event event, String line) {
		if (line.indexOf(EventType.START.getText()) > -1) {
			event.setEventType(EventType.START);
		} else if (line.indexOf(EventType.END.getText()) > -1) {
			event.setEventType(EventType.END);
		} else if (line.indexOf(EventType.KILL.getText()) > -1) {
			event.setEventType(EventType.KILL);
		}
	}

	private void setEventDate(Event event, String line) {
		String dateString = line.split("-")[0];
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {
			Date eventDate = sdf.parse(dateString);
			event.setEventDate(eventDate);
		} catch (ParseException e) {
			System.out.println("Invalid line format");
			return;
		}
	}

	/**
	 * @return the match
	 */
	public Match getMatch() {
		return match;
	}

}
