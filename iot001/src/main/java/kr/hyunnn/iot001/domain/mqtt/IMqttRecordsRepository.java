package kr.hyunnn.iot001.domain.mqtt;

 import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMqttRecordsRepository extends CrudRepository<MqttRecordsEntity, Long>{

	public List<MqttRecordsEntity> findByInsertTimeGreaterThanEqualAndInsertTimeLessThanEqual(LocalDate x, LocalDate y);
}
