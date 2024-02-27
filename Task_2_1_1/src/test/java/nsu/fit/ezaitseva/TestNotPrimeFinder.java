package nsu.fit.ezaitseva;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import nsu.fit.ezaitseva.finders.MultithreadsNotPrimeFinder;
import nsu.fit.ezaitseva.finders.NotPrimeFinderInterface;
import nsu.fit.ezaitseva.finders.ParallelSreamNotPrimeFinder;
import nsu.fit.ezaitseva.finders.SequentiallyNotPrimeFinder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;



public class TestNotPrimeFinder {

    List<Long> methodsList = new ArrayList<>();
    public void setMethodsList(NotPrimeFinderInterface method, ArrayList<Integer> numsList, boolean result)
            throws InterruptedException {
        long startTime = System.currentTimeMillis();
        boolean realRes = method.noPrime(numsList);
        Assertions.assertEquals(realRes, result);
        if (realRes == false) {methodsList.add(System.currentTimeMillis() - startTime);}
    }

    @ParameterizedTest
    @MethodSource("finder")
    void allMethodsTest(ArrayList<Integer> numsList, boolean result) throws InterruptedException{

        SequentiallyNotPrimeFinder finderSeq = new SequentiallyNotPrimeFinder();
        MultithreadsNotPrimeFinder finderMult1 = new MultithreadsNotPrimeFinder(10);
        MultithreadsNotPrimeFinder finderMult2 = new MultithreadsNotPrimeFinder(50);
        MultithreadsNotPrimeFinder finderMult3 = new MultithreadsNotPrimeFinder(100);
        MultithreadsNotPrimeFinder finderMult4 = new MultithreadsNotPrimeFinder(1000);
        ParallelSreamNotPrimeFinder finderPar = new ParallelSreamNotPrimeFinder();
        setMethodsList(finderSeq, numsList, result);
        setMethodsList(finderMult1, numsList, result);
        setMethodsList(finderMult2, numsList, result);
        setMethodsList(finderMult3, numsList, result);
        setMethodsList(finderMult4, numsList, result);
        setMethodsList(finderPar, numsList, result);

        for (long time : methodsList) {
            System.out.println(time);
        }
    }

    private static Stream<Arguments> finder() {
        Tools tools = new Tools();
        ArrayList<Integer> primeNums = tools.generateNums(10000000);
        ArrayList<Integer> notAllPrimeNums = tools.generateNums(10000000);
        notAllPrimeNums.add(1234);
        return Stream.of(
                Arguments.of(primeNums, false),
                Arguments.of(notAllPrimeNums, true));
    }

}