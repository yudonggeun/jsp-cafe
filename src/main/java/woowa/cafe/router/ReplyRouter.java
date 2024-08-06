package woowa.cafe.router;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import woowa.cafe.dto.Pageable;
import woowa.cafe.dto.ReplyInfo;
import woowa.cafe.dto.UserInfo;
import woowa.cafe.service.ReplyService;
import woowa.frame.web.annotation.HttpMapping;
import woowa.frame.web.annotation.Router;
import woowa.frame.web.collection.Page;

import java.util.Map;

@Router(url = "/question/{questionId}/reply")
public class ReplyRouter {

    private final ReplyService replyService;
    private final ObjectMapper om = new ObjectMapper();

    public ReplyRouter(ReplyService replyService) {
        this.replyService = replyService;
    }

    @HttpMapping(method = "GET")
    public Map<String, Object> getReplies(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getRequestURI();
        String id = url.substring(10, url.lastIndexOf("/reply"));
        Pageable pageable = getReplyPageable(request);
        Page<ReplyInfo> replies = replyService.getAllReplies(id, pageable);

        String replyPageInfo = om.convertValue(replies, String.class);
        return Map.of(
                "message", "success : get replies",
                "data", replyPageInfo
        );
    }

    /**
     * 댓글 페이지네이션 정보를 추출합니다.
     * 만약 관련 정보를 입력받지 않았다면 기본 설정을 적용하여 반환합니다.
     * <li>page : 1</li>
     * <li>size : 5</li>
     * <li>sort : createdDate</li>
     */
    private Pageable getReplyPageable(HttpServletRequest request) {
        try {
            String page = request.getParameter("page");
            String size = request.getParameter("size");
            String sort = request.getParameter("sort");

            if (page == null) {
                page = "1";
            }

            if (size == null) {
                size = "5";
            }

            if (sort == null) {
                sort = "createdDate";
            }

            return new Pageable(Integer.parseInt(page), Integer.parseInt(size), sort);
        } catch (RuntimeException e) {
            return null;
        }
    }

    @HttpMapping(method = "POST")
    public Map<String, Object> createReply(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getRequestURI();
        String questionId = url.substring(10, url.lastIndexOf("/reply"));
        String content = request.getParameter("content");
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");

        if (content == null || content.isEmpty()) {
            return null;
        }
        ReplyInfo replyInfo = replyService.createReply(questionId, content, userInfo.id(), userInfo.name());

        return Map.of(
                "message", "success : create reply",
                "data", Map.of(
                        "id", replyInfo.id() + "",
                        "content", replyInfo.content(),
                        "createdDate", replyInfo.createdDate().toLocalDate().toString(),
                        "status", replyInfo.status(),
                        "userId", replyInfo.userId(),
                        "authorName", replyInfo.authorName(),
                        "questionId", replyInfo.questionId()
                )
        );
    }

    @HttpMapping(method = "DELETE", urlTemplate = "/{replyId}")
    public Map<String, Object> deleteReply(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getRequestURI();
        String questionId = url.substring(10, url.lastIndexOf("/reply"));
        String replyId = url.substring(url.lastIndexOf("/reply/") + 7);
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");

        if (!replyService.deleteReply(userInfo, questionId, replyId)) {
            return null;
        }
        return Map.of(
                "message", "success : delete reply",
                "data", Map.of(
                        "questionId", questionId,
                        "replyId", replyId
                )
        );
    }
}
