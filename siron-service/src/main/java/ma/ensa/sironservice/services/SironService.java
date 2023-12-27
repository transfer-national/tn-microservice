package ma.ensa.sironservice.services;

import ma.ensa.sironservice.models.BlackListedClient;

public interface SironService {

    /**
     * check if the sender is blacklisted
     * @param ref reference of the client
     * @return {@code BlackListedClient} entity if the client is blacklisted
     * or {@code null} otherwise
     * @see BlackListedClient
     */
    BlackListedClient isSenderBlackListed(long ref);

    /**
     * check if the sender is blacklisted through the transfer sender
     * @param txRef reference of the client
     * @return BlackListedClient entity if the client is blacklisted
     * or null otherwise
     * @see BlackListedClient
     */

    BlackListedClient isSenderBlackListedByTransfer(long txRef);


    BlackListedClient isRecipientBlackListed(long ref);
}
