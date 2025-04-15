package myBlog.service;

import myBlog.repository.CommentaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentaryService {

    @Autowired
    private final CommentaryRepository commentaryRepository;

    public CommentaryService( CommentaryRepository commentaryRepository) {
        this.commentaryRepository = commentaryRepository;
    }
    @Transactional
    public void addCommentToPost(Long post_id, String text) {
        this.commentaryRepository.addCommentToPost(post_id, text);
    }
    @Transactional
    public void updateComment(Long id, String text) {
        this.commentaryRepository.updateComment(id, text);
    }
    @Transactional
    public void deleteById(Long id) {
        commentaryRepository.deleteById(id);
    }

}
