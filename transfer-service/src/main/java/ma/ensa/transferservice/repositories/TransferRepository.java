package ma.ensa.transferservice.repositories;

import jakarta.transaction.Transactional;
import ma.ensa.transferservice.models.Transfer;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Transactional
public interface TransferRepository extends JpaRepository<Transfer, Long> {


    List<Transfer> getAllByGroupId(long groupId);

}
