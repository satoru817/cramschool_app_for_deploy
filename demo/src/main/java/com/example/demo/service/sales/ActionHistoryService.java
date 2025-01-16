package com.example.demo.service.sales;

import com.example.demo.entity.ActionHistory;
import com.example.demo.repository.ActionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ActionHistoryService {
    private final ActionHistoryRepository actionHistoryRepository;

    @Transactional
    public boolean save(ActionHistory actionHistory){
        try{
            actionHistoryRepository.save(actionHistory);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public boolean deleteById(Integer actionHistoryId) {
        try{
            actionHistoryRepository.deleteById(actionHistoryId);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public ActionHistory getById(Integer actionHistoryId) {
        return actionHistoryRepository.findById(actionHistoryId)
                .orElseThrow(()->new RuntimeException("該当のアクション履歴は存在しません"));
    }
}
