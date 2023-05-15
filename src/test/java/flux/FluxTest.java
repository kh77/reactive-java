package flux;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxTest {


    @Test
    public void fluxWithConcatWithMethodTest() {

        Flux<String> stringFlux = Flux.just("Java", "C", "Ruby")
                .concatWith(Flux.just("After Error"))
                .log();

        stringFlux.subscribe(System.out::println,
                (e) -> System.err.println("Exception is " + e)
                , () -> System.out.println("Completed"));
    }

    @Test
    public void fluxTestElements_WithoutError() {

        Flux<String> stringFlux = Flux.just("Java", "C", "Ruby")
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Java")
                .expectNext("C")
                .expectNext("Ruby")
                .verifyComplete();
    }

    @Test
    public void fluxTestElements_WithError() {
        Flux<String> stringFlux = Flux.just("Java", "C", "Ruby")
                .concatWith(Flux.error(new RuntimeException("Exception is here")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Java", "C", "Ruby")
                //.expectError(RuntimeException.class)
                .expectErrorMessage("Exception is here")
                .verify();


    }

    @Test
    public void fluxTestElementsCount_WithError() {

        Flux<String> stringFlux = Flux.just("Java", "C", "Ruby")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .expectErrorMessage("Exception Occurred")
                .verify();
    }

    List<String> names = Arrays.asList("mark", "nick", "jack", "kim");

    @Test
    public void fluxUsingIterable(){

        Flux<String> namesFlux = Flux.fromIterable(names).log();

        StepVerifier.create(namesFlux)
                .expectNext("mark","nick","jack","kim")
                .verifyComplete();
    }

    @Test
    public void fluxUsingArray(){

        String[] names = new String[]{"mark","nick","jack","kim"};

        Flux<String> namesFlux = Flux.fromArray(names);
        StepVerifier.create(namesFlux)
                .expectNext("mark","nick","jack","kim")
                .verifyComplete();

    }

    @Test
    public void fluxUsingStream(){
        Flux<String> namesFlux = Flux.fromStream(names.stream());

        StepVerifier.create(namesFlux)
                .expectNext("mark","nick","jack","kim")
                .verifyComplete();

    }

    @Test
    public void fluxUsingRange(){
        Flux<Integer> integerFlux = Flux.range(1,5).log();

        StepVerifier.create(integerFlux)
                .expectNext(1,2,3,4,5)
                .verifyComplete();
    }

}
