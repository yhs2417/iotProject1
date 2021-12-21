package kr.hyunnn.iot001.mqtt;

import java.time.LocalDate;

import kr.hyunnn.iot001.domain.mqtt.MqttRecordsEntity;

public class MqttRecordsResponseVO {
	private Long Id;
	private double humidity;
	private double temperature; 
	private LocalDate insertTime;
	
	public MqttRecordsResponseVO() {
			 
	}
	
	public MqttRecordsResponseVO(MqttRecordsEntity mqttRecordsEntity) {
		 this.humidity = mqttRecordsEntity.getHumidity();
		 this.temperature = mqttRecordsEntity.getTemperature();
		 this.insertTime = mqttRecordsEntity.getInsertTime();
		 this.Id = mqttRecordsEntity.getId();			 
	}
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public double getHumidity() {
		return humidity;
	}
	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public LocalDate getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(LocalDate insertTime) {
		this.insertTime = insertTime;
	}
}
