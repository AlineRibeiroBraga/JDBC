
public class Application {

    public static void main(String[] args) {
        final TeamDAO teamDAO = new TeamDAO();

        System.out.println(teamDAO.findAll());

        teamDAO.findById(4L)
                .ifPresent(System.out::println);

        teamDAO.deleteById(4L);

        teamDAO.findById(4L)
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("nao encontrado")
                );

        final MemberDAO memberDAO = new MemberDAO();

        System.out.println(memberDAO.findAll());

        memberDAO.findById(2L)
                .ifPresent(System.out::println);

        memberDAO.deleteById(2L);

        memberDAO.findById(2L)
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("nao encontrado")
                );

    }
}