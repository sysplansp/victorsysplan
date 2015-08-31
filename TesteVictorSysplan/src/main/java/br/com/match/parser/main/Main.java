package br.com.match.parser.main;

import br.com.match.domain.Match;
import br.com.match.parser.MatchLogParser;

public class Main {
	
	public static void main(String[] args) {
		MatchLogParser parser = new MatchLogParser();
		if (args.length > 0) {
			parser.parserFilePath(args[0]);
			Match match = parser.getMatch();
			match.printResult();
		} else {
			System.out.println("Please set the log file path");
		}
	}

}
