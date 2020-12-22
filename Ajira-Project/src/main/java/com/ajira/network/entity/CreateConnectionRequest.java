package com.ajira.network.entity;

import java.util.List;

public class CreateConnectionRequest {
	private String source;
	private List<String> targets;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public List<String> getTargets() {
		return targets;
	}

	public void setTargets(List<String> targets) {
		this.targets = targets;
	}
	

}
