import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Concurrency {

    public static void main(String... args){

        long startime = System.currentTimeMillis();

        Service service = new Service();

        List<CompletableFuture<List<String>>> futureList = Stream.of("tmpl1", "tmpl2", "tmpl3", "tmpl4")
                .map(tmpl -> CompletableFuture.supplyAsync(() -> service.getResult(tmpl), getExecutor()))
                .collect(Collectors.toList());

        futureList.stream()
                .map(CompletableFuture::join)
                .forEach(System.out::println);

        long endtime = System.currentTimeMillis();
        System.out.format("Takes %s microseconds", endtime - startime);
    }

    private static Executor getExecutor(){
        return Executors.newFixedThreadPool(10,
                r -> {Thread thread = new Thread(r); thread.setDaemon(true); return thread;});
    }
}

class Service{

    List<String> getResult(String tmpl){
        try { Thread.sleep(1000);} catch (InterruptedException e) { e.printStackTrace(); }

        return Arrays.asList(tmpl, "element 1", "element 2");
    }
}
