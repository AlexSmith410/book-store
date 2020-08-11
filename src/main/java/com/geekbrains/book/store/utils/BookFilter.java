package com.geekbrains.book.store.utils;

import com.geekbrains.book.store.entities.Book;
import com.geekbrains.book.store.repositories.specifications.BookSpecifications;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Getter
public class BookFilter {
    private Specification<Book> spec;
    private StringBuilder filterParams = new StringBuilder("&");

    public BookFilter(MultiValueMap<String, String> params) {
        spec = Specification.where(null);
        if (params.containsKey("maxPrice") && !params.get("maxPrice").get(0).equals("")) {
            spec = spec.and(BookSpecifications.priceLesserOrEqualsThan(Integer.parseInt(params.get("maxPrice").get(0))));
            filterParams.append("maxPrice=").append(params.get("maxPrice").get(0)).append('&');
        }
        if (params.containsKey("minPrice") && !params.get("minPrice").get(0).equals("")) {
            spec = spec.and(BookSpecifications.priceGreaterOrEqualsThan(Integer.parseInt(params.get("minPrice").get(0))));
            filterParams.append("minPrice=").append(params.get("minPrice").get(0)).append('&');
        }
        if (params.containsKey("titlePart") && !params.get("titlePart").get(0).equals("")) {
            spec = spec.and(BookSpecifications.titleLike(params.get("titlePart").get(0)));
            filterParams.append("titlePart=").append(params.get("titlePart").get(0)).append('&');
        }
        if (params.containsKey("sincePublishYear") && !params.get("sincePublishYear").get(0).equals("")) {
            spec = spec.and(BookSpecifications.publishYearGreaterOrEqualsThan(Integer.parseInt(params.get("sincePublishYear").get(0))));
            filterParams.append("sincePublishYear=").append(params.get("sincePublishYear").get(0)).append('&');
        }
        if (params.containsKey("genre") && !(params.get("genre") == null)) {
            for (String genre : params.get("genre")) {
                spec = spec.and(BookSpecifications.genreEqual(genre));
                filterParams.append("genre=").append(params.get("genre")).append('&');

            }
        }
        if (filterParams.equals("&")) {
            filterParams = new StringBuilder("");
        }

    }
}

