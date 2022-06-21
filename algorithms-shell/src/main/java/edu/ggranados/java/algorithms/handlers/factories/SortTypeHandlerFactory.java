package edu.ggranados.java.algorithms.handlers.factories;

import edu.ggranados.java.algorithms.handlers.InsertSortIterativeHandler;
import edu.ggranados.java.algorithms.handlers.InsertSortRecursiveHandler;
import edu.ggranados.java.algorithms.handlers.TypeHandler;
import edu.ggranados.java.algorithms.sort.SortMethod;
import edu.ggranados.java.algorithms.sort.SortType;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.EnumUtils;

import java.security.InvalidParameterException;
import java.util.function.BiPredicate;

@Component("sortTypeHandlerFactory")
public class SortTypeHandlerFactory implements TypeHandlerFactory{

    private static final BiPredicate<SortType, SortMethod> IS_INSERT_SORT_ITERATIVE =
            (t, m) -> t == SortType.INSERT && m == SortMethod.ITERATIVE;

    private static final BiPredicate<SortType, SortMethod> IS_INSERT_SORT_RECURSIVE =
            (t, m) -> t == SortType.INSERT && m == SortMethod.RECURSIVE;

    private SortType type;
    private SortMethod method;

    @Override
    public SortTypeHandlerFactory withKey(SortType algorithmType) {
        this.type = algorithmType;
        return this;
    }

    @Override
    public SortTypeHandlerFactory withType(String type) {
        EnumUtils.findEnumInsensitiveCase(SortMethod.class, type);
        this.method = SortMethod.valueOf(type);
        return this;
    }

    @Override
    public TypeHandler build() {
        if(IS_INSERT_SORT_ITERATIVE.test(type, method))
            return new InsertSortIterativeHandler();

        if(IS_INSERT_SORT_RECURSIVE.test(type, method))
            return new InsertSortRecursiveHandler();
        throw new InvalidParameterException("Could find option in factory");
    }

}
