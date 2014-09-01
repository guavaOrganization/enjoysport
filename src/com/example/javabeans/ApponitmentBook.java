package com.example.javabeans;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class ApponitmentBook {
	public Map<String, Apponitment> getAppointmentsForToday() {
		Map<String, Apponitment> map = new HashMap<String, Apponitment>();
		map.put("A", new Apponitment());
		return map;
	}

	public Map<String, Apponitment> getAppointmentsForToday(Date day) {
		Map<String, Apponitment> map = new HashMap<String, Apponitment>();
		map.put("A", new Apponitment());
		return map;
	}
}
