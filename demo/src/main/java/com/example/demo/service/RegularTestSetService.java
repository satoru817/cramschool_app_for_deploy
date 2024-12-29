package com.example.demo.service;

import com.example.demo.entity.RegularTestSet;
import com.example.demo.repository.RegularTestSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegularTestSetService {
    private final RegularTestSetRepository regularTestSetRepository;

    public RegularTestSet findById(Integer regularTestSetId){
        return  regularTestSetRepository.findById(regularTestSetId)
                .orElseThrow(()->new RuntimeException("指定されたIDの定期テストセットが見つかりません"));
    }

}
