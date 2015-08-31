package br.com.match.domain;


public class Gun {
	
//	private static Map<String, Gun> guns = new HashMap<String, Gun>();
	
	private String name;
	
	private int gunNumber;
	
//	public static Gun getGun(String name) {
//		Gun newGun = guns.get(name);
//		if(newGun == null) {
//			newGun = new Gun(name);
//			guns.put(name, newGun);
//		}
//		return newGun;
//	}
	
	public Gun(String name) {
		this.name = name;
		this.gunNumber = 0;
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

	public void increaseGunNumber() {
		gunNumber++;
	}

	/**
	 * @return the gunNumber
	 */
	public int getGunNumber() {
		return gunNumber;
	}

}
