package ma.ensa.walletservice.repositories;

import jakarta.transaction.Transactional;
import ma.ensa.walletservice.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Transactional
public interface WalletRepository
        extends JpaRepository<Wallet, String> {

    @Query("SELECT w.balance FROM Wallet w WHERE w.id = :id")
    Optional<Double> findBalanceById(@Param("id") String id);


    @Modifying
    @Query("UPDATE Wallet w SET w.balance = w.balance + :amount " +
            "WHERE w.id = :id")
    void updateBalance(
            @Param("id") String walletId,
            @Param("amount") Double amount
    );
}
