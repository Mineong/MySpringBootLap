package com.basic.myspringboot.service;

import com.basic.myspringboot.controller.dto.PublisherDTO;
import com.basic.myspringboot.entity.Publisher;
import com.basic.myspringboot.exception.BusinessException;
import com.basic.myspringboot.exception.ErrorCode;
import com.basic.myspringboot.repository.BookRepository;
import com.basic.myspringboot.repository.PublisherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;

    public List<PublisherDTO.SimpleResponse> getAllPublishers() {
        return publisherRepository.findAll().stream()
                .map(p -> PublisherDTO.SimpleResponse.fromEntityWithCount(p,
                        bookRepository.countByPublisherId(p.getId())))
                .toList();
    }

    public PublisherDTO.Response getPublisherById(Long id) {
        Publisher publisher = publisherRepository.findByIdWithBooks(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Publisher", "id", id));
        return PublisherDTO.Response.fromEntity(publisher);
    }

    public PublisherDTO.SimpleResponse getPublisherByName(String name) {
        Publisher publisher = publisherRepository.findByName(name)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Publisher", "name", name));
        return PublisherDTO.SimpleResponse.fromEntityWithCount(publisher,
                bookRepository.countByPublisherId(publisher.getId()));
    }

    public PublisherDTO.SimpleResponse createPublisher(PublisherDTO.Request request) {
        if (publisherRepository.existsByName(request.getName())) {
            throw new BusinessException(ErrorCode.RESOURCE_ALREADY_EXISTS, "Publisher", request.getName());
        }

        Publisher publisher = Publisher.builder()
                .name(request.getName())
                .address(request.getAddress())
                .establishedDate(request.getEstablishedDate())
                .build();

        return PublisherDTO.SimpleResponse.fromEntity(publisherRepository.save(publisher));
    }

    public PublisherDTO.SimpleResponse updatePublisher(Long id, PublisherDTO.Request request) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Publisher", "id", id));

        // 이름이 바뀌었을 경우 중복 체크
        if (!publisher.getName().equals(request.getName()) &&
                publisherRepository.existsByName(request.getName())) {
            throw new BusinessException(ErrorCode.RESOURCE_DUPLICATE, "Publisher", "name", request.getName());
        }

        publisher.setName(request.getName());
        publisher.setAddress(request.getAddress());
        publisher.setEstablishedDate(request.getEstablishedDate());

        return PublisherDTO.SimpleResponse.fromEntity(publisher);
    }

    public void deletePublisher(Long id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Publisher", "id", id));

        if (!publisher.getBooks().isEmpty()) {
            throw new BusinessException(ErrorCode.RESOURCE_ALREADY_EXISTS,
                    "Book(s) exist under Publisher", publisher.getName());
        }

        publisherRepository.delete(publisher);
    }
}
