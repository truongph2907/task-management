package com.example.task_management.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.task_management.DTO.CustomUserPrincipal;
import com.example.task_management.DTO.Task;
import com.example.task_management.annotation.CurrentUser;
import com.example.task_management.entity.TaskEntity;
import com.example.task_management.entity.UserEntity;
import com.example.task_management.repository.TaskRepository;
import com.example.task_management.repository.UserRepository;

import jakarta.validation.Valid;

@Controller
public class WebController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping({ "/", "/home" })
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(@CurrentUser CustomUserPrincipal principal, Model model) {

        UserEntity user = userRepository.findByUsernameOrEmail(principal.getUsername(), principal.getEmail());
        model.addAttribute("user", user);

        // load tasks of current users
        List<Task> tasks = user.getTasks().stream().map(entity -> entity.convert2Dto()).toList();
        model.addAttribute("tasks", tasks);

        // prepare for new task
        model.addAttribute("newTask", Task.builder().dueDate(OffsetDateTime.now()).build());

        return "dashboard";
    }

    @PostMapping("/dashboard/task")
    public String newTask(@CurrentUser CustomUserPrincipal principal,
            @Valid @ModelAttribute("newTask") Task newTask,
            BindingResult bindingResult,
            Model model) {

        UserEntity user = userRepository.findByUsernameOrEmail(principal.getUsername(), principal.getEmail());
        model.addAttribute("user", user);

        // load tasks of current users
        List<TaskEntity> tasks = user.getTasks();
        model.addAttribute("tasks", tasks);

        if (bindingResult.hasErrors()) {
            return "dashboard";
        }

        taskRepository.save(newTask.convertToEntity(user));
        return "redirect:/dashboard";
    }

    @PostMapping("/dashboard/task/update")
    public String updateTask(@CurrentUser CustomUserPrincipal principal, Model model,
            @RequestParam Long id,
            @RequestParam(defaultValue = "false") boolean isDone) {

        Optional<TaskEntity> result = taskRepository.findById(id);
        if (result.isPresent()) {
            TaskEntity taskEntity = result.get();
            taskEntity.setDone(isDone);
            taskRepository.save(taskEntity);
            return "redirect:/dashboard";
        } else {
            UserEntity user = userRepository.findByUsernameOrEmail(principal.getUsername(), principal.getEmail());
            model.addAttribute("user", user);
            // load tasks of current users
            List<TaskEntity> tasks = user.getTasks();
            model.addAttribute("tasks", tasks);

            return "dashboard";
        }
    }

    @PostMapping("/dashboard/task/delete/{id}")
    public String deleteTask(@CurrentUser CustomUserPrincipal principal, Model model, @PathVariable("id") Long id) {

        Optional<TaskEntity> task = taskRepository.findById(id);
        if (task.isPresent()) {
            TaskEntity taskEntity = task.get();
            taskRepository.delete(taskEntity);
            return "redirect:/dashboard";
        } else {
            UserEntity user = userRepository.findByUsernameOrEmail(principal.getUsername(), principal.getEmail());
            model.addAttribute("user", user);
            // load tasks of current users
            List<TaskEntity> tasks = user.getTasks();
            model.addAttribute("tasks", tasks);

            return "dashboard";
        }
    }

    @GetMapping("/admin/manage_user")
    public String admin() {
        return "manage_user";
    }
}
