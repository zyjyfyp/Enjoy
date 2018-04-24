package com.hengyushop.airplane.data;

public class AirPlaneSelectFlightData {

	public String ImgUrl;
	public String FlightName;
	public String startTime;
	public String endTime;
	public String startAddress;
	public String endAddress;
	public String money;
	public String discount;
	public int type;

	public AirPlaneSelectFlightData(String flightName, int type) {

		this.FlightName = flightName;
		this.type = type;
	}

}
