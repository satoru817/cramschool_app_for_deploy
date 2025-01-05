package com.example.demo.service.sales;

import com.example.demo.entity.Action;
import com.example.demo.repository.ActionHistoryRepository;
import com.example.demo.repository.ActionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActionService {
    private final ActionRepository actionRepository;

    public List<Action> getAll() {
        return actionRepository.findAll();
    }

    @Transactional
    public void createAction(String name) {
        Action action = new Action();
        action.setActionName(name);
        actionRepository.save(action);
    }

    @Transactional
    public void updateAction(Integer actionId, String name) {
        Action action = actionRepository.findById(actionId)
                .orElseThrow(() -> new RuntimeException("指定されたアクションは見つかりません。"));
        action.setActionName(name);
        actionRepository.save(action);
    }

    @Transactional
    public void deleteAction(Integer actionId) {
        actionRepository.deleteById(actionId);
    }
}
