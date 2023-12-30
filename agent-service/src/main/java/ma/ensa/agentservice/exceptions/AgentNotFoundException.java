package ma.ensa.agentservice.exceptions;

public class AgentNotFoundException extends RuntimeException {

    public AgentNotFoundException(){
        super("AGENT NOT FOUND");
    }
}
