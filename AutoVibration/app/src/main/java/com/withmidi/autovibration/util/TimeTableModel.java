package com.withmidi.autovibration.util;

public class TimeTableModel {
	private int startnum;
	private int endnum;
	private int week;
	private String name="";
	private String teacher="";
	private String classroom="";

	@Override
	public String toString() {
		return "TimeTableModel [startnum=" + startnum
				+ ", endnum=" + endnum + ", week=" + week + ", name=" + name
				+ ", teacher=" + teacher + ", classroom=" + classroom
				+ "]";
	}

	public int getStartnum() {
		return startnum;
	}

	public int getEndnum() {
		return endnum;
	}

	public int getWeek() {
		return week;
	}

	public String getName() {
		return name;
	}

	public String getTeacher() {
		return teacher;
	}

	public String getClassroom() {
		return classroom;
	}

	public void setStartnum(int startnum) {
		this.startnum = startnum;
	}

	public void setEndnum(int endnum) {
		this.endnum = endnum;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	public TimeTableModel() {
		// TODO Auto-generated constructor stub
	}

	public TimeTableModel(int startnum, int endnum, int week,
			String name, String teacher, String classroom) {
		super();
		this.startnum = startnum;
		this.endnum = endnum;
		this.week = week;
		this.name = name;
		this.teacher = teacher;
		this.classroom = classroom;
	}

}
