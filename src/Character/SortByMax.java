package Character;

import java.util.Comparator;

public class SortByMax implements Comparator<Team>
{
    public int compare(Team a, Team b)
    {
        return b.moraleSum-a.moraleSum;
    }
}
