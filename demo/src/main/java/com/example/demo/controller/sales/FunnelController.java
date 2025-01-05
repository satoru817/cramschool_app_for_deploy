package com.example.demo.controller.sales;

import com.example.demo.entity.Funnel;
import com.example.demo.repository.FunnelRepository;
import com.example.demo.service.sales.FunnelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 流入経路(funnel)の情報について担当するコントローラークラスです。
 */

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/funnel")
public class FunnelController {
    private final FunnelService funnelService;

    /**
     * ファネル一覧画面を表示します。
     * @param model
     * @return ファネル一覧画面のテンプレート名
     */
    @GetMapping("/index")
    public String funnels(Model model) {
        List<Funnel> funnels = funnelService.getAll();
        model.addAttribute("funnels",funnels);

        return "sales/funnel/funnel_index";

    }

    /**
     * ファネルを編集します。
     * @param funnelId 編集対象のファネルID
     * @param name 新しいファネル名
     * @return リダイレクト先のパス
     */
    @PostMapping("/edit")
    public String edit(@RequestParam("funnelId") Integer funnelId,
                       @RequestParam("name") String name,
                       RedirectAttributes redirectAttributes) {
        String message;
        try {
            funnelService.updateFunnel(funnelId, name);
            message = "ファネルの更新に成功しました";
        } catch (RuntimeException e) {
            message = "ファネルの更新に失敗しました";
            log.error("Failed to update funnel", e);
        }
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/funnel/index";
    }

    /**
     * ファネルを削除します。
     * @param funnelId 削除対象のファネルID
     * @return リダイレクト先のパス
     */
    @PostMapping("/delete")
    public String delete(@RequestParam("funnelId") Integer funnelId,
                         RedirectAttributes redirectAttributes) {
        String message;
        try {
            funnelService.deleteFunnel(funnelId);
            message = "ファネルの削除に成功しました";
        } catch (RuntimeException e) {
            message = "ファネルの削除に失敗しました";
            log.error("Failed to delete funnel", e);
        }
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/funnel/index";
    }

    /**
     * 新規ファネルを作成します。
     * @param name 作成するファネルの名前
     * @return リダイレクト先のパス
     */
    @PostMapping("/create")
    public String create(@RequestParam("name") String name,
                         RedirectAttributes redirectAttributes) {
        String message;
        try {
            funnelService.createFunnel(name);
            message = "ファネルの作成に成功しました";
        } catch (RuntimeException e) {
            message = "ファネルの作成に失敗しました";
            log.error("Failed to create funnel", e);
        }
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/funnel/index";
    }
}
