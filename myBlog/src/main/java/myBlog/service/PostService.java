package myBlog.service;

import myBlog.model.Commentary;
import myBlog.model.Post;
import myBlog.model.PostRecord;
import myBlog.model.Tag;
import myBlog.repository.CommentaryRepository;
import myBlog.repository.PostRepository;
import myBlog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final CommentaryRepository commentaryRepository;
    @Autowired
    private final TagRepository tagRepository;
    @Autowired
    private final TagService tagService;

    public PostService(PostRepository repository, CommentaryRepository commentaryRepository, TagRepository tagRepository, TagService tagService) {
        this.postRepository = repository;
        this.commentaryRepository = commentaryRepository;
        this.tagRepository = tagRepository;
        this.tagService = tagService;
    }

    public List<Post> findAll() {
        List<Post> posts = this.postRepository.findAll();
        for (Post post: posts) {
            post.setCommentaries(loadCommentaries(post.getId()));
            post.setTags(loadTags(post.getId()));
        }
        return posts;
    }

    public List<Post> findAllFiltered(int page, int size, String selectedTag) {
        List<Post> posts = null;
        if (selectedTag == null || selectedTag.equals("")) {
            posts = this.postRepository.findAllPaginated(size,page);
        } else  {
            posts = this.postRepository.findAllPaginatedByTag(size,page,selectedTag);
        }

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
    @Transactional
    public void incrementLikeCounterById(Long id) {
        this.postRepository.incrementLikeCounterById(id);
    }

    public List<Commentary> loadCommentaries(long post_id) {
        return commentaryRepository.getByPostId(post_id);
    }
    public List<Tag> loadTags(long post_id) {
        return tagRepository.getByPostId(post_id);
    }
    @Transactional
    public void createNewPost(PostRecord postRecord) {
        Long post_id = postRepository.create(postRecord);
        this.createTagsInPost(post_id, postRecord.tags());
    }
    @Transactional
    public void updatePost(Long id, PostRecord postRecord) {
        postRepository.update(id, postRecord);
    }
    @Transactional
    public void deleteTagsByPostId(Long id) {
        tagRepository.deleteTagsByPostId(id);
    }
    @Transactional
    public void createTagsInPost(Long post_id, String tagsByComma) {
        String[] tags = tagsByComma.replaceAll("#","").split(",");
        for (String tag: tags) {
            if (!tag.equals("")) {
                String newTag = tag.trim().replaceAll("\\s{1,}","_");
                tagRepository.createLinkedTag(post_id, newTag);
            }

        }
    }

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }
}
