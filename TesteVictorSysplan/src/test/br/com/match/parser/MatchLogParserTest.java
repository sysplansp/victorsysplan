package br.com.match.parser;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import br.com.match.domain.Award;
import br.com.match.domain.Event;
import br.com.match.domain.EventType;
import br.com.match.domain.Match;

public class MatchLogParserTest  {
	
	private String logData = "23/04/2013 15:34:22 - New match 11348965 has started   "   +
			"23/04/2013 15:36:04 - Roman killed Nick using M16\n"   +
			"23/04/2013 15:36:06 - Victor killed Roman using AK47\n"   +
			"23/04/2013 15:36:08 - Victor killed Nick using AK47\n"   +
			"23/04/2013 15:36:10 - Victor killed Nick using AK47\n"   +
			"23/04/2013 15:36:12 - Victor killed Nick using AK47\n"   +
			"23/04/2013 15:36:14 - Victor killed Nick using AK47\n"   +
			"23/04/2013 15:37:33 - <WORLD> killed Nick by DROWN\n"   +
			"23/04/2013 15:39:22 - Match 11348965 has ended\n"   ;
	
	
	public MatchLogParserTest() {
		
	}
	
	@Test
	public void parserStartMatchLineTest() {
		MatchLogParser parser = new MatchLogParser();
		String line = "23/04/2013 15:34:22 - New match 11348965 has started";
		Event event = parser.parserLogLine(line);
		assertEquals(event.getEventType(), EventType.START);
	}
	
	@Test
	public void parserKillMatchLineTest() {
		MatchLogParser parser = new MatchLogParser();
		String line = "23/04/2013 15:36:04 - Roman killed Nick using M16";
		Event event = parser.parserLogLine(line);
		assertEquals(event.getEventType(), EventType.KILL);
	}
	
	@Test
	public void parserPlayerOneTest() {
		MatchLogParser parser = new MatchLogParser();
		String line = "23/04/2013 15:36:04 - Roman killed Nick using M16";
		Event event = parser.parserLogLine(line);
		assertEquals(event.getPlayerOne().getName(), "Roman");
	}
	
	@Test
	public void parserPlayerTwoTest() {
		MatchLogParser parser = new MatchLogParser();
		String line = "23/04/2013 15:36:04 - Roman killed Nick using M16";
		Event event = parser.parserLogLine(line);
		assertEquals(event.getPlayerTwo().getName(), "Nick");
	}
	
	@Test
	public void parserGunTest() {
		MatchLogParser parser = new MatchLogParser();
		String line = "23/04/2013 15:36:04 - Roman killed Nick using M16";
		Event event = parser.parserLogLine(line);
		assertEquals(event.getGun().getName(), "M16");
	}
	
	@Test
	public void parserEndMatchLineTest() {
		MatchLogParser parser = new MatchLogParser();
		String line = "23/04/2013 15:39:22 - Match 11348965 has ended";
		Event event = parser.parserLogLine(line);
		assertEquals(event.getEventType(), EventType.END);
	}
	
	@Test
	public void bestGunMathTest() {
		MatchLogParser parser = new MatchLogParser();
		parser.parserLog(logData);
		Match match = parser.getMatch();
		assertEquals(match.getBestGun().getName(), "AK47");
	}
	
	@Test
	public void bestSequenceMathTest() {
		MatchLogParser parser = new MatchLogParser();
		parser.parserLog(logData);
		Match match = parser.getMatch();
		assertEquals(match.getPlayerWithBestSequence().getName(), "Victor");
	}
	
	@Test
	public void awardsQuantityMathTest() {
		MatchLogParser parser = new MatchLogParser();
		parser.parserLog(logData);
		parser.getMatch();
		List<Award> awards =  parser.getPlayer("Victor").getAwards();
		assertEquals(awards.size(), 2);
	}
	
	@Test
	public void awardsMathTest() {
		MatchLogParser parser = new MatchLogParser();
		parser.parserLog(logData);
		parser.getMatch();
		List<Award> awards =  parser.getPlayer("Victor").getAwards();
		assertEquals(awards.get(0), Award.FIVE_IN_ONE);
		assertEquals(awards.get(1), Award.INVICT_WIN);
	}
	
	

}
