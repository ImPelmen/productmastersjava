package easy.printer;

public class StringPrinter<T> implements Printer<T> {

    @Override
    public void print(T value) {
        System.out.println(value.toString());
    }
}
