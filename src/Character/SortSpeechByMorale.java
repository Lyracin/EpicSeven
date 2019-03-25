package Character;

import java.util.Comparator;

public class SortSpeechByMorale implements Comparator<Speech> {
    public int compare(Speech a, Speech b)
    {
        return b.morale-a.morale;
    }
}
