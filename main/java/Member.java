public class Member {

    private long id;
    private String name;
    private long idTeam;

    public Member(long id, String name, long idTeam) {
        this.id = id;
        this.name = name;
        this.idTeam = idTeam;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getIdTeam() {
        return idTeam;
    }
}
