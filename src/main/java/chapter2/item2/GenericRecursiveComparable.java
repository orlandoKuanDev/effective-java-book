package chapter2.item2;

import java.util.*;
import java.util.function.UnaryOperator;

public class GenericRecursiveComparable{

    private static UnaryOperator<Object> IDENTITY_FN = (t) -> t;

    // Returns max value in a collection - uses recursive type bound
    public static <E> Set<E> union(Set<E> s1, Set<E> s2) {
        Set<E> result = new HashSet<>(s1);
        result.addAll(s2);
        return result;
    }

    public static <E extends Comparable<E>> E max(Collection<E> c) {
        if (c.isEmpty())
            throw new IllegalArgumentException("Empty collection");
        E result = null;
        for (E e : c)
            if (result == null || e.compareTo(result) > 0)
                result = Objects.requireNonNull(e);
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> UnaryOperator<T> identityFunction() {
        return (UnaryOperator<T>) IDENTITY_FN;
    }

    public interface Comparable<T> {
        int compareTo(T o);
    }

    public static void main(String[] args) {
        Set<String> guys = new TreeSet<> ();
        guys.add("Tom");
        guys.add("Dick");
        guys.add("Harry");
        Set<String> stooges = new TreeSet<> ();
        guys.add("Larry");
        guys.add("Moe");
        guys.add("Curly");
        Set<String> aflCio = union(guys, stooges);
        System.out.println(aflCio);

        List<Integer> listNumber = new ArrayList<>();
        listNumber.add(3);
        listNumber.add(5);
        listNumber.add(8);
        GenericRecursiveComparable.max((List) listNumber);

        String[] strings = { "jute", "hemp", "nylon" };
        UnaryOperator<String> sameString = identityFunction();
        for (String s : strings)
            System.out.println(sameString.apply(s));
        Number[] numbers = { 1, 2.0, 3L };
        UnaryOperator<Number> sameNumber = identityFunction();
        for (Number n : numbers)
            System.out.println(sameNumber.apply(n));
    }
}
