package ma.ensa.pincode.repositories;

import jakarta.transaction.Transactional;
import ma.ensa.pincode.models.PinCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Transactional
public interface PinRepository extends JpaRepository<PinCode, Long> {

    @Query(
            "SELECT p FROM PinCode p WHERE p.relatedId = :relatedId " +
            "AND DATE(p.createdAt) = CURRENT_DATE " +
            "ORDER BY p.createdAt DESC LIMIT 1"
    )
    Optional<PinCode> find(
            @Param("relatedId") String relId
    );


    @Modifying
    @Query("UPDATE PinCode p SET p.validated = true WHERE p.id = :id")
    void validatePinCode(
            @Param("id") long id
    );


    List<PinCode> findAllByRelatedId(String relatedId);

}
