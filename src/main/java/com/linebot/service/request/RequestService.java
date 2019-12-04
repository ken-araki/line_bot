package com.linebot.service.request;

import com.linebot.entity.Request;
import com.linebot.repository.RequestRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RequestService {
    private RequestRepository requestRepository;

    @Transactional(readOnly = true)
    public List<Request> findActive() {
        return requestRepository.findByStatusAndDeleted(0, "0");
    }

    @Transactional
    public Request insert(Request request) {
        return requestRepository.save(request);
    }
}
