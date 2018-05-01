package com.example.part_3.part3_extra_wrapping_blocking_jpa_optional.impl;

import com.example.annotations.Complexity;
import com.example.annotations.Optional;
import com.example.part_3.part3_extra_wrapping_blocking_jpa_optional.ConnectionsPool;
import com.example.part_3.part3_extra_wrapping_blocking_jpa_optional.Payment;
import com.example.part_3.part3_extra_wrapping_blocking_jpa_optional.PaymentsHistoryReactiveJpaRepository;
import java.util.concurrent.ForkJoinPool;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import static com.example.annotations.Complexity.Level.HARD;

public class ReactivePaymentsHistoryJpaRepositoryAdapter implements PaymentsHistoryReactiveJpaRepository {
	private BlockingPaymentsHistoryJpaRepository repository;
    Scheduler scheduler;

	public ReactivePaymentsHistoryJpaRepositoryAdapter(BlockingPaymentsHistoryJpaRepository repository) {
		this.repository = repository;
        int connectionPoolSize = ConnectionsPool.instance().size();
        this.scheduler = Schedulers.newParallel("jdbc-scheduler", connectionPoolSize);
        this.scheduler = Schedulers.fromExecutor(new ForkJoinPool(ConnectionsPool.instance().size()));
    }

	@Optional
	@Complexity(HARD)
	public Flux<Payment> findAllByUserId(String userId) {
		// TODO: provide asynchronous wrapping around blocking JPARepository
		// HINT: Consider provide custom singleton thread-pool with limited amount of workers
		//       ThreadCount == ConnectionsPool.size()

		int size = ConnectionsPool.instance().size();

		// we use defer, because repository.findAllByUserId blocking the queue
		// defer is waiting for subscriber before it builds the flow
//		return Flux.defer(() -> Flux.fromIterable(repository.findAllByUserId(userId)))
//				.subscribeOn(Schedulers.elastic());
        return Flux.defer(() -> Flux.fromIterable(repository.findAllByUserId(userId)))
                .subscribeOn(scheduler);

//		return Mono.fromCallable(() -> Flux.fromIterable(repository.findAllByUserId(userId)))
//				.subscribeOn(Schedulers.elastic());

//		throw new RuntimeException("Not implemented");
	}
}
