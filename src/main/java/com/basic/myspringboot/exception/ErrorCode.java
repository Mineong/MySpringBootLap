package com.basic.myspringboot.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    ISBN_DUPLICATE("Book already exists with ISBN: %s", HttpStatus.CONFLICT),
    BOOK_NOT_FOUND("Book not found with %s: %s", HttpStatus.NOT_FOUND),
    INVALID_PUBLISH_DATE("Book has invalid publish date: %s", HttpStatus.BAD_REQUEST),
    INVALID_PRICE("Book price must be positive or zero: %s", HttpStatus.BAD_REQUEST),
    AUTHOR_NOT_FOUND("No books found for author: %s", HttpStatus.NOT_FOUND),
    TITLE_NOT_FOUND("No books found for title: %s", HttpStatus.NOT_FOUND),

    BOOK_DETAIL_NOT_FOUND("BookDetail not found with %s: %s", HttpStatus.NOT_FOUND),
    INVALID_PAGE_COUNT("Page count must be a positive number: %s", HttpStatus.BAD_REQUEST),
    PUBLISHER_REQUIRED("Publisher name is required", HttpStatus.BAD_REQUEST),
    DESCRIPTION_TOO_SHORT("Description must be longer than %s characters", HttpStatus.BAD_REQUEST),

    RESOURCE_NOT_FOUND("%s not found with %s: %s", HttpStatus.NOT_FOUND),
    RESOURCE_DUPLICATE("%s already exists with %s: %s", HttpStatus.CONFLICT),
    RESOURCE_ALREADY_EXISTS("%s already exists: %s", HttpStatus.CONFLICT);

    private final String messageTemplate;
    private final HttpStatus httpStatus;

    public String formatMessage(Object... args) {
        return String.format(messageTemplate, args);
    }
}