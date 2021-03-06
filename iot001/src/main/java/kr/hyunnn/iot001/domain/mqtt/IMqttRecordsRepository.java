package kr.hyunnn.iot001.domain.mqtt;

 import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMqttRecordsRepository extends CrudRepository<MqttRecordsEntity, Long>{

	public List<MqttRecordsEntity> findByInsertTimeGreaterThanEqualAndInsertTimeLessThanEqualOrderByInsertTimeAsc(LocalDate x, LocalDate y);
	public List<MqttRecordsEntity> findAllByOrderByInsertTimeAsc();

	@Modifying(clearAutomatically = true)
	@Query(value = "select * from records where insert_time between ?1 AND ?2", nativeQuery=true )
	public List<MqttRecordsEntity> selectRecordsByDate(LocalDate x, LocalDate y);

}
