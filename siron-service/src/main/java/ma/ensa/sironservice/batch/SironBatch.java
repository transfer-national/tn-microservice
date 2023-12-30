package ma.ensa.sironservice.batch;

import lombok.RequiredArgsConstructor;
import ma.ensa.sironservice.models.Client;
import ma.ensa.sironservice.models.Transfer;
import ma.ensa.sironservice.models.TransferStatusDetails;
import ma.ensa.sironservice.models.enums.TransferStatus;
import ma.ensa.sironservice.repositories.BlcRepository;
import ma.ensa.sironservice.repositories.TransferRepository;
import ma.ensa.sironservice.repositories.TsdRepository;
import ma.ensa.sironservice.services.SironService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.BatchingRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static ma.ensa.sironservice.models.enums.TransferStatus.*;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class SironBatch {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final RabbitTemplate rabbitTemplate;
    private final TransferRepository transferRepository;
    private final TsdRepository tsdRepository;

    @Bean
    public ItemReader<Long> readFromRabbit(){

        var message = rabbitTemplate.receive("q.siron");

        var ref = Optional.ofNullable(message)
                .map(Message::getBody)
                .map(String::new).map(Long::parseLong)
                .orElse(0L);


        return () -> ref;

    }

    @Bean
    public ItemProcessor<Long, TransferStatusDetails> processor(){

        return (ref) -> {

            var tx = transferRepository
                    .findById(ref)
                    .orElseThrow();

            var status = Optional.of(tx)
                .map(Transfer::getSender)
                .map(Client::getBlc)
                .map((f) -> BLOCKED).orElse(TO_SERVE);

            return TransferStatusDetails.builder()
                    .transfer(tx)
                    .status(status)
                    .build();
        };

    }


    @Bean
    public RepositoryItemWriter<TransferStatusDetails> writeIntoDb(){
        return new RepositoryItemWriter<>(){{
            setMethodName("save");
            setRepository(tsdRepository);
        }};
    }





    @Bean
    public Step step1(){
        return new StepBuilder("siron-save", jobRepository)
            .<Long, TransferStatusDetails>chunk(100, platformTransactionManager)
            .reader(readFromRabbit())
            .processor(processor())
            .writer(writeIntoDb())
            .build();
    }

    @Bean
    public Job job(){
        return new JobBuilder("damnman", jobRepository)
                .start(step1())
                .build();
    }



}
