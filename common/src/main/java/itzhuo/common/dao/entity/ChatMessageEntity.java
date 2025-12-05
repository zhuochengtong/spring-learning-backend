package itzhuo.common.dao.entity;

import lombok.Data;
import java.time.LocalDateTime;
 
@Data
public class ChatMessageEntity {
    private Long id;
    private String conversationId;
    private MessageType messageType;
    private String content;
    private LocalDateTime createdAt;
 
    public enum MessageType {
        USER, ASSISTANT
    }
}