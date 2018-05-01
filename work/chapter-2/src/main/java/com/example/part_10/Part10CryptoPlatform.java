package com.example.part_10;

import com.example.part_10.controller.WSHandler;
import com.example.part_10.repository.TradeRepository;
import com.example.part_10.service.CryptoService;
import com.example.part_10.service.PriceService;
import com.example.part_10.service.TradeService;
import com.example.part_10.service.external.CryptoCompareService;
import com.example.part_10.service.impl.DefaultPriceService;
import com.example.part_10.repository.impl.DefaultTradeRepository;
import com.example.part_10.service.impl.DefaultTradeService;
import com.example.part_10.utils.EmbeddedMongo;
import com.example.part_10.utils.JsonUtils;
import com.example.part_10.utils.LoggerConfigurationTrait;
import com.example.part_10.utils.NettyUtils;
import java.util.function.BiConsumer;
import org.apache.commons.lang3.math.NumberUtils;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.ipc.netty.http.server.HttpServer;
import reactor.ipc.netty.http.websocket.WebsocketInbound;
import reactor.ipc.netty.http.websocket.WebsocketOutbound;

import java.io.IOException;
import java.util.function.BiFunction;
import java.util.logging.Logger;

import static com.example.part_10.utils.HttpResourceResolver.resourcePath;

public class Part10CryptoPlatform extends LoggerConfigurationTrait {

	private static final Logger logger = Logger.getLogger("http-server");

	public static void main(String[] args) throws IOException {
		CryptoService cryptoCompareService = new CryptoCompareService();
		TradeRepository tradeRepository = new DefaultTradeRepository();
		PriceService defaultPriceService = new DefaultPriceService(cryptoCompareService);
		TradeService defaultTradeService = new DefaultTradeService(cryptoCompareService, tradeRepository);
		WSHandler handler = new WSHandler(defaultPriceService, defaultTradeService);

		EmbeddedMongo.run();
		HttpServer.create(8080)
		          .startRouterAndAwait(hsr ->
			          hsr.ws("/stream", handleWebsocket(handler))
                         .file("/favicon.ico", resourcePath("ui/favicon.ico"))
                         .file("/main.js", resourcePath("ui/main.js"))
                         .file("/**", resourcePath("ui/index.html"))
		          );
	}

	private static BiFunction<WebsocketInbound, WebsocketOutbound, Publisher<Void>> handleWebsocket(WSHandler handler) {
		return (req, res) ->
			NettyUtils.prepareInput(req)
			          .doOnNext(inMessage -> logger.info("[WS] >> " + inMessage))
			          .transform(Part10CryptoPlatform::handleRequestedAveragePriceIntervalValue)
			          .transform(handler::handle)
			          .map(JsonUtils::writeAsString)
			          .doOnNext(outMessage -> logger.info("[WS] << " + outMessage))
			          .transform(Part10CryptoPlatform::handleOutgoingStreamBackpressure)
			          .transform(NettyUtils.prepareOutbound(res));
	}

	// Visible for testing
	public static Flux<Long> handleRequestedAveragePriceIntervalValue(Flux<String> requestedInterval) {
		// TODO: input may be incorrect, pass only correct interval
		// TODO: ignore invalid values (empty, non number, <= 0, > 60)

        // OPINTA v1
//        return requestedInterval
//                .map(s -> NumberUtils.toLong(s))
//                .filter(number -> number > 0 && number <= 60);

        // OPINTA v2
        return requestedInterval
                .map(Long::parseLong)
                .filter(n -> n > 0 && n <= 60)
                .errorStrategyContinue(new BiConsumer<Throwable, Object>() {
                            @Override
                            public void accept(Throwable throwable, Object aLong) {

                            }
                        });

//        // OPINTA v2
//        Hooks.onErrorDropped(t -> System.out.println(t));
//        return requestedInterval
//                .map(Long::parseLong)
//                .filter(n -> n > 0 && n <= 60)
//                .errorStrategyContinue();


//		return Flux.never();
	}

	// Visible for testing
	public static Flux<String> handleOutgoingStreamBackpressure(Flux<String> outgoingStream) {
		// TODO: Add backpressure handling
		// It is possible that writing data to output may be slower than rate of
		// incoming output data

        return outgoingStream
                .onBackpressureBuffer();

//        return outgoingStream
//                .onBackpressureBuffer(100);

//		return outgoingStream;
	}


}
