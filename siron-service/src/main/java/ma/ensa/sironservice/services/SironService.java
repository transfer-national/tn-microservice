package ma.ensa.sironservice.services;

public interface SironService {



    boolean isSenderBlackListed(long ref);

    boolean isRecipientBlackListed(long ref);
}
