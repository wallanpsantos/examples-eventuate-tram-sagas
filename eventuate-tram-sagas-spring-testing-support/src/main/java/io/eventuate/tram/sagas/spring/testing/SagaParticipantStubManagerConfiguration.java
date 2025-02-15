package io.eventuate.tram.sagas.spring.testing;

import io.eventuate.tram.commands.common.CommandNameMapping;
import io.eventuate.tram.commands.consumer.CommandReplyProducer;
import io.eventuate.tram.messaging.consumer.MessageConsumer;
import io.eventuate.tram.sagas.testing.SagaParticipantChannels;
import io.eventuate.tram.sagas.testing.SagaParticipantStubManager;
import io.eventuate.tram.spring.commands.producer.TramCommandProducerConfiguration;
import io.eventuate.tram.spring.events.publisher.TramEventsPublisherConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({TramCommandProducerConfiguration.class, TramEventsPublisherConfiguration.class,})
public class SagaParticipantStubManagerConfiguration {

  @Bean
  public SagaParticipantStubManager sagaParticipantStubManager(SagaParticipantChannels sagaParticipantChannels,
                                                               MessageConsumer messageConsumer,
                                                               CommandNameMapping commandNameMapping,
                                                               CommandReplyProducer commandReplyProducer) {
    return new SagaParticipantStubManager(sagaParticipantChannels, messageConsumer, commandNameMapping, commandReplyProducer);
  }

}
