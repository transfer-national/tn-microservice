package ma.ensa.sironservice.services;

import ma.ensa.sironservice.models.BlackListedClient;
import org.springframework.lang.Nullable;

public interface SironService {

    /**
     * check if the sender is blacklisted
     * @param ref reference of the client
     * @return {@code BlackListedClient} entity if the client is blacklisted
     * or {@code null} otherwise
     * @see BlackListedClient
     */
    @Nullable
    BlackListedClient isSenderBlackListed(long ref);

    /**
     * check if the sender is blacklisted through the transfer sender
     * @param txRef reference of the client
     * @return BlackListedClient entity if the client is blacklisted
     * or null otherwise
     * @see BlackListedClient
     */

    @Nullable
    BlackListedClient isSenderBlackListedByTransfer(long txRef);


    @Nullable
    BlackListedClient isRecipientBlackListed(long ref);
}
