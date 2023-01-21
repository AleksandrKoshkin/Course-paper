package ru.netology.graphics.image;

public class TextColor implements TextColorSchema {

    @Override
    public char convert(int color) {
        if(0<=color && color<=31){
            return '#';
        }else if (32<=color && color<=63){
            return '$';
        }else  if (64<=color && color<=95){
            return '@';
        }else if (96<=color && color<=127){
            return '%';
        }else  if (128<=color && color<=159){
            return '*';
        }else if (160<=color && color<=191){
            return '+';
        }else if (192<=color && color<=223){
            return '-';
        }else {
            return '\'';
        }
    }
}
