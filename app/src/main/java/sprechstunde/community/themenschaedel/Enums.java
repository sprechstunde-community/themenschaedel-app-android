package sprechstunde.community.themenschaedel;

public class Enums {

    public enum SORTED_BY {
        DATE_UP,
        DATE_DOWN,
        TITLE_UP,
        TITLE_DOWN,
        NUMBER_UP,
        NUMBER_DOWN,
        USER,
        STATE
    }

    public enum EpisodeClaimStatus
    {
        OPEN,
        CLAIMED,
        DONE,
        UNVERIFIED
    }

    public enum HostState
    {
        IN,
        OUT,
        NONE,
    }

    public enum FILTER_BY {
        COMMUNITY,
        BOYS,
        AD
    }
}
