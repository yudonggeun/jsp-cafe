package woowa.cafe.router;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import woowa.cafe.dto.QuestionInfo;
import woowa.cafe.dto.request.CreateQuestionRequest;
import woowa.cafe.service.QnaService;
import woowa.frame.web.annotation.HttpMapping;
import woowa.frame.web.annotation.Router;

import java.util.List;

@Router
public class QnaRouter {

    private final QnaService qnaService;

    public QnaRouter(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    @HttpMapping(method = "GET", urlTemplate = "/")
    public String showQuestions(HttpServletRequest request, HttpServletResponse response) {
        List<QuestionInfo> questions = qnaService.getQuestions();
        request.setAttribute("questions", questions);
        return "/template/qna/list.jsp";
    }

    @HttpMapping(method = "GET", urlTemplate = "/question")
    public String showQuestionForm(HttpServletRequest request, HttpServletResponse response) {
        return "/template/qna/form.jsp";
    }

    /**
     * 질문(게시글)을 생성하는 요청을 처리합니다.
     * 게시글 작성은 로그인한 유저만이 실행이 가능합니다. 로그인 여부는 {@link woowa.cafe.filter.AuthFilter} 필터에서 확인합니다.
     * <li> POST /question </li>
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @see woowa.cafe.filter.AuthFilter 로그인 여부를 확인하는 필터
     * @return 생성된 질문의 상세 페이지로 리다이렉트합니다. 질문 생성에 실패하면 질문 작성 페이지로 리다이렉트합니다.
     */
    @HttpMapping(method = "POST", urlTemplate = "/question")
    public String createQuestion(HttpServletRequest request, HttpServletResponse response) {
        CreateQuestionRequest req = new CreateQuestionRequest(
                request.getParameter("writer"),
                request.getParameter("title"),
                request.getParameter("contents")
        );

        try {
            qnaService.createQna(req);
            return "redirect:/";
        } catch (RuntimeException ex) {
            return "redirect:/question";
        }
    }

    @HttpMapping(method = "GET", urlTemplate = "/question/{id}")
    public String getQuestion(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getRequestURI().substring(10);
        QuestionInfo question = qnaService.getQuestion(id);

        if (question == null) {
            return "redirect:/question";
        }

        request.setAttribute("question", question);
        return "/template/qna/detail.jsp";
    }
}
