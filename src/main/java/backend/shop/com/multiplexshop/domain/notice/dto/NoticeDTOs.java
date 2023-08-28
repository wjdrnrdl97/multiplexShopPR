package backend.shop.com.multiplexshop.domain.notice.dto;

import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.notice.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoticeDTOs {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ResponseNoticeDTO{
        private Long noticeId;
        private String noticeTitle;
        private String noticeContent;
        private String memberName;
        private LocalDateTime regDate;
        private LocalDateTime modDate;
        private Long noticeViewCount;
        private Member member;

        public ResponseNoticeDTO(Notice notice){
            this.noticeId = notice.getNoticeId();
            this.noticeTitle = notice.getNoticeTitle();
            this.noticeContent = notice.getNoticeContent();
            this.memberName = notice.getMember().getMemberName();
            this.regDate = notice.getRegDate();
            this.modDate = notice.getModDate();
            this.noticeViewCount = notice.getNoticeViewCount();
            this.member = notice.getMember();
        }
    }
    @Getter
    @Builder
    @AllArgsConstructor
    public static class RequestNoticeDTO{
        private Long noticeID;
        private String noticeTitle;
        private String noticeContent;
        private String memberName;
        private Member member;
    }
}
