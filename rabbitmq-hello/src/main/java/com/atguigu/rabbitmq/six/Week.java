package com.atguigu.rabbitmq.six;

public enum Week {

    MONDAY("1"),TUESDAY("2"),WEDNESDAY("3");
    private final String day;
    Week(String day) {
        this.day=day;
    }
    public String getDay() {
        return day;
    }
}
