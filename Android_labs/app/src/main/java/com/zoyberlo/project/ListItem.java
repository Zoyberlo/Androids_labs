package com.zoyberlo.project;

public class ListItem {
    private CharSequence title;
    private CharSequence date;
    private CharSequence description;

    public ListItem(CharSequence title, CharSequence date, CharSequence description) {
        this.title = title;
        this.date = date;
        this.description = description;
    }

    public CharSequence getDescription() {
        return description;
    }

    public CharSequence getDate() {
        return date;
    }

    public CharSequence getTitle() {
        return title;
    }
}
