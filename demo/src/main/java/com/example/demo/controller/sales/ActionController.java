package com.example.demo.controller.sales;

import com.example.demo.entity.Action;
import com.example.demo.service.sales.ActionService;
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
 * 営業活動(Action)に関するコントローラークラスです。
 */

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/action")
public class ActionController {
    private final ActionService actionService;

    /**
     * アクション一覧画面を表示します。
     * @param model
     * @return アクション一覧画面のテンプレート名
     */
    @GetMapping("/index")
    public String actions(Model model){
        List<Action> actions = actionService.getAll();
        model.addAttribute("actions",actions);

        return "sales/action/action_index";
    }

    @PostMapping("/create")
    public String create(@RequestParam("name") String name,
                         RedirectAttributes redirectAttributes) {
        String message;
        try {
            actionService.createAction(name);
            message = "アクションの作成に成功しました";
        } catch (RuntimeException e) {
            message = "アクションの作成に失敗しました";
            log.error("Failed to create action", e);
        }
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/action/index";
    }

    @PostMapping("/edit")
    public String edit(@RequestParam("actionId") Integer actionId,
                       @RequestParam("name") String name,
                       RedirectAttributes redirectAttributes) {
        String message;
        try {
            actionService.updateAction(actionId, name);
            message = "アクションの更新に成功しました";
        } catch (RuntimeException e) {
            message = "アクションの更新に失敗しました";
            log.error("Failed to update action", e);
        }
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/action/index";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("actionId") Integer actionId,
                         RedirectAttributes redirectAttributes) {
        String message;
        try {
            actionService.deleteAction(actionId);
            message = "アクションの削除に成功しました";
        } catch (RuntimeException e) {
            message = "アクションの削除に失敗しました";
            log.error("Failed to delete action", e);
        }
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/action/index";
    }
}
