package io.eventuate.tram.sagas.reactive.orchestration;

import io.eventuate.tram.sagas.orchestration.Saga;
import io.eventuate.tram.sagas.orchestration.SagaInstance;
import io.eventuate.tram.sagas.orchestration.SagaManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ReactiveSagaInstanceFactory {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  private ConcurrentMap<ReactiveSaga<?>, ReactiveSagaManager<?>> sagaManagers = new ConcurrentHashMap<>();

  public ReactiveSagaInstanceFactory(ReactiveSagaManagerFactory sagaManagerFactory, Collection<ReactiveSaga<?>> sagas) {
    for (ReactiveSaga<?> saga : sagas) {
      sagaManagers.put(saga, makeSagaManager(sagaManagerFactory, saga));
    }
  }

  public <SagaData> SagaInstance create(Saga<SagaData> saga, SagaData data) {
    SagaManager<SagaData>  sagaManager = (SagaManager<SagaData>)sagaManagers.get(saga);
    if (sagaManager == null)
      throw new RuntimeException(("No SagaManager for " + saga));
    return sagaManager.create(data);
  }

  private <SagaData> ReactiveSagaManager<SagaData> makeSagaManager(ReactiveSagaManagerFactory sagaManagerFactory, ReactiveSaga<SagaData> saga) {
    ReactiveSagaManagerImpl<SagaData> sagaManager = sagaManagerFactory.make(saga);
    sagaManager.subscribeToReplyChannel();
    return sagaManager;
  }
}
