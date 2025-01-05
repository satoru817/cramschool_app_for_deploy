package com.example.demo.service.sales;

import com.example.demo.entity.Funnel;
import com.example.demo.repository.FunnelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FunnelService {
    private final FunnelRepository funnelRepository;

    public List<Funnel> getAll(){
        return funnelRepository.findAll();
    }

    @Transactional
    public void updateFunnel(Integer funnelId, String name) {
        Funnel funnel = funnelRepository.findById(funnelId)
                .orElseThrow(()->new RuntimeException("指定されたfunnelは見つかりません。"));

        funnel.setName(name);

        funnelRepository.save(funnel);
    }

    @Transactional
    public void deleteFunnel(Integer funnelId) {
        funnelRepository.deleteById(funnelId);
    }

    @Transactional
    public void createFunnel(String name) {
        Funnel funnel = new Funnel();
        funnel.setName(name);
        funnelRepository.save(funnel);
    }
}
