import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InfiniteInts {
    public static void main(String[] args) {
        List<Integer> ints = IntStream.iterate(0, i -> i + 1)
            .limit(10)
            .boxed()
            .collect(Collectors.toList());

        ints.forEach(System.out::println);
    }
}