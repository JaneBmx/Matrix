package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatrixInfoParser implements Parser {
    private static final String INFO_FORMAT = ".*(N.+(\\d+)).+\\n(Y.+(\\d))";

    private static class Holder {
        private static final Parser instance = new MatrixInfoParser();
    }

    public static Parser getInstance() {
        return MatrixInfoParser.Holder.instance;
    }

    @Override
    public int[] parse(String text) {
        int[] indexes = new int[2];
        Pattern matrixInfo = Pattern.compile(INFO_FORMAT);
        Matcher matcher = matrixInfo.matcher(text);
        if (matcher.find()) {
            indexes[0] = (Integer.parseInt(matcher.group(2)));
            indexes[1] = (Integer.parseInt(matcher.group(4)));
        }
        return indexes;
    }
}
