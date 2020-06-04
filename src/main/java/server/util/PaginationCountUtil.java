package server.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class PaginationCountUtil {
    public List<Integer> PageNumberList(int page, long objectCount) {
        if (objectCount == 0) return Collections.singletonList(0);
        long pageNumber = objectCount/10;
        if (objectCount%10 != 0 && pageNumber != 0) pageNumber++;
        List<Integer> pageList = new ArrayList<>();
        for (int i = page - 5; i < page + 6; i++){
            if (i < 0){
                page++;
                continue;
            }
            if (i > pageNumber) break;
            pageList.add(i);
        }
        return pageList;
    }
}
