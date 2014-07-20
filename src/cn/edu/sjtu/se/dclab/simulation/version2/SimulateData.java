package cn.edu.sjtu.se.dclab.simulation.version2;

import java.util.List;

import org.json.JSONObject;

public interface SimulateData {
	public void create();
	public List<JSONObject> get();
}
