package com.hospital.hospital_website.services;

import com.hospital.hospital_website.models.Visitor;
import com.hospital.hospital_website.repository.VisitorRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VisitorService {

    private final VisitorRepository visitorRepository;

    public ResponseEntity<?> updateVisitsCount(HttpSession session) {
        Optional<Visitor> visitor = visitorRepository.findById(Long.valueOf(session.getId()));
        if (visitor.isPresent()) {
            visitor.get().setVisitsCount(visitor.get().getVisitsCount() + 1);
            visitorRepository.save(visitor.get());
            return ResponseEntity.ok(visitorRepository.save(visitor.get()));
        }
        return ResponseEntity.notFound().build();
    }
}
