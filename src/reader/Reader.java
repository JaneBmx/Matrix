package reader;

import exception.ReadException;

public interface Reader {



    String read(String path) throws ReadException;


}
