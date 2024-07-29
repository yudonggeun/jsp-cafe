package woowa.cafe.repository;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import woowa.cafe.domain.Question;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class JdbcQuestionRepositoryTest {

    @Container
    private static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("woowa")
            .withUsername("user1")
            .withPassword("test");

    private final JdbcQuestionRepository jdbcQuestionRepository = new JdbcQuestionRepository(
            new TestJdbcConfig(mysql.getJdbcUrl(), mysql.getUsername(), mysql.getPassword())
    );

    @Test
    @Order(1)
    public void create_question() {
        // given
        Question question = new Question(
                "authorName",
                "title",
                "contents",
                "userId"
        );

        // when
        jdbcQuestionRepository.save(question);

        // then
        List<Question> questions = jdbcQuestionRepository.findAll();
        for (Question q : questions) {
            System.out.println(
                    "Question(" + q.getId() + ") : " +
                    q.getAuthorName() + ", " +
                    q.getTitle() + ", " +
                    q.getContent() + ", " +
                    q.getUserId()
            );
        }
        assertThat(questions).hasSize(1);
    }

}