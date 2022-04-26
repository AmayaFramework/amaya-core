package io.github.amayaframework.core.filters;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

enum Position {
    ALL(-1, "a"),
    FIRST(0, "f"),
    SECOND(1, "s"),
    THIRD(2, "t");

    private static final Map<String, Position> children = toMap();

    private final int index;
    private final String alias;

    Position(int index, String alias) {
        this.index = index;
        this.alias = alias;
    }

    private static Map<String, Position> toMap() {
        Map<String, Position> ret = new HashMap<>();
        for (Position position : Position.values()) {
            ret.put(position.alias, position);
        }
        return Collections.unmodifiableMap(ret);
    }

    public static Position fromAlias(String alias) {
        return children.get(alias);
    }

    public int getIndex() {
        return index;
    }
}

public class MapListFilter implements ContentFilter {
    private static final char SPLIT = ':';

    @Override
    @SuppressWarnings("unchecked")
    public Object transform(Object source, String name) {
        if (name.isEmpty()) {
            return null;
        }
        Map<String, List<String>> listMap;
        try {
            listMap = (Map<String, List<String>>) source;
        } catch (Exception e) {
            return null;
        }
        String retName;
        Position position;
        int splitIndex = name.indexOf(SPLIT);
        if (splitIndex < 0) {
            retName = name;
            position = Position.FIRST;
        } else {
            retName = name.substring(splitIndex + 1);
            position = Position.fromAlias(name.substring(0, splitIndex));
        }
        if (position == null) {
            return null;
        }
        List<String> ret = listMap.get(retName);
        if (ret == null) {
            return null;
        }
        if (position == Position.ALL) {
            return ret;
        }
        if (position.getIndex() < ret.size()) {
            return ret.get(position.getIndex());
        }
        return null;
    }
}
