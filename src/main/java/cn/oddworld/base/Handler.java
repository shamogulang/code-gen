package cn.oddworld.base;

import java.io.InputStream;
import java.util.List;

public interface Handler {

    List<String> genCode(InputStream inputStream);
}
