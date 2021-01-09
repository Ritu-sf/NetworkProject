package com.ajira.network.entity;

public class Device {
	
	private String name;
	private DeviceTypes type;
	private  int  strength = 5;
	
	public Device(String name, DeviceTypes type)
	{
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public DeviceTypes getType() {
		return type;
	}
	
	public int getStrength() {
		return strength;
	}
	public void setStrength(int strength) {
		this.strength = strength;
	}
	
	
	public String toString() {
	return "\n{\n\t'type': '"+this.type.name()+"',\n\t'name': '"+this.name+"'\n}";
	}
	
	

}
