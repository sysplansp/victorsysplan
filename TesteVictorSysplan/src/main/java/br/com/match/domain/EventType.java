package br.com.match.domain;

public enum EventType {
	
    
	
	START("New match"), END("has ended"), KILL("killed");
	
	private String text;
	 
	EventType(String text) {
		this.text = text;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

}
