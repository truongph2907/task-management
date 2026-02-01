package com.example.task_management.DTO;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.task_management.entity.TaskEntity;
import com.example.task_management.entity.UserEntity;
import com.example.task_management.entity.TaskEntity.Priority;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    private Long id;

    @NotNull(message = "Task name is required.")
    @NotBlank(message = "Task name is required.")
    private String name;

    @NotNull(message = "Task description is required.")
    @NotBlank(message = "Task description is required.")
    private String description;

    @NotNull(message = "Due Date is required.")
    @Future(message = "Due Date must be in the future.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private OffsetDateTime dueDate;

    @Builder.Default
    private boolean isDone = false;

    @NotNull(message = "Priority is required.")
    private Priority priority;

    public TaskEntity convertToEntity(UserEntity owner) {
        return TaskEntity.builder()
                .id(id).name(name)
                .description(description)
                .priority(priority).dueDate(dueDate)
                .isDone(isDone).owner(owner)
                .build();
    }
}
