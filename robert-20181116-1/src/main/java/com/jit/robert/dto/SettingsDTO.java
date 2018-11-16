package com.jit.robert.dto;

import lombok.Data;

@Data
public class SettingsDTO {
    private Integer id;
    private Integer pound_id;
    private Integer robert_id;
    private Float camera_depth;
    private Float sensor_depth;
    private Integer cruise_velocity;
    private Integer circle;
    private Integer return_velocity;
    private Integer bird_status;
    private String feed_name;
    private Float feed_weight;
    private Integer feed_velocity;
    private String medicine_name;
    private Float medicine_weight;
    private Integer medicine_velocity;
    private Integer type;
    private String category;
    private String pound_name;
    private String robert_name;
}
