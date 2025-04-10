package myBlog.service;

import myBlog.model.Tag;
import myBlog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private final TagRepository tagRepository;
    @Autowired
    private final Tag selectedTag;

    public TagService(TagRepository tagRepository, Tag selectedTag) {
        this.tagRepository = tagRepository;
        this.selectedTag = selectedTag;
    }

    public List<Tag> distinctTags() {
        return tagRepository.distinctTags();
    }

    public void setSelectedTag(String name) {
        selectedTag.setName(name);
    }

    public Tag getSelectedTag() {
        return selectedTag;
    }
}
