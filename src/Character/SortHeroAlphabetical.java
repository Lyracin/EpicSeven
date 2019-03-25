package Character;

import java.util.Comparator;

public class SortHeroAlphabetical implements Comparator<Hero> {

    public int compare(Hero a, Hero b) {
        return a.name.compareTo(b.name);
    }
}
