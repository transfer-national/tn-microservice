package ma.ensa.transferservice.services.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ma.ensa.transferservice.models.enums.ClientType;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Log4j2
public class RestCall {


    private final LoadBalancerClient loadBalancerClient;


    private RestTemplate restTemplate;

    @PostConstruct
    public void init(){
        restTemplate = new RestTemplate();
    }

    private String getServiceUrl(String serviceId){
        return loadBalancerClient
                .choose(serviceId+"-service")
                .getUri()
                .toString();
    }

    public void callSiron(long ref, ClientType clientType){

        final String path;

        try{
            path = String.format("%s/%s/%d",
                    getServiceUrl("siron"),
                    clientType.toString().toLowerCase(),
                    ref
            );
        }catch (Exception ignored){
            log.warn("the SIRON service is DOWN");
            return;
        }

        Boolean bl = restTemplate.getForObject(
                path, Boolean.class
        );

        if(bl != null && bl){
            String em = String.format(
                    "the %s is black listed",
                    clientType.toString().toLowerCase()
            );
            throw new RuntimeException(em);
        }


    }

    public void updateAgentBalance(String agentId, double amount){


    }

    public void updateWalletBalance(long clientRef, double amount){


    }

    public void sendNotification(){

    }


    public void debitAccount() {


    }
}
