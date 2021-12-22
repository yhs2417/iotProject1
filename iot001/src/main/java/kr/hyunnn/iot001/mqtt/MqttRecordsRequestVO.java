package kr.hyunnn.iot001.mqtt;

import java.time.LocalDate;

import kr.hyunnn.iot001.domain.mqtt.MqttRecordsEntity;

 
public class MqttRecordsRequestVO {

	//private Long Id;
	//private double humidity;
	//private double temperature; 
	private LocalDate startDate;
	private LocalDate endDate;
	
	public LocalDate getStartDate() {
		return startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
 
	
	

}
