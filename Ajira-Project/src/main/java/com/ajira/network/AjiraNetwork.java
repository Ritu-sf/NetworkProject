//package com.ajira.network;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Stack;
//
//import com.ajira.network.entity.Device;
//import com.ajira.network.entity.DeviceTypes;
//
//public class AjiraNetwork {
//
//	private Map<String, Device> deviceList = new HashMap<>();
//
//	private Map<String, ArrayList<Device>> connectedNodes = new HashMap<>();
//	
//	Stack<String> routePath = new Stack<String>(); 
//
//	void addDevice(String name, String type) {
//
//		if (deviceList.containsKey(name)) {
//			// Device already exists
//		}
//		else if (name.equals("") || type.equals("")) {
//			// Invalid Command
//		} else if (DeviceTypes.valueOf(type) == null) {
//			// Type not supported
//		} else {
//			Device d = new Device(name, DeviceTypes.valueOf(type));
//			deviceList.put(name, d);
//		}
//
//	}
//
//	void modifyStrength(String deviceName, int strength) {
//		if (!deviceList.containsKey(deviceName)) {
//			// Device not found
//		}
//
//		else {
//
//			Device d = deviceList.get(deviceName);
//			d.setStrength(strength);
//		}
//	}
//
//	String listDevices() {
//		return deviceList.values().toString();
//	}
//	
//	void connectDevice(String source, ArrayList<String> targetList) {
//		if (source.equals("") || targetList.isEmpty()) {
//			// Invalid Command
//		}
//		else if (!deviceList.containsKey(source)) {
//			// Node not found
//		}
//		if(targetList.contains(source))
//		{
//			targetList.remove(source);
//			// "Cannot connect device to itself"
//		}
//		List<Device> targetDeviceList = new ArrayList<>();
//		
//		for(int i=0;i<targetList.size();i++)
//		{
//			targetDeviceList.add(deviceList.get(targetList.get(i)));
//		}
//		 
//		ArrayList<Device> connectedDeviceList = connectedNodes.getOrDefault(source, new ArrayList<>());
//		connectedDeviceList.addAll(targetDeviceList);
//		connectedNodes.put(source, connectedDeviceList);
//		//Map<String, Boolean> visited = new HashMap<>();
//		for(int i=0;i<targetDeviceList.size();i++)
//		{
//			//deviceList.get(targetDeviceList.get(i).getName());
//			
//			connectedDeviceList = connectedNodes.getOrDefault(targetDeviceList.get(i).getName(), new ArrayList<>());
//			connectedDeviceList.add(deviceList.get(source));
//			connectedNodes.put(targetDeviceList.get(i).getName(), connectedDeviceList);
//			//System.out.println(routePath.get(i)+" -> ");
//		}
//
//	}
//	
//	
//	void printRoutePath()
//	{
//		for(int i=0;i<routePath.size();i++)
//		{
//			System.out.println(routePath.get(i)+" -> ");
//		}
//	}
//	
//	void DFSUtil(String s, String t, Map<String, Boolean> visited) {
//		routePath.push(s);
//		visited.put(s, true);
//		
//		if (s.equals(t)) {
//			// found the ans
//			printRoutePath();
//			return;
//		}
//		
//		
//			ArrayList<Device> x = connectedNodes.get(s);
//			
//			if(x!=null )
//			{
//				for (int i = 0; i < x.size(); i++) {
//					if (x.get(i).getName().equals(t))
//					{
//						routePath.push(t);
//						 printRoutePath();
//						 return;
//					}
//						
//				}
//				
//				for (int i = 0; i < x.size(); i++) {
//					if (!visited.get(x.get(i).getName()))
//						DFSUtil(x.get(i).getName(), t, visited);
//				}
//				
//			}
//			routePath.pop();
//	}
//
//	void DFS(String s, String t) {
//		if (s.equals(t)) {
//			routePath.push(s);
//			routePath.push(t);
//			return;
//		}
//
//		// Mark all the vertices as not visited
//		Map<String, Boolean> visited = new HashMap<>();
//		for(Map.Entry<String, Device> entry : deviceList.entrySet()) {
//			visited.put(entry.getKey(),false);
//			}
//		
//		DFSUtil(s, t, visited);
//	}
//	
//	void routeInfo(String s, String t) {
//		if (s == "" || t == " ") {
//			System.out.println("Invalid Request");
//		}
//		boolean RepeaterFound = false;
//		
//		for(Map.Entry<String, Device> entry : deviceList.entrySet()) {
//			if((entry.getValue().getName().equals(s) || entry.getValue().getName().equals(s)) && entry.getValue().getType().equals("Repeater")) {
//				RepeaterFound = true;
//			}
//			}
//		
//		
//		if (RepeaterFound) {
//			System.out.println("Route cannot be calculated with repeater");
//		}
//
//		DFS(s, t);
//		if(routePath.empty())
//			System.out.println("Route not found");
//
//	}
//	
//	public static void main(String args[]) {
//		
//		//System.out.println("Enter device name");
//		
//		AjiraNetwork d = new AjiraNetwork();
//		
//		d.addDevice("A1","COMPUTER");
//		d.addDevice("A2","COMPUTER");
//		d.addDevice("A3","COMPUTER");
//		d.addDevice("A4","COMPUTER");
//		d.addDevice("A5","COMPUTER");
//		d.addDevice("A6","COMPUTER");
//		d.addDevice("A7","COMPUTER");
//		d.addDevice("R1","REPEATER");
//		d.modifyStrength("A1", 10);
//		ArrayList<String> l = new ArrayList<>();
//		l.add("A2");
//		l.add("A3");
//		d.connectDevice("A1", l);
//		l.clear();
////		l.add(d.deviceList.get("R1"));
////		d.connectDevice("A3", l);
////		l.clear();
////		l.add(d.deviceList.get("A4"));
////		d.connectDevice("R1", l);
////		l.clear();
////		l.add(d.deviceList.get("A5"));
////		l.add(d.deviceList.get("A6"));
////		d.connectDevice("A4", l);
////		l.clear();
////		l.add(d.deviceList.get("A7"));
//		d.connectDevice("A6", l);
//		System.out.println(d.listDevices());
//		//d.routeInfo("A2", "A3");
//		d.routeInfo("A7", "A1");
//		for(Map.Entry<String, ArrayList<Device>> entry : d.connectedNodes.entrySet()) {
//			System.out.println("Key :: " + entry.getKey());
//			for(Device d1 : entry.getValue()) {
//				System.out.println(d1.getName()+" : "+d1.getStrength()+" : "+d1.getType());
//			}
//			
//		}
//		
//		
//	}
//
//}
