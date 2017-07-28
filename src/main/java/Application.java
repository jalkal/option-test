import java.util.Optional;
import java.util.stream.Stream;

/**
 * Author: Jose Alcalde jalcalde@gmail.com
 *
 * Use case of java.util.Optional class
 */
public class Application {

    public static void main(String... args){
        new Application().run();
    }

    public void run(){
        Stream<Optional<Car>> streamCars = Stream.of(Optional.ofNullable(null),
                Optional.of(new Car()),
                Optional.of(new Car(new Engine(150))));

        streamCars.map(car -> car.flatMap(Car::getEngine)
                .map(Engine::getPower).orElse(0)).forEach(System.out::println);
    }

    class Car{
        Engine engine;
        Car(){};
        Car(Engine engine){
            this.engine = engine;
        }
        void setEngine(Engine engine){this.engine = engine;}
        Optional<Engine> getEngine(){return Optional.ofNullable(engine);};
    }

    class Engine{
        Engine(int power){this.power = power;}
        int power;
        int getPower(){return power;}
    }
}
