package com.rateLimiter.ratelimitersolution.resource;

import com.rateLimiter.ratelimitersolution.service.IRateLimitingService;
import io.reactivex.Flowable;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.reactive.streams.api.CamelReactiveStreams;
import org.apache.camel.component.reactive.streams.api.CamelReactiveStreamsService;
import org.apache.camel.component.reactive.streams.util.UnwrapStreamProcessor;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.support.ExchangeHelper;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ApiResource extends RouteBuilder {

    @Value("${api.path}")
    String contextPath;

    @Autowired
    IRateLimitingService rateLimitingService;

    @Autowired
    private ProducerTemplate producerTemplate;


    //configure for a simple route
    @Override
    public void configure() throws Exception {
        CamelContext context = new DefaultCamelContext();
        CamelReactiveStreamsService rsCamel = CamelReactiveStreams.get(context);
        /*restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json);*/
        //TestProcessor tp = new TestProcessor();
        from("servlet:hello")
                .bean(rateLimitingService, "helloMovieFromDifferentSource")
                .process(new UnwrapStreamProcessor())
                .to("direct:persistence")
                .end();
        from("direct:persistence").routeId("PersistenceRoute")
                .errorHandler(defaultErrorHandler().disableRedelivery())
                .log( "Persist person ${body}").doTry()
                //.process(savePersonProcessor)
                .log( "Persist person ${body} SUCCESS")
                .doCatch(Exception.class)
                .log(
                        "Error saving person ${body}");
        /*from("servlet:hello").log("from servlet hello")
                .to("http:localhost:8082/movies/helloMovie?bridgeEndpoint=true").log("to direct camel");*/
       /* FluentProducerTemplate fluentTemplate = context.createFluentProducerTemplate();
        fluentTemplate.send();
        Publisher<Exchange> publisher = rsCamel.from("servlet:hello");

        Flux.from(publisher)
                // call the direct:inbox Camel route from within this flow
               // .doOnNext(e -> rsCamel.to("direct:inbox", e))
                // filter out files which has Camel in the text
             //   .filter(e -> e.getIn().getBody(String.class).contains("Camel"))
                // let Camel also be subscriber by the endpoint direct:camel
                .subscribe(rsCamel.subscriber("http:localhost:8082/movies/helloMovie?bridgeEndpoint=true"));*/


        /*from("direct:persistence").routeId("PersistenceRoute")
                .errorHandler(defaultErrorHandler().disableRedelivery())
                .log( "Persist person ${body}").doTry()
                //.process(savePersonProcessor)
                .log( "Persist person ${body} SUCCESS")
                .doCatch(Exception.class)
                .log(
                        "Error saving person ${body}");*/
       /*rest().get("/hello")bridgeEndpoint
                .to("direct:hello");*/
        //context.setStreamCaching(true);
      /* rest().get("/hello").produces(MediaType.APPLICATION_JSON_VALUE).route().log("Hitting hello URI")
               .multicast().parallelProcessing()
                .to("http:localhost:8082/movies/helloMovie?bridgeEndpoint=true").log("Done the call");*/
        /*from("servlet:hello")
                .bean(rateLimitingService, "helloMovieFromDifferentSource")
                .process(new UnwrapStreamProcessor())
                .split().body().streaming()
                .to("direct:persistence")
                .end();
        from("direct:persistence").routeId("PersistenceRoute")
                .errorHandler(defaultErrorHandler().disableRedelivery())
                .log( "Persist person ${body}").doTry()
                //.process(savePersonProcessor)
                .log( "Persist person ${body} SUCCESS")
                .doCatch(Exception.class)
                .log(
                        "Error saving person ${body}");*/
        //  .bean(new ExceptionRethrower()).endDoTry().end();*/
       /* Future<Object> future = producerTemplate.asyncRequestBody("direct:name", "Hello World");

        String response = producerTemplate.extractFutureBody(future, String.class);


        from("direct:persistence").routeId("PersistenceRoute")
                .errorHandler(defaultErrorHandler().disableRedelivery())
                .log( "Persist person ${body}").doTry()
                //.process(savePersonProcessor)
                .log( "Persist person ${body} SUCCESS")
                .doCatch(Exception.class)
                .log(
                        "Error saving person ${body}");
              //  .bean(new ExceptionRethrower()).endDoTry().end();*/


    }

    private ExecutorService buildExecutor() {
        return getContext().getExecutorServiceManager()
                .newThreadPool(this, "Main thread pool", 4, 4);
    }

    //configure for a reactive route

    /*@Override
    public void configure() throws Exception {
        //Flux<Integer> reactive = Flux.just(1, 2, 3, 4);
        *//*rest().get("/hello")
                .to("reactive-streams:reactive");
        Publisher<Exchange> publisher = service.fromStream("reactive");
        Flux.from(publisher)
                .doOnNext(System.out::println)
                .subscribe();*//*
        from("direct:hello1")
                .log(LoggingLevel.INFO, "Hello World")
                .transform().simple("   Hello World");
        from("direct:abc")
                .log(LoggingLevel.INFO, "Hello ABC")
                .transform().simple("   Hello ABC");
        rest().get("/hello")
                .to("reactive-streams:numbers");
        CamelContext context = new DefaultCamelContext();
        CamelReactiveStreamsService rsCamel = CamelReactiveStreams.get(context);
        Publisher numbers = rsCamel.fromStream("numbers");
        Flux.from(numbers).subscribe();
       // Flux.from(numbers).doOnNext(e-> rsCamel.to("direct:abc",e)).subscribe(rsCamel.subscriber("direct:hello1"));
        //numbers.subscribe(rsCamel.subscriber("direct:hello1"));
        //rsCamel.subscriber("direct:hello1")
        //numbers.subscribe(rsCamel.streamSubscriber("numbers",String.class));

       // Flux.just("3","4").subscribe(rsCamel.streamSubscriber("numbers",String.class));
        //String content = res.getIn().getBody(String.class);
        System.out.println("Done:: ");
    }*/
   /* @Override
    public void configure() throws Exception {
        CamelContext context = new DefaultCamelContext();
        rest().get("/hello")
                .to("reactive-streams:numbers");
        CamelReactiveStreamsService camel = CamelReactiveStreams.get(context);
        Publisher<Integer> numbers = camel.fromStream("numbers", Integer.class);
        Thread.sleep(500);
        Flux.from(numbers)
                .doOnNext(System.out::println)
                .subscribe();
        *//*CamelReactiveStreamsService crs = CamelReactiveStreams.get(context);
        Publisher<Exchange> timer = crs.from("timer:reactive?period=250&repeatCount=3");
        Thread.sleep(500);
        Flux.from(timer)
                .map(exchange -> ExchangeHelper.getHeaderOrProperty(exchange, Exchange.TIMER_COUNTER, Integer.class)).subscribe();*//*
    }*/

}
