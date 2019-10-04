import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemberDAO {

    private final DataSource dataSource;

    public MemberDAO() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUser("postgres");
        dataSource.setPassword("123123");
        dataSource.setDatabaseName("bancodedados");
        this.dataSource = dataSource;
    }

    public void insert(final Member member) {
        try (
                final Connection connection = dataSource.getConnection();
                final PreparedStatement stmt = connection.prepareStatement(
                        "insert into membros (nome_membro,id_time) values (?,?)"
                );
        ) {
            stmt.setString(1, member.getName());
            stmt.setLong(2,member.getIdTeam());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("ERRO");
            throw new RuntimeException(e);
        }
    }

    public List<Member> findAll() {
        try (
                final Connection connection = dataSource.getConnection();
                final Statement stmt = connection.createStatement();
                final ResultSet rs = stmt.executeQuery("select id, nome_membro, id_time from membros")
        ) {
            final List<Member> members = new ArrayList<>();

            while (rs.next()) {
            final Member member = new Member(
                    rs.getLong("id"),
                    rs.getString("nome_membro"),
                    rs.getLong("id_time")
                );
                members.add(member);
            }

            return members;
        } catch (SQLException e) {
            System.err.println("ERRO");
            throw new RuntimeException(e);
        }
    }

    public Optional<Member> findById(final Long id) {
        try (
                final Connection connection = dataSource.getConnection();
                final PreparedStatement stmt = connection.prepareStatement(
                        "select id, nome_membro, id_time from membros where id_time = ?"
                );
        ) {
            stmt.setLong(1, id);
            final ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(
                        new Member(
                            rs.getLong("id"),
                            rs.getString("nome_membro"),
                            rs.getLong("id_time")
                        )
                );
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            System.err.println("deu ruim");
            throw new RuntimeException(e);
        }
    }

    public void update(final Long id, final UpdateTeamRequest updateTeamRequest) {
        try (
                final Connection connection = dataSource.getConnection();
                final PreparedStatement stmt = connection.prepareStatement(
                        "update membros set nome_membro = ? where id_time = ?"
                )
        ) {
            stmt.setString(1, updateTeamRequest.getName());
            stmt.setLong(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("deu ruim");
            throw new RuntimeException(e);
        }
    }

    public void deleteById(final Long id) {
        try (
                final Connection connection = dataSource.getConnection();
                final PreparedStatement stmt = connection.prepareStatement(
                        "delete from membros where nome_membro = ?"
                )
        ) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("deu ruim");
            throw new RuntimeException(e);
        }
    }
}
