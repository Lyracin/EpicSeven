package Character;

import java.util.Comparator;

public class SortTeamByMaxMorale implements Comparator<Team>
{
    public int compare(Team a, Team b)
    {
        return b.moraleSum-a.moraleSum;
    }
}
