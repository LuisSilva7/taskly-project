package org.taskly.taskly_backend.comment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public Long createCommentByTaskId(CommentRequest request, Long taskId) {
        return commentRepository.save(
                commentMapper.toCommentEntity(request, taskId)
        ).getId();
    }
}