package com.ajira.network.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.springframework.stereotype.Service;

import com.ajira.network.entity.Device;
import com.ajira.network.entity.DeviceTypes;

@Service
public class AjiraServiceImplementation implements NetworkService {	
	
private Map<String, Device> deviceList = new HashMap<>();

private Map<String, ArrayList<Device>> connectedNodes = new HashMap<>();

Stack<String> routePath = new Stack<String>(); 

String path ="";

@Override
public String addDevice(String name, String type) {

	if (deviceList.containsKey(name)) {
		return "Device already exists";
	}
	else if ((name.equals(null) || type.equals(null) ) && (name.equals("") || type.equals(""))) {
		return  "Invalid Command";
	} else if (DeviceTypes.valueOf(type) == null) {
		return "Type not supported";
	} else {
		Device d = new Device(name, DeviceTypes.valueOf(type));
		deviceList.put(name, d);
		return "Successfully added "+name;
	}

}

public String modifyStrength(String deviceName, int strength, boolean flag) {
	Device d = deviceList.get(deviceName);
	if(strength < 0)
		return "Strength is negative";
	
	else if (!deviceList.containsKey(deviceName)) {
		return "Device not found";
	}
	
	else if(d.getType().toString().equals("REPEATER") && !flag) {
		d.setStrength(strength);
	  return "Strength cannot be defined for Repeater";
	}

	else {
		d.setStrength(strength);
		return "Successfully defined strength";
	}
}

public String listDevices() {
	return deviceList.values().toString();
}

public String connectDevice(String source, List<String> targetList) {
	if (source.equals("") || targetList.isEmpty()) {
		return "Invalid Command";
	}
	else if (!deviceList.containsKey(source)) {
		return "Node not found";
	}
	else if(targetList.contains(source))
	{
		targetList.remove(source);
		 return "Cannot connect device to itself";
	}
	else {
		List<Device> targetDeviceList = new ArrayList<>();
		
		for(int i=0;i<targetList.size();i++)
		{
			targetDeviceList.add(deviceList.get(targetList.get(i)));
		}
		
		ArrayList<Device> connectedDeviceList = connectedNodes.getOrDefault(source, new ArrayList<>());
		connectedDeviceList.addAll(targetDeviceList);
		connectedNodes.put(source, connectedDeviceList);
		
		for(int i=0;i<targetDeviceList.size();i++)
		{
			connectedDeviceList = connectedNodes.getOrDefault(targetDeviceList.get(i).getName(), new ArrayList<>());
			connectedDeviceList.add(deviceList.get(source));
			connectedNodes.put(targetDeviceList.get(i).getName(), connectedDeviceList);
			
		}
		 return "Successfully connected";
	}

}


void printRoutePath()
{
	
	for(int i=0;i<routePath.size()-1;i++)
	{
		path+=routePath.get(i)+" -> ";
	}
	path+=routePath.get(routePath.size()-1);
}

void DFSUtil(String s, String t, Map<String, Boolean> visited, int sourceStrength) {
	
	   if(sourceStrength <= 0)
		   	return;
	   
		routePath.push(s);
		visited.put(s, true);
		Device d = deviceList.get(s);
		
		//System.out.println("initial sourceStrength -- "+d.getStrength());
		
		if(d.getType().toString().equals("REPEATER"))
			modifyStrength(d.getName(),d.getStrength()*2,true);
			
		//System.out.println("final sourceStrength -- "+d.getStrength());

		if (s.equals(t)) {
			// found the ans
			printRoutePath();
			return;
		}
		
			ArrayList<Device> x = connectedNodes.get(s);
			
			if(x!=null )
			{
				for (int i = 0; i < x.size(); i++) {
					if (x.get(i).getName().equals(t))
					{
						routePath.push(t);
						 printRoutePath();
						 return;
					}
						
				}
				
				for (int i = 0; i < x.size(); i++) {
					if (!visited.get(x.get(i).getName()) && sourceStrength > 0)
						DFSUtil(x.get(i).getName(), t, visited, sourceStrength-1);
				}
				
			}
			
			routePath.pop();
			
	
}

void DFS(String s, String t) {
	if (s.equals(t)) {
		routePath.push(s);
		routePath.push(t);
		return;
	}

	// Mark all the vertices as not visited
	Map<String, Boolean> visited = new HashMap<>();
	for(Map.Entry<String, Device> entry : deviceList.entrySet()) {
		visited.put(entry.getKey(),false);
		}
	int sourceStrength = deviceList.get(s).getStrength();
	DFSUtil(s, t, visited, sourceStrength);
}

public String routeInfo(String s, String t) {
	if (s == "" || t == " ") {
		return "Invalid Request";
	}
	boolean RepeaterFound = false;
	
	if(deviceList.get(s).getType().toString().equals("REPEATER") || deviceList.get(t).getType().toString().equals("REPEATER"))
		RepeaterFound = true;
	
	if (RepeaterFound) {
		return "Route cannot be calculated with repeater";
	}

	DFS(s, t);
	if(routePath.empty())
		return "Route not found";
	else
		return  path ;

}

}
