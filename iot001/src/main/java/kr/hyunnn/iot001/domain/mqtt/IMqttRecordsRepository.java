package kr.hyunnn.iot001.domain.mqtt;

 import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMqttRecordsRepository extends CrudRepository<MqttRecordsEntity, Long>{

	public List<MqttRecordsEntity> findByInsertTimeGreaterThanEqualAndInsertTimeLessThanEqual(LocalDate x, LocalDate y);

	@Modifying(clearAutomatically = true)
	@Query(value = "select id, humidity, temperature, to_char(insert_time, 'MM-DD') as insert_time from records where insert_time >=TO_DATE(?1, 'YYYY-MM-DD') AND insert_time <=TO_DATE(?2, 'YYYY-MM-DD')", nativeQuery=true )
	public List<MqttRecordsEntity> selectRecordsByDate(LocalDate x, LocalDate y);

}
