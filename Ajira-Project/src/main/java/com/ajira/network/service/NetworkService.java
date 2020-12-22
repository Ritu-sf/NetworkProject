package com.ajira.network.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface NetworkService {
	
	String listDevices();
	
	String addDevice(String name, String type);

	String modifyStrength(String deviceName, int strength);
	
	String connectDevice(String source, List<String> targetList);
	
	String routeInfo(String s, String t);

}
