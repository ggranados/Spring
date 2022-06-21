package edu.ggranados.java.algorithms.handlers.factories;

import edu.ggranados.java.algorithms.handlers.TypeHandler;
import edu.ggranados.java.algorithms.sort.SortType;

public interface TypeHandlerFactory {

    SortTypeHandlerFactory withKey(SortType insert);

    SortTypeHandlerFactory withType(String type);

    TypeHandler build();
}
