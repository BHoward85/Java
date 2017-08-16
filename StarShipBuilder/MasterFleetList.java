import java.util.*;
import java.io.*;

public class MasterFleetList
{
	// Ship object, this covers all atbits of a ship, from Name to Armaments and other info
	public class Ship
	{
		// type and type code
		private String type;
		private int typeCode;
		
		// hull number counts the number of ship in ship type with the first of the set starting at 1
		private int hullNumber;
		
		// Ship class name and induvial ship name
		private String className;
		private String shipName;
		
		// if lead ship of the class, then there is no lead ship as it is its own lead ship
		private boolean isLeadOfClass;
		private Ship leadShip;
		
		// if sub class then what ship is it a subclass of
		private boolean isSubClass;
		private Ship parentShip;
		
		// is armed
		private boolean isArmed;
		private WeaponsSet armaments;
		
		public Ship()
		{
			type = null;
			hullNumber = 0;
			className = null;
			shipName = null;
			isLeadOfClass = false;
			isSubClass = false;
			leadShip = null;
			parentShip = null;
		}
		
		public Ship(String type, int hullNumber, String className, String shipName, boolean isLeadOfClass, boolean isSubClass, Ship leadShip, Ship parentShip, boolean isArmed)
		{
			this.type = type;
			this.hullNumber = hullNumber;
			this.className = className;
			this.shipName = shipName;
			this.isLeadOfClass = isLeadOfClass;
			this.isSubClass = isSubClass;
			this.leadShip = leadShip;
			this.parentShip = parentShip;
			
			this.typeCode = findTypeCode(type);
			this.isArmed = isArmed;
			
			if(isArmed)
				armaments = new WeaponsSet();
			
		}
	}
	
	// This is a weapons list object that stores all armaments of a armed ship
	public class WeaponsSet
	{
		// has set
		private boolean hasCannons;
		private boolean hasTorpedoes;
		private boolean hasDepthCharges;
		private boolean hasAAGuns;
		private boolean hasDeckCannons;
		private boolean hasMissiles;
		private boolean hasOtherWeapons;
		
		// number of set
		private int numberOfCannons;
		private int numberOfTorpedoes;
		private int numberOfDepthCharges;
		private int numberOfAAGuns;
		private int numberOfDeckCannons;
		private int numberOfMissiles;
		private int numberOfOtherWeapons;
	}
	
	// This is a induvial weapons from a WeaponsSet object
	public class weapons
	{
		private String name;
		private String type;
		private String mountType;
		private int year;
		private double weight;
	}
	
	// these are the induvial types of weapons that any ship can have
	public class cannons extends weapons
	{
		private double caliber;
		private double length;
		private double velocity;
		private double weightOfShot;
		private String loadingType;
		private boolean isRifled;
		private boolean isBrechLoading;
		private String breachType;
		private String[] ammoTypes;
		private double effectiveRange;
	}
	
	public class torpedoes extends weapons
	{
		private double diameter;
		private double length;
		private double speed;
		private warheadWeigth;
		private String engineType;
		private String guidanceSystem;
	}
	
	public class DepthCharges extends weapons
	{
		private boolean isNuclear;
		private String fuzeType;
		private String deliverySystem;
		private String detonationMechanism;
		private double warheadWeight;
		private String warheadType;
	}
	
	public class AAGuns extends weapons
	{
		private String type;
		private double caliber;
		private double length;
		private double velocity;
		private double weightOfShot;
		private int numberOfMounts;
		private boolean isAutomatic;
		private double rateOfFire;
		private String loadingType;
		private boolean isDualPurpose;
		private String[] ammoTypes;
	}
	
	public class DeckCannons extends cannon
	{
		private String cannonType;
		private boolean isQuickFiring;
		private boolean hasGunShield;
	}
	
	public class Missile extends weapons
	{
		private double diameter;
		private double length;
		private double warheadWeight;
		private String warheadType;
		private String guidanceSystem;
		private String engineType;
		private String detonationMechanism;
		private double range;
		private String lauchingEnvironment;
	}
	
	public class OtherWeapons extends weapons
	{
		private String weaponType;
	}
}