package myBlog.service;

import myBlog.model.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaginationService {

    @Autowired
    private final Pagination pagination;

    public PaginationService(Pagination pagination) {
        this.pagination = pagination;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void updatePagination(int size, int page){
        pagination.setSize(size);
        pagination.setPage(page);
    }

}
