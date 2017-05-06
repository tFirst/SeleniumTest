import java.util.Arrays;
import java.util.List;


public class MainTestClass {
    public static final List<String> categories = Arrays.asList
            ("Любая недвижимость", "Квартиры", "Дом / коттедж / таунхаус",
                    "Парковка / гараж", "Земельный участок", "Коммерческая недвижимость");

    public static final List<Integer> countElements = Arrays.asList(1488, 652, 146, 663, 12, 15);
}

class Assertion {
    Assertion(String msg) {
        assert false: msg;
    }
}
