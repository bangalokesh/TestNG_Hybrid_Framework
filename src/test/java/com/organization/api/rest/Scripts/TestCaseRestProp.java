package com.organization.api.rest.Scripts;

public class TestCaseRestProp {
	
	private String parentNodeComponent;

	private String parentNode;

	private String node;
	
	private String nodeComponent;
	
	private String nodeValue;

	public String getParentNodeComponent() {
		if (this.parentNodeComponent == null) {
	         return "";
	      } else {
	         return this.parentNodeComponent;
	      }
	}

	public void setParentNodeComponent(String parentNodeComponent) {
		this.parentNodeComponent = parentNodeComponent;
	}

	public String getParentNode() {
		if (this.parentNode == null) {
	         return "";
	      } else {
	         return this.parentNode;
	      }
	}

	public void setParentNode(String parentNode) {
		this.parentNode = parentNode;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getNodeComponent() {
		return nodeComponent;
	}

	public void setNodeComponent(String nodeComponent) {
		this.nodeComponent = nodeComponent;
	}

	public String getNodeValue() {
		if (this.nodeValue == null) {
	         return "";
	      } else {
	         return this.nodeValue;
	      }
	}

	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}

	

	
}
