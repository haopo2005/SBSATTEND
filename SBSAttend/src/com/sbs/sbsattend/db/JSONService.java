package com.sbs.sbsattend.db;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;

import com.sbs.sbsattend.model.Leave;
import com.sbs.sbsattend.model.Person;
import com.sbs.sbsattend.model.Work;
import com.sbs.sbsattend.model.WorkHistory;

public class JSONService {

	/**
	 * 解析JSON数据
	 * 
	 * @param inStream
	 * @return
	 */
	public static List<Person> parsePerson(InputStream inStream)
			throws Exception {
		List<Person> pes = new ArrayList<Person>();
		byte[] data = StreamTool.read(inStream);
		String json = new String(data);
		JSONArray array = new JSONArray(json);

		for (int i = 0; i < array.length(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Person p = new Person(jsonObject.getString("name"),
					jsonObject.getInt("permission"), jsonObject.getInt("id"),
					jsonObject.getDouble("quotan"));
			pes.add(p);
		}
		return pes;
	}
	
	public static List<WorkHistory> parseWorkHistory(InputStream inStream) throws Exception{
		List<WorkHistory> wks = new ArrayList<WorkHistory>();
		byte[] data = StreamTool.read(inStream);
		String json = new String(data);

		if (json.length() > 2) {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				WorkHistory w = new WorkHistory(jsonObject.getString("day"),
						jsonObject.getString("night"),
						jsonObject.getString("time"));
				wks.add(w);
			}
			return wks;
		}else
		{
			return null;
		}
	}

	public static List<Work> parseWork(InputStream inStream) throws Exception {
		List<Work> wks = new ArrayList<Work>();
		byte[] data = StreamTool.read(inStream);
		String json = new String(data);

		if (json.length() > 2) {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				Work w = new Work(jsonObject.getString("origin"),
						jsonObject.getString("current"),
						jsonObject.getString("name"),
						jsonObject.getString("originshift"),
						jsonObject.getString("currentshift"),
						jsonObject.getString("oweek"),
						jsonObject.getString("cweek"),
						jsonObject.getString("reason"),
						jsonObject.getInt("approve"), jsonObject.getInt("id"));
				wks.add(w);
			}
			return wks;
		}else
		{
			return null;
		}
		
	}

	public static List<Leave> parseLeave(InputStream inStream) throws Exception {
		List<Leave> les = new ArrayList<Leave>();
		byte[] data = StreamTool.read(inStream);
		String json = new String(data);
		if (json.length() > 2) {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				Leave l = new Leave(jsonObject.getString("name"),
						jsonObject.getString("start"),
						jsonObject.getString("end"),
						jsonObject.getString("origin"),
						jsonObject.getString("current"),
						jsonObject.getString("oweek"),
						jsonObject.getString("cweek"),
						jsonObject.getString("reason"),
						jsonObject.getInt("approve"), jsonObject.getInt("id"));
				les.add(l);
			}
			return les;
		}else
		{
			return null;
		}
		
	}

	public static List<String> parseWorkInfo(InputStream inStream)
			throws Exception {
		List<String> les = new ArrayList<String>();
		byte[] data = StreamTool.read(inStream);
		String json = new String(data);
		JSONArray array = new JSONArray(json);
		for (int i = 0; i < array.length(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			les.add(jsonObject.getString("work"));
		}
		return les;
	}

	/* 解析数据库操作返回结果 */
	public static int parseResult(InputStream inStream) throws Exception {
		int flag = 0;
		byte[] data = StreamTool.read(inStream);
		String json = new String(data);
		JSONArray array = new JSONArray(json);
		JSONObject jsonObject = array.getJSONObject(0);
		flag = jsonObject.getInt("result");
		return flag;
	}
}
