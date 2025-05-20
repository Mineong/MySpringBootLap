
package com.basic.myspringboot.controller;

import com.basic.myspringboot.controller.dto.BookDTO;
import com.basic.myspringboot.controller.dto.PublisherDTO;
import com.basic.myspringboot.service.BookService;
import com.basic.myspringboot.service.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherService publisherService;
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<PublisherDTO.SimpleResponse>> getAllPublishers() {
        return ResponseEntity.ok(publisherService.getAllPublishers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherDTO.Response> getPublisherById(@PathVariable Long id) {
        return ResponseEntity.ok(publisherService.getPublisherById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<PublisherDTO.SimpleResponse> getPublisherByName(@RequestParam String name) {
        return ResponseEntity.ok(publisherService.getPublisherByName(name));
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<List<BookDTO.Response>> getBooksByPublisherId(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBooksByPublisherId(id));
    }

    @PostMapping
    public ResponseEntity<PublisherDTO.SimpleResponse> createPublisher(@RequestBody @Valid PublisherDTO.Request request) {
        return ResponseEntity.ok(publisherService.createPublisher(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublisherDTO.SimpleResponse> updatePublisher(@PathVariable Long id,
                                                                       @RequestBody @Valid PublisherDTO.Request request) {
        return ResponseEntity.ok(publisherService.updatePublisher(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
        publisherService.deletePublisher(id);
        return ResponseEntity.noContent().build();
    }
}
