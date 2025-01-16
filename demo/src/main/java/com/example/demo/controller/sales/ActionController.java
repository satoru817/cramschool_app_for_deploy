package com.example.demo.controller.sales;

import com.example.demo.dto.ActionHistoryPostDTO;
import com.example.demo.entity.Action;
import com.example.demo.entity.ActionHistory;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.service.sales.ActionHistoryService;
import com.example.demo.service.sales.ActionService;
import com.example.demo.service.sales.InquiryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    private final UserService userService;
    private final InquiryService inquiryService;
    private final ActionHistoryService actionHistoryService;

    @GetMapping("/actionHistory/delete/{inquiryId}/{actionHistoryId}")
    public String deleteActionHistory(@PathVariable("inquiryId")Integer inquiryId,
                                      @PathVariable("actionHistoryId")Integer actionHistoryId,
                                      RedirectAttributes redirectAttributes){
        boolean b = actionHistoryService.deleteById(actionHistoryId);

        String message;
        if(b){
            message = "アクション履歴データが正常に削除されました";
        }else{
            message = "アクション履歴データの削除に失敗しました。";
        }

        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/sales/inquiry/actions/"+inquiryId;
    }
    @GetMapping("/actionHistory/edit/{actionHistoryId}")
    public String editActionHistory(@PathVariable("actionHistoryId")Integer actionHistoryId,
                                    Model model){
        ActionHistory actionHistory = actionHistoryService.getById(actionHistoryId);
        List<Action> actions = actionService.findAll();
        List<User> users = userService.findAll();
        ActionHistoryPostDTO actionHistoryPostDTO = ActionHistoryPostDTO.builder()
                .actionHistoryId(actionHistoryId)
                        .userId(actionHistory.getUser().getUserId())
                                .actionId(actionHistory.getAction().getActionId())
                                        .inquiryId(actionHistory.getInquiry().getInquiryId())
                                                .actionDate(actionHistory.getActionDate())
                                                        .comment(actionHistory.getComment())
                                                                .build();

        model.addAttribute("actions",actions);
        model.addAttribute("users",users);
        model.addAttribute("actionHistoryPostDTO",actionHistoryPostDTO);

        return "sales/action_history/edit_one_of_them";
    }

    @PostMapping("/actionHistory/update/{actionHistoryId}")
    public String updateActionHistory(@PathVariable("actionHistoryId")Integer actionHistoryId,
                                      @ModelAttribute ActionHistoryPostDTO actionHistoryPostDTO,
                                      RedirectAttributes redirectAttributes){
        ActionHistory actionHistory = ActionHistory.builder()
                .actionHistoryId(actionHistoryPostDTO.getActionHistoryId())
                .actionDate(actionHistoryPostDTO.getActionDate())
                .action(actionService.getById(actionHistoryPostDTO.getActionId()))
                .inquiry(inquiryService.getById(actionHistoryPostDTO.getInquiryId()))
                .user(userService.getById(actionHistoryPostDTO.getUserId()))
                .comment(actionHistoryPostDTO.getComment())
                .build();

        boolean b = actionHistoryService.save(actionHistory);

        String message;
        if(b){
            message = "アクション履歴は正常に更新されました。";
        }else{
            message = "アクション履歴の更新に失敗しました。";
        }

        redirectAttributes.addFlashAttribute("message",message);

        return "redirect:/sales/inquiry/actions/"+actionHistoryPostDTO.getInquiryId();
    }


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

    @PostMapping("/actionHistory/create")
    public String createActionHistory(@ModelAttribute ActionHistoryPostDTO actionHistoryPostDTO,
                                      RedirectAttributes redirectAttributes){
        ActionHistory actionHistory = ActionHistory.builder()
                .action(actionService.getById(actionHistoryPostDTO.getActionId()))
                .actionDate(actionHistoryPostDTO.getActionDate())
                .user(userService.getById(actionHistoryPostDTO.getUserId()))
                .comment(actionHistoryPostDTO.getComment())
                .inquiry(inquiryService.getById(actionHistoryPostDTO.getInquiryId()))
                .build();

        boolean b = actionHistoryService.save(actionHistory);

        String message;

        if(b){
            message = "アクション履歴は正常に保存されました。";
        }else{
            message="アクション履歴の保存がされませんでした";
        }

        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/sales/inquiry/actions/"+actionHistoryPostDTO.getInquiryId();
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
