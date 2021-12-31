package kr.hyunnn.iot001.domain.mqtt;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="Records")
public class MqttRecordsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MQQT_SEQ")
    @SequenceGenerator(sequenceName = "MQQT_SEQ", allocationSize = 1, name = "MQQT_SEQ")
	private Long Id;
	
	@Column(columnDefinition = "Number", nullable=false)
	private double humidity;
	
	@Column(columnDefinition = "Number", nullable=false)
	private double temperature; 
	
	@CreationTimestamp
	private LocalDate insertTime;

	@UpdateTimestamp
	private LocalDate updateTime;

	public MqttRecordsEntity(double humidity, double temperature) {

		this.humidity = humidity;
		this.temperature = temperature;
	}

	public MqttRecordsEntity() {
		 
	}

	public Long getId() {
		return Id;
	}

	public double getHumidity() {
		return humidity;
	}

	public double getTemperature() {
		return temperature;
	}

	public LocalDate getInsertTime() {
		return insertTime;
	}

	public LocalDate getUpdateTime() {
		return updateTime;
	}

}
