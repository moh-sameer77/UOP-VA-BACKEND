package com.flatdevs.virtualassistant.student.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reminder {
    private String title;
    private String note;
    private String firstTime;
    private String secondTime;
    private String date;
    private String color;
}
