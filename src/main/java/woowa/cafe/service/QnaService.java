package woowa.cafe.service;

import woowa.cafe.domain.Question;
import woowa.cafe.dto.request.CreateQuestionRequest;
import woowa.cafe.repository.QuestionRepository;
import woowa.frame.core.annotation.Component;

@Component
public class QnaService {

    private final QuestionRepository questionRepository;

    public QnaService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void createQna(CreateQuestionRequest request) {
        Question question = new Question(request.authorName(), request.title(), request.content());
        questionRepository.save(question);
    }
}
