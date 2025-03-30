package myBlog.service;

import model.Commentary;
import model.Post;
import model.Tag;
import myBlog.repository.CommentaryRepository;
import myBlog.repository.PostRepository;
import myBlog.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final CommentaryRepository commentaryRepository;
    private final TagRepository tagRepository;

    public PostService(PostRepository repository, CommentaryRepository commentaryRepository, TagRepository tagRepository) {
        this.postRepository = repository;
        this.commentaryRepository = commentaryRepository;
        this.tagRepository = tagRepository;
    }

    public List<Post> findAll() {
        List<Post> posts = this.postRepository.findAll();
        for (Post post: posts) {
            post.setCommentaries(loadCommentaries(post.getId()));
            post.setTags(loadTags(post.getId()));
        }
        return posts;
    }

    public Post getById(Long id) {
        Post post = this.postRepository.getById(id);
        post.setCommentaries(loadCommentaries(post.getId()));
        post.setTags(loadTags(post.getId()));
        return post;
    }

    public void incrementLikeCounterById(Long id) {
        this.postRepository.incrementLikeCounterById(id);
    }

    public void addCommentToPost(Long post_id, String text) {
        this.commentaryRepository.addCommentToPost(post_id, text);
    }

    public List<Commentary> loadCommentaries(long post_id) {
        return commentaryRepository.getByPostId(post_id);
    }
    public List<Tag> loadTags(long post_id) {
        return tagRepository.getByPostId(post_id);
    }
}
