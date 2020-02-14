package writer;

public interface Writer {
    void writeInFile(String text, String path);

    void writeInConsole(String message);
}
