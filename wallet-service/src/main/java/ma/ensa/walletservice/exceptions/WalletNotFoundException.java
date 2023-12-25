package ma.ensa.walletservice.exceptions;



public class WalletNotFoundException extends NotFoundException {

    public WalletNotFoundException(){
        super("WALLET NOT FOUND");
    }

}
