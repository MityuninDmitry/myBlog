package myBlog.service;

import myBlog.model.Commentary;
import myBlog.model.Post;
import myBlog.model.PostRequest;
import myBlog.model.Tag;
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

    public List<Commentary> loadCommentaries(long post_id) {
        return commentaryRepository.getByPostId(post_id);
    }
    public List<Tag> loadTags(long post_id) {
        return tagRepository.getByPostId(post_id);
    }

    public void createNewPost(PostRequest postRequest) {
        Long post_id = postRepository.create(postRequest);
        this.createTagsInPost(post_id, postRequest.tags());
    }

    public void updatePost(Long id, PostRequest postRequest) {
        postRepository.update(id, postRequest);
    }

    public void deleteTagsByPostId(Long id) {
        tagRepository.deleteTagsByPostId(id);
    }

    public void createTagsInPost(Long post_id, String tagsByComma) {
        String[] tags = tagsByComma.replaceAll("#","").split(",");
        for (String tag: tags) {
            String newTag = tag.trim().replaceAll("\\s{1,}","_");
            tagRepository.createLinkedTag(post_id, newTag);
        }
    }

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }
}
