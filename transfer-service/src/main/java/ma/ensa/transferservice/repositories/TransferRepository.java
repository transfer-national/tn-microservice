package ma.ensa.transferservice.repositories;

import jakarta.transaction.Transactional;
import ma.ensa.transferservice.dto.TransferResume;
import ma.ensa.transferservice.models.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
public interface TransferRepository extends JpaRepository<Transfer, Long> {


    List<Transfer> getAllByGroupId(long groupId);

    @Query(
        "SELECT COUNT(t) AS count, SUM(t.amount) AS totalAmount " +
        "FROM Transfer t " +
        "JOIN TransferStatusDetails s ON s.transfer = t " +
        "WHERE t.sender.ref = :ref " +
        "AND s.status = 0 " +
        "AND s.updatedAt >= :lastDays"
    )
    TransferResume getTransferResume(
            @Param("ref") long ref,
            @Param("lastDays") LocalDateTime lastDays
    );

}
