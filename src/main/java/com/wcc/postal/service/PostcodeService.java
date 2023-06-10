package com.wcc.postal.service;

import com.wcc.postal.model.Postcode;
import com.wcc.postal.repository.PostcodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostcodeService {
    private final PostcodeRepository postcodeRepository;

    @Autowired
    public PostcodeService(PostcodeRepository postcodeRepository) {
        this.postcodeRepository = postcodeRepository;
    }

    public Postcode getPostcode(Long id) {
        // We use findById to get a Postcode by id.
        // It returns an Optional, so we need to handle the case when the id does not exist in our database.
        Optional<Postcode> optionalPostcode = postcodeRepository.findById(id);
        return optionalPostcode.orElse(null);
    }

    public Postcode createPostcode(Postcode newPostcode) {
        // Before saving a new postcode, you could implement some business logic if needed.
        // For example, you could check if the postcode already exists in your database.
        return postcodeRepository.save(newPostcode);
    }

    // Implement other business methods like delete, update, list all, etc.
}
