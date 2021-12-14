package kr.hyunnn.iot001.domain.mqtt;

 import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMqttRecordsRepository extends CrudRepository<MqttRecordsEntity, Long>{


}
